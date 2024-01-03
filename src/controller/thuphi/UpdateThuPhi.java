package controller.thuphi;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.HoKhauModel;
import models.KhoanThuModel;
import models.PhongModel;
import models.ThuPhiBean;
import models.ThuPhiModel;
import models.XeModel;
import services.HoKhauService;
import services.KhoanThuService;
import services.PhongService;
import services.ThuPhiService;
import services.XeService;

public class UpdateThuPhi {
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

	private ThuPhiBean thuPhiBean;

    public void setThuPhiBean(ThuPhiBean thuPhiBean) {
        this.thuPhiBean = thuPhiBean;
        tfTenKhoanThu.setText(thuPhiBean.getTenKhoanThu());
        tfTenChuHo.setText(thuPhiBean.getTenChuHo());
        tfSoTienPhaiDong.setText(Double.toString(thuPhiBean.getSoTienPhaiDong()));
        tfSoTienDong.setText(Double.toString(thuPhiBean.getSoTienDong()));
        String NgayDong = thuPhiBean.getNgayDong();
		if (NgayDong != null && !NgayDong.isEmpty()) {
			LocalDate DOB = LocalDate.parse(NgayDong, DateTimeFormatter.ISO_DATE);
			dpNgayDong.setValue(DOB);
		} else {
		    dpNgayDong.setValue(LocalDate.parse("01/01/1900", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
    }

	public void updateThuPhi(ActionEvent event) throws ClassNotFoundException, SQLException {
		// kiem tra so tien va so tien dong la so thuc
		try {
			Double.parseDouble(tfSoTienPhaiDong.getText());
			Double.parseDouble(tfSoTienDong.getText());
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR, "Số tiền phải đóng và số tiền đóng phải là số thực!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		// kiem ngay dong dung dinh dang	
		if (dpNgayDong.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR, "Ngày đóng không được để trống!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		ThuPhiModel thuPhiModel =thuPhiBean.getThuPhiModel();
		// lay thong tin tu giao dien
		thuPhiModel.setSoTien(Double.parseDouble(tfSoTienDong.getText()));
		thuPhiModel.setSoTienPhaiDong(Double.parseDouble(tfSoTienPhaiDong.getText()));
		thuPhiModel.setNgayDong(dpNgayDong.getValue().format(DateTimeFormatter.ISO_DATE));
		// cap nhat vao database
		ThuPhiService.update(thuPhiModel);
		
		Alert alert = new Alert(AlertType.INFORMATION, "Cập nhật thành công!", ButtonType.OK);
		alert.setHeaderText(null);
		alert.showAndWait();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Cập nhật thu phí");
		stage.setResizable(false);
		stage.close();	
	}

	public void tinhSoTienPhaiDong() throws ClassNotFoundException, SQLException {
		KhoanThuModel khoanThuModel = KhoanThuService.getKhoanThu(thuPhiBean.getThuPhiModel().getIDKhoanThu());
		HoKhauModel hoKhauModel = HoKhauService.getHoKhau(thuPhiBean.getThuPhiModel().getIDHoKhau());
		String loai = khoanThuModel.getLoaiKhoanThu();
		double tien = 0;
		if (loai.equals("Tiền quản lý")) {
			PhongModel phongModel = PhongService.getPhongModel(hoKhauModel.getSoPhong());
			if (phongModel.getLoaiPhong().equals("Cao cấp")) {
				tien = khoanThuModel.getTrongSoDienTich() * phongModel.getDienTich()			
				+ khoanThuModel.getHangSo();
			}
			else {
				tien = khoanThuModel.getTrongSoSTV() * phongModel.getDienTich() 
				+ khoanThuModel.getHangSo();
			}
		}
		else if (loai.equals("Tiền giữ xe")) {
			XeModel xeModel = XeService.getXeModel(hoKhauModel.getIDHoKhau());
			tien = khoanThuModel.getTrongSoDienTich() * xeModel.getOTo()
			+ khoanThuModel.getTrongSoSTV() * xeModel.getXeMay()
			+ khoanThuModel.getHangSo() * xeModel.getXeDap();
		}

		tfSoTienPhaiDong.setText(String.valueOf(tien));
	}
}
