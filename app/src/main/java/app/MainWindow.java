package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;


public class MainWindow {

    @FXML ToggleGroup group;

    @FXML
    void onGreetClicked(ActionEvent event) {
        String selected = ((ToggleButton)group.getSelectedToggle()).getText();
        
        displayAlert(selected + ", World!");
    }

    private void displayAlert(String text) {
        var alert = new Alert(AlertType.INFORMATION, text);
        alert.setHeaderText(null);
        alert.show();
    }
}