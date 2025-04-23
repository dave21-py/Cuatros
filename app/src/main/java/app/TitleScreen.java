package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.text.Text;


public class TitleScreen{

    @FXML
    private Text jdText;

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

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("title.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        
        // Video(480p)

        Media video = new Media(getClass().getResource("project.mp4").toExternalForm());
        videoPlayer = new MediaPlayer(video);
        mediaView.setMediaPlayer(videoPlayer);
        videoPlayer.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4),e ->{
            jdText.setVisible(true);
        }));
        timeline.play();

        Timeline hideTimeline = new Timeline(new KeyFrame(Duration.seconds(6), e -> {
            jdText.setVisible(false);
        }));
        hideTimeline.play();


        videoPlayer.setOnEndOfMedia(() -> {
            stopMedia();
            stopVideo();
        });
    }
        @FXML
        public void onSkipClicked(ActionEvent event) throws IOException {
            stopMedia();
            stopVideo();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/MainWindow.fxml"));
                Parent root = loader.load();
                Stage stage = this.primaryStage;
                stage.setScene(new Scene(root, 800, 600));
                stage.setTitle("Cuatros");
                stage.show();
                FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), root);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(1);
                fadeIn.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }