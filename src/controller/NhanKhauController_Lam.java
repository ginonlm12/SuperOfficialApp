package controller;

import controller.nhankhau.ShowChiTiet_Lam;
import controller.nhankhau.UpdateNhanKhau_Lam;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.NhanKhauModel_Lam;
import services.NhanKhauService_Lam;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NhanKhauController_Lam implements Initializable {
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colMaNhanKhau; // Thay bang IDNhanKhau
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colTen; // Thay bang HoTen
	@FXML
	private TableColumn<NhanKhauModel_Lam, Integer> colTuoi; // Thay bang IDHoKhau
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colCMND; // Thay bang CCCD
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colSDT; // Thay bang NgheNghiep
	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colMaHo; // Thay bang NgaySinh

	@FXML
	private TableColumn<NhanKhauModel_Lam, String> colQHvsChuHo;
	@FXML
	private TableView<NhanKhauModel_Lam> tvNhanKhau;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;

	private ObservableList<NhanKhauModel_Lam> listValueTableView;
	private List<NhanKhauModel_Lam> listNhanKhau;
	private int ID_dexemchitiet;

	public TableView<NhanKhauModel_Lam> getTvNhanKhau() {
		return tvNhanKhau;
	}

	public void setTvNhanKhau(TableView<NhanKhauModel_Lam> tvNhanKhau) {
		this.tvNhanKhau = tvNhanKhau;
	}

	// hien thi thong tin nhan khau
	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = new NhanKhauService_Lam().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);
		for (NhanKhauModel_Lam nhankhau : listNhanKhau) {
			nhankhau.setSoPhong(NhanKhauService_Lam.getSoPhong(nhankhau.getIDHoKhau()));
		}

		// tao map anh xa gia tri Id sang maHo
		//Map<Integer, Integer> mapIdToMaho = new HashMap<>();
		//List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
//		listQuanHe.forEach(quanhe -> {
//			mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
//		});

		// thiet lap cac cot cho tableviews
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("IDNhanKhau"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("HoTen"));
		colTuoi.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, Integer>("SoPhong"));
		colCMND.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("CCCD"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("NgheNghiep"));
		colMaHo.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("NgaySinh"));
		colQHvsChuHo.setCellValueFactory(new PropertyValueFactory<NhanKhauModel_Lam, String>("QHvsChuHo"));
