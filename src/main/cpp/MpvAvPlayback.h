#ifndef MPVSDLAVPLAYBACK_H_
#define MPVSDLAVPLAYBACK_H_

#include <Windows.h>
#include <atomic>
#include <cstdint>
#include <clocale>
#include <string.h>  
#include <MPV/client.h> // This include is just for the MPV_FORMAT usage
#include "FfmpegAVPlayback.h"

extern "C" {
	#include <SDL2/SDL.h>
	#include <SDL2/SDL_thread.h>
	#include <SDL2/SDL_syswm.h>
}

// MPV functtions signature
typedef intptr_t(*MpvCreate)();
typedef int(*MpvInitialize)(intptr_t);
typedef int(*MpvCommand)(intptr_t, const char**);
typedef int(*MpvCommandString)(intptr_t, const char*);
typedef int(*MpvTerminateDestroy)(intptr_t);
typedef int(*MpvSetOption)(intptr_t, const char *, int,void *);
typedef int(*MpvSetOptionString)(intptr_t, const char *, const char *);
typedef char*(*MpvGetPropertystring)(intptr_t, const char *);
typedef int(*MpvGetProperty)(intptr_t, const char *,int,void *);
typedef int(*MpvSetProperty)(intptr_t, const char *,int,void *);
typedef void(*MpvFree)(intptr_t);

/* The MpvAvPlayback class will the mpv-1.dll (must be copied 
* in the working directory) and will extract needed functions
* for the proper useage of the player.
* Note: No need to add the mpv-1.lib to the linker since 
* we are relying on the function poiter extracted for the dll 
* and the include of the client.h is just to use the type defined
* in the API; mpv_format for instance
* NOTE: THe mpv error codes schema:  >= 0 Succes, < 0 error
*/
// TODO(Reda) Much naming convention of the ffmpeg plugin and error returned
class MpvAvPlayback : public FfmpegAvPlayback {

private:
	HINSTANCE				_libMpvDll;
	intptr_t				_mpvHandle;

	//Function pointers to mpv api functions
	MpvCreate				_mpvCreate;
	MpvInitialize			_mpvInitialize;
	MpvCommand				_mpvCommand;
	MpvCommandString		_mpvCommandString;
	MpvTerminateDestroy		_mpvTerminateDestroy;
	MpvSetOption			_mpvSetOption;
	MpvSetOptionString		_mpvSetOptionString;
	MpvGetPropertystring	_mpvGetPropertyString;
	MpvGetProperty			_mpvGetProperty;
	MpvSetProperty			_mpvSetProperty;
	MpvFree					_mpvFree;
		
	void					LoadMpvDynamic();

	int						DoMpvCommand(const char **cmd);
	int						Pause();

	double					_streamDuration;
public:
	MpvAvPlayback();
	~MpvAvPlayback();

	int						Init(const char *filename, const long windowID);
	void					Destroy();
	void					init_and_event_loop(const char *filename);
	bool					IsPaused();
	int						Play();
	int						Stop();
	void					toggle_pause();
	int						SetRate(const double newRate);
	double					GetRate();
	double					GetDuration();
	int						StepBackward();
	int						StepForward();
	int						SetTime(double value);
	double					GetPresentationTime();
};

#endif MPVAVPLAYBACK_H_

