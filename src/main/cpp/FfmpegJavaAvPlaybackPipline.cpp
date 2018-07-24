#include "FfmpegJavaAvPlaybackPipline.h"

uint32_t FfmpegJavaAvPlaybackPipline::Init(const char * input_file) {
	av_log_set_flags(AV_LOG_SKIP_REPEATED);
	av_log(NULL, AV_LOG_WARNING, "Init Network\n");
	AVInputFormat *file_iformat = nullptr;
	pJavaPlayback = new FfmpegJavaAvPlayback(input_file, file_iformat);

	// Assign the callback functions	
	pJavaPlayback->set_player_state_callback_func(TO_UNKNOWN, [this] {
		this->UpdatePlayerState(Unknown);
	});
	pJavaPlayback->set_player_state_callback_func(TO_READY, [this] {
		this->UpdatePlayerState(Ready);
	});
	pJavaPlayback->set_player_state_callback_func(TO_PLAYING, [this] {
		this->UpdatePlayerState(Playing);
	});
	pJavaPlayback->set_player_state_callback_func(TO_PAUSED, [this] {
		this->UpdatePlayerState(Paused);
	});
	pJavaPlayback->set_player_state_callback_func(TO_STOPPED, [this] {
		this->UpdatePlayerState(Stopped);
	});
	pJavaPlayback->set_player_state_callback_func(TO_STALLED, [this] {
		this->UpdatePlayerState(Stalled);
	});
	pJavaPlayback->set_player_state_callback_func(TO_FINISHED, [this] {
		this->UpdatePlayerState(Finished);
	});

	pJavaPlayback->init_and_start_display_loop();

	return ERROR_NONE;
}

void FfmpegJavaAvPlaybackPipline::Dispose() {
	//TODO(Reda): re-visit destroy function for the java playback
	//pJavaPlayback->destroy();
	delete pJavaPlayback;
	pJavaPlayback = nullptr;
}

FfmpegJavaAvPlaybackPipline::FfmpegJavaAvPlaybackPipline(CPipelineOptions * pOptions) 
	: CPipeline(pOptions), pJavaPlayback(nullptr) 
{ }

FfmpegJavaAvPlaybackPipline::~FfmpegJavaAvPlaybackPipline() {
	// Clean-up done in dispose that is called from the destructor of the super-class
}

