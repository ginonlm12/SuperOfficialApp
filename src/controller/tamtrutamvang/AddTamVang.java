package controller.tamtrutamvang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.NhanKhauModel_Lam;
import models.TamVangModel;
import services.TamVangService;
import services.XuLyLoiService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class AddTamVang {
    private static NhanKhauModel_Lam nhanKhauModel;
    private static TamVangModel tamVangModel;
    @FXML
    private Label textKhaibao;
    @FXML
    private Button addbtn;
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

    public static NhanKhauModel_Lam getNhanKhauModel() {
        return nhanKhauModel;
    }

    public void setNhanKhauModel(NhanKhauModel_Lam nhanKhauModel) throws ClassNotFoundException, SQLException {
        AddTamVang.nhanKhauModel = nhanKhauModel;
        IDNhanKhau = nhanKhauModel.getIDNhanKhau();
        tamVangModel = TamVangService.checkCurrent(IDNhanKhau);

        Error.setVisible(false);
        tfIDNhanKhau.setText(String.valueOf(IDNhanKhau));
        tfHoTen.setText(nhanKhauModel.getHoTen());
        if (tamVangModel.getIDTamVang() != 0) {
            tfNgayketthuc.setEditable(false);
            tfNgaybatdau.setEditable(false);
            tfLydo.setEditable(false);
            tfNgaybatdau.setValue(tamVangModel.getNgayBatDau());
            tfNgayketthuc.setValue(tamVangModel.getNgayKetThuc());
            tfLydo.setText(tamVangModel.getLyDo());
            addbtn.setVisible(false);
            textKhaibao.setText("Nhân khẩu đang tạm vắng!");
        } else {
            LocalDate currentDate = LocalDate.now();
            tfNgaybatdau.setValue(currentDate);
            tfNgayketthuc.setValue(currentDate.plusMonths(3));
        }
    }

    @FXML
    void addTamVang(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException, IOException {
        if (tfNgaybatdau.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
            XuLyLoiService.xuLyLoi(tfNgaybatdau);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgaybatdau);
        }
        if (tfNgayketthuc.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
            XuLyLoiService.xuLyLoi(tfNgayketthuc);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgayketthuc);
        }

        LocalDate ngayBatDau = tfNgaybatdau.getValue();
        LocalDate ngayKetThuc = tfNgayketthuc.getValue();
        String LyDo = tfLydo.getText();
        boolean isBefore = ngayBatDau.isBefore(ngayKetThuc);

        if (!isBefore) {
            XuLyLoiService.xuLyLoi(Error, tfNgayketthuc, "Ngày kết thúc sau ngày bắt đầu", 250, -15);
            return;
        } else {
            XuLyLoiService.xoaLoi(Error, tfNgayketthuc);
        }

        TamVangModel tamVangModel = new TamVangModel(IDNhanKhau, ngayBatDau, ngayKetThuc, LyDo);
        if (TamVangService.addTamVang(tamVangModel)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Thêm thành công!", ButtonType.CLOSE);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.CLOSE) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }
}
