package controller;

import controller.tamtrutamvang.UpdateTamVang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.TamVangModel;
import services.TamVangService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TamVangController implements Initializable {
    private final LocalDate currentDate = LocalDate.now();
    @FXML
    private ComboBox<String> cbChooseSearchTV;
    @FXML
    private TableColumn<TamVangModel, String> colHoTenTV;
    @FXML
    private TableColumn<TamVangModel, Integer> colID;
    @FXML
    private TableColumn<TamVangModel, String> colLyDoTV;
    @FXML
    private TableColumn<TamVangModel, LocalDate> colNgayBatDauTV;
    @FXML
    private TableColumn<TamVangModel, LocalDate> colNgayKetThucTV;
    @FXML
    private TextField tfSearchTV;
    @FXML
    private TableView<TamVangModel> tvTamVang;
    private List<TamVangModel> listTamVang;
    private ObservableList<TamVangModel> listValueTableView;

    public void showTamVang() throws ClassNotFoundException, SQLException {
        listTamVang = TamVangService.getListTamVang();
        listValueTableView = FXCollections.observableArrayList(listTamVang);

        colID.setCellValueFactory(new PropertyValueFactory<TamVangModel, Integer>("IDNhanKhau"));
        colHoTenTV.setCellValueFactory(new PropertyValueFactory<TamVangModel, String>("HoTen"));
        colNgayBatDauTV.setCellValueFactory(new PropertyValueFactory<TamVangModel, LocalDate>("NgayBatDau"));
        colNgayKetThucTV.setCellValueFactory(new PropertyValueFactory<TamVangModel, LocalDate>("NgayKetThuc"));
        colLyDoTV.setCellValueFactory(new PropertyValueFactory<TamVangModel, String>("LyDo"));

        tvTamVang.setItems(listValueTableView);
    }

    @FXML
    void searchTamVang(ActionEvent event) {
        ObservableList<TamVangModel> listValueTableView_tmp = null;
        String keySearch = tfSearchTV.getText();

        SingleSelectionModel<String> typeSearch = cbChooseSearchTV.getSelectionModel();
        String typeSearchString = typeSearch.getSelectedItem();

        switch (typeSearchString) {
            case "Họ Tên": {
                // neu khong nhap gi -> thong bao loi
                if (keySearch.length() == 0) {
                    tvTamVang.setItems(listValueTableView);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    break;
                }

                int index = 0;
                List<TamVangModel> listTamVangSearch = new ArrayList<>();
                for (TamVangModel tamVangModel : listTamVang) {
                    if (tamVangModel.getHoTen().contains(keySearch)) {
                        listTamVangSearch.add(tamVangModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listTamVangSearch);
                tvTamVang.setItems(listValueTableView_tmp);

                // neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
                if (index == 0) {
                    tvTamVang.setItems(listValueTableView); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                break;
            }
            default: {

                int index = 0;
                List<TamVangModel> listTamVangSearch = new ArrayList<>();
                for (TamVangModel tamVangModel : listTamVang) {
                    if (!tamVangModel.getNgayBatDau().isAfter(currentDate) && !tamVangModel.getNgayKetThuc().isBefore(currentDate)) {
                        listTamVangSearch.add(tamVangModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listTamVangSearch);
                tvTamVang.setItems(listValueTableView_tmp);

                // neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
                if (index == 0) {
                    tvTamVang.setItems(listValueTableView); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void addTamvang(ActionEvent event) throws IOException {
        //        HomeController homeController = new HomeController();
//        homeController.setNhanKhau(event);
    }

    @FXML
    void updateTamvang(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        TamVangModel tamVangModel = tvTamVang.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/tamtrutamvang/UpdateTamVang.fxml"));
        Parent home = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 400, 300));
        UpdateTamVang updateTamVang = loader.getController();

        // bat loi truong hop khong hop le
        if (updateTamVang == null) return;
        if (tamVangModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn trạng thái tạm vắng cần sửa!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        updateTamVang.setTamVangModel(tamVangModel);

        stage.setResizable(false);
        stage.showAndWait();
        showTamVang();
    }

    @FXML
    void delTamvang(ActionEvent event) throws SQLException, ClassNotFoundException {
        TamVangModel tamVangModel = tvTamVang.getSelectionModel().getSelectedItem();

        if (tamVangModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn lịch sử tạm vắng cần xóa!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Xóa lịch sử tạm vắng này?", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.NO) {
                return;
            } else {
                TamVangService.del(tamVangModel.getIDTamVang());
            }
        }
        showTamVang();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        try {
            ObservableList<String> listComboBoxTV = FXCollections.observableArrayList("Họ Tên", "Đang tạm vắng");
            cbChooseSearchTV.setValue("Họ Tên");
            cbChooseSearchTV.setItems(listComboBoxTV);

            cbChooseSearchTV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (cbChooseSearchTV.getValue().equals("Đang tạm vắng")) {
                    tfSearchTV.setEditable(false);
                    tfSearchTV.setText(String.valueOf(currentDate));
                } else {
                    tfSearchTV.setEditable(true);
                    tfSearchTV.setText("");
                }
            });

            showTamVang();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
