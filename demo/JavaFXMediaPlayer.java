import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.datavyu.plugins.MediaPlayerWindow;
import org.datavyu.plugins.ffmpeg.FfmpegSdlMediaPlayer;

import java.io.File;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaFXMediaPlayer extends Application {
    private static Logger logger = LogManager.getLogger(JavaFXMediaPlayer.class);
    private JMediaPlayerControlFrame controller;
    private MediaPlayerWindow mediaPlayer;
    private URI mediaPath;

    @Override
    public void start(Stage primaryStage) {
        mediaPath = new File("Nature_30fps_1080p.mp4").toURI();

        MediaPlayerTask mediaPlayerTask = new MediaPlayerTask(mediaPath);

        mediaPlayerTask.setOnSucceeded(
                event -> {
                    mediaPlayer = mediaPlayerTask.getValue();

                    mediaPlayer.addMediaErrorListener(
                            // Handle error thrown by The Media Player
                            (source, errorCode, message) -> logger.error(errorCode + ": " + message));

                    // Handle Window Key events triggered from SDL window
                    mediaPlayer.addSdlKeyEventListener(
                            (source, nativeMediaRef, javaKeyCode) ->
                                    controller.handleKeyEvents(javaKeyCode));
                    // Open a simple JFrame to control the media player through key commands
                    // Be creative and create your own controller in JavaFX
                    Platform.runLater(
                            () -> {
                                controller = new JMediaPlayerControlFrame(mediaPlayer);
                            });
                });

        mediaPlayerTask.setOnFailed(event -> Platform.exit());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(mediaPlayerTask);
        executorService.shutdown();
    }
}

class MediaPlayerTask extends Task<MediaPlayerWindow> {
    private URI mediaPath;

    MediaPlayerTask(URI mediaPath) {
        this.mediaPath = mediaPath;
    }

    @Override
    protected MediaPlayerWindow call() throws Exception {
        MediaPlayerWindow mediaPlayer = new FfmpegSdlMediaPlayer(mediaPath);
        mediaPlayer.init();
        return mediaPlayer;
    }
}