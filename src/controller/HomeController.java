package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
	@FXML
	private BorderPane borderPane;
	@FXML
	private HBox hBox;

	@FXML
	private GridPane gridPane;
	@FXML
	private ImageView showBtn;
	@FXML Pane middlePane;

	private int check=0;

	public void setNhanKhau(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/NhanKhau_Lam.fxml"));
		Pane nhankhauPane = loader.load();
		borderPane.setCenter(nhankhauPane);
	}

	public void setHoKhau(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/HoKhau.fxml"));
//		Stage stage = new Stage();
//		stage.setResizable(true);

		Pane hokhauPane = loader.load();
		borderPane.setCenter(hokhauPane);
	}

	public void setKhoanPhi(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/KhoanThu.fxml"));
		Pane khoanphiPane = loader.load();
		borderPane.setCenter(khoanphiPane);
	}

	public void setDongPhi(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NopTien.fxml"));
		Pane dongphiPane = loader.load();
		borderPane.setCenter(dongphiPane);
	}

	public void setThongKe(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/ThongKe.fxml"));
		Pane thongkePane = loader.load();
		borderPane.setCenter(thongkePane);
	}

	public void setTrangChu(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/Main.fxml"));
		Pane trangchuPane = loader.load();
		borderPane.setCenter(trangchuPane);
	}

	public void anim(int x, int y) {
		KeyValue keyValue = new KeyValue(gridPane.translateXProperty(), x);
		KeyValue keyValue1 = new KeyValue(gridPane.translateXProperty(), y);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.35), keyValue);
		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.35), keyValue1);
		Timeline timeline = new Timeline(keyFrame, keyFrame1);
		timeline.play();
	}

	void menuShow() {
		anim(165, 0);
		borderPane.setEffect(new BoxBlur(5, 5, 3));
		middlePane.setDisable(false);
	}

	void menuHide() {
		anim(0, 0);
		borderPane.setEffect(null);
		middlePane.setDisable(true);
	}
	@FXML
	void showbutonClicked(MouseEvent event) {
//		System.out.println("button show clicked");
		if (check == 0) {
			check = 1;
			menuShow();
		} else {
			check = 0;
			menuHide();
		}
	}

	@FXML
	void middlePaneClicked(MouseEvent event) {
		if (check == 1) {
			check = 0;
			menuHide();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Pane login = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
			borderPane.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