uint32_t FfmpegJavaAvPlaybackPipline::Play() {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	pJavaPlayback->play();

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::Stop() {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	pJavaPlayback->stop();

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::Pause() {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	pJavaPlayback->toggle_pause();

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::Finish() {
	// TODO(fraudies): Stalling and finish need to be set from the video player
	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::Seek(double dSeekTime) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	double pos = pJavaPlayback->get_master_clock();
	if (isnan(pos))
		pos = (double)pJavaPlayback->get_seek_pos() / AV_TIME_BASE;
	double incr = dSeekTime - pos;
	if (pJavaPlayback->get_start_time() != AV_NOPTS_VALUE && dSeekTime < pJavaPlayback->get_start_time() / (double)AV_TIME_BASE)
		dSeekTime = pJavaPlayback->get_start_time() / (double)AV_TIME_BASE;

	pJavaPlayback->stream_seek((int64_t)(dSeekTime * AV_TIME_BASE), (int64_t)(incr * AV_TIME_BASE), 0);

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::GetDuration(double* pdDuration) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	*pdDuration = pJavaPlayback->get_duration();

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::GetStreamTime(double* pdStreamTime) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}

	*pdStreamTime = pJavaPlayback->get_master_clock();

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::SetRate(float fRate) {
	// TODO(fraudies): Implement this once ready
	// At the moment we don't have a way of setting this
	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetRate(float* pfRate) {
	// TODO(fraudies): Implement this once ready
	// At the moment we don't have a way of setting this
	return ERROR_NONE;
}

// Note this function is available only when streaming through SDL pipline
uint32_t FfmpegJavaAvPlaybackPipline::SetVolume(float fVolume) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this once ready
	//pSdlPlayback->update_volume(signbit(fVolume), fVolume * SDL_MIX_MAXVOLUME);
}

// Note this function is available only when streaming through SDL pipline
uint32_t FfmpegJavaAvPlaybackPipline::GetVolume(float* pfVolume) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this once ready
	//*pfVolume = pSdlPlayback->get_audio_volume() / (double)SDL_MIX_MAXVOLUME;

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::SetBalance(float fBalance) {
	// TODO(fraudies): Not sure how to wire this
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}

	pJavaPlayback->set_balance(fBalance);

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetBalance(float* pfBalance) {
	// TODO(fraudies): Not sure how to wire this
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}

	*pfBalance = pJavaPlayback->get_balance();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::SetAudioSyncDelay(long lMillis) {
	// TODO(fraudies): Implement this
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}

	pJavaPlayback->set_audioSyncDelay(lMillis);

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::GetAudioSyncDelay(long* plMillis) {
	// TODO(fraudies): Implement this
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}

	*plMillis = pJavaPlayback->get_audioSyncDelay();

	return ERROR_NONE; // no error
}

uint32_t FfmpegJavaAvPlaybackPipline::HasAudioData(bool* bAudioData) const {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this
	*bAudioData = pJavaPlayback->has_audio_data();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::HasImageData(bool* bImageData) const {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this
	*bImageData = pJavaPlayback->has_image_data();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetImageWidth(int* width) const {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this
	*width = pJavaPlayback->get_image_width();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetImageHeight(int* iHeight) const {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this
	*iHeight = pJavaPlayback->get_image_height();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetAudioFormat(AudioFormat* pAudioFormat) const {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement this
	*pAudioFormat = pJavaPlayback->get_audio_format();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetPixelFormat(PixelFormat* pPixelFormat) const {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement the pixel format for the ouput
	*pPixelFormat = pJavaPlayback->get_pixel_format();

	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetImageBuffer(uint8_t** ppImageBuffer) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement the image buffer data
	return ERROR_NONE;
}

uint32_t FfmpegJavaAvPlaybackPipline::GetAudioBuffer(uint8_t** ppAudioBuffer) {
	if (pJavaPlayback == nullptr) {
		return ERROR_PLAYER_NULL;
	}
	// TODO(fraudies): Implement the audio buffer data
	return ERROR_NONE;
}

void FfmpegJavaAvPlaybackPipline::UpdatePlayerState(PlayerState newState) {
	PlayerState newPlayerState = m_PlayerState;	// If we assign the same state again
	bool bSilent = false;

	switch (m_PlayerState)
	{
	case Unknown:
		if (Ready == newState)
		{
			newPlayerState = Ready;
		}
		break;

	case Ready:
		if (Playing == newState)
		{
			newPlayerState = Playing;
		}
		break;

	case Playing:
		if (Stalled == newState || Paused == newState || Stopped == newState || Finished == newState) {
			newPlayerState = newState;
		}
		break;

	case Paused:
		if (Stopped == newState || Playing == newState)
		{
			newPlayerState = newState;
		}
		break;

	case Stopped:
		if (Playing == newState || Paused == newState)
		{
			newPlayerState = newState;
		}
		break;

	case Stalled:
	{
		if (Stopped == newState || Paused == newState || Playing == newState) {
			newPlayerState = newState;
		}
		break;
	}

	case Finished:
		if (Playing == newState) {
			// We can go from Finished to Playing only when seek happens (or repeat)
			// This state change should be silent.
			newPlayerState = Playing;
			bSilent = true;
		}
		if (Stopped == newState) {
			newPlayerState = Stopped;
		}

		break;

	case Error:
		break;
	}

	// The same thread can acquire the same lock several times
	SetPlayerState(newPlayerState, bSilent);
}


void FfmpegJavaAvPlaybackPipline::SetPlayerState(PlayerState newPlayerState, bool bSilent) {

	// Determine if we need to send an event out
	bool updateState = newPlayerState != m_PlayerState;
	if (updateState)
	{
		if (NULL != m_pEventDispatcher && !bSilent)
		{
			m_PlayerState = newPlayerState;

			if (!m_pEventDispatcher->SendPlayerStateEvent(newPlayerState, 0))
			{
				m_pEventDispatcher->SendPlayerMediaErrorEvent(ERROR_JNI_SEND_PLAYER_STATE_EVENT);
			}
		}
		else
		{
			m_PlayerState = newPlayerState;
		}
	}

	if (updateState && newPlayerState == Stalled) { // Try to play
		Play();
	}
}



