package controller.phong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.PhongModel;
import services.PhongService;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdatePhong {

    ObservableList<String> LoaiPhongList = FXCollections.observableArrayList("Cao cấp", "Thường", "Giá rẻ");
    @FXML
    private TextField tfDienTich;
    @FXML
    private ComboBox<String> tfLoaiPhong;
    @FXML
    private TextField tfSoPhong;

    private PhongModel phongModel = new PhongModel();

    public void setPhongModel(PhongModel phongModel) {
        this.phongModel = phongModel;

        tfSoPhong.setText(String.valueOf(phongModel.getSoPhong()));
        tfDienTich.setText(String.valueOf(phongModel.getDienTich()));
        tfLoaiPhong.setValue(phongModel.getLoaiPhong());

        tfLoaiPhong.setItems(LoaiPhongList);
    }

    @FXML
    void updatePhong(ActionEvent event) throws SQLException, ClassNotFoundException {
        Pattern pattern;

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

        PhongModel newphongModel = new PhongModel(SoPhong, DienTich, LoaiPhong);
        if (PhongModel.comparePhongModel(phongModel, newphongModel)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Không thay đổi thông tin à!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            PhongService.updatePhongModel(newphongModel);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
