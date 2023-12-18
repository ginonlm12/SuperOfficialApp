package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	private VBox vBox;
	@FXML
	private ImageView showBtn;

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
		KeyValue keyValue = new KeyValue(vBox.translateXProperty(), x);
		KeyValue keyValue1 = new KeyValue(hBox.translateXProperty(), y);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.35), keyValue1);

		Timeline timeline = new Timeline(keyFrame, keyFrame1);

		timeline.play();
	}

	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			if (check == 0) {
				check = 1;
				borderPane.getCenter().prefWidth(632);
				// Đặt chiều sâu của borderPane cao hơn
				borderPane.setTranslateZ(1);
				anim(262, 213);

//				PauseTransition pause = new PauseTransition(Duration.seconds(3));
//				pause.setOnFinished(event -> {
//					try {
//						check = 0;
//						anim(-262, 0);
//					} catch (Exception e1) {
//						throw new RuntimeException(e1);
//					} finally {
//						// Đặt chiều sâu của borderPane về giá trị mặc định
//						borderPane.setTranslateZ(0);
//					}
//				});
//				pause.play();
			} else {
				check = 0;
				anim(-262, 0);
				borderPane.setTranslateZ(0);
			}
		}
	};

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Pane login = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
			borderPane.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
		showBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

}
