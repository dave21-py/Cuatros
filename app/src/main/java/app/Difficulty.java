package app;


import java.io.IOException;

import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

public class Difficulty {

    private final AudioClip clickSound = new AudioClip(getClass().getResource("sound-[AudioTrimmer.com].mp3").toExternalForm());

    private void playClickSound(){
        clickSound.play();
    }

    @FXML
    private static final Duration easyLevel = Duration.millis(1000);

    @FXML
    private static final Duration mediumLevel = Duration.millis(500);

    @FXML
    private static final Duration hardLevel = Duration.millis(200);

    public static Duration baseFallSpeed = Duration.millis(500); 
    public static double scoreMultiplier = 1.0;

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
            Difficulty.baseFallSpeed = Duration.millis(1000);
            Difficulty.scoreMultiplier = 1.0;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/LoadingScreen.fxml"));
            Parent loadingRoot = loader.load();
            LoadingScreen controller = loader.getController();
            controller.setFallAnimation(Duration.millis(1000));   
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(stage);
            stage.setScene(new Scene(loadingRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            playClickSound();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), loadingRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();

            stopMedia();
        }
    @FXML
        public void onMediumClicked(ActionEvent event) throws IOException {
            Difficulty.baseFallSpeed = Duration.millis(500);
            Difficulty.scoreMultiplier = 1.5;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/LoadingScreen.fxml"));
            Parent loadingRoot = loader.load();
            LoadingScreen controller = loader.getController();
            controller.setFallAnimation(Duration.millis(500));    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(stage);
            stage.setScene(new Scene(loadingRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            playClickSound();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), loadingRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
            stopMedia();
        }
    @FXML
        public void onHardClicked(ActionEvent event) throws IOException {
            Difficulty.scoreMultiplier = 2.0;
            Difficulty.baseFallSpeed = Duration.millis(200);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/LoadingScreen.fxml"));
            Parent loadingRoot = loader.load();
            LoadingScreen controller = loader.getController();
            controller.setFallAnimation(Duration.millis(200));    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(stage);
            stage.setScene(new Scene(loadingRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            playClickSound();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), loadingRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
            stopMedia();
        }
    @FXML
        public void onBackClicked(ActionEvent event) throws IOException {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            playClickSound();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), mainRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
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

    