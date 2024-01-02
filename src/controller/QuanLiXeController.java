package controller;

import controller.xe.UpdateXe;
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
import models.XeModel;
import services.XeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class QuanLiXeController implements Initializable {


    ObservableList<XeModel> listValueTableView;
    @FXML
    private TableColumn<XeModel, Integer> colMaHoKhau;
    @FXML
    private TableColumn<XeModel, Integer> colOTo;
    @FXML
    private TableColumn<XeModel, Integer> colXeDap;
    @FXML
    private TableColumn<XeModel, Integer> colXeMay;
    @FXML
    private TableColumn<XeModel, String> colChuHo;

    @FXML
    private TextField tfSearch;

    @FXML
    private Label noti;

    // thong ke
    @FXML
    private Label lbSoXeDap;

    @FXML
    private Label lbSoXeMay;

    @FXML
    private Label lbSoOto;
    @FXML
    private TableView<XeModel> tbGuiXe;
    private List<XeModel> listXe = null;


    public QuanLiXeController() throws SQLException, ClassNotFoundException {

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            showTableXe_and_statistic();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTableXe_and_statistic() throws SQLException, ClassNotFoundException {
        listXe = XeService.getAllXe();
        listValueTableView = FXCollections.observableArrayList(listXe);
        colMaHoKhau.setCellValueFactory(new PropertyValueFactory<XeModel, Integer>("IDHoKhau"));
        colOTo.setCellValueFactory(new PropertyValueFactory<XeModel, Integer>("OTo"));
        colXeDap.setCellValueFactory(new PropertyValueFactory<XeModel, Integer>("XeDap"));
        colXeMay.setCellValueFactory(new PropertyValueFactory<XeModel, Integer>("XeMay"));

        colChuHo.setCellValueFactory(new PropertyValueFactory<XeModel, String>("ChuHo"));

        tbGuiXe.setItems(listValueTableView);

        // thong ke
        int soXeDap = 0;
        int soXeMay = 0;
        int soOto = 0;
        for (XeModel xe : listXe) {
            soXeDap += xe.getXeDap();
            soXeMay += xe.getXeMay();
            soOto += xe.getOTo();
        }
        lbSoXeDap.setText(String.valueOf(soXeDap));
        lbSoXeMay.setText(String.valueOf(soXeMay));
        lbSoOto.setText(String.valueOf(soOto));
    }


    public
    @FXML
    void CapNhatSoXe(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        XeModel xe = tbGuiXe.getSelectionModel().getSelectedItem();
        if(xe == null){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn chưa chọn hộ khẩu nào!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/xe/UpdateXe.fxml"));
            Parent home = loader.load();
            Stage stg = new Stage();
            UpdateXe updateXe = loader.getController();

            updateXe.setXeModel_Tuan(xe);
            stg.setScene(new Scene(home, 500, 400));
            stg.setResizable(false);
            stg.showAndWait();
        }

        showTableXe_and_statistic();
    }

    @FXML
    void searchHoKhau(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<XeModel> listSearch = FXCollections.observableArrayList();
        String id = tfSearch.getText();
        if(id.equals("")){
            noti.setText("");
            tfSearch.setStyle("");
            showTableXe_and_statistic();
        }
        else{
            for (XeModel xe : listXe) {
                if (String.valueOf(xe.getIDHoKhau()).equals(id)) {
                    noti.setText("");
                    tfSearch.setStyle("");
                    listSearch.add(xe);
                }
            }
            if(listSearch.isEmpty()){
                listSearch = FXCollections.observableArrayList(listXe);
                noti.setText("Không tìm thấy hộ khẩu");
                tfSearch.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                //set postion of label noti under textfield
                noti.setLayoutX(tfSearch.getLayoutX());
                noti.setLayoutY(tfSearch.getLayoutY() + 40);
            }
            else tbGuiXe.setItems(listSearch);
        }
    }
}
