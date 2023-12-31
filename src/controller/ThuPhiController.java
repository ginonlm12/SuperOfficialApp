package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.thuphi.UpdateThuPhi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ThuPhiBean;
import services.ThuPhiService;

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

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên khoản thu", "Tên chủ hộ");
		cbChooseSearch.setValue("Tên khoản thu");
		cbChooseSearch.setItems(listComboBox);
	}

	// Tim kiem khoan thu
	public void searchThuPhi() {
		ObservableList<ThuPhiBean> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
			case "Tên khoản thu": {
				// neu khong nhap gi -> thong bao loi
				if (keySearch.length() == 0) {
					tvThuPhi.setItems(listValueTableView);
					Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào thông tin cần tìm kiếm!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					break;
				}

				int index = 0;
				List<ThuPhiBean> listThuPhiBeansSearch = new ArrayList<>();
				for (ThuPhiBean ThuPhiBean : listThuPhi) {
					if (ThuPhiBean.getTenKhoanThu().contains(keySearch)) {
						listThuPhiBeansSearch.add(ThuPhiBean);
						index++;
					}
				}
				listValueTableView_tmp = FXCollections.observableArrayList(listThuPhiBeansSearch);
				tvThuPhi.setItems(listValueTableView_tmp);

				// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
				// tim thay
				if (index == 0) {
					tvThuPhi.setItems(listValueTableView); // hien thi toan bo thong tin
					Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
				}
				break;
			}
			default: { // truong hop con lai : tim theo ma khoan thu
				// neu khong nhap gi -> thong bao loi
				if (keySearch.length() == 0) {
					tvThuPhi.setItems(listValueTableView);
					Alert alert = new Alert(AlertType.INFORMATION, "Bạn cần nhập vào thông tin tìm kiếm!",
							ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					break;
				}

				for (ThuPhiBean ThuPhiBean : listThuPhi) {
					if (ThuPhiBean.getTenChuHo().contains(keySearch)) {
						listValueTableView_tmp = FXCollections.observableArrayList(ThuPhiBean);
						tvThuPhi.setItems(listValueTableView_tmp);
						return;
					}
				}

				// khong tim thay thong tin -> thong bao toi nguoi dung
				tvThuPhi.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
		}
	}

	public void addThuPhi() throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/thuphi/AddThuPhi.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
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

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/thuphi/UpdateThuPhi.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		UpdateThuPhi updateThuPhi = loader.getController();
		updateThuPhi.setThuPhiBean(thuPhiBean);
		if (thuPhiBean == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn khoản thu update !", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		stage.setResizable(false);
		stage.showAndWait();
		hienThuPhi();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			hienThuPhi();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}