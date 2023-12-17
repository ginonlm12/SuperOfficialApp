package controller.hokhau;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.*;
import services.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class UpdateHoKhau_Lam {

    @FXML
    private TextField tfHoTen;

    @FXML
    private TextField tfIDHoKhau;

    @FXML
    private ChoiceBox<Integer> tfIDNhanKhau;

    @FXML
    private DatePicker tfNgayDen;

    @FXML
    private TextField tfSDT;

    @FXML
    private ChoiceBox<Integer> tfSoPhong;

    private HoKhauBean_Tuan hoKhauBean;
    private HoKhauBean_Tuan newhoKhauBean;
    private List<NhanKhauModel_Lam> nhanKhauList = new ArrayList<>();
    private HoKhauModel_Tuan hoKhauModel;
    private NhanKhauModel_Lam chuHo;

    ObservableList<Integer> IDNhanKhauinHoKhau = FXCollections.observableArrayList();
    ObservableList<Integer> SoPhong = FXCollections.observableArrayList();
    @FXML
    void updateHoKhau(ActionEvent event) throws SQLException, ClassNotFoundException {

        Pattern pattern;
        // kiem tra sdt nhap vao
        // SDT nhap vao phai khong chua chu cai va nho hon 15 chu so
        pattern = Pattern.compile("\\d{1,15}");
        if (!pattern.matcher(tfSDT.getText()).matches()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào SĐT hợp lệ!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        if (tfNgayDen.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn ngày chuyển đến", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // ghi nhan gia tri ghi tat ca deu da hop le
        HoKhauModel_Tuan newHoKhauModel = new HoKhauModel_Tuan();
        newHoKhauModel = hoKhauModel;

        newhoKhauBean.setSoTV(hoKhauBean.getSoTV());
        newhoKhauBean.setListNhanKhau(hoKhauBean.getListNhanKhau());
        newhoKhauBean.setHoKhauModel_tuan(newHoKhauModel);
        //System.out.println("Số phòng cũ 1: " + hoKhauModel.getSoPhong());
        newHoKhauModel.setSoPhong(tfSoPhong.getValue());
        //System.out.println("Số phòng cũ 2: " + hoKhauModel.getSoPhong());

        newHoKhauModel.setNgayDen(String.valueOf(tfNgayDen.getValue()));
        newHoKhauModel.setSDT(tfSDT.getText());
        //System.out.println("Số phòng cũ 3: " + hoKhauModel.getSoPhong());
//		System.out.println("ID Nhân khẩu mới: " + tfIDNhanKhau);
//		System.out.println("ID Nhân khẩu cũ: " + chuHo.getIDNhanKhau());
        if(!tfIDNhanKhau.getValue().equals(chuHo.getIDNhanKhau())){
            int IDHoKhau = hoKhauBean.getHoKhauModel_tuan().getIDHoKhau();
            //int old_IDChuho = chuHo.getIDNhanKhau();
            int new_IDChuho = tfIDNhanKhau.getValue();
            System.out.println("haha");
            HoKhauService_Tuan.changeChuHo(IDHoKhau, new_IDChuho);

            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng cập nhật lại quan hệ với chủ hộ mới", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        HoKhauService_Tuan.updateHoKhau(newHoKhauModel);


        hoKhauBean.setHoKhauModel_tuan(newHoKhauModel);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setHoKhauModel(HoKhauBean_Tuan hoKhauBean) throws ClassNotFoundException, SQLException {
        this.hoKhauBean = hoKhauBean;
        this.nhanKhauList = hoKhauBean.getListNhanKhau();
        this.hoKhauModel = hoKhauBean.getHoKhauModel_tuan();
        this.newhoKhauBean = hoKhauBean;
        System.out.println(hoKhauBean.getSoTV());

        tfIDHoKhau.setText(String.valueOf(hoKhauModel.getIDHoKhau()));
        tfSoPhong.setValue(hoKhauModel.getSoPhong());
        LocalDate DOB = LocalDate.parse(hoKhauModel.getNgayDen(), DateTimeFormatter.ISO_DATE);
        tfNgayDen.setValue(DOB);
        tfSDT.setText(hoKhauModel.getSDT());
        for (NhanKhauModel_Lam nhankhau : nhanKhauList) {
            if (nhankhau.getQHvsChuHo().equals("Chủ hộ")) {
                chuHo = nhankhau;
            }
            IDNhanKhauinHoKhau.add(nhankhau.getIDNhanKhau());
        }
        tfIDNhanKhau.setValue(chuHo.getIDNhanKhau());
        tfHoTen.setText(chuHo.getHoTen());

        tfIDNhanKhau.setItems(IDNhanKhauinHoKhau);
        SoPhong = SoPhongService_Tuan.getListSoPhong();
        SoPhong.add(hoKhauModel.getSoPhong());
        tfSoPhong.setItems(SoPhong);

        tfIDNhanKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
            for (NhanKhauModel_Lam nhankhau : nhanKhauList) {
                if (nhankhau.getIDNhanKhau() == tfIDNhanKhau.getValue()) {
                    tfHoTen.setText(nhankhau.getHoTen());
                }
            }
        });
    }
}
