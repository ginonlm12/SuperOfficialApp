package controller.hokhau;

import controller.nhankhau.ShowChiTiet_Lam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.NhanKhauModel_Lam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowThanhVien_Tuan {
    private static List<NhanKhauModel_Lam> ListThanhVien;
    @FXML
    private TableColumn<NhanKhauModel_Lam, Integer> colIDThanhVien;
    @FXML
    private TableColumn<NhanKhauModel_Lam, String> colQHvsCH;
    @FXML
    private TableColumn<NhanKhauModel_Lam, String> colTenThanhVien;
    @FXML
    private Label lbTitle;
    @FXML
    private TableView<NhanKhauModel_Lam> tvHoKhau;
    private ObservableList<NhanKhauModel_Lam> listaValueofTable;

    @FXML
    public void XemThongTin(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        NhanKhauModel_Lam nhanKhauModel = tvHoKhau.getSelectionModel().getSelectedItem();
        if (nhanKhauModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn thành viên bạn muốn xem thông tin", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/nhankhau/ShowChiTiet_Lam.fxml"));
            Parent home = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(home, 690, 400));
            ShowChiTiet_Lam showChiTiet = loader.getController();

            // bat loi truong hop khong hop le
            if (showChiTiet == null) return;
            if (nhanKhauModel == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn nhân khẩu cần update !", ButtonType.OK);
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            showChiTiet.setNhanKhauModel(nhanKhauModel);

            stage.setResizable(false);
            stage.show();
        }
    }

    public void setTable(List<NhanKhauModel_Lam> ListNhanKhau) {
        ListThanhVien = ListNhanKhau;

        listaValueofTable = FXCollections.observableArrayList(ListThanhVien);
        colIDThanhVien.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, Integer>("IDNhanKhau"));
        colTenThanhVien.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("HoTen"));
        colQHvsCH.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("QHvsChuHo"));

        tvHoKhau.setItems(listaValueofTable);
    }

    public void setLbTitle(String title) {
        this.lbTitle.setText(title);
    }


}
