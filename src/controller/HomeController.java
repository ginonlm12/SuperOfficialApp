package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    Pane middlePane;
    @FXML
    private VBox menuNhanKhau;
    @FXML
    private AnchorPane allElements;
    @FXML
    private Button showBtn;
    @FXML
    private VBox phanconlai;
    @FXML
    private VBox menu = new VBox();
    @FXML
    public Pane mainPane;
    @FXML
    private Button btnPhong;


    void createNewStage(String fxmlFilePath, int width, int height) throws IOException {
        Parent home = FXMLLoader.load(getClass().getResource(fxmlFilePath));
        Stage stage = new Stage();
        home.setFocusTraversable(true);
        stage.setScene(new Scene(home, width, height));

        stage.setResizable(false);
        stage.showAndWait();
    }

    private boolean show = false;

    private void changeMainPane(String fxmlfile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
        Pane newContentPane = loader.load();
        // Replace the content of mainPane with the new content
        mainPane.getChildren().setAll(newContentPane);
        anim(menuNhanKhau, 330, 0, 0.2);
        show = false;
    }

    public void setNhanKhau(MouseEvent event) {
        //changeMainPane("/views/NhanKhau.fxml");
        if (!show) {
            show = !show;
            menuShow(menuNhanKhau, 430, 0, 0.2);
        } else {
            show = !show;
            anim(menuNhanKhau, 330, 0, 0.2);
        }
    }

    @FXML
    public void setCuTru(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NhanKhau.fxml"));
        Pane newContentPane = loader.load();
        // Replace the content of mainPane with the new content
        mainPane.getChildren().setAll(newContentPane);
    }

    @FXML
    public void setTamTru(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TamTru.fxml"));
        Pane newContentPane = loader.load();
        // Replace the content of mainPane with the new content
        mainPane.getChildren().setAll(newContentPane);
    }

    @FXML
    public void setTamVang(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TamVang.fxml"));
        Pane newContentPane = loader.load();
        // Replace the content of mainPane with the new content
        mainPane.getChildren().setAll(newContentPane);
    }

    public void setHoKhau(ActionEvent event) throws IOException {
        changeMainPane("/views/HoKhau.fxml");
    }

    public void setKhoanPhi(ActionEvent event) throws IOException {
        changeMainPane("/views/KhoanThu.fxml");
    }

    public void setDongPhi(ActionEvent event) throws IOException {
        changeMainPane("/views/ThuPhi.fxml");
    }

    public void setThongKe(ActionEvent event) throws IOException {
        changeMainPane("/views/ThongKe.fxml");
    }

    public void setTrangChu(ActionEvent event) throws IOException {
        changeMainPane("/views/Main.fxml");
    }

    public void anim(VBox menu, int x, int y, double keysecond) {
        KeyValue keyValue = new KeyValue(menu.translateXProperty(), x);
        KeyValue keyValue1 = new KeyValue(menu.translateXProperty(), y);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(keysecond), keyValue);
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(keysecond), keyValue1);
        Timeline timeline = new Timeline(keyFrame, keyFrame1);
        timeline.play();
	}

  
   @FXML
   void setPhong(ActionEvent event) throws IOException {
        changeMainPane("/views/Phong.fxml");
   }

    public void setGuiXe(ActionEvent event) throws IOException {
        changeMainPane("/views/GuiXe.fxml");
    }

    void menuShow(VBox menu, int x, int y, double keysecond) {
        anim(menu, x, y, keysecond);
        mainPane.setEffect(new ColorAdjust(0, -0.5, -0.5, 0));
        //phanconlai.setEffect(new BoxBlur(5, 5, 3));
        middlePane.setDisable(false);
    }

    void menuHide(VBox menu) {
        anim(menu, 0, 0, 0.35);
        mainPane.setEffect(null);
        middlePane.setDisable(true);
        show = false;
    }

    @FXML
    void onShowbtnClicked(MouseEvent event) {
        menuShow(menu, 165, 0, 0.35);
        menuShow(menuNhanKhau, 330, 0, 0.35);
    }

    @FXML
    void middlePaneClicked(MouseEvent event) {
        menuHide(menu);
        menuHide(menuNhanKhau);
    }

    @FXML
    void backupClicked(ActionEvent event) throws IOException {
        createNewStage("/views/users/backupdata.fxml", 680, 550);
    }

    @FXML
    void restoreClicked(ActionEvent event) throws IOException {
        createNewStage("/views/users/restoredata.fxml",600,600);
    }

    @FXML
    void changepassClicked(ActionEvent event) throws IOException {
        createNewStage("/views/users/changepass.fxml",400,250);
    }

    @FXML
    void logoutClicked(ActionEvent event) throws IOException {

        Parent home = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(home);

        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerNode().getScene().getWindow();
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


    @FXML
    void logobtnClicked(MouseEvent event) throws URISyntaxException {
        Desktop desktop = java.awt.Desktop.getDesktop();

        URI oURL = null;

        oURL = new URI("https://husteduvn-my.sharepoint.com/:w:/r/personal/quang_nm215461_sis_hust_edu_vn/_layouts/15/Doc.aspx?sourcedoc=%7B3BD014E7-7289-4956-9252-F0F9651D0715%7D&file=SuperApp_QLTT.docx&action=default&mobileredirect=true");
        try {
            desktop.browse(oURL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
