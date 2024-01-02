package controller.tamtrutamvang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.NhanKhauModel;
import models.TamVangModel;
import services.TamVangService;
import services.XuLyLoiService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateTamVang {
    private static NhanKhauModel nhanKhauModel;
    private static TamVangModel tamVangModel;
    @FXML
    private Label textKhaibao;
    @FXML
    private Label Error;
    @FXML
    private TextField tfIDNhanKhau;
    @FXML
    private TextField tfHoTen;
    @FXML
    private DatePicker tfNgaybatdau;
    @FXML
    private DatePicker tfNgayketthuc;
    @FXML
    private TextArea tfLydo;
    private int IDNhanKhau;

    public static NhanKhauModel getNhanKhauModel() {
        return nhanKhauModel;
    }

    public void setTamVangModel(TamVangModel tamVangModel) throws ClassNotFoundException, SQLException {
        UpdateTamVang.tamVangModel = tamVangModel;
        IDNhanKhau = tamVangModel.getIDNhanKhau();

        Error.setVisible(false);
        tfIDNhanKhau.setText(String.valueOf(IDNhanKhau));
        tfHoTen.setText(tamVangModel.getHoTen());

        tfNgaybatdau.setValue(tamVangModel.getNgayBatDau());
        tfNgayketthuc.setValue(tamVangModel.getNgayKetThuc());
        tfLydo.setText(tamVangModel.getLyDo());
    }

    @FXML
    void updateTamVang(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException, IOException {
        LocalDate ngayBatDau = tfNgaybatdau.getValue();
        LocalDate ngayKetThuc = tfNgayketthuc.getValue();
        String LyDo = tfLydo.getText();
        boolean isBefore = ngayBatDau.isBefore(ngayKetThuc);

        if (!isBefore) {
            XuLyLoiService.xuLyLoi(Error, tfNgayketthuc, "Ngày kết thúc sau ngày bắt đầu", 200, -15);
            return;
        } else {
            XuLyLoiService.xoaLoi(Error, tfNgayketthuc);
        }

        System.out.println(ngayKetThuc);
        tamVangModel.setNgayBatDau(ngayBatDau);
        tamVangModel.setNgayKetThuc(ngayKetThuc);
        tamVangModel.setLyDo(LyDo);
        if (TamVangService.updateTamVang(tamVangModel)) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
