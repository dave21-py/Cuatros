package app;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindow {

    @FXML
    private Button muteButton;

    private boolean isMuted;

    private final AudioClip clickSound = new AudioClip(getClass().getResource("sound-[AudioTrimmer.com].mp3").toExternalForm())
;
    @FXML
    private ToggleGroup group;

    @FXML private VBox vbox;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    private void playClickSound(){
        clickSound.play();
    }



    @FXML
    void onPlayClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/Difficulty.fxml"));
            Parent loadingRoot = loader.load();
            Difficulty controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            controller.setPrimaryStage(stage);
            stage.setScene(new Scene(loadingRoot, 800, 600));
            stage.setTitle("Loading....");
            stage.show();
            
            // Media sound = new Media(getClass().getResource("sound.mp3").toExternalForm());
            // MediaPlayer clickPlayer = new MediaPlayer(sound);
            // clickPlayer.play();
            // clickPlayer.setOnEndOfMedia(() -> clickPlayer.dispose()) ;
            playClickSound();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), loadingRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopMedia();
    }

    @FXML
    public void initialize() {
        //Audio
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        Media video =  new Media(getClass().getResource("animation.mp4").toExternalForm());
        MediaPlayer bPlayer = new MediaPlayer(video);
        bPlayer.setCycleCount(MediaPlayer.INDEFINITE); 
        bPlayer.setMute(true);
        bPlayer.play();
        mediaView.setMediaPlayer(bPlayer);

        mediaPlayer.setOnEndOfMedia(() ->  {
            stopMedia();
    
        });

    }


    @FXML
    void onAboutClicked(ActionEvent event) {
        try {
            Parent aboutRoot = FXMLLoader.load(getClass().getResource("/app/About.fxml"));
            Scene aboutScene = new Scene(aboutRoot, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(aboutScene);
            stage.setTitle("About");
            stage.show();
            playClickSound();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), aboutRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopMedia();
    }

    @FXML
    void onSettingsClicked(ActionEvent event) {
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("/app/Settings.fxml"));
            Scene settingsScene = new Scene(settingsRoot, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(settingsScene);
            stage.setTitle("Settings");
            stage.show();
            playClickSound();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), settingsRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopMedia();
    }

    @FXML
    void onTutorialClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/Tutorial.fxml"));
            Parent tutorialRoot = loader.load();
            Tutorial tutorialController = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            tutorialController.setPrimaryStage(stage);
            Scene tutorialScene = new Scene(tutorialRoot, 800, 600);
            stage.setScene(tutorialScene);
            stage.setTitle("Welcome to CUATROS Tutorial");
            stage.show();
            playClickSound();

            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), tutorialRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopMedia();
    }

    @FXML
    void onLeaderBoardClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/Leaderboard.fxml"));
            Parent leaderBoardRoot = loader.load();
            LeaderBoard controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(leaderBoardRoot, 800, 600));
            stage.setTitle("LeaderBoards");
            stage.show();
            playClickSound();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), leaderBoardRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopMedia();
    }

    @FXML
    void onHelpClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/Help.fxml"));
            Parent helpRoot = loader.load();
            Help controller = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(helpRoot, 800, 600));
            stage.setTitle("Help");
            stage.show();
            playClickSound();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), helpRoot);
                fadeIn.setFromValue(0);   
                fadeIn.setToValue(2);
                fadeIn.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //Mute button
    @FXML
    void onMuteClicked(){
        playClickSound();
            
        if(mediaPlayer != null){
            if(isMuted == false){
                mediaPlayer.setMute(true);
                muteButton.setText("🔈");
                isMuted = true;

            }else{
            mediaPlayer.setMute(false);
            muteButton.setText("🔇");
            isMuted = false;
        }
        }
}

    

}

