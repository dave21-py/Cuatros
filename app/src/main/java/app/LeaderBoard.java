package app;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeaderBoard{

    @FXML
    private MediaView mediaView;

    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private VBox vbox;

    private final AudioClip clickSound = new AudioClip(getClass().getResource("sound-[AudioTrimmer.com].mp3").toExternalForm());

    private void playClickSound(){
        clickSound.play();
    }

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("mainwindow.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        Media video =  new Media(getClass().getResource("animation.mp4").toExternalForm());
        MediaPlayer bPlayer = new MediaPlayer(video);
        bPlayer.setCycleCount(MediaPlayer.INDEFINITE); 
        bPlayer.setMute(true);
        bPlayer.play();
        mediaView.setMediaPlayer(bPlayer);



        mediaPlayer.setOnEndOfMedia(()-> {
            stopMedia();
        });

        loadLeaderboard();

    }
    @FXML
        public void onBackClicked(ActionEvent event) throws IOException {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainRoot, 800, 600));
            stage.setTitle("Cuatros");
            stage.show();
            stopMedia();
            playClickSound();
        }

        @FXML
        public void stopMedia(){
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null;
            }
        }

        private void loadLeaderboard() {
            List<ScoreEntry> entries = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("LeaderBoard.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String initials = parts[0].trim();
                        int score = Integer.parseInt(parts[1].trim());
                        entries.add(new ScoreEntry(initials, score));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        
            // sort descending by score
            entries.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        
            // show top 10
            int rank = 1;
            for (ScoreEntry entry : entries.subList(0, Math.min(3, entries.size()))) {
                Label scoreLabel = new Label(rank + ". " + entry.getInitials() + " - " + entry.getScore());
                scoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-family: 'Courier New';");
                vbox.getChildren().add(scoreLabel);
                rank++;
            }
        }
    }