package controller.thuphi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.NhanKhauModel_Lam;
import models.PhongModel;
import models.ThuPhiBean;
import models.ThuPhiModel;
import models.XeModel_Tuan;
import services.PhongService;
import services.ThuPhiService;
import services.XeService_Tuan;

public class AddThuPhi implements Initializable {
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private TextField tfTenChuHo;
	@FXML
	private TextField tfSoTienPhaiDong;
	@FXML
	private TextField tfSoTienDong;
	@FXML
	private DatePicker dpNgayDong;

	private KhoanThuModel khoanThuModel;
	private NhanKhauModel_Lam nhanKhauModel;

	@Override
	public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
		// set dpNgayDong la ngay hien tai
		dpNgayDong.setValue(java.time.LocalDate.now());
	}

	public void chooseKhoanThu() throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/ThuPhi/ChooseKhoanThu.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();

		ChooseKhoanThu chooseKhoanNop = loader.getController();
		khoanThuModel = chooseKhoanNop.getKhoanThuChoose();
		if (khoanThuModel == null)
			return;
		tfTenKhoanThu.setText(khoanThuModel.getTenKT());

		if (nhanKhauModel != null)
			tinhSoTienPhaiDong();
	}

	public void chooseChuHo() throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/ThuPhi/ChooseChuHo.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Chọn chủ hộ");
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();

		ChooseChuHo chooseChuHo = loader.getController();
		nhanKhauModel = chooseChuHo.getNhanKhauChoose();
		if (nhanKhauModel == null)
			return;
		tfTenChuHo.setText(nhanKhauModel.getHoTen());

		if (khoanThuModel != null)
			tinhSoTienPhaiDong();
	}

	private void tinhSoTienPhaiDong() throws ClassNotFoundException, SQLException {
		String loai = khoanThuModel.getLoaiKhoanThu();
		double tien = 0;
		if (loai.equals(new String("Tiền quản lý"))) {
			PhongModel phongModel = PhongService.getPhongModel(nhanKhauModel.getSoPhong());
			if (phongModel.getLoaiPhong().equals(new String("Cao cấp"))) {
				tien = khoanThuModel.getTrongSoDienTich() * phongModel.getDienTich()			
				+ khoanThuModel.getHangSo();
			}
			else {
				tien = khoanThuModel.getTrongSoSTV() * phongModel.getDienTich() 
				+ khoanThuModel.getHangSo();
			}
		}
		else if (loai.equals(new String("Tiền giữ xe"))) {
			XeModel_Tuan xeModel = XeService_Tuan.getXeModel(nhanKhauModel.getIDHoKhau());
			tien = khoanThuModel.getTrongSoDienTich() * xeModel.getOTo()
			+ khoanThuModel.getTrongSoSTV() * xeModel.getXeMay()
			+ khoanThuModel.getHangSo() * xeModel.getXeDap();
		}

		tfSoTienPhaiDong.setText(String.valueOf(tien));
	}

	public void addThuPhi(ActionEvent event) throws ClassNotFoundException, SQLException {
		if (tfTenKhoanThu.getText().length() == 0 || tfTenChuHo.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn khoản thu và chủ hộ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		} 
		List<ThuPhiBean> listThuPhi = new ThuPhiService().getListThuPhi();
		for (ThuPhiBean thuPhiBean : listThuPhi) {
			if (thuPhiBean.getThuPhiModel().getIDHoKhau() == nhanKhauModel.getIDHoKhau()
					&& thuPhiBean.getThuPhiModel().getIDKhoanThu() == khoanThuModel.getIDKhoanThu()) {
				Alert alert = new Alert(AlertType.WARNING, "Người này đã từng nộp khoản phí này!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}
		// kiem tra xem so tien dong co la mot so thuc khong
		if (!tfSoTienDong.getText().matches("\\d*\\.?\\d+")) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào số tiền đóng hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra so tien phai dong co la mot so thuc khong
		if (!tfSoTienPhaiDong.getText().matches("\\d*\\.?\\d+")) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào số tiền phải đóng hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra ngay dong co de trong hay khong
		if (dpNgayDong.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn ngày đóng!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra so tne dong co nho hon so tien phai dong hay khong
		if (Double.parseDouble(tfSoTienDong.getText()) < Double.parseDouble(tfSoTienPhaiDong.getText())) {
			Alert alert = new Alert(AlertType.WARNING, 
					"Số tiền muốn đóng nhỏ hơn số tiền cần đóng, bạn có muốn tiếp tục thêm thu phí không?", 
					ButtonType.YES, ButtonType.NO);
			alert.setHeaderText(null);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.NO) return;
		}

		ThuPhiModel thuPhiModel = new ThuPhiModel();
		thuPhiModel.setIDHoKhau(nhanKhauModel.getIDHoKhau());
		thuPhiModel.setIDKhoanThu(khoanThuModel.getIDKhoanThu());
		thuPhiModel.setSoTienPhaiDong(Double.parseDouble(tfSoTienPhaiDong.getText()));
		thuPhiModel.setSoTien(Double.parseDouble(tfSoTienDong.getText()));
		thuPhiModel.setNgayDong(dpNgayDong.getValue().toString());

		ThuPhiService.add(thuPhiModel);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Thêm thu phí");
		stage.setResizable(false);
		stage.close();
	}
}
