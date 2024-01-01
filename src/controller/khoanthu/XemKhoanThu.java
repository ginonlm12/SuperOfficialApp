package controller.khoanthu;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.KhoanThuModel;
import java.time.LocalDate;

public class XemKhoanThu {
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

    public void setKhoanThuModel(KhoanThuModel khoanThuModel) {
		khoanThuModel.getIDKhoanThu();

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

        cbLoaiKhoanThu.getItems().addAll(khoanThuModel.getLoaiKhoanThu());
        // set selected item for cbLoaiKhoanThu
		cbLoaiKhoanThu.getSelectionModel().select(khoanThuModel.getLoaiKhoanThu());
        
        // goi chonLoaiKhoanThu de hien thi cac thanh phan tuong ung voi loai khoan thu
        ChonKhoanThu();
	}
	
	public void ChonKhoanThu() {
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
			tfThanhPhan1.setVisible(false);
			tfThanhPhan2.setVisible(false);
			tfThanhPhan3.setVisible(false);

			tfTrongSoDienTich.setVisible(false);
			tfTrongSoSTV.setVisible(false);
			tfHangSo.setVisible(false);
		}

        // set uneditable all elements
        tfIDKhoanThu.setEditable(false);
        tfTenKhoanThu.setEditable(false);
        dpNgayBatDau.setEditable(false);
        dpNgayKetThuc.setEditable(false);
        tfTrongSoDienTich.setEditable(false);
        tfTrongSoSTV.setEditable(false);
        tfHangSo.setEditable(false);
        tfThanhPhan1.setEditable(false);
        tfThanhPhan2.setEditable(false);
        tfThanhPhan3.setEditable(false);
	}
}
