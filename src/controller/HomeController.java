package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
	@FXML
	private AnchorPane allElements;
	@FXML
	private Button showBtn;
	@FXML
	private VBox phanconlai;

	@FXML
	private VBox menu;

	@FXML Pane middlePane;

	@FXML
	private Pane mainPane;

	void createNewStage(String fxmlFilePath) throws IOException{
		Parent home = FXMLLoader.load(getClass().getResource(fxmlFilePath));
		Stage stage = new Stage();
		home.setFocusTraversable(true);
		stage.setScene(new Scene(home, 600, 400));

		stage.setResizable(false);
		stage.showAndWait();
	}
	private void changeMainPane(String fxmlfile) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
		Pane newContentPane = loader.load();
		// Replace the content of mainPane with the new content
		mainPane.getChildren().setAll(newContentPane);
	}

	public void setNhanKhau(ActionEvent event) throws IOException {
		changeMainPane("/views/NhanKhau_Lam.fxml");
	}

	public void setHoKhau(ActionEvent event) throws IOException {
		changeMainPane("/views/HoKhau.fxml");
	}

	public void setKhoanPhi(ActionEvent event) throws IOException {
		changeMainPane("/views/KhoanThu.fxml");
	}

	public void setDongPhi(ActionEvent event) throws IOException {
		changeMainPane("/views/NopTien.fxml");
	}

	public void setThongKe(ActionEvent event) throws IOException {
		changeMainPane("/views/ThongKe.fxml");
	}

	public void setTrangChu(ActionEvent event) throws IOException {
		changeMainPane("/views/Main.fxml");
	}

	public void anim(int x, int y) {
		KeyValue keyValue = new KeyValue(menu.translateXProperty(), x);
		KeyValue keyValue1 = new KeyValue(menu.translateXProperty(), y);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.35), keyValue);
		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.35), keyValue1);
		Timeline timeline = new Timeline(keyFrame, keyFrame1);
		timeline.play();
	}

	@FXML
	void menuShow() {
		anim(165, 0);
		middlePane.setDisable(false);
		phanconlai.setEffect(new BoxBlur(5, 5, 3));
	}

	void menuHide() {
		anim(0, 0);
		phanconlai.setEffect(null);
		middlePane.setDisable(true);
	}

	@FXML
	void onShowbtnClicked(MouseEvent event) {
		menuShow();
	}

	@FXML
	void middlePaneClicked(MouseEvent event) {
		menuHide();
	}

	@FXML
	void logobtnClicked(MouseEvent event) throws URISyntaxException {
		Desktop desktop = java.awt.Desktop.getDesktop();

		//specify the protocol along with the URL
		URI oURL = null;

		oURL = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUHcmlja3JsbA%3D%3D");
		try {
			desktop.browse(oURL);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	void backupClicked(ActionEvent event) throws IOException {
		createNewStage("/views/users/backupdata.fxml");
	}

	@FXML
	void restoreClicked(ActionEvent event) throws IOException {
		createNewStage("/views/users/restoredata.fxml");
	}

	@FXML
	void changepassClicked(ActionEvent event) throws IOException	 {
		createNewStage("/views/users/changepass.fxml");
	}

	@FXML
	void logoutClicked(ActionEvent event)  throws IOException{

		Parent home = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
		Scene scene = new Scene(home);

		Stage stage = (Stage)((MenuItem) event.getSource()).getParentPopup().getOwnerNode().getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
			Pane newContentPane = loader.load();
			// Replace the content of mainPane with the new content
			mainPane.getChildren().setAll(newContentPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
