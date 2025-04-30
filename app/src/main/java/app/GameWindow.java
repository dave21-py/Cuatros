package app;

import java.io.IOException;

import app.model.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWindow {

    private final AudioClip clickSound = new AudioClip(getClass().getResource("sound-[AudioTrimmer.com].mp3").toExternalForm());

    private void playClickSound(){
        clickSound.play();
    }

    private static final int CELL_SIZE = 25;
    private static final int GRID_ROWS = 20;
    private static final int GRID_COLS = 10;
    private static final int BORDER_THICKNESS = 1;

    private static final int DISPLAY_ROWS = GRID_ROWS + 2 * BORDER_THICKNESS; // 22
    private static final int DISPLAY_COLS = GRID_COLS + 2 * BORDER_THICKNESS; // 12

    private boolean isMuted;
    private Timeline timeline;
    private GameBoard board;

    @FXML
    private Button muteButton;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private Pane gameArea;

    @FXML
    private Pane nextPane;

    @FXML
    private Pane holdPane;

    @FXML
    private Pane leaderBoard;

    @FXML
    private Duration fallAnimation = Duration.millis(500);

    public void setFallAnimation(Duration animation) {
        this.fallAnimation = animation;
        if (board != null) {
            startAnimation();
        }
    }

    @FXML
    public void initialize() {
        board = new GameBoard();
        drawBoardBorders();
        renderBoard();
        startAnimation();

        Media sound = new Media(getClass().getResource("gamewindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        Media video = new Media(getClass().getResource("animation.mp4").toExternalForm());
        MediaPlayer bPlayer = new MediaPlayer(video);
        bPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bPlayer.setMute(true);
        bPlayer.play();
        mediaView.setMediaPlayer(bPlayer);

        mediaPlayer.setOnEndOfMedia(() -> {
            stopMedia();
        });

        board.scores.textProperty().bind(board.scoreNumber);
        board.scores.setFont(new Font("Arial", 30));
        board.scores.setLayoutY(40);
        board.scores.setLayoutX(80);
        board.scores.setTextFill(Color.WHITE);
        leaderBoard.getChildren().add(board.scores);
    }

    // begin game timeline cycle
    private void startAnimation() {

        if (timeline != null) { // Passes difficulty logic lol
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(fallAnimation, event -> {
            if (!board.checkGameOver()) {
                board.dropBlock(); // move block down one

                board.removeGameBlocks(); // check row to clear lines after lock
                renderBoard(); // re-render the entire board
                renderNextBlock(); // render next block
            } else {
                board.dropBlock();
                timeline.stop();
                showGameOver();
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // ensure gameArea can receive key pressed events
        gameArea.setFocusTraversable(true);
        gameArea.requestFocus();

        gameArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT -> {
                        board.moveBlockLeft();
                        renderBoard();
                    }
                    case RIGHT -> {
                        board.moveBlockRight();
                        renderBoard();
                    }
                    case DOWN -> {
                        board.dropBlock();
                        renderBoard();
                    }
                    case UP -> {
                        board.rotateBlock();
                        renderBoard();
                    }
                    case SPACE -> {
                        board.hardDrop();
                        board.removeGameBlocks();
                        renderBoard();
                    }
                    case C -> {
                        board.holdCurrentBlock();
                        renderBoard();
                        renderHoldBlock();
                        renderNextBlock();
                    }
                }
                gameArea.setOnMouseClicked(e -> gameArea.requestFocus());
                event.consume();
            }
        });
    }

    // alert window to end game, prompt user to play again
    private void showGameOver() {
        LeaderBoard.scoreFile();
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Game Over! You scored " + board.scoreNumber.get() + " points.");
            alert.setContentText("Play again?");

            ButtonType playAgainButton = new ButtonType("Play Again");
            ButtonType menuButton = new ButtonType("Back to Menu");
            ButtonType exitButton = new ButtonType("Exit");

            alert.getButtonTypes().setAll(playAgainButton, exitButton, menuButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == playAgainButton) {
                    restartGame();
                } else if (response == menuButton){
                    try {
                        Parent mainRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
                        Stage stage = (Stage) mediaView.getScene().getWindow();
                        stage.setScene(new Scene(mainRoot, 800, 600));
                        stage.setTitle("Cuatros");
                        stage.show();
                        stopMedia();
                        playClickSound();
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.exit();
                }
            });
        });
    }

    private void restartGame() {
        board = new GameBoard();
        renderBoard();
        startAnimation();
    }

    @FXML
    public void onPauseClicked(ActionEvent event) throws IOException {
        var alert = new Alert(Alert.AlertType.NONE, "GAME PAUSED");
        alert.setHeaderText(null);
        javafx.scene.control.ButtonType resumeButton = new javafx.scene.control.ButtonType("Resume");
        javafx.scene.control.ButtonType menuButton = new javafx.scene.control.ButtonType("Menu");
        alert.getButtonTypes().setAll(resumeButton, menuButton);

        if (timeline != null) { // Pause
            playClickSound();
            timeline.pause();
        }
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == resumeButton) {
                System.out.println("Game Resumed");
                if (timeline != null) { // Resume
                    playClickSound();
                    timeline.play();

                }
            } else if (buttonType == menuButton) {
                try {
                    Parent mainRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(mainRoot, 800, 600));
                    stage.setTitle("Cuatros");
                    stage.show();
                    playClickSound();
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), mainRoot);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(2);
                    fadeIn.play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stopMedia();
            }
        });
    }

    @FXML
    public void stopMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }

    // render the 20 x 10 game board to the gameArea
    private void renderBoard() {
        // clear any old squares not tagged with cell (border squares)
        gameArea.getChildren().removeIf(node -> node instanceof Rectangle && "cell".equals(node.getUserData()));
        board.setScore(board.score.get());

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

        // render the first active falling block
        Block block = board.getCurrentBlock();
        if (block != null) {
            for (Square square : block.getSquares()) {
                gameArea.getChildren().add(renderSquare(square));
            }
        }
    }

    // preview next block spawned in pane
    private void renderNextBlock() {
        nextPane.getChildren().clear();
        Block next = board.getNextBlock();
        if (next == null) {
            return;
        }
        for (Square square : next.getSquares()) {
            Rectangle rectangle = new Rectangle(CELL_SIZE - 10, CELL_SIZE - 10);
            rectangle.setX((square.getX() - 4) * (CELL_SIZE / 1.6) + 66); // 20, 19,
            rectangle.setY((square.getY() - 4) * (CELL_SIZE / 1.6) + 100);
            rectangle.setFill(addColor(square.getColorCode()));
            rectangle.setStroke(Color.BLACK);
            nextPane.getChildren().add(rectangle);
        }
    }

    // render hold block in pane
    private void renderHoldBlock() {
        holdPane.getChildren().clear();
        Block hold = board.getHoldBlock();
        if (hold == null) {
            return;
        }
        
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        
        // set max values for the pane
        for (Square square : hold.getSquares()) {
            minX = Math.min(minX, square.getX());
            minY = Math.min(minY, square.getY());
        }
    
        for (Square square : hold.getSquares()) {
            Rectangle rectangle = new Rectangle(CELL_SIZE - 10, CELL_SIZE - 10);
            rectangle.setX((square.getX() - minX) * (CELL_SIZE / 1.6) + 75);
            rectangle.setY((square.getY() - minY) * (CELL_SIZE / 1.6) + 40);
            rectangle.setFill(addColor(square.getColorCode()));
            rectangle.setStroke(Color.BLACK);
            holdPane.getChildren().add(rectangle);
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

    // render individual sqaures, "cell" refers to border
    private Rectangle renderSquare(Square square) {
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setX((square.getX() + BORDER_THICKNESS) * CELL_SIZE);
        rect.setY((square.getY() + BORDER_THICKNESS) * CELL_SIZE);
        rect.setFill(addColor(square.getColorCode()));
        rect.setStroke(Color.BLACK);
        rect.setUserData("cell");
        return rect;
    }

    // add color to individual squares using the code char of Block
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

    // Mute button
    @FXML
    void onMuteClicked() {
        playClickSound();
        if (mediaPlayer != null) {
            if (isMuted == false) {
                mediaPlayer.setMute(true);
                muteButton.setText("🔈");
                isMuted = true;

            } else {
                mediaPlayer.setMute(false);
                muteButton.setText("🔇");
                isMuted = false;
            }
        }
        
    }
}
