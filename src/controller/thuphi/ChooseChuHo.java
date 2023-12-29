package controller.thuphi;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.NhanKhauModel_Lam;
import services.NhanKhauService_Lam;

public class ChooseChuHo implements Initializable {
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colMaNhanKhau;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colTen;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colTuoi;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colCMND;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colSDT;
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colMaHo;
	@FXML
	private TableView<NhanKhauModel_Lam> tvNhanKhau;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;

	private NhanKhauModel_Lam nhanKhauChoose;

	public NhanKhauModel_Lam getNhanKhauChoose() {
		return nhanKhauChoose;
	}

	public void setNhanKhauChoose(NhanKhauModel_Lam nhanKhauChoose) {
		this.nhanKhauChoose = nhanKhauChoose;
	}

	private ObservableList<NhanKhauModel_Lam> listValueTableView;
	private List<NhanKhauModel_Lam> listNhanKhau;

	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = new NhanKhauService_Lam().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);

		// tao map anh xa gia tri Id sang maHo
		Map<Integer, Integer> mapIdToMaho = new HashMap<>();
		List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
		listQuanHe.forEach(quanhe -> {
			mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
		});

		// thiet lap cac cot cho tableviews
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("id"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("ten"));
		colTuoi.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("tuoi"));
		colCMND.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("cmnd"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("sdt"));
		try {
			colMaHo.setCellValueFactory((CellDataFeatures<NhanKhauModel_Lam, String> p) -> new ReadOnlyStringWrapper(
					mapIdToMaho.get(p.getValue().getId()).toString()));
		} catch (Exception e) {
			// TODO: handle exception
		}

		tvNhanKhau.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên", "Tuổi", "id");
		cbChooseSearch.setValue("Tên");
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
		case "Tên": {
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
				if (NhanKhauModel_Lam.getTen().contains(keySearch)) {
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
		case "Tuổi": {
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
				if (NhanKhauModel_Lam.getTuoi() == Integer.parseInt(keySearch)) {
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
				if (NhanKhauModel_Lam.getId() == Integer.parseInt(keySearch)) {
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
