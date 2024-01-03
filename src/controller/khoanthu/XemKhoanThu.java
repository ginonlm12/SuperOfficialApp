package controller.khoanthu;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.KhoanThuModel;

public class XemKhoanThu {
	@FXML
	private TextField tfIDKhoanThu;
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private TextField dpNgayBatDau;
	@FXML
	private TextField dpNgayKetThuc;
	@FXML
	private TextField cbLoaiKhoanThu;
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
		
		// hien thi ngay bat dau
		dpNgayBatDau.setText(khoanThuModel.getNgayBatDau());
		
		// hien thi ngay ket thuc
		dpNgayKetThuc.setText(khoanThuModel.getNgayKetThuc());

		tfTrongSoDienTich.setText(String.valueOf(khoanThuModel.getTrongSoDienTich()));
		tfTrongSoSTV.setText(String.valueOf(khoanThuModel.getTrongSoSTV()));
		tfHangSo.setText(String.valueOf(khoanThuModel.getHangSo()));

        cbLoaiKhoanThu.setText(khoanThuModel.getLoaiKhoanThu());
        
        // goi chonLoaiKhoanThu de hien thi cac thanh phan tuong ung voi loai khoan thu
        ChonKhoanThu();
	}
	
	public void ChonKhoanThu() {
		if (cbLoaiKhoanThu.getText().equals(new String("Tiền điện"))) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(true);
			tfThanhPhan3.setVisible(true);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(true);
			tfHangSo.setVisible(true);

			tfThanhPhan1.setText("0 - 200 KW");
			tfThanhPhan2.setText("200 - 400 KW");
			tfThanhPhan3.setText(">= 400 KW");
		} else if (cbLoaiKhoanThu.getText().equals("Tiền nước")) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(false);
			tfThanhPhan3.setVisible(false);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(false);
			tfHangSo.setVisible(false);

			tfThanhPhan1.setText("Giá 1m3 nước");
		} else if (cbLoaiKhoanThu.getText().equals(new String("Tiền quản lý"))) {
			tfThanhPhan1.setVisible(true);
			tfThanhPhan2.setVisible(true);
			tfThanhPhan3.setVisible(true);

			tfTrongSoDienTich.setVisible(true);
			tfTrongSoSTV.setVisible(true);
			tfHangSo.setVisible(true);

			tfThanhPhan1.setText("Giá 1m2 Phòng thường");
			tfThanhPhan2.setText("Giá 1m2 Phòng cao cấp");
			tfThanhPhan3.setText("Cộng thêm");
		} else if (cbLoaiKhoanThu.getText().equals(new String("Tiền giữ xe"))) {
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

        // set uneditable all elements
        tfIDKhoanThu.setEditable(false);
        tfTenKhoanThu.setEditable(false);
        dpNgayBatDau.setEditable(false);
        dpNgayKetThuc.setEditable(false);
		cbLoaiKhoanThu.setEditable(false);
        tfTrongSoDienTich.setEditable(false);
        tfTrongSoSTV.setEditable(false);
        tfHangSo.setEditable(false);
        tfThanhPhan1.setEditable(false);
        tfThanhPhan2.setEditable(false);
        tfThanhPhan3.setEditable(false);
	}
}
