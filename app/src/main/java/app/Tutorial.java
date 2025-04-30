package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


public class Tutorial{

    private final AudioClip clickSound = new AudioClip(getClass().getResource("sound-[AudioTrimmer.com].mp3").toExternalForm());

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaPlayer videoPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    private void playClickSound(){
        clickSound.play();
    }

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        
        // Video(480p)

        Media video = new Media(getClass().getResource("tutorial.mp4").toExternalForm());
        videoPlayer = new MediaPlayer(video);
        mediaView.setMediaPlayer(videoPlayer);
        videoPlayer.play();

        videoPlayer.setOnEndOfMedia(() -> {
            stopMedia();
            stopVideo();
        });
    }
        @FXML
        public void onSkipClicked(ActionEvent event) throws IOException {
            stopMedia();
            stopVideo();
            playClickSound();
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
                Parent mainWindowRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
                Scene mainWindowScene = new Scene(mainWindowRoot, 800, 600);
                primaryStage.setScene(mainWindowScene);
                primaryStage.setTitle("Cuatros");
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }