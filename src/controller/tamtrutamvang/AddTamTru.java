package controller.tamtrutamvang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.NhanKhauModel;
import models.TamTruModel;
import services.NhanKhauService;
import services.TamTruService;
import services.XuLyLoiService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class AddTamTru implements Initializable {
    ObservableList<String> genderList = FXCollections.observableArrayList("Nam", "Nữ");
    ObservableList<String> QueQuanList = FXCollections.observableArrayList("Việt Nam", "Khác");
    ObservableList<String> Tinh_List = FXCollections.observableArrayList();
    ObservableList<String> Huyen_List = FXCollections.observableArrayList();
    ObservableList<String> Xa_List = FXCollections.observableArrayList();
    ObservableList<String> IDHoKhau_List = FXCollections.observableArrayList();
    ObservableList<Integer> IDHoKhau_onlyID = FXCollections.observableArrayList();
    @FXML
    private TextArea tfLyDo;
    @FXML
    private DatePicker tfNgayBatDau;
    @FXML
    private DatePicker tfNgayKetThuc;
    @FXML
    private TextField tfCCCD;
    @FXML
    private TextField tfChuHo;
    @FXML
    private ComboBox<String> tfCountry;
    @FXML
    private ComboBox<String> tfDistrict;
    @FXML
    private ComboBox<String> tfGioiTinh;
    @FXML
    private TextField tfHoTen;
    @FXML
    private Label tfHuyen;
    @FXML
    private ComboBox<String> tfIDHoKhau;
    @FXML
    private DatePicker tfNgaySinh;
    @FXML
    private ComboBox<String> tfProvince;
    @FXML
    private TextField tfQueQuan;
    @FXML
    private Label tfTinh;
    @FXML
    private ComboBox<String> tfWard;
    @FXML
    private Label tfXa;
    private List<NhanKhauModel> listNhanKhau;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Error.setVisible(false);
        //Error.setStyle("-fx-color: linear-gradient(to bottom right, #FF0000, #CC0000);");
        try {
            listNhanKhau = new NhanKhauService().getListNhanKhau();
            Vector<String> household = new Vector<>();
            for (NhanKhauModel nhankhau : listNhanKhau) {
                Integer data = nhankhau.getIDHoKhau();
                if (!IDHoKhau_onlyID.contains(data)) {
                    IDHoKhau_onlyID.add(data);
                    household.add(data + " - Phòng: " + NhanKhauService.getSoPhong(data));
                }
            }
            NhanKhauService.sortVectorByRoomNumber(household);
            for (String hold : household) {
                IDHoKhau_List.add(hold);
            }
            tfIDHoKhau.setItems(IDHoKhau_List);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tfGioiTinh.setItems(genderList);
        tfCountry.setItems(QueQuanList);

        tfIDHoKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
            int IDHoKhau = Integer.parseInt(NhanKhauService.extractIdHoKhau(tfIDHoKhau.getValue()));
            try {
                tfChuHo.setText(NhanKhauService.getChuHo(IDHoKhau));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        LocalDate currentDate = LocalDate.now();
        tfNgayBatDau.setValue(currentDate);
        tfNgayKetThuc.setValue(currentDate.plusMonths(3));

        tfCountry.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String selectedCountry = tfCountry.getValue();
            if (selectedCountry.equals("Khác")) {
                tfQueQuan.setVisible(true);
                tfTinh.setVisible(false);
                tfHuyen.setVisible(false);
                tfXa.setVisible(false);
                tfProvince.setVisible(false);
                tfDistrict.setVisible(false);
                tfWard.setVisible(false);
            }
            if (selectedCountry.equals("Việt Nam")) {
                tfQueQuan.setVisible(false);
                tfTinh.setVisible(true);
                tfHuyen.setVisible(true);
                tfXa.setVisible(true);
                tfProvince.setVisible(true);
                tfDistrict.setVisible(true);
                tfWard.setVisible(true);
                try {
                    Tinh_List = NhanKhauService.getProvince();
                    tfProvince.setItems(Tinh_List);

                    tfProvince.getSelectionModel().selectedItemProperty().addListener((observablee, oldValuee, newValuee) -> {
                        try {
                            String selectedProvince = tfProvince.getValue();

                            if (selectedProvince != null && !selectedProvince.isEmpty()) {
                                Huyen_List = NhanKhauService.GetDistrict(selectedProvince);
                                tfDistrict.setItems(Huyen_List);

                                tfDistrict.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
                                    try {
                                        String selectedDistrict = tfDistrict.getValue();

                                        if (selectedDistrict != null && !selectedDistrict.isEmpty()) {
                                            Xa_List = NhanKhauService.GetWard(selectedDistrict, selectedProvince);
                                            tfWard.setItems(Xa_List);
                                        }
                                    } catch (SQLException | ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void addNhanKhau(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (tfHoTen.getText().length() >= 50 || tfHoTen.getText().length() <= 1) {
            XuLyLoiService.xuLyLoi(tfHoTen);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfHoTen);
        }

        if (tfGioiTinh.getValue() == null || tfGioiTinh.getValue().isEmpty()) {
            XuLyLoiService.xuLyLoi(tfGioiTinh);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfGioiTinh);
        }

        if (tfNgaySinh.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
            XuLyLoiService.xuLyLoi(tfNgaySinh);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgaySinh);
        }

        LocalDate BornDate = tfNgaySinh.getValue();
        LocalDate currentDate = LocalDate.now();

        if (BornDate.isAfter(currentDate)) {
            XuLyLoiService.xuLyLoi(tfNgaySinh);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgaySinh);
        }

        if (tfIDHoKhau.getValue() == null) {
            XuLyLoiService.xuLyLoi(tfIDHoKhau);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfIDHoKhau);
        }
        if (tfNgayBatDau.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
            XuLyLoiService.xuLyLoi(tfNgayBatDau);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfNgayBatDau);
        }
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

        if (tfCountry.getValue() == null) {
            XuLyLoiService.xuLyLoi(tfCountry);
            return;
        } else {
            XuLyLoiService.xoaLoi(tfCountry);
        }
        String QueQuan = "(Chưa nhập)";
        if (tfCountry.getValue().equals("Khác")) {
            if (tfQueQuan.getText() == null || tfQueQuan.getText().isEmpty()) {
                XuLyLoiService.xuLyLoi(tfQueQuan);
                return;
            } else {
                XuLyLoiService.xoaLoi(tfQueQuan);
            }
            QueQuan = tfQueQuan.getText();
        }
        if (tfCountry.getValue().equals("Việt Nam")) {
            if (tfWard.getValue() == null) {
                if (tfDistrict.getValue() == null) {
                    XuLyLoiService.xuLyLoi(tfDistrict);
                } else {
                    XuLyLoiService.xoaLoi(tfProvince);
                }
                if (tfProvince.getValue() == null) {
                    XuLyLoiService.xuLyLoi(tfProvince);
                } else {
                    XuLyLoiService.xoaLoi(tfDistrict);
                }
                XuLyLoiService.xuLyLoi(tfWard);
                return;
            } else {
                XuLyLoiService.xoaLoi(tfWard);
            }
            QueQuan = tfWard.getValue() + ", " + tfDistrict.getValue() + ", " + tfProvince.getValue() + ", Việt Nam";
        }

        // Ghi nhan gia tri ghi tat ca deu da hop le

        int IDHoKhau = Integer.parseInt(NhanKhauService.extractIdHoKhau(tfIDHoKhau.getValue()));
        String HoTen = tfHoTen.getText();
        String CCCD;
        if (tfCCCD.getText() == null || tfIDHoKhau.getValue().isEmpty())
            CCCD = "Chưa cung cấp";
        else {
            CCCD = tfCCCD.getText();
        }
        String GioiTinh = tfGioiTinh.getValue();
        String LyDo = tfLyDo.getText();

        TamTruModel tamTruModel = new TamTruModel(IDHoKhau, HoTen, QueQuan, ngayBatDau, ngayKetThuc, LyDo, BornDate, CCCD, GioiTinh);

        if (!TamTruService.add(tamTruModel)) {

        } else {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
