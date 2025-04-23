package app;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LoadingScreen{

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaPlayer videoPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        
        // Video(480p)

        Media video = new Media(getClass().getResource("loading.mp4").toExternalForm());
        videoPlayer = new MediaPlayer(video);
        mediaView.setMediaPlayer(videoPlayer);
        videoPlayer.setMute(true);
        videoPlayer.play();

        videoPlayer.setOnEndOfMedia(() -> {
            stopMedia();
            stopVideo();
        });
    }

        @FXML
        public void stopMedia(){
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null;
            }
            if(videoPlayer != null){
                videoPlayer.stop();
                videoPlayer.dispose();
                videoPlayer = null;
            }
        }

        @FXML
        public void stopVideo(){
            try {
                Parent gameWindowRoot = FXMLLoader.load(getClass().getResource("/app/GameWindow.fxml"));
                Scene gameWindowScene = new Scene(gameWindowRoot, 800, 600);
                gameWindowRoot.setOpacity(0);
                primaryStage.setScene(gameWindowScene);
                primaryStage.setTitle("Cuatros");
                primaryStage.show();
                FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), gameWindowRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }