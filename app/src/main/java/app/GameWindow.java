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
        //Teris frame
        ImageView frameOneBlock = new ImageView();
        Image frameOneImage = new Image(getClass().getResource("/app/frame.png").toExternalForm());
        frameOneBlock.setImage(frameOneImage);
        frameOneBlock.setFitHeight(400);
        frameOneBlock.setFitWidth(20);
        frameOneBlock.setPreserveRatio(false);
        frameOneBlock.setX(0);
        frameOneBlock.setY(2);
        gameArea.getChildren().add(frameOneBlock);

        ImageView frameTwoBlock = new ImageView();
        Image frameTwoImage = new Image(getClass().getResource("/app/frame.png").toExternalForm());
        frameTwoBlock.setImage(frameTwoImage);
        frameTwoBlock.setFitHeight(400);
        frameTwoBlock.setFitWidth(20);
        frameTwoBlock.setPreserveRatio(false);
        frameTwoBlock.setX(0); //<---
        frameTwoBlock.setY(182);
        gameArea.getChildren().add(frameTwoBlock);

        ImageView frameThreeBlock = new ImageView();
        Image frameThreeImage = new Image(getClass().getResource("/app/frame3.png").toExternalForm());
        frameThreeBlock.setImage(frameThreeImage);
        frameThreeBlock.setFitHeight(19);
        frameThreeBlock.setFitWidth(314);
        frameThreeBlock.setPreserveRatio(false);
        frameThreeBlock.setX(0);
        frameThreeBlock.setY(0);
        gameArea.getChildren().add(frameThreeBlock);

        ImageView frameFourBlock = new ImageView();
        Image frameFourImage = new Image(getClass().getResource("/app/frame.png").toExternalForm());
        frameFourBlock.setImage(frameFourImage);
        frameFourBlock.setFitHeight(400);
        frameFourBlock.setFitWidth(20);
        frameFourBlock.setPreserveRatio(false);
        frameFourBlock.setX(294);
        frameFourBlock.setY(0);
        gameArea.getChildren().add(frameFourBlock);

        ImageView frameFiveBlock = new ImageView();
        Image frameFiveImage = new Image(getClass().getResource("/app/frame.png").toExternalForm());
        frameFiveBlock.setImage(frameFiveImage);
        frameFiveBlock.setFitHeight(400);
        frameFiveBlock.setFitWidth(20);
        frameFiveBlock.setPreserveRatio(false);
        frameFiveBlock.setX(294);
        frameFiveBlock.setY(182);
        gameArea.getChildren().add(frameFiveBlock);

        ImageView frameSixBlock = new ImageView();
        Image frameSixImage = new Image(getClass().getResource("/app/frame3.png").toExternalForm());
        frameSixBlock.setImage(frameSixImage);
        frameSixBlock.setFitHeight(19);
        frameSixBlock.setFitWidth(314);
        frameSixBlock.setPreserveRatio(false);
        frameSixBlock.setX(0);
        frameSixBlock.setY(563);
        gameArea.getChildren().add(frameSixBlock);

        





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
