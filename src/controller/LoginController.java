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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static application.Main.user;

public class LoginController implements Initializable {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
    Connection conn = null;

    public void Login(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (UserService.checkUser(username, password)) {
            user.setUsername(username);
            user.setPasswd(password);
            showMainMenu(event);
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Bạn nhập sai tài khoản hoặc mật khẩu rồi !", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        tfUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 6) {
                tfUsername.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            } else {
                tfUsername.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
            }
        });
    }

    void showMainMenu(ActionEvent event) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        Scene scene = new Scene(home);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void showNewStage(String fxmlFilePath) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource(fxmlFilePath));
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 600, 400));
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    void create_acc_clicked(MouseEvent event) throws IOException {
        showNewStage("/views/users/register.fxml");
    }

    @FXML
    void forgot_clicked(MouseEvent event) throws IOException {
        showNewStage("/views/users/forgetpass.fxml");
    }
}
