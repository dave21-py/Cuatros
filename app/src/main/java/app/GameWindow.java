package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class GameWindow {

    @FXML
    private Pane gameArea;
    
    @FXML
    public void initialize() {
        Media sound = new Media(getClass().getResource("hope.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        //Teris block
        ImageView tetrisBlock = new ImageView();
        Image blockImage = new Image(getClass().getResource("/app/tetris_shape.png").toExternalForm());
        tetrisBlock.setImage(blockImage);
        tetrisBlock.setFitHeight(70);
        tetrisBlock.setFitWidth(70);
        tetrisBlock.setPreserveRatio(false);
        tetrisBlock.setX(135);
        tetrisBlock.setY(225);
        gameArea.getChildren().add(tetrisBlock);
    }

    @FXML 
    public void onPauseClicked(ActionEvent event) throws IOException{
        var alert = new Alert(Alert.AlertType.NONE, "GAME PAUSED");
        alert.setHeaderText(null);
        javafx.scene.control.ButtonType resumeButton   =  new javafx.scene.control.ButtonType("Resume");
        javafx.scene.control.ButtonType menuButton = new javafx.scene.control.ButtonType("Menu");
        alert.getButtonTypes().setAll(resumeButton, menuButton);
        alert.showAndWait().ifPresent(buttonType ->{
            if(buttonType == resumeButton){
                System.out.println("Game Resumed");
            } else if (buttonType == menuButton) {
                try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
                
            }
        });
    }

    void setImage() {
        
    }
}
