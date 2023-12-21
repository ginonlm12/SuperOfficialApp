package controller.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class registerController {

    @FXML
    void confirmClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tính năng đang phát triển!!!", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
