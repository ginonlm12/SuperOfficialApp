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

public class AddHoKhau_Tuan implements Initializable {
	@FXML
	private ChoiceBox<String> GioiTinh;

	@FXML
	private DatePicker NgayDen;

	@FXML
	private TextField tfCCCD;

	@FXML
	private TextField tfChuHo;

	@FXML
	private TextField tfMaChuHo;

	@FXML
	private TextField tfMaHoKhau;

	@FXML
	private TextField tfSdt;

    ObservableList<String> listGioiTinh = FXCollections.observableArrayList("Nam", "Nữ");
    ObservableList<Integer> listSoPhong = FXCollections.observableArrayList();
    @FXML
	private ChoiceBox<Integer> SoPhong;
    @FXML
	private TextField tfTenChuHo;


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
	}

	@FXML
	public void addHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
		// khai bao mot mau de so sanh
//		if(SoPhong.getValue() == null || tfTenChuHo.getText() == ""){
//			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập đầy đủ thông tin!", ButtonType.OK);
//			alert.setHeaderText(null);
//			alert.showAndWait();
//		}

		Pattern pattern;

        if (tfTenChuHo.getText() == "") {
            Alert alert = new Alert(AlertType.WARNING, "Hãy nhập tên chủ hộ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
        if (SoPhong.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING, "Chọn phòng cho hộ khẩu!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
		// kiem tra cmnd nhap vao
		// cmnd nhap vao phai la mot day so tu 1 toi 20 so
		pattern = Pattern.compile("\\d{1,20}");
		if (!pattern.matcher(tfCCCD.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CCCD hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra sdt nhap vao
		// SDT nhap vao phai khong chua chu cai va nho hon 15 chu so
		pattern = Pattern.compile("\\d{1,15}");
		if (!pattern.matcher(tfSdt.getText()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào SĐT hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

        if (NgayDen.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING, "Vui lòng chọn ngày chuyển đến", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// ghi nhan cac gia tri khi tat ca deu hop le
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

}