package controller;

import controller.khoanthu.UpdateKhoanThu;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import models.KhoanThuModel;
import services.KhoanThuService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class KhoanThuController implements Initializable {
	@FXML
	private TableView<KhoanThuModel> tvKhoanThu;
	@FXML
	private TableColumn<KhoanThuModel, Integer> colIDKhoanThu;
	@FXML
	private TableColumn<KhoanThuModel, String> colTenKhoanThu;
	@FXML
	private TableColumn<KhoanThuModel, Double> colTSDienTich;
	@FXML
	private TableColumn<KhoanThuModel, Double> colTSSoThanhVien;
	@FXML
	private TableColumn<KhoanThuModel, Double> colCongThem;
	@FXML
	private TableColumn<KhoanThuModel, String> colNgayBatDau;
	@FXML
	private TableColumn<KhoanThuModel, String> colNgayKetThuc;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;
	private List<KhoanThuModel> listKhoanThu;
	private ObservableList<KhoanThuModel> listValueTableView;

	// viet phuong thuc hien cac khoan thu trong co so du lieu
	public void hienKhoanThu() throws ClassNotFoundException, SQLException {
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listValueTableView = FXCollections.observableArrayList(listKhoanThu);
		
		colIDKhoanThu.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, Integer>("IDKhoanThu"));
		colTenKhoanThu.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("TenKT"));
		colTSDienTich.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, Double>("TrongSoDienTich"));
		colTSSoThanhVien.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, Double>("TrongSoSTV"));
		colCongThem.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, Double>("HangSo"));
		colNgayBatDau.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("NgayBatDau"));
		colNgayKetThuc.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("NgayKetThuc"));

		tvKhoanThu.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên khoản thu", "ID khoản thu");
		cbChooseSearch.setValue("Tên khoản thu");
		cbChooseSearch.setItems(listComboBox);
	}

	// Tim kiem khoan thu
	public void searchKhoanThu() {
		ObservableList<KhoanThuModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Tên khoản thu": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvKhoanThu.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<KhoanThuModel> listKhoanThuModelsSearch = new ArrayList<>();
			for (KhoanThuModel khoanThuModel : listKhoanThu) {
				if (khoanThuModel.getTenKT().contains(keySearch)) {
					listKhoanThuModelsSearch.add(khoanThuModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuModelsSearch);
			tvKhoanThu.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvKhoanThu.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { // truong hop con lai : tim theo ma khoan thu
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvKhoanThu.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.INFORMATION, "Bạn cần nhập vào thông tin tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			// kiem tra thong tin tim kiem co hop le hay khong
			Pattern pattern = Pattern.compile("\\d{1,}");
			if (!pattern.matcher(keySearch).matches()) {
				Alert alert = new Alert(AlertType.WARNING, "Bạn phải nhập vào 1 số!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}

			for (KhoanThuModel khoanThuModel : listKhoanThu) {
				if (khoanThuModel.getIDKhoanThu() == Integer.parseInt(keySearch)) {
					listValueTableView_tmp = FXCollections.observableArrayList(khoanThuModel);
					tvKhoanThu.setItems(listValueTableView_tmp);
					return;
				}
			}

			// khong tim thay thong tin -> thong bao toi nguoi dung
			tvKhoanThu.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy thông tin!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		}
	}

	public void addKhoanThu() throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/khoanthu/AddKhoanThu.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();
		hienKhoanThu();
	}

	public void delKhoanThu() throws ClassNotFoundException, SQLException {
		KhoanThuModel khoanThuModel = tvKhoanThu.getSelectionModel().getSelectedItem();

		if (khoanThuModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn khoản thu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc chắn muốn xóa khoản thu này!", ButtonType.YES,
					ButtonType.NO);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				new KhoanThuService().del(khoanThuModel.getIDKhoanThu());
			}
		}
		hienKhoanThu();
	}

	public void updateKhoanThu() throws ClassNotFoundException, SQLException, IOException {
		// lay ra nhan khau can update
		KhoanThuModel khoanThuModel = tvKhoanThu.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/khoanthu/UpdateKhoanThu.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		UpdateKhoanThu updateKhoanThu = loader.getController();

		// bat loi truong hop khong hop le
		if (updateKhoanThu == null) return;
		if (khoanThuModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn khoản thu update !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		updateKhoanThu.setKhoanThuModel(khoanThuModel);

		stage.setResizable(false);
		stage.showAndWait();
		hienKhoanThu();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			hienKhoanThu();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
