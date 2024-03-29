package controller;

import controller.nhankhau.ShowChiTiet;
import controller.nhankhau.UpdateNhanKhau;
import controller.tamtrutamvang.AddTamVang;
import javafx.animation.RotateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.NhanKhauModel;
import services.NhanKhauService;
import services.TamVangService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class NhanKhauController implements Initializable {
	@FXML
	private TableColumn<NhanKhauModel, String> colMaNhanKhau; // Thay bang IDNhanKhau
	@FXML
	private TableColumn<NhanKhauModel, String> colTen; // Thay bang HoTen
	@FXML
	private TableColumn<NhanKhauModel, Integer> colTuoi; // Thay bang IDHoKhau
	@FXML
	private TableColumn<NhanKhauModel, String> colCMND; // Thay bang CCCD
	@FXML
	private TableColumn<NhanKhauModel, String> colSDT; // Thay bang NgheNghiep
	@FXML
	private TableColumn<NhanKhauModel, String> colMaHo; // Thay bang NgaySinh

	@FXML
	private TableColumn<NhanKhauModel, String> colQHvsChuHo;
	@FXML
	private TableView<NhanKhauModel> tvNhanKhau;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;
	private ObservableList<NhanKhauModel> listValueTableView;
	private List<NhanKhauModel> listNhanKhau;
	private int ID_dexemchitiet;


	// hien thi thong tin nhan khau
	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = new NhanKhauService().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);
		for (NhanKhauModel nhankhau : listNhanKhau) {
			nhankhau.setSoPhong(NhanKhauService.getSoPhong(nhankhau.getIDHoKhau()));
		}

		// thiet lap cac cot cho tableviews
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("IDNhanKhau"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("HoTen"));
		colTuoi.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, Integer>("SoPhong"));
		colCMND.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("CCCD"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("NgheNghiep"));
		colMaHo.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("NgaySinh"));
		colQHvsChuHo.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("QHvsChuHo"));

		tvNhanKhau.setItems(listValueTableView);

		// Thiết lập giá trị cho ComboBox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Họ Tên", "Số Phòng", "ID Nhân khẩu");
		cbChooseSearch.setValue("Họ Tên");
		cbChooseSearch.setItems(listComboBox);
	}

	// tim kiem nhan khau theo ten, tuoi, id
	public void searchNhanKhau() {
		ObservableList<NhanKhauModel> listValueTableView_tmp = null;
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
				List<NhanKhauModel> listNhanhKhauModelsSearch = new ArrayList<>();
				for (NhanKhauModel nhanKhauModel : listNhanKhau) {
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
				List<NhanKhauModel> listNhanKhau_tmp = new ArrayList<>();
				for (NhanKhauModel nhanKhauModel : listNhanKhau) {
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

				for (NhanKhauModel nhanKhauModel : listNhanKhau) {
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
		Parent home = FXMLLoader.load(getClass().getResource("/views/nhankhau/AddNhanKhau.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 670, 570));
		stage.setResizable(false);
		stage.showAndWait();
		showNhanKhau();
	}

	@FXML
	public void delNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();

		if (nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn nhân khẩu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {

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
				NhanKhauService.del(nhanKhauModel.getIDNhanKhau());
				// Xóa luôn nhân khẩu ở bảng tạm vắng
				TamVangService.delbyIDNhanKhau(nhanKhauModel.getIDNhanKhau());
			}
		}
		showNhanKhau();
	}

	@FXML
	public void updateNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		// lay ra nhan khau can update
		NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/UpdateNhanKhau.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 670, 600));
		UpdateNhanKhau updateNhanKhau = loader.getController();

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
		NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/ShowChiTiet.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 690, 400));
		ShowChiTiet showChiTiet = loader.getController();

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

	@FXML
	void addTamvang(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/tamtrutamvang/AddTamVang.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 400, 300));
		AddTamVang addTamVang = loader.getController();

		// bat loi truong hop khong hop le
		if (addTamVang == null) return;
		if (nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chưa chọn nhân khẩu!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		addTamVang.setNhanKhauModel(nhanKhauModel);

		stage.setResizable(false);
		stage.show();
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

	@FXML
	private ImageView reloadimg;

	@FXML
	void Reload(MouseEvent event) throws IOException, SQLException, ClassNotFoundException {
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), reloadimg);

		rotateTransition.setByAngle(720);
		rotateTransition.setOnFinished(eventt -> {
			try {
				showNhanKhau();
				tfSearch.setText("");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		rotateTransition.play();
	}
}
