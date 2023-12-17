package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
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
//import models.ChuHoModel;
//import models.HoKhauModel;
//import models.KhoanThuModel;
//import models.NhanKhauModel;
//import models.QuanHeModel;
//import services.ChuHoService;
//import services.HoKhauService;
//import services.NhanKhauService;
//import services.QuanHeService;

import models.HoKhauBean_Tuan;
import models.NhanKhauModel_Lam;
import services.HoKhauService_Tuan;
import services.NhanKhauService_Lam;
import controller.hokhau.UpdateHoKhau_Lam;



public class HoKhauController implements Initializable {
	@FXML
	TableColumn<HoKhauBean_Tuan, Integer> colMaHoKhau;
	@FXML
	TableColumn<HoKhauBean_Tuan, String> colTenCH;
	@FXML
	TableColumn<HoKhauBean_Tuan, Integer> colSoThanhVien;
	@FXML
	TableColumn<HoKhauBean_Tuan, String> colSDT;
	@FXML
	TableView<HoKhauBean_Tuan> tvHoKhau;
	@FXML
	TextField tfSearch;

	@FXML
	ComboBox<String> cbChooseSearch;

	@FXML
	ComboBox<String> cbTrangThai ;

	ObservableList<HoKhauBean_Tuan> listValueTableView ;
	private List<HoKhauBean_Tuan> listHoKhau = HoKhauService_Tuan.getListHoKhau();
	private List<HoKhauBean_Tuan> temp_listHoKhauDangCuTru = new ArrayList<>();;
	private List<HoKhauBean_Tuan> temp_listHoKhauChuyenDi = new ArrayList<>();;

    public HoKhauController() throws SQLException, ClassNotFoundException {
    }

	public void ShowHoKhauTable() throws ClassNotFoundException, SQLException {
		setTrangThai();

		if(cbTrangThai.getValue() == "Đang cư trú") {
			showHoKhau(temp_listHoKhauDangCuTru);
		}
		else{
			showHoKhau(temp_listHoKhauChuyenDi);
		}
	}

