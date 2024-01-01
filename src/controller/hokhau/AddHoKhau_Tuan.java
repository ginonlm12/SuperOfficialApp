package controller.hokhau;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.HoKhauModel_Tuan;
import models.NhanKhauModel_Lam;
import services.HoKhauService_Tuan;
import services.NhanKhauService_Lam;
import services.PhongService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import services.XuLyLoiService;


public class AddHoKhau_Tuan implements Initializable {
	@FXML
	private ComboBox<String> GioiTinh;

	@FXML
	private DatePicker NgayDen;

	@FXML
	private TextField tfCCCD;

	@FXML
	private TextField tfTenChuHo;

	@FXML
	private TextField tfMaChuHo;

	@FXML
	private TextField tfMaHoKhau;

	@FXML
	private TextField tfSdt;

	@FXML
	private Label xuliloi1;
	@FXML
	private Label xuliloi2;
	@FXML
	private Label xuliloi3;

	@FXML
	private Button Xacnhan;

    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
    ObservableList<Integer> listSoPhong = FXCollections.observableArrayList();
    @FXML
	private ComboBox<Integer> SoPhong;



	public void initialize(URL url, ResourceBundle resourceBundle) {
		// set cac gia tri cho gioi tinh va so phong
		GioiTinh.setItems(listGioiTinh);
		try {
			listSoPhong = PhongService.getListSoPhong("Duoc su dung");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		SoPhong.setItems(listSoPhong);

		//set gia tri mac dinh cho ma ho khau va ma chu ho
		try {
			tfMaHoKhau.setText(String.valueOf(HoKhauService_Tuan.getNewIDHoKhau()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			tfMaChuHo.setText(String.valueOf(NhanKhauService_Lam.getNewIDNhanKhau()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// set xu li loi
		checkError();
	}

	public void checkError() {
		// ngay den
		NgayDen.valueProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null) {
				XuLyLoiService.xuLyLoi(xuliloi1, NgayDen, "Vui lòng chọn ngày chuyển đến", 0, 40);
			} else if (newValue != null && NgayDen.getValue().isAfter(java.time.LocalDate.now())) {
				XuLyLoiService.xuLyLoi(xuliloi1, NgayDen, "Ngày chuyển đến không được lớn hơn ngày hiện tại", 0, 40);
			} else {
				XuLyLoiService.xoaLoi(xuliloi1, NgayDen);
			}
		});

		//sdt
		tfSdt.textProperty().addListener((observableValue, oldValue, newValue) -> {
			Pattern pattern = Pattern.compile("\\d{1,15}");
			if (!pattern.matcher(newValue).matches()) {
				XuLyLoiService.xuLyLoi(xuliloi1, tfSdt, "Hãy nhập vào SĐT hợp lệ!", 0, 40);

			} else {
				XuLyLoiService.xoaLoi(xuliloi1, tfSdt);
			}
		});

		// ten chu ho
		tfTenChuHo.textProperty().addListener((observableValue, oldValue, newValue) -> {
			// it not null and dont have number
			Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+");
			if (!pattern.matcher(newValue).matches()) {
				XuLyLoiService.xuLyLoi(xuliloi2, tfTenChuHo, "Hãy nhập vào tên hợp lệ!", 0, 40);
			} else {
				XuLyLoiService.xoaLoi(xuliloi2, tfTenChuHo);
			}
		});

		// cccd
		tfCCCD.textProperty().addListener((observableValue, oldValue, newValue) -> {
			Pattern pattern = Pattern.compile("\\d{1,20}");
			if (!pattern.matcher(newValue).matches()) {
				XuLyLoiService.xuLyLoi(xuliloi2, tfCCCD, "Hãy nhập vào CCCD hợp lệ!", 0, 40);
			} else {
				XuLyLoiService.xoaLoi(xuliloi2, tfCCCD);
			}
		});

		// gioi tinh
		GioiTinh.valueProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null) {
				XuLyLoiService.xuLyLoi(xuliloi2, GioiTinh, "Hãy chọn giới tính!", 0, 40);
			} else {
				XuLyLoiService.xoaLoi(xuliloi2, GioiTinh);
			}
		});
		// so phong
		SoPhong.valueProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null) {
				XuLyLoiService.xuLyLoi(xuliloi1, SoPhong, "Hãy chọn số phòng!", 0, 40);
			} else {
				XuLyLoiService.xoaLoi(xuliloi1, SoPhong);
			}
		});
	}
	@FXML
	public void addHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// if xuliloi1 and xuliloi2 is visible => return
		if(SoPhong.getValue() == null || GioiTinh.getValue() == null){
			if(SoPhong.getValue() == null){
				XuLyLoiService.xuLyLoi(xuliloi1, SoPhong, "Vui lòng chọn số phòng", 0, 40);
			}
			else if(GioiTinh.getValue() == null){
				XuLyLoiService.xuLyLoi(xuliloi2, GioiTinh, "Vui lòng chọn giới tính", 0, 40);
			}
			else{
				XuLyLoiService.xoaLoi(xuliloi1, SoPhong);
				XuLyLoiService.xoaLoi(xuliloi2, GioiTinh);
			}
		}
		else if(NgayDen.getValue() == null || tfSdt.getText() == null || tfTenChuHo.getText() == null || tfCCCD.getText() == null || tfMaChuHo.getText() == null || tfMaHoKhau.getText() == null){
			xuliloi3.setVisible(true);
			xuliloi3.setText("Vui lòng điền đủ thông tin trước khi xác nhận");
		}
		else if (xuliloi1.isVisible() || xuliloi2.isVisible()) {
			xuliloi3.setVisible(true);
			xuliloi3.setText("Vui lòng sửa các lỗi trước khi xác nhận");
		}
		else{
			int maHo = Integer.parseInt(tfMaHoKhau.getText());
			int maChuHo = Integer.parseInt(tfMaChuHo.getText());
			String tenChuHo = tfTenChuHo.getText();
			String cccdChuHo = tfCCCD.getText();
			String sdtChuHo = tfSdt.getText();
			String ngayDen = NgayDen.getValue().toString();
			String gioiTinh = GioiTinh.getValue();
			int soPhong = SoPhong.getValue();

			NhanKhauModel_Lam nhanKhauModel = new NhanKhauModel_Lam(maChuHo, maHo, "Chủ hộ", tenChuHo, cccdChuHo, gioiTinh);
			HoKhauModel_Tuan hoKhauModel = new HoKhauModel_Tuan(maHo, soPhong, ngayDen, "0001/01/01", sdtChuHo);
			new HoKhauService_Tuan().add(hoKhauModel);
			new NhanKhauService_Lam().add(nhanKhauModel);


			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
		// ghi nhan cac gia tri khi tat ca deu hop le

	}

}