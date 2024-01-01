package controller;

import controller.hokhau.DetailHoKhau;
import controller.hokhau.UpdateHoKhau;
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
import models.HoKhauBean;
import services.HoKhauService;
import services.NhanKhauService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class HoKhauController implements Initializable {
	private final List<HoKhauBean> temp_listHoKhauDangCuTru = new ArrayList<>();
	private final List<HoKhauBean> temp_listHoKhauChuyenDi = new ArrayList<>();
	@FXML
	TableColumn<HoKhauBean, Integer> colMaHoKhau;
	@FXML
	TableColumn<HoKhauBean, String> colTenCH;
	@FXML
	TableColumn<HoKhauBean, Integer> colSoThanhVien;
	@FXML
	TableColumn<HoKhauBean, String> colSDT;
	@FXML
	TextField tfSearch;

	@FXML
	ComboBox<String> cbChooseSearch;

	@FXML
    ComboBox<String> cbTrangThai;
	@FXML
	TableColumn<HoKhauBean, Integer> colSoPhong;
	@FXML
	TableView<HoKhauBean> tvHoKhau;
	ObservableList<HoKhauBean> listValueTableView;
	private List<HoKhauBean> listHoKhau = HoKhauService.getListHoKhau();

	public HoKhauController() throws SQLException, ClassNotFoundException {
	}

	public void ShowHoKhauTable() throws ClassNotFoundException, SQLException {
		setTrangThai();

        if (cbTrangThai.getValue() == "Đang cư trú") {
			colSoPhong.setText("Số phòng");
			showHoKhau(temp_listHoKhauDangCuTru);
        } else {
			colSoPhong.setText("Số phòng (đã ở)");
			showHoKhau(temp_listHoKhauChuyenDi);
		}
	}

	// Hien thi thong tin ho khau
	public void showHoKhau(List<HoKhauBean> listHoKhau) throws ClassNotFoundException, SQLException {
//		listHoKhau = new HoKhauService().getListHoKhau();
		listValueTableView = FXCollections.observableArrayList(listHoKhau);

		// colMaHoKhau get value from HoKhauBean.HoKhauModel.maHo, write it for me

		colMaHoKhau.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean, Integer> p) ->
						new ReadOnlyIntegerWrapper(p.getValue().getHoKhauModel_tuan().getIDHoKhau()).asObject()
		);

		colTenCH.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean, String> p) ->
				{
					try {
						return new ReadOnlyStringWrapper(NhanKhauService.loadDatafromID(HoKhauService.getIDChuHo(p.getValue().getHoKhauModel_tuan().getIDHoKhau())).getHoTen());
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
		);
		colSoThanhVien.setCellValueFactory(new PropertyValueFactory<HoKhauBean, Integer>("SoTV"));
		colSDT.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean, String> p) ->
						new ReadOnlyStringWrapper(p.getValue().getHoKhauModel_tuan().getSDT())
		);
		colSoPhong.setCellValueFactory(
				(TableColumn.CellDataFeatures<HoKhauBean, Integer> p) ->
						new ReadOnlyIntegerWrapper(p.getValue().getHoKhauModel_tuan().getSoPhong()).asObject()
		);

		tvHoKhau.setItems(listValueTableView);
	}

	public void addHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/hokhau/AddHoKhau.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 670, 570));
		stage.setResizable(false);
		stage.showAndWait();
		listHoKhau = new HoKhauService().getListHoKhau();

        reload();
	}

	public void delHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		HoKhauBean hk = tvHoKhau.getSelectionModel().getSelectedItem();

		//ko dc xoa ho khau da chuyen di
        if (cbTrangThai.getValue() == "Đã chuyển đi") {
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
				HoKhauService.delHoKhau(hk);
			}
		}

		reload();
	}

	public void detailHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
// lay ra nhan khau can update
		HoKhauBean hoKhauBean = tvHoKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/hokhau/DetailHoKhau.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 680, 540));
		DetailHoKhau detailHoKhau = loader.getController();

		// bat loi truong hop khong hop le
		if (detailHoKhau == null)
			return;
		if (hoKhauBean == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn hộ khẩu cần xem !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		detailHoKhau.setHoKhauModel(hoKhauBean);

		stage.setResizable(true);
		stage.showAndWait();
		listHoKhau = new HoKhauService().getListHoKhau();
		setTrangThai();
		showHoKhau(temp_listHoKhauDangCuTru);
		cbTrangThai.setValue("Đang cư trú");
	}

	public void searchHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		ObservableList<HoKhauBean> listValueTableView_tmp = FXCollections.observableArrayList();
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();
//		System.out.println(typeSearchString + "..." + keySearch);

//		 tim kiem thong tin theo lua chon da lay ra

        if (keySearch.length() == 0) {
			tvHoKhau.setItems(listValueTableView);
			Alert alert = new Alert(Alert.AlertType.WARNING, "Thông tin bạn nhập chưa đúng!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		switch (typeSearchString) {
			case "Tên chủ hộ":
				listValueTableView_tmp.clear();
				for (HoKhauBean hoKhauBean : listValueTableView) {
					if (NhanKhauService.loadDatafromID(HoKhauService.getIDChuHo(hoKhauBean.getHoKhauModel_tuan().getIDHoKhau())).getHoTen().contains(keySearch)) {
						listValueTableView_tmp.add(hoKhauBean);
					}
				}
				break;
			case "SĐT liên hệ":
				listValueTableView_tmp.clear();
				for (HoKhauBean hoKhauBean : listValueTableView) {
					if (hoKhauBean.getHoKhauModel_tuan().getSDT().contains(keySearch)) {
						listValueTableView_tmp.add(hoKhauBean);
					}
				}
				break;
			default:
				listValueTableView_tmp.clear();
				for (HoKhauBean hoKhauBean : listValueTableView) {
					if (String.valueOf(hoKhauBean.getHoKhauModel_tuan().getIDHoKhau()).contains(keySearch)) {
						listValueTableView_tmp.add(hoKhauBean);
					}
				}
				break;
		}
        if (listValueTableView_tmp.size() == 0) {
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
		HoKhauBean hoKhauBean = tvHoKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/hokhau/UpdateHoKhau.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 600, 550));
		UpdateHoKhau updateHoKhau = loader.getController();
// bat loi truong hop khong hop le
		if (updateHoKhau == null)
			return;
		if (hoKhauBean == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Chọn hộ khẩu cần sửa !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

        if (cbTrangThai.getValue() == "Đã chuyển đi") {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Hộ khẩu đã chuyển đi không thể sửa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		updateHoKhau.setHoKhauModel(hoKhauBean);

		stage.setResizable(false);
		stage.showAndWait();

        reload();
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

    public void setTrangThai() {
		temp_listHoKhauDangCuTru.clear();
		temp_listHoKhauChuyenDi.clear();
		for (HoKhauBean hoKhauBean : listHoKhau) {
            if (hoKhauBean.getHoKhauModel_tuan().getNgayDi().equals("0001-01-01")) {
				temp_listHoKhauDangCuTru.add(hoKhauBean);
//				System.out.println(hoKhauBean.getHoKhauModel_tuan().getNgayDi());
            } else {
				temp_listHoKhauChuyenDi.add(hoKhauBean);
			}
		}
	}

    public void reload() throws SQLException, ClassNotFoundException {
		listHoKhau = HoKhauService.getListHoKhau();
        setTrangThai();
        showHoKhau(temp_listHoKhauDangCuTru);
        cbTrangThai.setValue("Đang cư trú");
    }
}