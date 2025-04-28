package app;

import java.io.IOException;

import app.model.*;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameWindow implements BoardObserver {

    @FXML
    private Duration fallAnimation = Duration.millis(500);

    public void setFallAnimation(Duration animation){
        this.fallAnimation = animation;
        if(board != null){
            startAnimation();
        }
    }

    @FXML
    private Button muteButton;

    private boolean isMuted;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

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
    private Pane nextPane;

    @FXML
    public void initialize() {
        board = new GameBoard();
        drawBoardBorders();
        renderBoard();
        startAnimation();

        Media sound = new Media(getClass().getResource("gamewindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        Media video =  new Media(getClass().getResource("animation.mp4").toExternalForm());
        MediaPlayer bPlayer = new MediaPlayer(video);
        bPlayer.setCycleCount(MediaPlayer.INDEFINITE); 
        bPlayer.setMute(true);
        bPlayer.play();
        mediaView.setMediaPlayer(bPlayer);


        mediaPlayer.setOnEndOfMedia(() -> {
            stopMedia();
        });
    }

    // begin game timeline cycle
    private void startAnimation() {

        if(timeline != null){ //Passes difficulty logic lol
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(fallAnimation, event -> {
            board.dropBlock(); // move block down one
            renderBoard(); // re-render the entire board

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
                }
                gameArea.setOnMouseClicked(e -> gameArea.requestFocus());
                event.consume();
            }
        });
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
        board.removeGameBlocks();
        // If a row is full with squares, that row will be deleted.
        if (board.row.doubleValue() != -1) {
            gameArea.getChildren().removeIf(node -> board.row.doubleValue() != 0);
            for (Node node : gameArea.getChildren()) {
                if ((node).layoutYProperty().doubleValue() > board.row.doubleValue()) {
                    gameArea.getChildren().remove(node);
                }
            }
            for (Square[] sq : board.getGrid()) {
                for (Square s : sq) {
                    if (s != null) {
                        if (s.getY() > board.row.doubleValue()) {
                            gameArea.getChildren().add(renderSquare(s));
                        }
                    }
                }
            }
        }

        drawBoardBorders();

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
        for (Square square : block.getSquares()) {
            gameArea.getChildren().add(renderSquare(square));
        }
    }

    @Override
    public void onBoardChanged() {
        for (Node node : gameArea.getChildren()) {
            if (node.getLayoutY() == (board.row.doubleValue())) {
                gameArea.getChildren().remove(node);
            }
        }
        renderBoard();
        startAnimation();
    }

    private void renderNextBlock(){
        nextPane.getChildren().clear();
        Block next = board.getNextBlock();
        if(next == null){
            return;
        }

            for(Square square : next.getSquares()){
                Rectangle rectangle = new Rectangle(CELL_SIZE -10, CELL_SIZE-10);
                rectangle.setX((square.getX()-4) * (CELL_SIZE/ 1.6) + 66); // 20, 19, 
                rectangle.setY((square.getY()-4) * (CELL_SIZE / 1.6) + 100);
                rectangle.setFill(addColor(square.getColorCode()));
                rectangle.setStroke(Color.BLACK);
                nextPane.getChildren().add(rectangle);
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

    // Mute button
    @FXML
    void onMuteClicked() {
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
