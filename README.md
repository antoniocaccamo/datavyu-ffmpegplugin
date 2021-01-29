# Datavyu Player
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
<sup>Windows:</sup> [![AppVeyor](https://ci.appveyor.com/api/projects/status/n59twat1udwdt7rw?svg=true)](https://ci.appveyor.com/project/TheoWolf/datavyu-ffmpegplugin)
<sup>Mac OS:</sup> [![Travis CI](https://travis-ci.com/databrary/datavyu-ffmpegplugin.svg)](https://travis-ci.com/databrary/datavyu-ffmpegplugin)

## Overview
The Datavyu Player is a Java Media Player using [FFmpeg](https://github.com/FFmpeg/FFmpeg) and [AVFoundation](https://developer.apple.com/av-foundation/) Players as backend engines that we interface too through Java Native Interface (JNI). It supports a wide variety of video file formats, audio and video codecs for Windows and Mac OS Platforms. Datavyu Player is used within [Datavyu](http://www.datavyu.org/); a video annotation tool but could be embedded in any Java application.

To learn how to use the plugin, please refer to the [Examples](##Examples) section below as well as the [Demo](demo/) projects. You may also find it useful to refer to the [wiki](https://github.com/databrary/datavyu-ffmpegplugin/wiki) pages to set up a development environment and contribute to the project.

What's special about this player?

1. It provides frame precision as much as possible with the engines.
1. It provides fast forward playback for both the images and sound, e.g. 0 ... +32x
1. It provides forward and backward frame stepping functionality.

What we are working on?

1. Faster FFmpeg Java player rendering.
1. Backward Playback from 0x to -32x.

## System Requirements

- An implementation of Java SE 8 or newer; [OpenJDK](http://openjdk.java.net/install/) or
[Oracle JDK](http://www.oracle.com/technetwork/java/javase/downloads/).
- Windows 7 or later.
- Mac OS X 10.6 or later.

## Downloads
The latest version of the Datavyu Player could be downloaded using the following Maven dependency (inside your pom.xml file):

``` xml  
    <dependency>
        <groupId>org.datavyu</groupId>
        <artifactId>ffmpeg-plugin</artifactId>
        <version>0.24</version>
    </dependency>
```

The Datavyu Player supports Mac OS and Windows platforms. Therefore, we provide a classifier for you respective platform.

For Windows:
``` xml  
    <dependency>
        <groupId>org.datavyu</groupId>
        <artifactId>ffmpeg-plugin</artifactId>
        <version>0.24</version>
        <classifier>win</classifier>
    </dependency>
```

For Mac OS:
``` xml  
    <dependency>
        <groupId>org.datavyu</groupId>
        <artifactId>ffmpeg-plugin</artifactId>
        <version>0.24</version>
        <classifier>mac</classifier>
    </dependency>
```
## Examples
With Datavyu Player you can launch and control multiple media player instances from your java application. Creating and instantiating a Media Player is a matter of passing a file path to the MediaPlayer interface.

### SDL Player
The SDL player is relying on FFmpeg engine as the Java player does, but is using [Simple DirectMedia Layer SDL2 Framework](https://www.libsdl.org/) to Display Images and Play Audio natively.

Here is a simple example on how to create and initialize an SDL Player, all what you have to do is to be creative and create your own Java controller for the player

``` java
    import org.datavyu.plugins.ffmpeg.*;

    import java.io.File;
    import java.net.URI;

    public class SimpleSdlMediaPlayer {
        public static void main(String[] args) {

    // Define the media file
    URI mediaPath = new File("Nature_30fps_1080p.mp4").toURI();

    // Create the media player using the constructor with File
    MediaPlayerWindow mediaPlayer = new FfmpegSdlMediaPlayer(mediaPath);

    // Handle Media Player errors
    mediaPlayer.addMediaErrorListener(
        (source, errorCode, message) -> System.out.println(errorCode + ": " + message));

    // Initialize the player
    mediaPlayer.init();

    // Open a simple JFrame to control the media player through key commands
    // Be creative and create your own controller
    JMediaPlayerControlFrame controller = new JMediaPlayerControlFrame(mediaPlayer);

    // Handle Window Key events
    mediaPlayerWindow.addSdlKeyEventListener(
            (source, nativeMediaRef, javaKeyCode) -> controller.handleKeyEvents(javaKeyCode));
        }
    }
```

A simple video controller example is used [here](src/main/java/org/datavyu/plugins/examples/JMediaPlayerControlFrame.java) to control media players through key binding, and a more sophisticated controller is provided in [Datavyu](https://github.com/databrary/datavyu/blob/master/src/main/java/org/datavyu/views/VideoController.java).

If you are interested in using Datavyu Player in a JavaFX Application, check [this](demo/SimpleJavaFXMediaPlayer.java) example. 

**Important**: Using FFmpegSdlMediaPlayer with JavaFX works only on Windows platforms (Mac OSX Fix in progress)

### AVFoundation Player
AVFoundation is a framework that provides media audiovisual services on Apple operating systems, the player provided via the ```libNativeOSXCanvas``` artifact, require an [AWT Canvas](https://docs.oracle.com/javase/7/docs/api/java/awt/Canvas.html) to attach to the [AVPlayer](https://developer.apple.com/documentation/avfoundation/avplayer). 

```$xml
    <dependency>
       <groupId>org.datavyu</groupId>
       <artifactId>libNativeOSXCanvas</artifactId>
       <version>0.92</version>
    </dependency>
```

Here is a simple example on how to create and initialize an AVFoundation Player, all what you have to do is to be creative and create your own Java controller for the player

``` java
    import org.datavyu.plugins.ffmpeg.*;

    import java.io.File;
    import java.net.URI;

    public class SimpleAVFoundationMediaPlayer {
        public static void main(String[] args) {
            // Define the media file, add your file path here !
            URI mediaPath = new File("PATH/TO/MOVIE/FILE").toURI();
          
            // Create the media player using the constructor with File
            MediaPlayer mediaPlayer = new AVFoundationMediaPlayer(mediaPath, new JDialog());

            // Initialize the player
            mediaPlayer.init();
        
            // Start Playing
            mediaPlayer.play();
        }
    }
```
Note that the AVFoundation player is only available on Mac OS platforms.

## Bug reports
Please use the [issue tracker](https://github.com/databrary/datavyu-ffmpegplugin/issues) provided by GitHub to send us bug reports or feature requests. Follow the template's instructions or the issue will likely be ignored or closed as invalid.

Using the bug tracker as place for simple questions is recommended.

## Contributing
Please refer to the [wiki](https://github.com/databrary/datavyu-ffmpegplugin/wiki) and read on how you could help us to
improve this tool.

You can check the wiki or the issue tracker for ideas on what you could contribute with.

## Authors
* Florian Raudies
* Reda Nezzar
* Jesse Lingeman

[<img src="http://datavyu.org/theme/img/logo/datavyu.png" width="100" height="25">](https://www.datavyu.org/)
[<img src="http://www.datavyu.org/theme/img/logo/databrary-nav.png" width="100" height="25">](https://www.databrary.org/)

[<img src="https://nyu.databrary.org/web/images/grants/nyu.jpg" width="200" height="50">](https://www.nyu.edu/)
[<img src="https://nyu.databrary.org/web/images/grants/pennstate.png" width="150" height="50">](http://www.psu.edu/)

[<img src="https://nyu.databrary.org/web/images/grants/nsf2.png" width="200" height="25">](http://www.nsf.gov/awardsearch/showAward?AWD_ID=1238599&HistoricalAwards=false)
[<img src="https://nyu.databrary.org/web/images/grants/nichd.png" width="200" height="25">](http://projectreporter.nih.gov/project_info_description.cfm?aid=8531595&icde=15908155&ddparam=&ddvalue=&ddsub=&cr=1&csb=default&cs=ASC)
