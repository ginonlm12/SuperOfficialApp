package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.thuphi.ChooseChuHo;
import controller.thuphi.XemHoChuaDongDu;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ThongKeModel;
import services.ThongKeService;

public class ThongKeController implements Initializable {
	@FXML
	TableView<ThongKeModel> tvThongKe;
	@FXML
	TableColumn<ThongKeModel, String> colIDKhoanThu;
	@FXML
	TableColumn<ThongKeModel, String> colTenKhoanThu;
	@FXML
	TableColumn<ThongKeModel, String> colLoai;
	@FXML
	TableColumn<ThongKeModel, String> colTinhTrang;
	@FXML
	TableColumn<ThongKeModel, String> colSoHoDaThu;
	@FXML
	TableColumn<ThongKeModel, String> colTongSoTien;
	@FXML
	ComboBox<String> cbTinhTrang;
	@FXML
	ComboBox<String> cbLoai;
	@FXML
	TextField tfTimKiem;

	private List<ThongKeModel> listThongKe;
	private ObservableList<ThongKeModel> listValueTableView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// set value for combobox
			ObservableList<String> listTinhTrang = FXCollections.observableArrayList("Tất cả", "Chưa đến hạn", "Đã quá hạn");
			cbTinhTrang.setItems(listTinhTrang);
			cbTinhTrang.getSelectionModel().selectFirst();

			ObservableList<String> listLoai = FXCollections.observableArrayList("Tên khoản thu", "ID khoản thu");
			cbLoai.setItems(listLoai);
			cbLoai.getSelectionModel().selectFirst();

			hienThongKe();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hienThongKe() throws ClassNotFoundException, SQLException {
		listThongKe = ThongKeService.getListThongKe(cbTinhTrang.getValue());
		listValueTableView = FXCollections.observableArrayList(listThongKe);

		// set value for tableview
		colIDKhoanThu.setCellValueFactory(new PropertyValueFactory<>("IDKhoanThu"));
		colTenKhoanThu.setCellValueFactory(new PropertyValueFactory<>("TenKhoanThu"));
		colLoai.setCellValueFactory(new PropertyValueFactory<>("Loai"));
		colTinhTrang.setCellValueFactory(new PropertyValueFactory<>("TinhTrang"));
		colSoHoDaThu.setCellValueFactory(new PropertyValueFactory<>("SoHoDaThu"));
		colTongSoTien.setCellValueFactory(new PropertyValueFactory<>("TongSoTien"));

		tvThongKe.setItems(listValueTableView);
	}

	public void timKiem() {
		try {
			List<ThongKeModel> listThongKe_tmp;
			if (tfTimKiem.getText().equals("")) {
				hienThongKe();
				Alert alert = new Alert(AlertType.INFORMATION, "Nhap gia tri can tim!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}

			if (cbLoai.getValue().equals("Tên khoản thu")) {
				listThongKe_tmp = ThongKeService.timKiemTheoTenKhoanThu(cbTinhTrang.getValue(), tfTimKiem.getText());
			}
			else {
				// kiem tra xem gia tri nhap vao co phai la so hay khong
				try {
					Integer.valueOf(tfTimKiem.getText());
				}
				catch (Exception e) {
					hienThongKe();
					Alert alert = new Alert(AlertType.INFORMATION, "Nhap gia tri can tim la so!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}
				listThongKe_tmp = ThongKeService.timKiemTheoIDKhoanThu(cbTinhTrang.getValue(), Integer.valueOf(tfTimKiem.getText()));
			}

			if (listThongKe_tmp.size() == 0) {
				hienThongKe();
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy thông tin!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}

			// set value for tableview
			colIDKhoanThu.setCellValueFactory(new PropertyValueFactory<>("IDKhoanThu"));
			colTenKhoanThu.setCellValueFactory(new PropertyValueFactory<>("TenKhoanThu"));
			colLoai.setCellValueFactory(new PropertyValueFactory<>("Loai"));
			colTinhTrang.setCellValueFactory(new PropertyValueFactory<>("TinhTrang"));
			colSoHoDaThu.setCellValueFactory(new PropertyValueFactory<>("SoHoDaThu"));
			colTongSoTien.setCellValueFactory(new PropertyValueFactory<>("TongSoTien"));
			ObservableList<ThongKeModel> listValueTableView_tmp;
			listValueTableView_tmp = FXCollections.observableArrayList(listThongKe_tmp);
			tvThongKe.setItems(listValueTableView_tmp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dsHoChuaNopDu() throws IOException, ClassNotFoundException, SQLException {
		// lay phan tu duoc chon
		ThongKeModel thongKeModel = tvThongKe.getSelectionModel().getSelectedItem();
		if (thongKeModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn khoản thu cần xem!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// load XemHoChuaDongDu.fxml
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/ThuPhi/XemHoChuaDongDu.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Xem hộ chưa đóng đủ");
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		loader.<XemHoChuaDongDu>getController().setIDKhoanThu(thongKeModel.getIDKhoanThu());
		stage.showAndWait();

	}
}
