package controller.tamtrutamvang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.TamTruModel;
import services.NhanKhauService_Lam;
import services.TamTruService;
import services.XuLyLoiService;

import java.sql.SQLException;
import java.time.LocalDate;

public class ShowTamTru {

    @FXML
    private TextField tfCCCD;

    @FXML
    private TextField tfChuHo;

    @FXML
    private ComboBox<String> tfGioiTinh;

    @FXML
    private TextField tfHoTen;

    @FXML
    private ComboBox<String> tfIDHoKhau;

    @FXML
    private TextArea tfLyDo;

    @FXML
    private DatePicker tfNgayBatDau;

    @FXML
    private DatePicker tfNgayKetThuc;

    @FXML
    private DatePicker tfNgaySinh;

    @FXML
    private TextField tfQueQuan;

    private TamTruModel tamTruModel;

    public void setTamTruModel(TamTruModel tamTruModel) throws SQLException, ClassNotFoundException {
        this.tamTruModel = tamTruModel;

        tfHoTen.setText(tamTruModel.getHoTen());
        tfGioiTinh.setValue(tamTruModel.getGioiTinh());
        tfCCCD.setText(tamTruModel.getCCCD());
        tfNgaySinh.setValue(tamTruModel.getNgaySinh());
        tfIDHoKhau.setValue(String.valueOf(tamTruModel.getIDHoKhau()));
        tfChuHo.setText(NhanKhauService_Lam.getChuHo(tamTruModel.getIDHoKhau()));
        tfNgayBatDau.setValue(tamTruModel.getNgayBatDau());
        tfNgayKetThuc.setValue(tamTruModel.getNgayKetThuc());
        tfLyDo.setText(tamTruModel.getLyDo());
        tfQueQuan.setText(tamTruModel.getThuongTru());
    }

    @FXML
    void setCapNhat(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tfNgayKetThuc.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
            XuLyLoiService.xuLyLoi(tfNgayKetThuc);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgayKetThuc);
        }

        LocalDate ngayBatDau = tfNgayBatDau.getValue();
        LocalDate ngayKetThuc = tfNgayKetThuc.getValue();
        boolean isBefore = ngayBatDau.isBefore(ngayKetThuc);

        if (!isBefore) {
            XuLyLoiService.xuLyLoi(tfNgayKetThuc);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgayKetThuc);
        }

        TamTruService.updateNgayKetThuc(tamTruModel.getIDTamTru(), ngayKetThuc);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
