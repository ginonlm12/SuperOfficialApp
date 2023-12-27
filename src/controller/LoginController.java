package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	
	public void Login(ActionEvent event) throws IOException {

		String name = tfUsername.getText();
		String pass = tfPassword.getText();

		// check username and password
        if (!name.equals("") || !pass.equals("")) {
            Alert alert = new Alert(AlertType.WARNING, "Bạn nhập sai mật khẩu rồi hihi!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        Parent home = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(home);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
	}

    public void initialize(URL arg0, ResourceBundle arg1){
        tfUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 6) {
                tfUsername.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
            else{
                tfUsername.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
            }
        });
    }
}
