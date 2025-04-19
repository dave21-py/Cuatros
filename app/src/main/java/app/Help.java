package app;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Help {

    @FXML
    private MediaPlayer mediaPlayer;

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

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

    