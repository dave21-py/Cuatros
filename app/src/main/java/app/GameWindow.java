package app;

import java.io.IOException;

import app.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWindow {

    @FXML
    private MediaPlayer mediaPlayer;

    private static final int CELL_SIZE = 25;
    private static final int GRID_ROWS = 20;
    private static final int GRID_COLS = 10;
    private static final int BORDER_THICKNESS = 1;

    private static final int DISPLAY_ROWS = GRID_ROWS + 2 * BORDER_THICKNESS; // 22
    private static final int DISPLAY_COLS = GRID_COLS + 2 * BORDER_THICKNESS; // 12

    private GameBoard board;

    @FXML
    private Pane gameArea;
    private Timeline timeline;

    @FXML
    public void initialize() {
        board = new GameBoard();
        renderBoard();
        startAnimation();
        drawBoardBorders();

        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(()-> {
            stopMedia();
        });
    }

    private void startAnimation() {
        timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            board.dropBlock(); // move the block down
            renderBoard(); // re-render the entire board
            // TODO: add collision logic later
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
                stopMedia();

            }
        });
    
    }

    @FXML
    public void stopMedia(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    // render the 20 x 10 game board to the gameArea
    private void renderBoard() {
        // clear any old squares not tagged with cell (border squares)
        gameArea.getChildren().removeIf(node -> node instanceof Rectangle && "cell".equals(node.getUserData()));

        // render squares on the board
        Square[][] grid = board.getGrid();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                Square square = grid[row][col];
                if (square != null) {
                    gameArea.getChildren().add(renderSquare(square));
                }
            }
        }

        // ender the first active falling block
        Block block = board.getCurrentBlock();
        for (Square square : block.getSquares()) {
            gameArea.getChildren().add(renderSquare(square));
        }
    }

    // render the borders of the board using Rectangle objects
    private void drawBoardBorders() {
        for (int row = 0; row < DISPLAY_ROWS; row++) {
            for (int col = 0; col < DISPLAY_COLS; col++) {
                if (row == 0 || row == DISPLAY_ROWS - 1 || col == 0 || col == DISPLAY_COLS - 1) {
                    Rectangle border = new Rectangle(CELL_SIZE, CELL_SIZE);
                    border.setX(col * CELL_SIZE);
                    border.setY(row * CELL_SIZE);
                    border.setFill(Color.GRAY);
                    border.setStroke(Color.DARKGRAY);
                    gameArea.getChildren().add(border);
                }
            }
        }
    }
    

    private Rectangle renderSquare(Square square) {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setX((square.getX() + BORDER_THICKNESS) * CELL_SIZE);
        rect.setY((square.getY() + BORDER_THICKNESS) * CELL_SIZE);
        rect.setFill(addColor(square.getColorCode()));
        rect.setStroke(Color.BLACK);
        rect.setUserData("cell");
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
}