//		try {
//			colMaHo.setCellValueFactory(
//					(CellDataFeatures<NhanKhauModel_Lam, String> p) -> new ReadOnlyStringWrapper(mapIdToMaho.get(p.getValue().getId()).toString())
//			);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

		tvNhanKhau.setItems(listValueTableView);

		// Thiết lập giá trị cho ComboBox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Họ Tên", "Số Phòng", "ID Nhân khẩu");
		cbChooseSearch.setValue("Họ Tên");
		cbChooseSearch.setItems(listComboBox);
	}

	// tim kiem nhan khau theo ten, tuoi, id
	public void searchNhanKhau() {
		ObservableList<NhanKhauModel_Lam> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
			case "Họ Tên": {
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
				for (NhanKhauModel_Lam nhanKhauModel : listNhanKhau) {
					if (nhanKhauModel.getHoTen().contains(keySearch)) {
						listNhanhKhauModelsSearch.add(nhanKhauModel);
						index++;
					}
				}
				listValueTableView_tmp = FXCollections.observableArrayList(listNhanhKhauModelsSearch);
				tvNhanKhau.setItems(listValueTableView_tmp);

				// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong tim thay
				if (index == 0) {
					tvNhanKhau.setItems(listValueTableView); // hien thi toan bo thong tin
					Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
				}
				break;
			}
			case "Số Phòng": {
				// neu khong nhap gi -> thong bao loi
				if (keySearch.length() == 0) {
					tvNhanKhau.setItems(listValueTableView);
					Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					break;
				}

				// kiem tra chuoi nhap vao co phai la chuoi hop le hay khong
				Pattern pattern = Pattern.compile("^[1-9]\\d*$");
				if (!pattern.matcher(keySearch).matches()) {
					Alert alert = new Alert(AlertType.WARNING, "Số phòng phải là số nguyên dương!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					break;
				}

				int index = 0;
				List<NhanKhauModel_Lam> listNhanKhau_tmp = new ArrayList<>();
				for (NhanKhauModel_Lam nhanKhauModel : listNhanKhau) {
					if (nhanKhauModel.getSoPhong() == Integer.parseInt(keySearch)) {
						listNhanKhau_tmp.add(nhanKhauModel);
						index++;
					}
				}
				listValueTableView_tmp = FXCollections.observableArrayList(listNhanKhau_tmp);
				tvNhanKhau.setItems(listValueTableView_tmp);

				// neu khong tim thay thong tin tim kiem -> thong bao toi nguoi dung
				if (index == 0) {
					tvNhanKhau.setItems(listValueTableView); // hien thi toan bo thong tin
					Alert alert = new Alert(AlertType.INFORMATION, "Không tồn tại nhân khẩu thuộc hộ khẩu này", ButtonType.OK);
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
				Pattern pattern = Pattern.compile("^[1-9]\\d*$");
				if (!pattern.matcher(keySearch).matches()) {
					Alert alert = new Alert(AlertType.WARNING, "Bạn phải nhập vào 1 số!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					break;
				}

				for (NhanKhauModel_Lam nhanKhauModel : listNhanKhau) {
					if (nhanKhauModel.getIDNhanKhau() == Integer.parseInt(keySearch)) {
						listValueTableView_tmp = FXCollections.observableArrayList(nhanKhauModel);
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

	@FXML
	public void addNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		// return ;
		Parent home = FXMLLoader.load(getClass().getResource("/views/nhankhau/AddNhanKhau_Lam.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();
		showNhanKhau();
	}

	// con truong hop neu xoa chu ho chua xet
	@FXML
	public void delNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		NhanKhauModel_Lam nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();
		// int maho = 0;

		if (nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn nhân khẩu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			// kiem tra dieu kien chu ho
			// List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
			if (nhanKhauModel.getQHvsChuHo().equals("Chủ hộ")) {
				Alert alert = new Alert(AlertType.WARNING, "Bạn không thể xóa chủ hộ tại đây, hãy xóa chủ hộ tại mục hộ khẩu!", ButtonType.OK);
				alert.setHeaderText("Nhân khẩu này là 1 chủ hộ!");
				alert.showAndWait();
				return;
			}

			Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc chắn muốn xóa nhân khẩu này!", ButtonType.YES, ButtonType.NO);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				new NhanKhauService_Lam().del(nhanKhauModel.getIDNhanKhau());
			}
		}
		showNhanKhau();
	}

	@FXML
	public void updateNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		// lay ra nhan khau can update
		NhanKhauModel_Lam nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/UpdateNhanKhau_Lam.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		UpdateNhanKhau_Lam updateNhanKhau = loader.getController();

		// bat loi truong hop khong hop le
		if (updateNhanKhau == null) return;
		if (nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn nhân khẩu cần update !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		updateNhanKhau.setNhanKhauModel(nhanKhauModel);

		stage.setResizable(false);
		stage.showAndWait();
		showNhanKhau();
	}

	public int getID_dexemchitiet() {
		return ID_dexemchitiet;
	}

	public void setID_dexemchitiet(int ID_dexemchitiet) {
		this.ID_dexemchitiet = ID_dexemchitiet;
	}

	@FXML
	void showChiTiet(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		// lay ra nhan khau can xem chi tiet
		NhanKhauModel_Lam nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();
		// Lấy cái code ni đưa vào button Xem thông tin của Tuấn này
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/ShowChiTiet_Lam.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		ShowChiTiet_Lam showChiTiet = loader.getController();

		// bat loi truong hop khong hop le
		if (showChiTiet == null) return;
		if (nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn nhân khẩu cần update !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		showChiTiet.setNhanKhauModel(nhanKhauModel);

		stage.setResizable(false);
		stage.show();
		//stage.showAndWait();
		showNhanKhau();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			showNhanKhau();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
