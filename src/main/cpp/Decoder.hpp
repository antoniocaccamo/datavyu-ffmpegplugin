#include "PacketQueue.hpp"
#include "FrameQueue.hpp"
#include <condition_variable>
#include <thread>

extern "C" {
	#include <libavcodec/avcodec.h> // codecs
    #include <libavutil/error.h> // error codes
    #include <libavutil/rational.h>
}

#ifndef DECODER_H_
#define DECODER_H_

// Note, I stripped out the sub title and replaced the SDL mutex by the std mutex
// and the SDL_Thread by the std::thread

class Decoder {
    private:
		AVPacket pkt;
		AVCodecContext *avctx; //  TODO: Change this to ref
		PacketQueue *queue; // TODO: Change this to ref
		std::condition_variable *empty_queue_cond; // TODO: Change this to ref
		int pkt_serial;
		int finished;
		int packet_pending;
		int decoder_reorder_pts;
		int64_t start_pts;
		AVRational start_pts_tb;
		int64_t next_pts;
		AVRational next_pts_tb;
		std::thread *decoder_tid;
    public:
        Decoder(AVCodecContext *avctx, PacketQueue *queue, std::condition_variable *empty_queue_cond) :
			avctx(avctx), 
			queue(queue), 
			empty_queue_cond(empty_queue_cond), 
			pkt_serial(-1), 
			finished(0), 
			packet_pending(0),
			decoder_reorder_pts(0),
			start_pts(AV_NOPTS_VALUE),
			start_pts_tb(av_make_q(0, 0)),
			next_pts(0), 
			next_pts_tb(av_make_q(0, 0)),
			decoder_tid(nullptr) {
			// Note, that pkt will need to be initialized for the case when decode_frame is never run
			// Sidenote: the move ref code will clean this initialization
			av_init_packet(&pkt);
		}

		virtual ~Decoder() {			
			av_packet_unref(&pkt);
			// TODO(fraudies): Clean-up design, move this de-allocation to the VideoState (where it is initialized)
			avcodec_free_context(&avctx);
		}

        int decode_frame(AVFrame *frame) {
            int ret = AVERROR(EAGAIN);

            for (;;) {
                AVPacket pkt;

                if (queue->get_serial() == this->pkt_serial) {
                    do {
                        if (queue->is_abort_request())
                            return -1;

                        switch (avctx->codec_type) {
                            case AVMEDIA_TYPE_VIDEO:
                                ret = avcodec_receive_frame(avctx, frame);
                                if (ret >= 0) {
                                    if (decoder_reorder_pts == -1) {
                                        frame->pts = frame->best_effort_timestamp;
                                    } else if (!decoder_reorder_pts) {
                                        frame->pts = frame->pkt_dts;
                                    }
                                }
                                break;
                            case AVMEDIA_TYPE_AUDIO:
                                ret = avcodec_receive_frame(avctx, frame);
                                if (ret >= 0) {
                                    AVRational tb = av_make_q(1, frame->sample_rate);
                                    if (frame->pts != AV_NOPTS_VALUE)
                                        frame->pts = av_rescale_q(frame->pts, avctx->pkt_timebase, tb);
                                    else if (next_pts != AV_NOPTS_VALUE)
                                        frame->pts = av_rescale_q(next_pts, next_pts_tb, tb);
                                    if (frame->pts != AV_NOPTS_VALUE) {
                                        next_pts = frame->pts + frame->nb_samples;
                                        next_pts_tb = tb;
                                    }
                                }
                                break;
                        }
                        if (ret == AVERROR_EOF) {
                            this->finished = this->pkt_serial;
                            avcodec_flush_buffers(avctx);
                            return 0;
                        }
                        if (ret >= 0)
                            return 1;
                    } while (ret != AVERROR(EAGAIN));
                }

                do {
                    if (queue->get_nb_packets() == 0)
                        empty_queue_cond->notify_all();

                    if (packet_pending) {
                        av_packet_move_ref(&pkt, &this->pkt);
                        packet_pending = 0;
                    } else {
						if (queue->get(&pkt, 1, &pkt_serial))
							return -1;
                    }
                } while (queue->get_serial() != pkt_serial);

                if (queue->is_flush_packet(pkt)) {
                    avcodec_flush_buffers(avctx);
                    finished = 0;
                    next_pts = start_pts;
                    next_pts_tb = start_pts_tb;
                } else {
                    if (avcodec_send_packet(avctx, &pkt) == AVERROR(EAGAIN)) {
                        // TODO: Improve logging
                        av_log(avctx, AV_LOG_ERROR,
                            "Receive_frame and send_packet both returned EAGAIN, which is an API violation.\n");
                        packet_pending = 1;
                        av_packet_move_ref(&this->pkt, &pkt);
                    }
                    av_packet_unref(&pkt);
                }
            }
        }

		// TODO(fraudies): This is tied to the audio/image/subtitle decode thread; 
		// all three use the decode thread method from above with the respective object
		// Re-design with lambda and tighter typing -- rather than passing the void pointers around
        int start(int (*fn)(void *), void *arg) {
			queue->start();
            decoder_tid = new std::thread(fn, arg);
            if (!decoder_tid) {
                av_log(NULL, AV_LOG_ERROR, "Can't create thread");
                return AVERROR(ENOMEM);
            }
            return 0;
        }

        void abort(FrameQueue *fq) {
			// TODO(fraudies): Cleanup this design by keeping frame queue and packet queue together
			queue->abort();
			fq->signal();
			// Take care of the case when we never called start
			if (decoder_tid) {
				decoder_tid->join();
				delete decoder_tid;
				decoder_tid = nullptr;
			}
			queue->flush();
        }
};

#endif DECODER_H_