package controller;

import controller.phong.UpdatePhong;
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
import models.PhongModel;
import services.PhongService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PhongController implements Initializable {
    @FXML
    private ComboBox<String> cbChooseSearch;
    @FXML
    private TableColumn<PhongModel, Double> colDienTich;
    @FXML
    private TableColumn<PhongModel, String> colLoaiPhong;
    @FXML
    private TableColumn<PhongModel, Integer> colSoPhong;
    @FXML
    private TableColumn<PhongModel, String> colTrangThai;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<PhongModel> tvPhong;
    private ObservableList<PhongModel> listValueTableView;
    private List<PhongModel> listPhong;

    public TableView<PhongModel> getTvPhong() {
        return tvPhong;
    }

    // hien thi so luong tung loai phong
    @FXML
    private Label lbSoCC;

    @FXML
    private Label lbSoRe;

    @FXML
    private Label lbSoThg;

    public void setTvPhong(TableView<PhongModel> tvPhong) {
        this.tvPhong = tvPhong;
    }

    public void showPhong() throws ClassNotFoundException, SQLException {
        listPhong = new PhongService().getListPhong();
        listValueTableView = FXCollections.observableArrayList(listPhong);

        // thiet lap cac cot cho tableviews
        colSoPhong.setCellValueFactory(new PropertyValueFactory<PhongModel, Integer>("SoPhong"));
        colDienTich.setCellValueFactory(new PropertyValueFactory<PhongModel, Double>("DienTich"));
        colLoaiPhong.setCellValueFactory(new PropertyValueFactory<PhongModel, String>("LoaiPhong"));
        colTrangThai.setCellValueFactory(new PropertyValueFactory<PhongModel, String>("TrangThai"));

        tvPhong.setItems(listValueTableView);

        // Thiết lập giá trị cho ComboBox
        ObservableList<String> listComboBox = FXCollections.observableArrayList("Số phòng", "Loại phòng", "Trạng thái");
        cbChooseSearch.setValue("Số phòng");
        cbChooseSearch.setItems(listComboBox);
    }

    public void showSoLuongPhong() throws ClassNotFoundException, SQLException {
        int soCC = 0, soRe = 0, soThg = 0;
        for (PhongModel phongModel : listPhong) {
            if (phongModel.getLoaiPhong().equals("Cao cấp")) {
                soCC++;
            } else if (phongModel.getLoaiPhong().equals("Giá rẻ")) {
                soRe++;
            }
            else if (phongModel.getLoaiPhong().equals("Thường")) {
                soThg++;
            }
        }
        lbSoCC.setText(String.valueOf(soCC));
        lbSoRe.setText(String.valueOf(soRe));
        lbSoThg.setText(String.valueOf(soThg));
    }

    @FXML
    void addPhong(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        Parent home = FXMLLoader.load(getClass().getResource("/views/phong/AddPhong.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 450, 300));
        stage.setResizable(false);
        stage.showAndWait();
        showPhong();
        showSoLuongPhong();
    }

    @FXML
    void delPhong(ActionEvent event) throws SQLException, ClassNotFoundException {
        PhongModel phongModel = tvPhong.getSelectionModel().getSelectedItem();
        // int maho = 0;

        if (phongModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy chọn phòng bạn muốn xóa!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (phongModel.getTrangThai().equals("Đang được sử dụng")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Không thể xóa phòng đang được sử dụng!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn có chắc chắn muốn xóa phòng này!", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.NO) {
                return;
            } else {
                PhongService.del(phongModel.getSoPhong());
            }
        }
        showPhong();
        showSoLuongPhong();
    }

    @FXML
    void searchPhong(ActionEvent event) {
        ObservableList<PhongModel> listValueTableView_tmp = null;
        String keySearch = tfSearch.getText();

        // lay lua chon tim kiem cua khach hang
        SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
        String typeSearchString = typeSearch.getSelectedItem();

        // tim kiem thong tin theo lua chon da lay ra
        switch (typeSearchString) {
            case "Loại phòng": {
                // neu khong nhap gi -> thong bao loi
                if (keySearch.length() == 0) {
                    tvPhong.setItems(listValueTableView);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    break;
                }

                int index = 0;
                List<PhongModel> listPhongModelsSearch = new ArrayList<>();
                for (PhongModel phongModel : listPhong) {
                    if (phongModel.getLoaiPhong().contains(keySearch)) {
                        listPhongModelsSearch.add(phongModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listPhongModelsSearch);
                tvPhong.setItems(listValueTableView_tmp);

                // neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
                if (index == 0) {
                    tvPhong.setItems(listValueTableView); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                break;
            }
            case "Số phòng": {
                // neu khong nhap gi -> thong bao loi
                if (keySearch.length() == 0) {
                    tvPhong.setItems(listValueTableView);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    break;
                }

                // kiem tra chuoi nhap vao co phai la chuoi hop le hay khong
                Pattern pattern = Pattern.compile("^[1-9]\\d*$");
                if (!pattern.matcher(keySearch).matches()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Số phòng phải là số nguyên dương!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    break;
                }

                int index = 0;
                List<PhongModel> listPhong_tmp = new ArrayList<>();
                for (PhongModel phongModel : listPhong) {
                    if (phongModel.getSoPhong() == Integer.parseInt(keySearch)) {
                        listPhong_tmp.add(phongModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listPhong_tmp);
                tvPhong.setItems(listValueTableView_tmp);

                // neu khong tim thay thong tin tim kiem -> thong bao toi nguoi dung
                if (index == 0) {
                    tvPhong.setItems(listValueTableView); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tồn tại phòng này", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                break;
            }
            default: { // truong hop con lai : tim theo id
                // neu khong nhap gi -> thong bao loi
                if (keySearch.length() == 0) {
                    tvPhong.setItems(listValueTableView);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Bạn cần nhập vào thông tin tìm kiếm!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    break;
                }

                int index = 0;
                List<PhongModel> listPhong_tmp = new ArrayList<>();
                for (PhongModel phongModel : listPhong) {
                    if (phongModel.getTrangThai().contains(keySearch)) {
                        listPhong_tmp.add(phongModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listPhong_tmp);
                tvPhong.setItems(listValueTableView_tmp);

                // khong tim thay thong tin -> thong bao toi nguoi dung
                if (index == 0) {
                    tvPhong.setItems(listValueTableView); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                break;
            }
        }
    }

    @FXML
    void updatePhong(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        PhongModel phongModel = tvPhong.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/phong/UpdatePhong.fxml"));
        Parent home = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 450, 300));
        UpdatePhong updatePhong = loader.getController();

        // bat loi truong hop khong hop le
        if (updatePhong == null) return;
        if (phongModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn phòng cần thay đổi!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        if (phongModel.getTrangThai().equals("Đang được sử dụng")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn có chắc chắn muốn sửa phòng đang được sử dụng!", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.NO) {
                return;
            }
        }
        updatePhong.setPhongModel(phongModel);

        stage.setResizable(false);
        stage.showAndWait();
        showPhong();
        showSoLuongPhong();
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        try {
            showPhong();
            showSoLuongPhong();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
