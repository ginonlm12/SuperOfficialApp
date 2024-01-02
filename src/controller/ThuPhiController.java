package controller;

import controller.thuphi.UpdateThuPhi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ThuPhiBean;
import services.HoKhauService;
import services.ThuPhiService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ThuPhiController implements Initializable {
	@FXML
	TableView<ThuPhiBean> tvThuPhi;
	@FXML
	TableColumn<ThuPhiBean, String> colTenKhoanPhi;
	@FXML
	TableColumn<ThuPhiBean, String> colTenChuHo;
	@FXML
	TableColumn<ThuPhiBean, String> colPhaiDong;
	@FXML
	TableColumn<ThuPhiBean, String> colDaDong;
	@FXML
	TableColumn<ThuPhiBean, String> colNgayThu;
	@FXML
	TextField tfSearch;
	@FXML
	ComboBox<String> cbChooseSearch;
	private List<ThuPhiBean> listThuPhi;
	private ObservableList<ThuPhiBean> listValueTableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			hienThuPhi();
			// thiet lap gia tri cho combobox
			ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên khoản thu", "Tên chủ hộ");
			cbChooseSearch.setValue("Tên khoản thu");
			cbChooseSearch.setItems(listComboBox);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// viet phuong thuc hien cac khoan thu trong co so du lieu
	public void hienThuPhi() throws ClassNotFoundException, SQLException {
		listThuPhi = new ThuPhiService().getListThuPhi();
		listValueTableView = FXCollections.observableArrayList(listThuPhi);

		// colIDKhoanThu.setCellValueFactory(new PropertyValueFactory<ThuPhiBean,
		// Integer>("IDKhoanThu"));
		colTenKhoanPhi.setCellValueFactory(new PropertyValueFactory<ThuPhiBean, String>("tenKhoanThu"));
		colTenChuHo.setCellValueFactory(new PropertyValueFactory<ThuPhiBean, String>("tenChuHo"));
		colPhaiDong.setCellValueFactory(new PropertyValueFactory<ThuPhiBean, String>("soTienPhaiDong"));
		colDaDong.setCellValueFactory(new PropertyValueFactory<ThuPhiBean, String>("soTienDong"));
		colNgayThu.setCellValueFactory(new PropertyValueFactory<ThuPhiBean, String>("ngayDong"));

		tvThuPhi.setItems(listValueTableView);
	}

	// Tim kiem khoan thu
	public void searchThuPhi() throws ClassNotFoundException, SQLException {
		ObservableList<ThuPhiBean> listValueTableView_tmp;
		List<ThuPhiBean> listThuPhiBeansSearch = new ArrayList<>();
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// neu khong nhap gi -> thong bao loi
				if (keySearch.length() == 0) {
					tvThuPhi.setItems(listValueTableView);
					Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}

		// tim kiem thong tin theo lua chon da lay ra
		int index = 0;
		switch (typeSearchString) {
			case "Tên khoản thu": {
				for (ThuPhiBean thuPhiBean : listThuPhi) {
					if (thuPhiBean.getTenKhoanThu().contains(keySearch)) {
						listThuPhiBeansSearch.add(thuPhiBean);
						index++;
					}
				}
				break;
			}
			case "ID khoản thu": {
				// kiem tra thong tin tim kiem co hop le hay khong
				Pattern pattern = Pattern.compile("\\d{1,}");
				if (!pattern.matcher(keySearch).matches()) {
					Alert alert = new Alert(AlertType.WARNING, "Bạn phải nhập vào 1 số!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}

				for (ThuPhiBean thuPhiBean : listThuPhi) {
					// kiem tra xem ID khoan thu co chua keySearch hay khong
					if (String.valueOf(thuPhiBean.getThuPhiModel().getIDKhoanThu()).contains(keySearch)) {
						listThuPhiBeansSearch.add(thuPhiBean);
						index++;
					}
				}
			}
			case "Tên chủ hộ": {
				for (ThuPhiBean thuPhiBean : listThuPhi) {
					if (thuPhiBean.getTenChuHo().contains(keySearch)) {
						listThuPhiBeansSearch.add(thuPhiBean);
						index++;
					}
				}
				break;
			}
			case "ID hộ khẩu": {
				// kiem tra keySearch co phai la so nguyen hay khong
				Pattern pattern = Pattern.compile("\\d{1,}");
				if (!pattern.matcher(keySearch).matches()) {
					Alert alert = new Alert(AlertType.WARNING, "Bạn phải nhập vào 1 số!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
				}

				for (ThuPhiBean thuPhiBean : listThuPhi) {
					// kiem tra xem id ho khau co chua keySearch hay khong
					if (String.valueOf(thuPhiBean.getThuPhiModel().getIDHoKhau()).contains(keySearch)) {
						listThuPhiBeansSearch.add(thuPhiBean);
						index++;
					}
				}
				break;
			}
			default: { // truong hop con lai : tim theo so phong
				// kiem tra thong tin tim kiem co phai so nguyen khong
				Pattern pattern = Pattern.compile("\\d{1,}");
				if (!pattern.matcher(keySearch).matches()) {
					Alert alert = new Alert(AlertType.WARNING, "Bạn phải nhập vào 1 số!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
				}

				for (ThuPhiBean thuPhiBean : listThuPhi) {
					// kiem tra xem so phong co chua keySearch hay khong
					if (String.valueOf(HoKhauService.getHoKhau(thuPhiBean.getThuPhiModel().getIDHoKhau()).getSoPhong()).contains(keySearch)) {
						listThuPhiBeansSearch.add(thuPhiBean);
						index++;
					}
				}
			}
		}
		// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
		// tim thay
		if (index == 0) {
			tvThuPhi.setItems(listValueTableView); // hien thi toan bo thong tin
			Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}

		listValueTableView_tmp = FXCollections.observableArrayList(listThuPhiBeansSearch);
				tvThuPhi.setItems(listValueTableView_tmp);

	}

	public void addThuPhi() throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/thuphi/AddThuPhi.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 700, 430));
		stage.setResizable(false);
		stage.showAndWait();
		hienThuPhi();
	}

	public void delThuPhi() throws ClassNotFoundException, SQLException {
		ThuPhiBean ThuPhiBean = tvThuPhi.getSelectionModel().getSelectedItem();

		if (ThuPhiBean == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn mục thu phí bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc chắn muốn xóa mục thu phí này!", ButtonType.YES,
					ButtonType.NO);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				new ThuPhiService().del(ThuPhiBean.getThuPhiModel());
			}
		}
		hienThuPhi();
	}

	public void updateThuPhi() throws ClassNotFoundException, SQLException, IOException {
		// lay ra thi phi can update
		ThuPhiBean thuPhiBean = tvThuPhi.getSelectionModel().getSelectedItem();
		
		if (thuPhiBean == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn mục thu phí bạn muốn cập nhật!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/thuphi/UpdateThuPhi.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 700, 430));
		UpdateThuPhi updateThuPhi = loader.getController();
		updateThuPhi.setThuPhiBean(thuPhiBean);

		stage.setResizable(false);
		stage.showAndWait();
		hienThuPhi();
	}
}