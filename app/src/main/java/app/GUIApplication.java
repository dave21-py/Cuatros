package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class GUIApplication extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TitleScreen.fxml"));
        Parent root = loader.load();

        TitleScreen controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        var scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Cuatros"); // Title of main window
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}