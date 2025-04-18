package app;

import java.io.IOException;

import app.model.Block;
import app.model.Square;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class GameWindow {

    private static final int CELL_SIZE = 30;

    @FXML
    private Pane gameArea;
    private ImageView tetrisBlock;
    private Timeline timeline;

    @FXML
    public void initialize() {
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        // Teris block
        tetrisBlock = new ImageView();
        Image blockImage = new Image(getClass().getResource("/app/tetris_shape.png").toExternalForm());
        tetrisBlock.setImage(blockImage);
        tetrisBlock.setFitHeight(70);
        tetrisBlock.setFitWidth(70);
        tetrisBlock.setPreserveRatio(false);
        tetrisBlock.setX(135);
        tetrisBlock.setY(-70);
        gameArea.getChildren().add(tetrisBlock);
        startAnimation();
        // Teris frame
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
        frameTwoBlock.setX(0); // <---
        frameTwoBlock.setY(182);
        gameArea.getChildren().add(frameTwoBlock);

        ImageView frameThreeBlock = new ImageView();
        Image frameThreeImage = new Image(getClass().getResource("/app/frame3.png").toExternalForm());
        frameThreeBlock.setImage(frameThreeImage);
        frameThreeBlock.setFitHeight(19);
        frameThreeBlock.setFitWidth(314);
        frameThreeBlock.setPreserveRatio(false);
        frameThreeBlock.setX(0);
        frameThreeBlock.setY(-10);
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
        frameSixBlock.setY(573);
        gameArea.getChildren().add(frameSixBlock);

    }

    private void startAnimation() { // Animation for Falling down with 40ms timeframe
        final double yPosition = 514; // Position to stop
        timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> {
            tetrisBlock.setY(tetrisBlock.getY() + 5); // Moving 5pixels downn
            if (tetrisBlock.getY() >= yPosition) {
                tetrisBlock.setY(yPosition);
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void onPauseClicked(ActionEvent event) throws IOException {
        var alert = new Alert(Alert.AlertType.NONE, "GAME PAUSED");
        alert.setHeaderText(null);
        javafx.scene.control.ButtonType resumeButton = new javafx.scene.control.ButtonType("Resume");
        javafx.scene.control.ButtonType menuButton = new javafx.scene.control.ButtonType("Menu");
        alert.getButtonTypes().setAll(resumeButton, menuButton);

        if (timeline != null) { // Pause
            timeline.pause();
        }
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == resumeButton) {
                System.out.println("Game Resumed");
                if (timeline != null) { // Resume
                    timeline.play();
                }
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

    private Rectangle renderSquare(Square square) {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setX(square.getX() * CELL_SIZE);
        rect.setY(square.getY() * CELL_SIZE);
        rect.setFill(addColor(square.getColorCode()));
        rect.setStroke(Color.BLACK);
        return rect;
    }

    private Color addColor(char code) {
        return switch (code) {
            case 'Y' -> Color.YELLOW;
            case 'R' -> Color.RED;
            case 'G' -> Color.GREEN;
            case 'B' -> Color.BLUE;
            case 'O' -> Color.ORANGE;
            case 'C' -> Color.CYAN;
            case 'P' -> Color.PURPLE;
            default -> Color.GRAY;
        };
    }

    // Block block = board.getCurrentBlock();
    // for (Square sq : block.getSquares()) {
    // Rectangle r = renderSquare(sq);
    // gamePane.getChildren().add(r);
}
