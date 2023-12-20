package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    Pane middlePane;
	@FXML
	private HBox hBox;
	@FXML
    private Button showBtn;
	@FXML
    private VBox vbox;
	@FXML
    private Pane mainPane;

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
        KeyValue keyValue = new KeyValue(vbox.translateXProperty(), x);
        KeyValue keyValue1 = new KeyValue(vbox.translateXProperty(), y);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.35), keyValue);
		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.35), keyValue1);
		Timeline timeline = new Timeline(keyFrame, keyFrame1);
		timeline.play();
	}

    void menuShow() {
        anim(165, 0);
        mainPane.setEffect(new BoxBlur(5, 5, 3));
        middlePane.setDisable(false);
    }

    void menuHide() {
        anim(0, 0);
        mainPane.setEffect(null);
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
