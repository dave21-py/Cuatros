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
            stage.setScene(new Scene(gameRoot));
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

    private void displayAlert(String text) {
        var alert = new Alert(AlertType.INFORMATION, text);
        alert.setHeaderText(null);
        alert.show();
    }
}