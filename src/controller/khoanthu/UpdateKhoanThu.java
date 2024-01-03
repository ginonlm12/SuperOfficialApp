package controller.khoanthu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class UpdateKhoanThu {
	@FXML
	private TextField tfIDKhoanThu;
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private DatePicker dpNgayBatDau;
	@FXML
	private DatePicker dpNgayKetThuc;
	@FXML
	private ComboBox<String> cbLoaiKhoanThu;
	@FXML
	private TextField tfTrongSoDienTich;
	@FXML
	private TextField tfTrongSoSTV;
	@FXML
	private TextField tfHangSo;
	@FXML
	private TextField tfThanhPhan1;
	@FXML
	private TextField tfThanhPhan2;
	@FXML
	private TextField tfThanhPhan3;

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

        cbLoaiKhoanThu.getItems().addAll("Tiền quản lý", "Tiền giữ xe", "Tiền điện", "Tiền nước", "Tiền khác");
        // set cbLoaiKhoanThu theo khoanThuModel
        cbLoaiKhoanThu.getSelectionModel().select(khoanThuModel.getLoaiKhoanThu());
        // goi chonLoaiKhoanThu de hien thi cac thanh phan tuong ung voi loai khoan thu
        ChonKhoanThu(null);
	}
	
	public void ChonKhoanThu(ActionEvent event) {
		if (cbLoaiKhoanThu.getSelectionModel().getSelectedItem().equals("Tiền điện")) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(true);
			tfThanhPhan3.setVisible(true);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(true);
			tfHangSo.setVisible(true);

			tfThanhPhan1.setText("0 - 200 KW");
			tfThanhPhan2.setText("200 - 400 KW");
			tfThanhPhan3.setText(">= 400 KW");
		} else if (cbLoaiKhoanThu.getSelectionModel().getSelectedItem().equals("Tiền nước")) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(false);
			tfThanhPhan3.setVisible(false);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(false);
			tfHangSo.setVisible(false);

			tfThanhPhan1.setText("Giá 1m3 nước");
		} else if (cbLoaiKhoanThu.getSelectionModel().getSelectedItem().equals("Tiền quản lý")) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(true);
			tfThanhPhan3.setVisible(true);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(true);
			tfHangSo.setVisible(true);

			tfThanhPhan1.setText("Giá 1m2 Phòng thường");
			tfThanhPhan2.setText("Giá 1m2 Phòng cao cấp");
			tfThanhPhan3.setText("Cộng thêm");
		} else if (cbLoaiKhoanThu.getSelectionModel().getSelectedItem().equals("Tiền giữ xe")) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(true);
			tfThanhPhan3.setVisible(true);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(true);
			tfHangSo.setVisible(true);

			tfThanhPhan1.setText("Tiền một xe ô tô");
			tfThanhPhan2.setText("Tiền một xe máy");
			tfThanhPhan3.setText("Tiền một xe đạp");
		} else { // khong lam gi ca
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(false);
			tfThanhPhan3.setVisible(false);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(false);
			tfHangSo.setVisible(false);

			tfThanhPhan1.setText("Số tiền phải thu");
		}
	}

	public void updateKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
		Pattern pattern;

		// IDKhoanThu la day so tu 1 toi 11 chu so
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfIDKhoanThu.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã khoản thu là số từ 1 đến 11 chữ số!",
					ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra ma khoan thu them moi co bi trung voi nhung ma khoan thu da ton tai
		// hay khong
		List<KhoanThuModel> listKhoanThuModels = KhoanThuService.getListKhoanThu();
		for (KhoanThuModel khoanThuModel : listKhoanThuModels) {
			if (khoanThuModel.getIDKhoanThu() == Integer.parseInt(tfIDKhoanThu.getText()) 
			&& khoanThuModel.getIDKhoanThu() != IDKhoanThu_old) {
				Alert alert = new Alert(AlertType.WARNING, "Mã khoản thu đã bị trùng!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfTenKhoanThu.getText().length() >= 50 || tfTenKhoanThu.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào tên khoản thu là chuỗi dưới 50 ký tự!",
					ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem tra ngay bat dau
		// ngay bat dau va ket thuc khong duoc de trong
		if (dpNgayBatDau.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ngày bắt đầu!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// ngay bat dau va ket thuc khong duoc de trong
		if (dpNgayKetThuc.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ngày kết thúc!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
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
		khoanThuModel.setNgayKetThuc(dpNgayKetThuc.getValue().toString());
		khoanThuModel.setTrongSoDienTich(Double.parseDouble(tfTrongSoDienTich.getText()));
		khoanThuModel.setTrongSoSTV(Double.parseDouble(tfTrongSoSTV.getText()));
		khoanThuModel.setHangSo(Double.parseDouble(tfHangSo.getText()));
		khoanThuModel.setLoaiKhoanThu(cbLoaiKhoanThu.getSelectionModel().getSelectedItem());
		// them khoan thu vao database
		KhoanThuService.update(IDKhoanThu_old, khoanThuModel);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}
}
