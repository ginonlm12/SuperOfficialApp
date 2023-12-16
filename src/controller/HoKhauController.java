package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.hokhau.UpdateHoKhau_Lam;
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
import models.HoKhauBean_Tuan;
import services.HoKhauService_Tuan;
import services.NhanKhauService_Lam;



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
	ObservableList<HoKhauBean_Tuan> listValueTableView;
	private List<HoKhauBean_Tuan> listHoKhau = new HoKhauService_Tuan().getListHoKhau();

    public HoKhauController() throws SQLException, ClassNotFoundException {
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
		showHoKhau(listHoKhau);
	}

	public void delHoKhau() throws ClassNotFoundException, SQLException {
//		HoKhauModel hoKhauModel = tvHoKhau.getSelectionModel().getSelectedItem();
//
//		if (hoKhauModel == null) {
//			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn hộ khẩu bạn muốn xóa!", ButtonType.OK);
//			alert.setHeaderText(null);
//			alert.showAndWait();
//		} else {
//			Alert alert = new Alert(AlertType.WARNING, "Khi xóa hộ khẩu, tất cả thành viên trong hộ đều sẽ bị xóa!",
//					ButtonType.YES, ButtonType.NO);
//			alert.setHeaderText("Bạn có chắc chắn muốn xóa hộ khẩu này");
//			Optional<ButtonType> result = alert.showAndWait();
//
//			if (result.get() == ButtonType.NO) {
//				return;
//			} else {
//				/// tao map anh xa gia tri Id sang maHo
//				Map<Integer, Integer> mapIdToMaho = new HashMap<>();
//				List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
//				listQuanHe.forEach(quanhe -> {
//					mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
//				});
//
//				// xoa toan bo nhan khau trong ho khau
//				int idHoKhauDel = hoKhauModel.getMaHo(); // lay ra ma ho de so sanh
//				List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
//				listNhanKhauModels.stream().filter(nhankhau -> mapIdToMaho.get(nhankhau.getId()) == idHoKhauDel)
//						.forEach(nk -> {
//							try {
//								new NhanKhauService().del(nk.getId());
//							} catch (ClassNotFoundException | SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						});
//
//				// xoa ho khau
//				new HoKhauService().del(idHoKhauDel);
//			}
//		}
//
//		showHoKhau();
	}

	public void searchHoKhau() throws ClassNotFoundException, SQLException {
		ObservableList<HoKhauBean_Tuan> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();
//		System.out.println(typeSearchString + "..." + keySearch);

//		 tim kiem thong tin theo lua chon da lay ra
		listValueTableView_tmp = FXCollections.observableArrayList(HoKhauService_Tuan.getListHoKhau(keySearch, typeSearchString));

		if(keySearch.length() == 0 ||listValueTableView_tmp.size() ==  0){
			tvHoKhau.setItems(listValueTableView);
			Alert alert = new Alert(Alert.AlertType.WARNING, "Thông tin bạn nhập chưa đúng!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		switch (typeSearchString) {
			case "Tên chủ hộ":
				showHoKhau(listValueTableView_tmp);
				break;
			case "SĐT liên hệ":
				showHoKhau(listValueTableView_tmp);
				break;
			default:
				showHoKhau(listValueTableView_tmp);
		}
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
		updateHoKhau.setHoKhauModel(hoKhauBean);

		stage.setResizable(false);
		stage.showAndWait();
		listHoKhau = new HoKhauService_Tuan().getListHoKhau();
		showHoKhau(listHoKhau);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showHoKhau(listHoKhau);
			// thiet lap gia tri cho combobox
			ObservableList<String> listComboBox = FXCollections.observableArrayList("ID Hộ khẩu", "Tên chủ hộ", "SĐT liên hệ");
			cbChooseSearch.setValue("ID Hộ khẩu");
			cbChooseSearch.setItems(listComboBox);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
