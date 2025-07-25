package app;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class Help {

    @FXML
    private MediaView mediaView;

    @FXML
    private MediaPlayer mediaPlayer;

    private final AudioClip clickSound = new AudioClip(getClass().getResource("sound-[AudioTrimmer.com].mp3").toExternalForm());

    private void playClickSound(){
        clickSound.play();
    }

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        Media video =  new Media(getClass().getResource("animation.mp4").toExternalForm());
        MediaPlayer bPlayer = new MediaPlayer(video);
        bPlayer.setCycleCount(MediaPlayer.INDEFINITE); 
        bPlayer.setMute(true);
        bPlayer.play();
        mediaView.setMediaPlayer(bPlayer);

        mediaPlayer.setOnEndOfMedia(()-> {
            stopMedia();
        });
    }
    @FXML
        public void onBackClicked(ActionEvent event) throws IOException {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            stopMedia();
            playClickSound();
        }

        @FXML
        public void stopMedia(){
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null;
            }
        }

    }

    