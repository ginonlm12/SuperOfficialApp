package controller.nhankhau;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.NhanKhauModel_Lam;
import models.QuanHeModel;
import services.HoKhauService;
import services.NhanKhauService;
import services.NhanKhauService_Lam;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;


public class AddNhanKhau_Lam implements Initializable {
	@FXML
	private TextField tfCCCD;
	@FXML
	private ChoiceBox<String> tfDanToc;
	@FXML
	private ChoiceBox<String> tfGioiTinh;
	@FXML
	private TextField tfHoTen;
	//@FXML
	//private TextField tfIDHoKhau;
	@FXML
	private TextField tfIDNhanKhau;
	@FXML
	private DatePicker tfNgaySinh;
	@FXML
	private TextField tfNgheNghiep;
	@FXML
	private ChoiceBox<String> tfQHvsChuHo;
	@FXML
	private TextField tfQueQuan;
	@FXML
	private ChoiceBox<String> tfCountry;
	@FXML
	private ChoiceBox<String> tfProvince;
	@FXML
	private ChoiceBox<String> tfDistrict;
	@FXML
	private ChoiceBox<String> tfWard;
	@FXML
	private ChoiceBox<String> tfIDHoKhau;
	@FXML
	private CheckBox tfXacNhan;
	@FXML
	private Label tfTinh;
	@FXML
	private Label tfHuyen;
	@FXML
	private Label tfXa;
	@FXML
	private TextField tfSoPhong;
	private List<NhanKhauModel_Lam> listNhanKhau;
	ObservableList<String> genderList = FXCollections.observableArrayList("Nam", "Nữ");
	ObservableList<String> relationshipList = FXCollections.observableArrayList("Vợ/Chồng", "Con cái", "Bố mẹ", "Ông bà", "Cháu chắt", "Khác");
	ObservableList<String> ethnicityList = FXCollections.observableArrayList();
	ObservableList<String> QueQuanList = FXCollections.observableArrayList("Việt Nam", "Khác");
	ObservableList<String> Tinh_List = FXCollections.observableArrayList();
	ObservableList<String> Huyen_List = FXCollections.observableArrayList();
	ObservableList<String> Xa_List = FXCollections.observableArrayList();
	ObservableList<String> IDHoKhau_List = FXCollections.observableArrayList();

