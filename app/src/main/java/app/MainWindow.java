package app;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainWindow {

    @FXML
    private ToggleGroup group;

    @FXML private VBox vbox;

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
    }

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
        ImageView imageView = new ImageView();
        Image image = new Image(getClass().getResource("title.png").toExternalForm());
        imageView.setImage(image);
        imageView.setFitHeight(300.0);
        imageView.setFitWidth(600.0);
        vbox.getChildren().add(0, imageView);
    }

    @FXML
    private void onAbout(ActionEvent event) throws IOException {
        
        var alert = new Alert(AlertType.INFORMATION, //
        """
        The game ends when the player's screen is filled to the top with no more space for another block to spawn. 
        Cuatros is a block puzzle game where players shift and rotate falling four-square shaped blocks to fill lines horizontally. 
        As the lines of blocks are filled, the lines will be cleared, awarding the player points for each line cleared. 
        As the game progresses, the blocks will fall at a higher rate and will require quicker decision making to survive. 
        The score is calculated by how many blocks have been placed and the number of lines that have been cleared.""" //
        //
        //
        );
        alert.setHeaderText(null);
        alert.show();
    }
}