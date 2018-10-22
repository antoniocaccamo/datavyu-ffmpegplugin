# Datavyu ffmpegplugin

## Overview
The Datavyu ffmpegplugin is a Media Player based on [FFmpeg libraries](https://github.com/FFmpeg/FFmpeg) 
and [MPV Player](https://github.com/mpv-player/mpv). It supports a 
wide variety of video file formats, audio and video codecs for Windows Platform. Datavyu ffmpegplugin is primarily 
used with [Datavyu](http://www.datavyu.org/) coding tool.

This repository holds the native c++/java code that composes the ffmpeg plugin. It is structured like so:
    
   1. Java Code: Provides a Java wrapper for the native media players, this part is relying on 
   Java Native Interface (JNI) to control the player produced by the Visual Studio Solution.
   1. Cpp code: Where the Visual Studio Solution lives, the later is composed of 5 projects
        1. FFmpegJavaMediaPlayer: Produce the Java Media player dll with a Java bridge.
        1. FFmpegSdlMediaPlayer: Produce the SDL Media player dll with a Java bridge.
        1. MpvMediaPlayer: Produce the MPV Media player dll with a Java bridge.
        1. MediaPlayer: A Basic SDL and MPV player used for debugging and testing purposes.
        1. MediaPlayerTest: Unit Test for the VS Solution.
        1. VideoState: A shared project referenced in the projects listed above. 
            
To compile the dll's for these projects follow the directions under "Compiling Native Code".

Note: Tee Java Media Player is the only one that provides access to video and audio buffers
to be consumed in the Java Side. 

## JNI bridge
The design and development of this bridge follows the JavaFx project 
closely. The javafx for project is [here](http://hg.openjdk.java.net/openjfx/jfx/rt):

To compile the wrapper classes for the JNI bridge, use the following commands from the 
root directory of the Datavyu ffmpegplugin project:

    javah -d src/main/cpp -classpath src/main/java org.datavyu.plugins.ffmpeg.NativeMediaPlayer
    javah -d src/main/cpp -classpath src/main/java org.datavyu.plugins.ffmpeg.FfmpegSdlMediaPlayer
    javah -d src/main/cpp -classpath src/main/java org.datavyu.plugins.ffmpeg.FfmpegJavaMediaPlayer
    javah -d src/main/cpp -classpath src/main/java org.datavyu.plugins.ffmpeg.MpvMediaPlayer

Note, from `NativeMediaPlayer.class` we only use the produced stub `org_datavyu_plugins_ffmpeg_NativeMediaPlayer.h` to
get the state codes.


## Native Code
The native code interfaces to the c API from ffmpeg using JNI.

## Compiling Native Code (Windows) in Visual Studio
1. Download and install Microsoft Visual Studio Community Edition [link](https://visualstudio.microsoft.com/vs/community/)
1. Download the "Dev" 64-bit version of FFmpeg [link](https://ffmpeg.zeranoe.com/builds/)
1. Download and install the Java 8 JDK from [Oracle Website](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
1. Download the SDL2 development libraries for Visual C++ 32/64-bit [link](https://www.libsdl.org/download-2.0.php) and unzip them to a directory (we'll use `C:\SDL2`)
1. Go to the `include` folder in `C:\SDL2`. Create a new directory called `SDL2` inside of the `include` folder and then 
    move all of the files in the `include` folder into the `SDL2` folder (this is for compatibility due to SDL packaging the Visual Studio distribution differently than the rest of their distributions).
1. Download the "Dev" version of MPV from [link](https://mpv.srsfckn.biz/) 
1. Unzip the Dev MPV version to a directory (we will use `C:\MPV` as an example) and copy
    the `include` folder for the downloaded zip into `C:\MPV`
1. Unzip the Dev version of ffmpeg to a directory (we will use `C:\FFmpeg-dev` as an example)
1. Clone this repository to a directory of your choosing.
1. In Visual Studio, open MediaPlayer.sln using `File -> Open -> Project/Solution` and navigating to `src\main\cpp` in the folder where you cloned this repository.
1. Once the solution is open, we have to tell Visual Studio where to find the FFmpeg, Java, and SDL headers and libraries.
	1. Click on "Solution Explorer" in the bottom of the left pane.
	1. Right click the `FfmpegMediaPlayer` project under `Solution 'MediaPlayer'` and click `Properties`.
	1. In the left pane under `Configuration Properties` click `VC++ Directories`.
		1. Add a directory to `Include Directories` that points to `C:\FFmpeg-dev\include`.
		1. Add a directory to `Include Directories` that points to `C:\SDL2\include` (not the SDL2 folder inside of `include`).
		1. Add a directory to `Include Directories` that points to `C:\MPV\include`.
		1. Add a directory to `Include Directories` that points to `C:\Program Files\Java\jdk1.8.0_YOURVERSION\include`
		1. Add a directory to `Include Directories` that points to `C:\Program Files\Java\jdk1.8.0_YOURVERSION\include\win32`
		1. Add a directory to `Library Directories` that points to `C:\FFmpeg-dev\lib`.
		1. Add a directory to `Library Directories` that points to `C:\Program Files\Java\jdk1.8.0_YOURVERSION\lib`.
		1. Add a directory to `Library Directories` that points to `C:\SDL2\lib\x64`.
1. Right click on the `MediaPlayer` solution and hit build. The build should be a success and create new DLL's in your root directory of the Datavyu ffmpegplugin project.

### Code formatting
We follow google's style guide. You can use the following command line to format the code
```
    clang-format -style=Google -i *.cc *.h
```
from the path that the files are.

On windows you can install the clang compiler from here
https://llvm.org/builds/
or the extension for Visual Studio from here
https://marketplace.visualstudio.com/items?itemName=LLVMExtensions.ClangFormat
If you plan to use the extension use CTRL + R & D to all code in one file.

### Testing Native Code
To test the native code we use Google's C++ test framework [link](https://github.com/google/googletest).

All Unit tests are located in the MediaPlayerTest project, the tests run automatically when building the 
Visual Studio solution (MediaPlayer.sln)

We strongly recommend building the VS solution instead of by project in order to test changes made to the different players. 

## Java Code
The java code provides [examples](src/main/java/org/datavyu/plugins/ffmpeg/examples) on how to interface with the 
movie stream interface; especially on how to playback image frames and audio frames 
through separate threads.
    
## Deployment
To build process we use maven. Dependencies are described in a pom file. The package is build with

    mvn package
    
and deployed with 

    mvn deploy
    
Emergency deployment (not recommended): If tests are broken you can exclude them from the deployment
through the command

    mvn -Dmaven.test.skip=true deploy

## Authors
* Florian Raudies
* Reda Nezzar
* Jesse Lingeman