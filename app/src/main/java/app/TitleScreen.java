package app;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TitleScreen{
    @FXML
    private MediaView mediaView;

    @FXML
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void initialize(){
        // Audio
        Media sound = new Media(getClass().getResource("title.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        // Video(480p)

        Media video = new Media(getClass().getResource("title.mp4").toExternalForm());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        mediaView.setMediaPlayer(videoPlayer);
        videoPlayer.play();
        videoPlayer.setOnEndOfMedia(() -> {
            try{
                Parent mainWindowRoot = FXMLLoader.load(getClass().getResource("/app/MainWindow.fxml"));
                primaryStage.setScene(new Scene(mainWindowRoot, 800, 600));
                primaryStage.setTitle("Cuatros");
            } catch(Exception e){
                e.printStackTrace();

            }
        });

    }
}