package controller.khoanthu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

public class UpdateKhoanThu_old {
	@FXML
	private TextField tfIDKhoanThu;
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private DatePicker dpNgayBatDau;
	@FXML
	private DatePicker dpNgayKetThuc;
	@FXML
	private TextField tfTrongSoDienTich;
	@FXML
	private TextField tfTrongSoSTV;
	@FXML
	private TextField tfHangSo;

	private KhoanThuModel khoanThuModel;
	private int IDKhoanThu_old;

	public void setKhoanThuModel(KhoanThuModel khoanThuModel) {
		this.khoanThuModel = khoanThuModel;
		this.IDKhoanThu_old = khoanThuModel.getIDKhoanThu();

		// hien thi thong tin khoan thu len cac o text field
		tfIDKhoanThu.setText(String.valueOf(khoanThuModel.getIDKhoanThu()));
		tfTenKhoanThu.setText(khoanThuModel.getTenKT());
		
		// Convert the string value to a LocalDate object
		dpNgayBatDau.setValue(LocalDate.parse(khoanThuModel.getNgayBatDau()));
		
		// Convert the string value to a LocalDate object
		dpNgayKetThuc.setValue(LocalDate.parse(khoanThuModel.getNgayKetThuc()));
		
		tfTrongSoDienTich.setText(String.valueOf(khoanThuModel.getTrongSoDienTich()));
		tfTrongSoSTV.setText(String.valueOf(khoanThuModel.getTrongSoSTV()));
		tfHangSo.setText(String.valueOf(khoanThuModel.getHangSo()));
	}

	public void updateKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
		Pattern pattern;
		
		// maKhoanThu la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfIDKhoanThu.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã khoản thu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra ma khoan thu them moi co bi trung voi nhung ma khoan thu da ton tai
		// hay khong
		List<KhoanThuModel> listKhoanThuModels = new KhoanThuService().getListKhoanThu();
		for (KhoanThuModel khoanThuModel : listKhoanThuModels) {
			if (khoanThuModel.getIDKhoanThu() == Integer.parseInt(tfIDKhoanThu.getText()) && khoanThuModel.getIDKhoanThu() != IDKhoanThu_old) {
				Alert alert = new Alert(AlertType.WARNING, "Mã khoản thu đã bị trùng!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenKhoanThu.getText().length() >= 50 || tfTenKhoanThu.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên khoản thu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra ngay bat dau
		// ngay bat dau phai nho hon ngay ket thuc
		if (dpNgayBatDau.getValue().compareTo(dpNgayKetThuc.getValue()) > 0) {
			Alert alert = new Alert(AlertType.WARNING, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra trong so dien tich
		// trong so dien tich la so thuc lon hon hoac bang 0
		pattern = Pattern.compile("\\d{1,11}(\\.\\d{1,11})?");
		if (!pattern.matcher(tfTrongSoDienTich.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào trọng số diện tích hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		if (Double.parseDouble(tfTrongSoDienTich.getText()) < 0) {
			Alert alert = new Alert(AlertType.WARNING, "Trọng số diện tích phải lớn hơn 0!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra trong so so thanh vien
		// trong so so thanh vien la so thuc lon hon hoac bang 0
		pattern = Pattern.compile("\\d{1,11}(\\.\\d{1,11})?");
		if (!pattern.matcher(tfTrongSoSTV.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào trọng số số thành viên hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		if (Double.parseDouble(tfTrongSoSTV.getText()) < 0) {
			Alert alert = new Alert(AlertType.WARNING, "Trọng số số thành viên phải lớn hơn 0!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra hang so
		// hang so la so thuc lon hon hoac bang 0
		pattern = Pattern.compile("\\d{1,11}(\\.\\d{1,11})?");
		if (!pattern.matcher(tfHangSo.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào hằng số hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		if (Double.parseDouble(tfHangSo.getText()) < 0) {
			Alert alert = new Alert(AlertType.WARNING, "Hằng số phải lớn hơn 0!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// gan gia tri cac truong cua khoan thu
		khoanThuModel.setIDKhoanThu(Integer.parseInt(tfIDKhoanThu.getText()));
		khoanThuModel.setTenKT(tfTenKhoanThu.getText());
		khoanThuModel.setNgayBatDau(dpNgayBatDau.getValue().toString());
		khoanThuModel.setNgayKetThuc(dpNgayBatDau.getValue().toString());
		khoanThuModel.setTrongSoDienTich(Double.parseDouble(tfTrongSoDienTich.getText()));
		khoanThuModel.setTrongSoSTV(Double.parseDouble(tfTrongSoSTV.getText()));
		khoanThuModel.setHangSo(Double.parseDouble(tfHangSo.getText()));
		
		// update khoan thu
		new KhoanThuService().update(IDKhoanThu_old, khoanThuModel);
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
	}
}
