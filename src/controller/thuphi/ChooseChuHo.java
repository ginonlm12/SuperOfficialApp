package controller.thuphi;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.NhanKhauModel_Lam;
import services.NhanKhauService_Lam;

public class ChooseChuHo implements Initializable {
	@FXML
	private TableView<NhanKhauModel_Lam> tvNhanKhau;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colIDHoKhau;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colTen;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colNgaySinh;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colCCCD;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colSDT;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;

	private NhanKhauModel_Lam nhanKhauChoose;
	private ObservableList<NhanKhauModel_Lam> listValueTableView;
	private List<NhanKhauModel_Lam> listNhanKhau;

	public NhanKhauModel_Lam getNhanKhauChoose() {
		return nhanKhauChoose;
	}

	public void setNhanKhauChoose(NhanKhauModel_Lam nhanKhauChoose) {
		this.nhanKhauChoose = nhanKhauChoose;
	}

	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = NhanKhauService_Lam.getListChuHo();
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);
		for (NhanKhauModel_Lam nhankhau : listNhanKhau) {
			nhankhau.setSoPhong(NhanKhauService_Lam.getSoPhong(nhankhau.getIDHoKhau()));
		}

		// thiet lap cac cot cho tableviews
		colIDHoKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("IDHoKhau"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("HoTen"));
		colNgaySinh.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("NgaySinh"));
		colCCCD.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("CCCD"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("NgheNghiep"));

		tvNhanKhau.setItems(listValueTableView);

		// Thiết lập giá trị cho ComboBox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Họ tên", "Số phòng", "ID hộ khẩu");
		cbChooseSearch.setValue("Họ tên");
		cbChooseSearch.setItems(listComboBox);
	}

	public void searchNhanKhau() {
		ObservableList<NhanKhauModel_Lam> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Họ tên": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<NhanKhauModel_Lam> listNhanhKhauModelsSearch = new ArrayList<>();
			for (NhanKhauModel_Lam NhanKhauModel_Lam : listNhanKhau) {
				if (NhanKhauModel_Lam.getHoTen().contains(keySearch)) {
					listNhanhKhauModelsSearch.add(NhanKhauModel_Lam);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNhanhKhauModelsSearch);
			tvNhanKhau.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvNhanKhau.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		case "ID hộ khẩu": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			// kiem tra chuoi nhap vao co phai la chuoi hop le hay khong
			Pattern pattern = Pattern.compile("\\d{1,}");
			if (!pattern.matcher(keySearch).matches()) {
				Alert alert = new Alert(AlertType.WARNING, "Tuổi nhập vào phải là 1 số!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<NhanKhauModel_Lam> listNhanKhau_tmp = new ArrayList<>();
			for (NhanKhauModel_Lam NhanKhauModel_Lam : listNhanKhau) {
				if (NhanKhauModel_Lam.getIDHoKhau() == Integer.parseInt(keySearch)) {
					listNhanKhau_tmp.add(NhanKhauModel_Lam);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNhanKhau_tmp);
			tvNhanKhau.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin tim kiem -> thong bao toi nguoi dung
			if (index == 0) {
				tvNhanKhau.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { // truong hop con lai : tim theo id
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
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
			}

			for (NhanKhauModel_Lam NhanKhauModel_Lam : listNhanKhau) {
				if (NhanKhauModel_Lam.getSoPhong() == Integer.parseInt(keySearch)) {
					listValueTableView_tmp = FXCollections.observableArrayList(NhanKhauModel_Lam);
					tvNhanKhau.setItems(listValueTableView_tmp);
					return;
				}
			}

			// khong tim thay thong tin -> thong bao toi nguoi dung
			tvNhanKhau.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy thông tin!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		}
	}

	public void xacnhan(ActionEvent event) {
		nhanKhauChoose = tvNhanKhau.getSelectionModel().getSelectedItem();
		setNhanKhauChoose(nhanKhauChoose);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showNhanKhau();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