    // Hien thi thong tin ho khau
	public void showHoKhau(List<HoKhauBean_Tuan> listHoKhau) throws ClassNotFoundException, SQLException {
//		listHoKhau = new HoKhauService_Tuan().getListHoKhau();
		listValueTableView = FXCollections.observableArrayList(listHoKhau);

		// colMaHoKhau get value from HoKhauBean_Tuan.HoKhauModel_Tuan.maHo, write it for me

		colMaHoKhau.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean_Tuan, Integer> p) ->
						new ReadOnlyIntegerWrapper(p.getValue().getHoKhauModel_tuan().getIDHoKhau()).asObject()
		);

		colTenCH.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean_Tuan, String> p) ->
                {
                    try {
                        return new ReadOnlyStringWrapper(NhanKhauService_Lam.loadDatafromID(HoKhauService_Tuan.getIDChuHo(p.getValue().getHoKhauModel_tuan().getIDHoKhau())).getHoTen());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
		);
		colSoThanhVien.setCellValueFactory(new PropertyValueFactory<HoKhauBean_Tuan,Integer>("SoTV"));
		colSDT.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean_Tuan, String> p) ->
						new ReadOnlyStringWrapper(p.getValue().getHoKhauModel_tuan().getSDT())
		);

		tvHoKhau.setItems(listValueTableView);
	}

	public void addHoKhau() throws ClassNotFoundException, SQLException, IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/hokhau/AddHoKhau_Tuan.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();
		listHoKhau = new HoKhauService_Tuan().getListHoKhau();
		setTrangThai();
		showHoKhau(temp_listHoKhauDangCuTru);
	}

	public void delHoKhau() throws ClassNotFoundException, SQLException {
		HoKhauBean_Tuan hk = tvHoKhau.getSelectionModel().getSelectedItem();

		//ko dc xoa ho khau da chuyen di
		if(cbTrangThai.getValue() == "Đã chuyển đi"){
			Alert alert = new Alert(Alert.AlertType.WARNING, "Hộ khẩu đã chuyển đi không thể xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// Ngc lai thi:
		// chua chon ho khau
		if (hk == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy chọn hộ khẩu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Khi xóa hộ khẩu, tất cả thành viên trong hộ đều sẽ bị xóa!",
					ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("Bạn có chắc chắn muốn xóa hộ khẩu này");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				HoKhauService_Tuan.delHoKhau(hk);
			}
		}

		showHoKhau(listHoKhau);
	}

	public void detailHoKhau() throws ClassNotFoundException, SQLException, IOException {

	}

	public void searchHoKhau() throws ClassNotFoundException, SQLException {
		ObservableList<HoKhauBean_Tuan> listValueTableView_tmp = FXCollections.observableArrayList();
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();
//		System.out.println(typeSearchString + "..." + keySearch);

//		 tim kiem thong tin theo lua chon da lay ra

		if(keySearch.length() == 0 ){
			tvHoKhau.setItems(listValueTableView);
			Alert alert = new Alert(Alert.AlertType.WARNING, "Thông tin bạn nhập chưa đúng!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		switch (typeSearchString) {
			case "Tên chủ hộ":
				listValueTableView_tmp.clear();
				for (HoKhauBean_Tuan hoKhauBean : listValueTableView) {
					if (NhanKhauService_Lam.loadDatafromID(HoKhauService_Tuan.getIDChuHo(hoKhauBean.getHoKhauModel_tuan().getIDHoKhau())).getHoTen().contains(keySearch)) {
						listValueTableView_tmp.add(hoKhauBean);
					}
				}
					break;
			case "SĐT liên hệ":
				listValueTableView_tmp.clear();
				for (HoKhauBean_Tuan hoKhauBean : listValueTableView) {
					if (hoKhauBean.getHoKhauModel_tuan().getSDT().contains(keySearch)) {
						listValueTableView_tmp.add(hoKhauBean);
					}
				}
				break;
				default:
					listValueTableView_tmp.clear();
					for (HoKhauBean_Tuan hoKhauBean : listValueTableView) {
						if (String.valueOf(hoKhauBean.getHoKhauModel_tuan().getIDHoKhau()).contains(keySearch)) {
							listValueTableView_tmp.add(hoKhauBean);
						}
					}
					break;
		}
		if(listValueTableView_tmp.size() == 0){
			showHoKhau(listValueTableView);
			Alert alert = new Alert(Alert.AlertType.WARNING, "Không có hộ khẩu nào, mời bạn nhập lại", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		tvHoKhau.setItems(listValueTableView_tmp);
	}
	@FXML
	public void updateHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		// lay ra nhan khau can update
		HoKhauBean_Tuan hoKhauBean = tvHoKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/hokhau/UpdateHoKhau_Lam.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 600, 600));
		UpdateHoKhau_Lam updateHoKhau = loader.getController();

		// bat loi truong hop khong hop le
		if (updateHoKhau == null)
			return;
		if (hoKhauBean == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn hộ khẩu cần sửa !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		if(cbTrangThai.getValue() == "Đã chuyển đi"){
			Alert alert = new Alert(Alert.AlertType.WARNING, "Hộ khẩu đã chuyển đi không thể sửa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		updateHoKhau.setHoKhauModel(hoKhauBean);

		stage.setResizable(false);
		stage.showAndWait();
		listHoKhau = new HoKhauService_Tuan().getListHoKhau();
		setTrangThai();
		showHoKhau(temp_listHoKhauDangCuTru);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			setTrangThai();
			showHoKhau(temp_listHoKhauDangCuTru);
			// thiet lap gia tri cho comboboxChooseSearch
			ObservableList<String> listComboBox = FXCollections.observableArrayList("ID Hộ khẩu", "Tên chủ hộ", "SĐT liên hệ");
			cbChooseSearch.setValue("ID Hộ khẩu");
			cbChooseSearch.setItems(listComboBox);
			// thiet lap gia tri cho comboboxTrangThai
			ObservableList<String> listComboBoxTrangThai = FXCollections.observableArrayList("Đang cư trú", "Đã chuyển đi");
			cbTrangThai.setValue("Đang cư trú");
			cbTrangThai.setItems(listComboBoxTrangThai);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setTrangThai(){
		temp_listHoKhauDangCuTru.clear();
		temp_listHoKhauChuyenDi.clear();
		for(HoKhauBean_Tuan hoKhauBean : listHoKhau) {
			if(hoKhauBean.getHoKhauModel_tuan().getNgayDi().equals("0001-01-01")) {
				temp_listHoKhauDangCuTru.add(hoKhauBean);
				System.out.println(hoKhauBean.getHoKhauModel_tuan().getNgayDi());
			}
			else {
				temp_listHoKhauChuyenDi.add(hoKhauBean);
			}
		}
	}

}
