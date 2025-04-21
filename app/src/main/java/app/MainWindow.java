package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MainWindow {

    @FXML
    private Button muteButton;

    private boolean isMuted;

    @FXML
    private ToggleGroup group;

    @FXML private VBox vbox;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    void onPlayClicked(ActionEvent event) {
        try {
            Parent gameRoot = FXMLLoader.load(getClass().getResource("/app/GameWindow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot, 800, 600));
            stage.setTitle("GameWindow");
            stage.show();
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
        if(mediaPlayer != null){
            if(isMuted == false){
                mediaPlayer.setMute(true);
                muteButton.setText("Unmute 🔈");
                isMuted = true;

            }else{
            mediaPlayer.setMute(false);
            muteButton.setText("Mute 🔇");
            isMuted = false;
        }
    }
}
}

