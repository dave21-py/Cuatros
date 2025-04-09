package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUIApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
      
        var resource = getClass().getResource("MainWindow.fxml");
        var loader = new FXMLLoader(resource);
        var scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Cuatros"); // Title of main window
        stage.show();
    }

}