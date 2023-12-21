package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static services.MysqlConnection.getMysqlConnection;

public class LoginController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	Connection conn = null;

	public void Login(ActionEvent event) throws IOException,SQLException, ClassNotFoundException {
		String username = tfUsername.getText();
		String password = tfPassword.getText();
		if (conn == null) conn = getMysqlConnection();

		// SQL query to check credentials
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);

		// Execute the query
		ResultSet resultSet = preparedStatement.executeQuery();
		boolean have_first_row = resultSet.next();

		if (have_first_row) {
			showMainMenu(event);
			conn.close();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "Bạn nhập sai tài khoản hoặc mật khẩu rồi !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}

	}

	void showMainMenu(ActionEvent event) throws IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
		Scene scene = new Scene(home);

		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
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
	void create_acc_clicked(MouseEvent event) throws IOException{
		showNewStage("/views/users/register.fxml");
	}

	@FXML
	void forgot_clicked(MouseEvent event) throws IOException{
		showNewStage("/views/users/forgetpass.fxml");
	}
}
