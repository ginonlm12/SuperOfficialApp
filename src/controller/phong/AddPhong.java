package controller.phong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.PhongModel;
import services.PhongService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddPhong implements Initializable {

    List<Integer> phongModelList = new ArrayList<>();
    ObservableList<String> LoaiPhongList = FXCollections.observableArrayList("Cao cấp", "Thường", "Giá rẻ");
    @FXML
    private TextField tfDienTich;
    @FXML
    private ComboBox<String> tfLoaiPhong;
    @FXML
    private TextField tfSoPhong;

    @FXML
    void addPhong(ActionEvent event) throws ClassNotFoundException, SQLException {
        Pattern pattern;
        pattern = Pattern.compile("\\d{1,11}");

        if (!pattern.matcher(tfSoPhong.getText()).matches()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào số phòng hợp lệ!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        phongModelList = PhongService.getListSoPhong("Tat ca");

        boolean checkExistedPhong = false;
        for (Integer soPhong : phongModelList) {
            //System.out.print("haha + false");
            if (soPhong == Integer.parseInt(tfSoPhong.getText())) {
                //System.out.print("haha + true");
                checkExistedPhong = true;
                break;
            }
        }
        if (checkExistedPhong) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Phòng nhập vào đã tồn tại!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        pattern = Pattern.compile("\\d*\\.?\\d+");
        if (!pattern.matcher(tfDienTich.getText()).matches()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào diện tích hợp lệ!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // ghi nhan gia tri ghi tat ca deu da hop le
        int SoPhong = Integer.parseInt(tfSoPhong.getText());
        Double DienTich = Double.valueOf(tfDienTich.getText());
        String LoaiPhong = tfLoaiPhong.getValue();

        PhongModel phongModel = new PhongModel(SoPhong, DienTich, LoaiPhong);
        if (!PhongService.add(phongModel)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Phòng nhập vào đã tồn tại!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfLoaiPhong.setItems(LoaiPhongList);
    }
}
