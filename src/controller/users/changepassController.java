package controller.users;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class changepassController {

    @FXML
    private PasswordField newpaass1;

    @FXML
    private PasswordField newpass2;

    @FXML
    private PasswordField oldpass;

    @FXML
    void comfirmClicked(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Tính năng đang phát triển!!!", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
