package controller.thuphi;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.NhanKhauModel;
import services.HoKhauService;
import services.KhoanThuService;
import services.NhanKhauService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class XemHoChuaDongDu {
	@FXML
	private TableView<NhanKhauModel> tvNhanKhau;
	@FXML
	private TableColumn<NhanKhauModel, String> colIDHoKhau;
	@FXML
	private TableColumn<NhanKhauModel, String> colTen;
	@FXML
	private TableColumn<NhanKhauModel, String> colPhong;
	@FXML
	private TableColumn<NhanKhauModel, String> colCCCD;
	@FXML
	private TableColumn<NhanKhauModel, String> colSDT;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;

    private int IDKhoanThu;
	private ObservableList<NhanKhauModel> listValueTableView;
	private List<NhanKhauModel> listNhanKhau;

    public void setIDKhoanThu(int IDKhoanThu) throws ClassNotFoundException, SQLException {
        this.IDKhoanThu = IDKhoanThu;
        // Thiết lập giá trị cho ComboBox
        ObservableList<String> listComboBox = FXCollections.observableArrayList("Họ tên", "Số phòng", "ID hộ khẩu");
        cbChooseSearch.setValue("Họ tên");
        cbChooseSearch.setItems(listComboBox);

        showNhanKhau();
    }

	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = NhanKhauService.getListChuHoChuaDongDu(IDKhoanThu);
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);
		KhoanThuModel khoanThuModel = KhoanThuService.getKhoanThu(IDKhoanThu);

		for (NhanKhauModel nhankhau : listNhanKhau) {
			nhankhau.setSoPhong(NhanKhauService.getSoPhong(nhankhau.getIDHoKhau()));
			if (AddThuPhi.tinhSoTienPhaiDong(khoanThuModel, nhankhau).equals(0.0)) {
				listValueTableView.remove(nhankhau);
			}
		}

		if (listValueTableView.size() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION, "Tất cả các hộ đều đã đóng", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
			// dong cua so hien tai
			Stage stage = (Stage) tvNhanKhau.getScene().getWindow();
			stage.close();
            return;
        }

		// thiet lap cac cot cho tableviews
		colIDHoKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("IDHoKhau"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("HoTen"));
		colPhong.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("SoPhong"));
		colCCCD.setCellValueFactory(
				(TableColumn.CellDataFeatures<NhanKhauModel, String> p) ->
				{
					try {
						return new ReadOnlyStringWrapper(String.valueOf(AddThuPhi.tinhSoTienPhaiDong(khoanThuModel, p.getValue())));
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
		);
		colSDT.setCellValueFactory(
				(TableColumn.CellDataFeatures<NhanKhauModel, String> p) ->
				{
					try {
						return new ReadOnlyStringWrapper(HoKhauService.getHoKhau(NhanKhauService.getIDHoKhau(p.getValue().getIDNhanKhau())).getSDT());
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
		);

		tvNhanKhau.setItems(listValueTableView);
	}

	public void searchNhanKhau() {
		ObservableList<NhanKhauModel> listValueTableView_tmp;
		List<NhanKhauModel> listNhanhKhauModelsSearch = new ArrayList<>();
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
				for (NhanKhauModel NhanKhauModel : listNhanKhau) {
					if (NhanKhauModel.getHoTen().contains(keySearch)) {
						listNhanhKhauModelsSearch.add(NhanKhauModel);
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

				for (NhanKhauModel NhanKhauModel : listNhanKhau) {
					// kiem tra xem id ho khau co chua keySearch hay khong
					if (String.valueOf(NhanKhauModel.getIDHoKhau()).contains(keySearch)) {
						listNhanhKhauModelsSearch.add(NhanKhauModel);
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

				for (NhanKhauModel nhanKhauModel : listNhanKhau) {
					// kiem tra xem so phong co chua keySearch hay khong
					if (String.valueOf(nhanKhauModel.getSoPhong()).contains(keySearch)) {
						listNhanhKhauModelsSearch.add(nhanKhauModel);
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
}
