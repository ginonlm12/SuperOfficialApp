package controller.thuphi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;
import services.PhongService;
import services.ThuPhiService;
import services.XeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

	private NhanKhauModel nhanKhauModel;
	FXMLLoader loaderChooseKhoanThu;
	Scene sceneChooseKhoanThu;
	private List<KhoanThuModel> listKhoanThuChoose; 
	private Double tien = 0.0;

	@Override
	public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
		// set dpNgayDong la ngay hien tai
		dpNgayDong.setValue(java.time.LocalDate.now());
	}

	public NhanKhauModel getNhanKhauModel() {
		return nhanKhauModel;
	}

	public void setTfSearch(String tfSearch) {
		this.tfTenKhoanThu.appendText(tfSearch + ", ");
	}

	public void chooseKhoanThu() throws IOException, ClassNotFoundException, SQLException {
		if (nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn chủ hộ trước!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		if (loaderChooseKhoanThu == null) {
			loaderChooseKhoanThu = new FXMLLoader();
			loaderChooseKhoanThu.setLocation(getClass().getResource("/views/ThuPhi/ChooseKhoanThu.fxml"));
			sceneChooseKhoanThu = new Scene(loaderChooseKhoanThu.load(), 800, 600);
		}
		
		ChooseKhoanThu chooseKhoanThu = loaderChooseKhoanThu.getController();
		chooseKhoanThu.setNhanKhauModel(nhanKhauModel);
		Stage stage = new Stage();
		stage.setScene(sceneChooseKhoanThu);
		stage.setResizable(false);
		stage.showAndWait();

		listKhoanThuChoose = chooseKhoanThu.getListKhoanThuChoose();
		if (listKhoanThuChoose == null)
			return;

		String dsTen = new String("");
		for (KhoanThuModel khoanThuModel : listKhoanThuChoose) {
			dsTen = dsTen + khoanThuModel.getTenKT() + ", ";
		}
		if (dsTen.length() > 0)
			dsTen = dsTen.substring(0, dsTen.length() - 2);
		tfTenKhoanThu.setText(dsTen);
		tien = 0.0;
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
		sceneChooseKhoanThu = null;
		loaderChooseKhoanThu = null;
		listKhoanThuChoose = null;
		tfTenKhoanThu.setText("");
		tfSoTienPhaiDong.setText("");
		tfSoTienDong.setText("");
		tien = 0.0;
	}

	public void tinhSoTienPhaiDong() throws ClassNotFoundException, SQLException {
		for(KhoanThuModel khoanThuModel : listKhoanThuChoose) {
			String loai = khoanThuModel.getLoaiKhoanThu();
	
			if (loai.equals("Tiền quản lý")) {
				PhongModel phongModel = PhongService.getPhongModel(nhanKhauModel.getSoPhong());
				if (phongModel.getLoaiPhong().equals("Cao cấp")) {
					tien += khoanThuModel.getTrongSoDienTich() * phongModel.getDienTich()			
					+ khoanThuModel.getHangSo();
				}
				else {
					tien += khoanThuModel.getTrongSoSTV() * phongModel.getDienTich() 
					+ khoanThuModel.getHangSo();
				}
			}
			else if (loai.equals("Tiền giữ xe")) {
				XeModel xeModel = XeService.getXeModel(nhanKhauModel.getIDHoKhau());
				tien += khoanThuModel.getTrongSoDienTich() * xeModel.getOTo()
				+ khoanThuModel.getTrongSoSTV() * xeModel.getXeMay()
				+ khoanThuModel.getHangSo() * xeModel.getXeDap();
			}
		}

		tfSoTienPhaiDong.setText(String.valueOf(tien));
		tfSoTienDong.setText(String.valueOf(tien));
	}

	public static Double tinhSoTienPhaiDong(KhoanThuModel khoanThuModel, NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
		String loai = khoanThuModel.getLoaiKhoanThu();
		double tien = 0;
		if (loai.equals("Tiền quản lý")) {
			PhongModel phongModel = PhongService.getPhongModel(nhanKhauModel.getSoPhong());
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
			XeModel xeModel = XeService.getXeModel(nhanKhauModel.getIDHoKhau());
			tien = khoanThuModel.getTrongSoDienTich() * xeModel.getOTo()
			+ khoanThuModel.getTrongSoSTV() * xeModel.getXeMay()
			+ khoanThuModel.getHangSo() * xeModel.getXeDap();
		}

		return tien;
	}

	public void addThuPhi(ActionEvent event) throws ClassNotFoundException, SQLException {
		if (tfTenKhoanThu.getText().length() == 0 || tfTenChuHo.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn khoản thu và chủ hộ!", ButtonType.OK);
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
		for(KhoanThuModel khoanThuModel : listKhoanThuChoose) {
			ThuPhiModel thuPhiModel = new ThuPhiModel();
			thuPhiModel.setIDHoKhau(nhanKhauModel.getIDHoKhau());
			thuPhiModel.setIDKhoanThu(khoanThuModel.getIDKhoanThu());
			thuPhiModel.setSoTienPhaiDong(Double.parseDouble(tfSoTienPhaiDong.getText()));
			thuPhiModel.setSoTien(Double.parseDouble(tfSoTienDong.getText()));
			thuPhiModel.setNgayDong(dpNgayDong.getValue().toString());
	
			ThuPhiService.add(thuPhiModel);
		}
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

		Alert alert = new Alert(AlertType.INFORMATION, "Thêm thu phí thành công!", ButtonType.OK);
	}
}
