package controller.hokhau;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.HoKhauBean;
import models.HoKhauModel;
import models.NhanKhauModel;
import services.HoKhauService;
import services.NhanKhauService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateQHvsChuHo {
    ObservableList<Integer> IDNhanKhauinHoKhau = FXCollections.observableArrayList();
    ObservableList<String> QHvsChuHo_List = FXCollections.observableArrayList("Vợ/Chồng", "Con cái", "Bố mẹ", "Ông bà", "Cháu chắt", "Khác");
    private ChangeListener<String> qhvsChuHoChangeListener;
    @FXML
    private TextField tfChuHo;
    @FXML
    private TextField tfHoTen;
    @FXML
    private ComboBox<Integer> tfIDNhanKhau;
    @FXML
    private ComboBox<String> tfQHvsChuHo = new ComboBox<>();
    @FXML
    private TextField tfIDhoKhau;
    private HoKhauBean hoKhauBean;


    private List<NhanKhauModel> nhanKhauList = new ArrayList<>();
    private HoKhauModel hoKhauModel;
    private NhanKhauModel chuHo;

    public void setHoKhauBean(HoKhauBean hoKhauBean) throws SQLException, ClassNotFoundException {
        this.hoKhauBean = hoKhauBean;
        hoKhauBean = HoKhauService.getListHoKhau(String.valueOf(hoKhauBean.getHoKhauModel_tuan().getIDHoKhau()), "ID Hộ khẩu").get(0);
        hoKhauModel = hoKhauBean.getHoKhauModel_tuan();
        nhanKhauList = hoKhauBean.getListNhanKhau();

        for (NhanKhauModel nhankhau : nhanKhauList) {
            // System.out.println(nhankhau.getHoTen() + ": " +  nhankhau.getQHvsChuHo());
            if (nhankhau.getQHvsChuHo().equals("Chủ hộ")) {
                chuHo = nhankhau;
                tfChuHo.setText(chuHo.getHoTen());
            } else {
                IDNhanKhauinHoKhau.add(nhankhau.getIDNhanKhau());
            }
        }
        tfIDNhanKhau.setItems(IDNhanKhauinHoKhau);
        tfIDhoKhau.setText(String.valueOf(hoKhauModel.getIDHoKhau()));

//        tfIDNhanKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
//            //tfQHvsChuHO.
//            int selectedIDNhankhau = tfIDNhanKhau.getValue();
//            System.out.print(selectedIDNhankhau + " ");
//            for (NhanKhauModel nhankhau : nhanKhauList) {
//                if (nhankhau.getIDNhanKhau() == selectedIDNhankhau) {
//                    tfHoTen.setText(nhankhau.getHoTen());
//                    tfQHvsChuHo.setItems(QHvsChuHo_List);
//                    if(!nhankhau.getQHvsChuHo().equals("(Chưa điền)")){
//                        tfQHvsChuHo.setValue(nhankhau.getQHvsChuHo());
//                        tfQHvsChuHo.getSelectionModel().selectedItemProperty().addListener((observablee, oldValuee, newValuee) -> {
//                            nhankhau.setQHvsChuHo(tfQHvsChuHo.getValue());
//                            System.out.println("A: " + nhankhau.getHoTen() + ": " + nhankhau.getQHvsChuHo());
//                        });
//                    }
//                    else{
//                        tfQHvsChuHo.getSelectionModel().selectedItemProperty().addListener((observablee, oldValuee, newValuee) -> {
//                            nhankhau.setQHvsChuHo(tfQHvsChuHo.getValue());
//                            System.out.println("B: " + nhankhau.getHoTen() + ": " + nhankhau.getQHvsChuHo());
//                        });
//                    }
//                    break;
//                }
//            }
//        });
//        tfIDNhanKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
//            // Loại bỏ listener cũ (nếu có)
//            if (qhvsChuHoChangeListener != null) {
//                tfQHvsChuHo.getSelectionModel().selectedItemProperty().removeListener(qhvsChuHoChangeListener);
//            }
//
//            int selectedIDNhankhau = tfIDNhanKhau.getValue();
//            // System.out.print(selectedIDNhankhau + " ");
//
//            for (NhanKhauModel nhankhau : nhanKhauList) {
//                if (nhankhau.getIDNhanKhau() == selectedIDNhankhau) {
//                    tfHoTen.setText(nhankhau.getHoTen());
//                    tfQHvsChuHo.setItems(QHvsChuHo_List);
//
//                    // Tạo listener mới
//                    qhvsChuHoChangeListener = (observablee, oldValuee, newValuee) -> {
//                        nhankhau.setQHvsChuHo(tfQHvsChuHo.getValue());
//                        // System.out.println("A: " + nhankhau.getHoTen() + ": " + nhankhau.getQHvsChuHo());
//                    };
//
//                    if (!nhankhau.getQHvsChuHo().equals("(Chưa điền)")) {
//                        tfQHvsChuHo.setValue(nhankhau.getQHvsChuHo());
//                    }
//
//                    // Thêm listener mới
//                    tfQHvsChuHo.getSelectionModel().selectedItemProperty().addListener(qhvsChuHoChangeListener);
//
//                    break;
//                }
//            }
//        });
        tfIDNhanKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
            // Loại bỏ listener cũ (nếu có)
            if (qhvsChuHoChangeListener != null) {
                tfQHvsChuHo.getSelectionModel().selectedItemProperty().removeListener(qhvsChuHoChangeListener);
            }

            int selectedIDNhankhau = tfIDNhanKhau.getValue();
            // System.out.print(selectedIDNhankhau + " ");

            for (NhanKhauModel nhankhau : nhanKhauList) {
                if (nhankhau.getIDNhanKhau() == selectedIDNhankhau) {
                    tfHoTen.setText(nhankhau.getHoTen());
                    tfQHvsChuHo.setItems(QHvsChuHo_List);

                    // Tạo listener mới
                    qhvsChuHoChangeListener = (observablee, oldValuee, newValuee) -> {
                        nhankhau.setQHvsChuHo(tfQHvsChuHo.getValue());
                        // System.out.println("A: " + nhankhau.getHoTen() + ": " + nhankhau.getQHvsChuHo());
                    };

                    if (!nhankhau.getQHvsChuHo().equals("(Chưa điền)")) {
                        tfQHvsChuHo.setValue(nhankhau.getQHvsChuHo());
                    }

                    // Thêm listener mới
                    tfQHvsChuHo.getSelectionModel().selectedItemProperty().addListener(qhvsChuHoChangeListener);

                    break;
                }
            }
        });

    }

    @FXML
    void btnThayDoi(ActionEvent event) throws SQLException, ClassNotFoundException {
        for (NhanKhauModel nhankhau : nhanKhauList) {
            if (!NhanKhauModel.compareNhanKhauModels(nhankhau, chuHo)) {
                NhanKhauService.update(nhankhau);
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public List<NhanKhauModel> getNhanKhauList() {
        return nhanKhauList;
    }
}