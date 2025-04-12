package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


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
    public void onPauseClicked(ActionEvent event){
        
    }

    void setImage() {
        
    }
}
