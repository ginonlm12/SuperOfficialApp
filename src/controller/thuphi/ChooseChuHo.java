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
	private TableColumn<NhanKhauModel_Lam, String> colPhong;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showNhanKhau();
			// Thiết lập giá trị cho ComboBox
			ObservableList<String> listComboBox = FXCollections.observableArrayList("Họ tên", "Số phòng", "ID hộ khẩu");
			cbChooseSearch.setValue("Họ tên");
			cbChooseSearch.setItems(listComboBox);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

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
		colPhong.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("SoPhong"));
		colCCCD.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("CCCD"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("NgheNghiep"));

		tvNhanKhau.setItems(listValueTableView);
	}

	public void searchNhanKhau() {
		ObservableList<NhanKhauModel_Lam> listValueTableView_tmp;
		List<NhanKhauModel_Lam> listNhanhKhauModelsSearch = new ArrayList<>();
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// neu khong nhap gi -> thong bao loi
		if (keySearch.length() == 0) {
			tvNhanKhau.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		int index = 0;

		switch (typeSearchString) {
			case "Họ tên": {
				for (NhanKhauModel_Lam NhanKhauModel_Lam : listNhanKhau) {
					if (NhanKhauModel_Lam.getHoTen().contains(keySearch)) {
						listNhanhKhauModelsSearch.add(NhanKhauModel_Lam);
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

				for (NhanKhauModel_Lam NhanKhauModel_Lam : listNhanKhau) {
					// kiem tra xem id ho khau co chua keySearch hay khong
					if (String.valueOf(NhanKhauModel_Lam.getIDHoKhau()).contains(keySearch)) {
						listNhanhKhauModelsSearch.add(NhanKhauModel_Lam);
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

				for (NhanKhauModel_Lam nhanKhauModel_Lam : listNhanKhau) {
					// kiem tra xem so phong co chua keySearch hay khong
					if (String.valueOf(nhanKhauModel_Lam.getSoPhong()).contains(keySearch)) {
						listNhanhKhauModelsSearch.add(nhanKhauModel_Lam);
						index++;
					}
				}
			}
		}
		// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
		if (index == 0) {
			tvNhanKhau.setItems(listValueTableView); // hien thi toan bo thong tin
			Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		listValueTableView_tmp = FXCollections.observableArrayList(listNhanhKhauModelsSearch);
		tvNhanKhau.setItems(listValueTableView_tmp);
	}

	public void xacnhan(ActionEvent event) {
		nhanKhauChoose = tvNhanKhau.getSelectionModel().getSelectedItem();
		if (nhanKhauChoose == null) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng chọn chủ hộ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		setNhanKhauChoose(nhanKhauChoose);

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
