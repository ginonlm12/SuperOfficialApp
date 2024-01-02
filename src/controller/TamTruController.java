package controller;

import controller.tamtrutamvang.ShowTamTru;
import javafx.animation.RotateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.TamTruModel;
import services.TamTruService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TamTruController implements Initializable {
    private final LocalDate currentDate = LocalDate.now();
    @FXML
    private ComboBox<String> cbChooseSearchTT;
    @FXML
    private TableColumn<TamTruModel, String> colHoTenTT;
    @FXML
    private TableColumn<TamTruModel, LocalDate> colNgayBatDauTT;
    @FXML
    private TableColumn<TamTruModel, LocalDate> colNgayKetThucTT;
    @FXML
    private TableColumn<TamTruModel, String> colLyDoTT;
    @FXML
    private TextField tfSearchTT;
    @FXML
    private TableView<TamTruModel> tvTamTru;
    private List<TamTruModel> listTamTru;
    private ObservableList<TamTruModel> listValueTableViewTT;

    private void showTamTru() throws SQLException, ClassNotFoundException {
        listTamTru = TamTruService.getListTamTru();
        listValueTableViewTT = FXCollections.observableArrayList(listTamTru);

        colHoTenTT.setCellValueFactory(new PropertyValueFactory<TamTruModel, String>("HoTen"));
        colNgayBatDauTT.setCellValueFactory(new PropertyValueFactory<TamTruModel, LocalDate>("NgayBatDau"));
        colNgayKetThucTT.setCellValueFactory(new PropertyValueFactory<TamTruModel, LocalDate>("NgayKetThuc"));
        colLyDoTT.setCellValueFactory(new PropertyValueFactory<TamTruModel, String>("LyDo"));

        tvTamTru.setItems(listValueTableViewTT);
    }

    @FXML
    void searchTamTru(ActionEvent event) {
        ObservableList<TamTruModel> listValueTableView_tmp = null;
        String keySearch = tfSearchTT.getText();

        SingleSelectionModel<String> typeSearch = cbChooseSearchTT.getSelectionModel();
        String typeSearchString = typeSearch.getSelectedItem();

        switch (typeSearchString) {
            case "Họ Tên": {
                // neu khong nhap gi -> thong bao loi
                if (keySearch.length() == 0) {
                    tvTamTru.setItems(listValueTableViewTT);
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    break;
                }

                int index = 0;
                List<TamTruModel> listTamTruSearch = new ArrayList<>();
                for (TamTruModel tamTruModel : listTamTru) {
                    if (tamTruModel.getHoTen().contains(keySearch)) {
                        listTamTruSearch.add(tamTruModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listTamTruSearch);
                tvTamTru.setItems(listValueTableView_tmp);

                // neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
                if (index == 0) {
                    tvTamTru.setItems(listValueTableViewTT); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                break;
            }
            default: {

                int index = 0;
                List<TamTruModel> listTamTruSearch = new ArrayList<>();
                for (TamTruModel tamTruModel : listTamTru) {
                    if (!tamTruModel.getNgayBatDau().isAfter(currentDate) && !tamTruModel.getNgayKetThuc().isBefore(currentDate)) {
                        listTamTruSearch.add(tamTruModel);
                        index++;
                    }
                }
                listValueTableView_tmp = FXCollections.observableArrayList(listTamTruSearch);
                tvTamTru.setItems(listValueTableView_tmp);

                // neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
                if (index == 0) {
                    tvTamTru.setItems(listValueTableViewTT); // hien thi toan bo thong tin
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void addTamtru(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        Parent home = FXMLLoader.load(getClass().getResource("/views/tamtrutamvang/AddTamTru.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 650, 580));
        stage.setResizable(false);
        stage.showAndWait();
        showTamTru();
    }

    @FXML
    void delTamtru(ActionEvent event) throws SQLException, ClassNotFoundException {
        TamTruModel tamTruModel = tvTamTru.getSelectionModel().getSelectedItem();

        if (tamTruModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn lịch sử tạm trú cần xóa!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Xóa lịch sử tạm trú này?", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.NO) {
                return;
            } else {
                TamTruService.del(tamTruModel.getIDTamTru());
            }
        }
        showTamTru();
    }

    @FXML
    void showTamtru(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        TamTruModel tamTruModel = tvTamTru.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/tamtrutamvang/ShowTamTru.fxml"));
        Parent home = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 650, 480));
        ShowTamTru showTamTru = loader.getController();

        // bat loi truong hop khong hop le
        if (showTamTru == null) return;
        if (tamTruModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn trạng thái tạm vắng cần xem!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        showTamTru.setTamTruModel(tamTruModel);

        stage.setResizable(false);
        stage.showAndWait();
        showTamTru();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        try {
            ObservableList<String> listComboBoxTT = FXCollections.observableArrayList("Họ Tên", "Đang tạm trú");
            cbChooseSearchTT.setValue("Họ Tên");
            cbChooseSearchTT.setItems(listComboBoxTT);

            cbChooseSearchTT.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (cbChooseSearchTT.getValue().equals("Đang tạm trú")) {
                    tfSearchTT.setEditable(false);
                    tfSearchTT.setText(String.valueOf(currentDate));
                } else {
                    tfSearchTT.setEditable(true);
                    tfSearchTT.setText("");
                }
            });

            showTamTru();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private ImageView reloadimg;

    @FXML
    void Reload(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), reloadimg);

        rotateTransition.setByAngle(720);
        rotateTransition.setOnFinished(eventt -> {
            try {
                showTamTru();
                cbChooseSearchTT.setValue("Họ Tên");
                tfSearchTT.setText("");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        rotateTransition.play();
    }
}
