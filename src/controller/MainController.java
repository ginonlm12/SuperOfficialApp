package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{
	@FXML
	private Label lbSoHoKhau;

	@FXML
	private Label lbSoKhoanThu;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		try {
//			List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
//			long soHoKhau = listHoKhau.stream().count();
//			lbSoHoKhau.setText(Long.toString(soHoKhau));
//
//			List<KhoanThuModel> listKhoanThu = new KhoanThuService().getListKhoanThu();
//			long soKhoanThu = listKhoanThu.stream().count();
//			lbSoKhoanThu.setText(Long.toString(soKhoanThu));
//
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
