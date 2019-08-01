package org.datavyu.plugins.examples;

import org.datavyu.plugins.MediaErrorListener;
import org.datavyu.plugins.MediaPlayer;
import org.datavyu.plugins.SdlKeyEventListener;
import org.datavyu.plugins.ffmpeg.FfmpegSdlMediaPlayer;

import java.io.File;
import java.net.URI;

public class SimpleSdlMediaPlayer {
  public static void main(String[] args) {
    // Define the media file
    URI mediaPath = new File("Nature_30fp.mp4").toURI();

    // Create the media player using the constructor with File
    MediaPlayer mediaPlayer = new FfmpegSdlMediaPlayer(mediaPath);

    mediaPlayer.addMediaErrorListener(
        new MediaErrorListener() {
          @Override
          public void onError(Object source, int errorCode, String message) {
            System.err.println(errorCode + ": " + message);
          }
        });

    mediaPlayer.addSdlKeyEventListener(
        new SdlKeyEventListener() {
          @Override
          public void onKeyEvent(Object source, long nativeMediaRef, int javaKeyCode) {
            System.out.println("SDL Media " + nativeMediaRef + " event " + javaKeyCode);
          }
        });

    // Initialize the player
    mediaPlayer.init();
    // Open a JFrame to control the media player through key commands
    new JMediaPlayerControlFrame(mediaPlayer);
  }
}