	private String CCCD = "-1";
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			listNhanKhau = new NhanKhauService_Lam().getListNhanKhau();
			for(NhanKhauModel_Lam nhankhau: listNhanKhau){
				String data = String.valueOf(nhankhau.getIDHoKhau());
				if (!IDHoKhau_List.contains(data)) {
					IDHoKhau_List.add(data);
				}
			}
			tfIDHoKhau.setItems(IDHoKhau_List);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		int IDnewNhanKhau = 0;
		try {
			IDnewNhanKhau = NhanKhauService_Lam.getNewIDNhanKhau();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		tfIDNhanKhau.setText(String.valueOf(IDnewNhanKhau));
		tfGioiTinh.setItems(genderList);
		tfQHvsChuHo.setItems(relationshipList);
		tfCountry.setItems(QueQuanList);

		try (Scanner scanner = new Scanner(new File("database/DanToc.txt"))) {
			while (scanner.hasNextLine()) {
				ethnicityList.add(scanner.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tfDanToc.setItems(ethnicityList);

		tfIDHoKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
				int IDHoKhau = Integer.parseInt(tfIDHoKhau.getValue());
			try {
				tfSoPhong.setText(String.valueOf(NhanKhauService_Lam.getSoPhong(IDHoKhau)));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		tfCountry.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				String selectedCountry = tfCountry.getValue();
				if (selectedCountry.equals("Khác")) {
					tfQueQuan.setVisible(true);
					tfTinh.setVisible(false);
					tfHuyen.setVisible(false);
					tfXa.setVisible(false);
					tfProvince.setVisible(false);
					tfDistrict.setVisible(false);
					tfWard.setVisible(false);
				}
				if (selectedCountry.equals("Việt Nam")){
					tfQueQuan.setVisible(false);
					tfTinh.setVisible(true);
					tfHuyen.setVisible(true);
					tfXa.setVisible(true);
					tfProvince.setVisible(true);
					tfDistrict.setVisible(true);
					tfWard.setVisible(true);
					try {
						Tinh_List = NhanKhauService_Lam.getProvince();
						tfProvince.setItems(Tinh_List);

						tfProvince.getSelectionModel().selectedItemProperty().addListener((observablee, oldValuee, newValuee) -> {
							try {
								String selectedProvince = tfProvince.getValue();

								if (selectedProvince != null && !selectedProvince.isEmpty()) {
									Huyen_List = NhanKhauService_Lam.GetDistrict(selectedProvince);
									tfDistrict.setItems(Huyen_List);

									tfDistrict.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
										try {
											String selectedDistrict = tfDistrict.getValue();

											if (selectedDistrict != null && !selectedDistrict.isEmpty()) {
												Xa_List = NhanKhauService_Lam.GetWard(selectedDistrict, selectedProvince);
												tfWard.setItems(Xa_List);
											}
										} catch (SQLException | ClassNotFoundException e) {
											e.printStackTrace();
										}
									});
								}
							} catch (SQLException | ClassNotFoundException e) {
								e.printStackTrace();
							}
						});
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
				});
	}
	@FXML
	void addNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException{
		// khai bao mot mau de so sanh
		Pattern pattern;
		System.out.println("haha");
		// kiem tra id nhap vao
		// id la day so tu 1 toi 11 chu so
//		pattern = Pattern.compile("\\d{1,11}");
//		if (!pattern.matcher(tfId.getText()).matches()) {
//			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào mã nhân khẩu hợp lệ!", ButtonType.OK);
//			alert.setHeaderText(null);
//			alert.showAndWait();
//			return;
//		}
		// kiem tra ID them moi co bi trung voi nhung ID da ton tai hay khong
//		List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
//		for (NhanKhauModel nhankhau : listNhanKhauModels) {
//			if (nhankhau.getId() == Integer.parseInt(tfId.getText())) {
//				Alert alert = new Alert(AlertType.WARNING, "ID bị trùng với một người khác!", ButtonType.OK);
//				alert.setHeaderText(null);
//				alert.showAndWait();
//				return;
//			}
//		}

		// kiem tra ten nhap vao
		// ten nhap vao la chuoi tu 1 toi 50 ki tu
		if (tfHoTen.getText().length() >= 50 || tfHoTen.getText().length() <= 1) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào 1 tên hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// kiem tra da chon gioi tinh hay chua
		if (tfGioiTinh.getValue() == null || tfGioiTinh.getValue().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn giới tính!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
//
		// kiem tra cmnd nhap vao
		// cmnd nhap vao phai la mot day so tu 1 toi 20 so
//		if(!CCCD.equals("0") || (tfCCCD.getText() != null && !tfCCCD.getText().isEmpty())){
//			CCCD = tfCCCD.getText();
//		};
//		pattern = Pattern.compile("\\d{1,20}");
//		if(CCCD == null || CCCD.isEmpty()){
//			CCCD = "0";
//			Alert alert = new Alert(AlertType.WARNING, "Xác nhận nhân khẩu không có CCCD!", ButtonType.OK);
//			alert.setHeaderText(null);
//			alert.showAndWait();
//		}
//
//		if(!pattern.matcher(CCCD).matches()){
//			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào CMND hợp lệ!", ButtonType.OK);
//			alert.setHeaderText(null);
//			alert.showAndWait();
//			return;
//		}

		// kiem tra ngay sinh da duoc nhap hay chua

		if (tfNgaySinh.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng chọn ngày sinh", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		// Kiem tra ID Hộ Khẩu có đúng định dạng và đã tạo chưa?
		pattern = Pattern.compile("\\d{1,11}");
		if (!pattern.matcher(tfIDHoKhau.getValue()).matches()) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ID hộ khẩu hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		List<NhanKhauModel_Lam> listNhanKhauModels = new NhanKhauService_Lam().getListNhanKhau();
		boolean checkExistedHoKhau = false;
		for (NhanKhauModel_Lam nhankhau : listNhanKhauModels) {
			//System.out.print("haha + false");
			if (nhankhau.getIDNhanKhau() == Integer.parseInt(tfIDHoKhau.getValue())) {
				//System.out.print("haha + true");
				checkExistedHoKhau = true;
				break;
			}
		}
		if(checkExistedHoKhau == false){
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ID hộ khẩu đã tồn tại!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		if (tfQHvsChuHo.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng chọn quan hệ với chủ hộ", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		if (tfDanToc.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng chọn dân tộc", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		if(tfCountry.getValue() == null){
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng điền quê quán", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		String QueQuan = "(Chưa nhập)";
		if(tfCountry.getValue().equals("Khác")){
			if(tfQueQuan.getText() == null || tfQueQuan.getText().isEmpty()){
				Alert alert = new Alert(AlertType.WARNING, "Vui lòng điền đầy đủ quê quán nước ngoài", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
			QueQuan = tfQueQuan.getText();
		}
		if(tfCountry.getValue().equals("Việt Nam")){
			if(tfWard.getValue() == null){
				Alert alert = new Alert(AlertType.WARNING, "Vui lòng điền đầy đủ quê quán trong nước", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
			QueQuan = tfWard.getValue() + ", " + tfDistrict.getValue() + ", " + tfProvince.getValue() + ", Việt Nam";
		}

		if(!tfXacNhan.isSelected()){
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng xác nhận những thông tin cung cấp trên là chính xác", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		System.out.println("haha");

		// ghi nhan gia tri ghi tat ca deu da hop le
		int IDNhanKhau = Integer.parseInt(tfIDNhanKhau.getText());
		int IDHoKhau = Integer.parseInt(tfIDHoKhau.getValue());
		String QHvsChuHo = tfQHvsChuHo.getValue();
		String HoTen = tfHoTen.getText();
		String NgaySinh = String.valueOf(tfNgaySinh.getValue());
		String CCCD;
		if(tfCCCD.getText() == null || tfIDHoKhau.getValue().isEmpty())
			CCCD = "Chưa cung cấp";
		else{
			CCCD = tfCCCD.getText();
		}
		String NgheNghiep;
		if(tfNgheNghiep.getText() == null || tfNgheNghiep.getText().isEmpty())
			NgheNghiep = "Chưa cung cấp";
		else{
			NgheNghiep = tfNgheNghiep.getText();
		}
		String GioiTinh = tfGioiTinh.getValue();
		String DanToc = tfDanToc.getValue();
//		if(tfQueQuan.getText() == null || tfQueQuan.getText().isEmpty())
//			QueQuan = "Chưa cung cấp";
//		else{
//			QueQuan = tfQueQuan.getText();
//		}
//
		System.out.print(QueQuan);
		NhanKhauService_Lam nhanKhauService = new NhanKhauService_Lam();
//		QuanHeService quanHeService = new QuanHeService();
//
		NhanKhauModel_Lam nhanKhauModel = new NhanKhauModel_Lam(IDNhanKhau, IDHoKhau, QHvsChuHo, HoTen, NgaySinh, CCCD, NgheNghiep, GioiTinh, DanToc, QueQuan);
//		QuanHeModel quanHeModel = new QuanHeModel(mahokhauInt, idInt, quanheString);

		if(!nhanKhauService.add(nhanKhauModel)){
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ID hộ khẩu đã tồn tại!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		else{
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}


}
