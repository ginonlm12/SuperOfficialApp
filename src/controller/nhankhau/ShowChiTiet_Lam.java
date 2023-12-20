package controller.nhankhau;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.NhanKhauModel_Lam;
import services.NhanKhauService_Lam;

import java.sql.SQLException;

public class ShowChiTiet_Lam {
	private int IDNhanKhau;
	@FXML
	private TextField tfCCCD1;
	@FXML
	private TextField tfDanToc1;
	@FXML
	private TextField tfGioiTinh1;
	@FXML
	private TextField tfHoTen1;
	@FXML
	private TextField tfIDHoKhau1;
	@FXML
	private TextField tfIDNhanKhau1;
	@FXML
	private TextField tfNgaySinh1;
	@FXML
	private TextField tfNgheNghiep1;
	@FXML
	private TextField tfQHvsChuHo1;
	@FXML
	private TextField tfQueQuan1;
	@FXML
	private TextField tfSoPhong;

	private static NhanKhauModel_Lam nhanKhauModel;
	public static NhanKhauModel_Lam getNhanKhauModel() {
		return nhanKhauModel;
	}

	public void setIDNhanKhau(int idNhanKhau) {
		IDNhanKhau = idNhanKhau;
	}
	public void setNhanKhauModel(NhanKhauModel_Lam nhanKhauModel) throws ClassNotFoundException, SQLException {
		ShowChiTiet_Lam.nhanKhauModel = nhanKhauModel;
		IDNhanKhau = nhanKhauModel.getIDNhanKhau();
		System.out.println(IDNhanKhau);
//		tfMaNhanKhau.setText(Integer.toString(maNhanKhau));
//		tfTuoi.setText(Integer.toString(nhanKhauModel.getTuoi()));
//		tfTenNhanKhau.setText(nhanKhauModel.getTen());
//		tfSoDienThoai.setText(nhanKhauModel.getSdt());
//		tfSoCMND.setText(nhanKhauModel.getCmnd());maNhanKhau = nhanKhauModel.getId();
		initializeLogic();
	}

	private void initializeLogic() throws SQLException, ClassNotFoundException {
		System.out.println("ha: " + IDNhanKhau);
		try {
			nhanKhauModel = NhanKhauService_Lam.loadDatafromID(IDNhanKhau);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if(nhanKhauModel != null) {
			tfHoTen1.setText(nhanKhauModel.getHoTen());
			tfIDNhanKhau1.setText(String.valueOf(IDNhanKhau));
			tfCCCD1.setText(nhanKhauModel.getCCCD());
			tfNgaySinh1.setText(nhanKhauModel.getNgaySinh());
			tfGioiTinh1.setText(nhanKhauModel.getGioiTinh());
			tfNgheNghiep1.setText(nhanKhauModel.getNgheNghiep());
			tfIDHoKhau1.setText(String.valueOf(nhanKhauModel.getIDHoKhau()));
			tfQHvsChuHo1.setText(nhanKhauModel.getQHvsChuHo());
			tfDanToc1.setText(nhanKhauModel.getDanToc());
			tfQueQuan1.setText(nhanKhauModel.getQueQuan());
			tfSoPhong.setText(String.valueOf(NhanKhauService_Lam.getSoPhong(nhanKhauModel.getIDHoKhau())));
		}
	}
}
