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
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Difficulty {

    @FXML
    private static final Duration easyLevel = Duration.millis(1000);

    @FXML
    private static final Duration mediumLevel = Duration.millis(500);

    @FXML
    private static final Duration hardLevel = Duration.millis(200);



    @FXML
    private MediaView mediaView;

    @FXML
    private MediaPlayer mediaPlayer;

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
        public void onEasyClicked(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/GameWindow.fxml"));
            Parent gameRoot = loader.load();
            GameWindow controller = loader.getController();
            controller.setFallAnimation(easyLevel);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            stopMedia();
        }
    @FXML
        public void onMediumClicked(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/GameWindow.fxml"));
            Parent gameRoot = loader.load();
            GameWindow controller = loader.getController();
            controller.setFallAnimation(mediumLevel);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            stopMedia();
        }
    @FXML
        public void onHardClicked(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/GameWindow.fxml"));
            Parent gameRoot = loader.load();
            GameWindow controller = loader.getController();
            controller.setFallAnimation(hardLevel);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            stopMedia();
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

    