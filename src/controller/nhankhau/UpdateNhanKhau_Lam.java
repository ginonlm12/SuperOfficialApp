package controller.nhankhau;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.NhanKhauModel_Lam;
import services.NhanKhauService_Lam;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UpdateNhanKhau_Lam {
	ObservableList<String> genderList = FXCollections.observableArrayList("Nam", "Nữ");
	ObservableList<String> relationshipList = FXCollections.observableArrayList("Vợ/Chồng", "Con cái", "Bố mẹ", "Ông bà", "Cháu chắt", "Khác");
    ObservableList<String> ethnicityList = FXCollections.observableArrayList("Không mang dân tộc Việt Nam");
	ObservableList<String> QueQuanList = FXCollections.observableArrayList("Việt Nam", "Khác");
	ObservableList<String> Tinh_List = FXCollections.observableArrayList();
	ObservableList<String> Huyen_List = FXCollections.observableArrayList();
	ObservableList<String> Xa_List = FXCollections.observableArrayList();
	ObservableList<String> IDHoKhau_List = FXCollections.observableArrayList();
	private int maNhanKhau;
	@FXML
	private TextField tfCCCD;
	@FXML
    private ComboBox<String> tfCountry;
	@FXML
    private ComboBox<String> tfDanToc;
	@FXML
    private ComboBox<String> tfDistrict;
	@FXML
    private ComboBox<String> tfGioiTinh;
	@FXML
	private TextField tfHoTen;
	@FXML
	private Label tfHuyen;
	@FXML
	private Text tfIDNhanKhau;
	@FXML
	private DatePicker tfNgaySinh;
	@FXML
	private TextField tfNgheNghiep;
	@FXML
    private ComboBox<String> tfProvince;
	@FXML
    private ComboBox<String> tfQHvsChuHo;
	@FXML
	private TextField tfQueQuan;
	@FXML
	private Label tfTinh;
	@FXML
    private ComboBox<String> tfWard;
	@FXML
	private Label tfXa;
	@FXML
	private CheckBox tfXacNhan;
	@FXML
	private CheckBox tfXacNhanQQ;
	@FXML
	private TextField tfSoPhong;
	@FXML
	private TextField tfQuequancu;
	@FXML
	private Label tfQuequanmoi_text;
	@FXML
    private ComboBox<String> tfIDHoKhau;
	private NhanKhauModel_Lam nhanKhauModel;
	private NhanKhauModel_Lam newNhanKhauModel;
	private List<NhanKhauModel_Lam> listNhanKhau;

	public NhanKhauModel_Lam getNhanKhauModel() {
		return nhanKhauModel;
	}

	public void setNhanKhauModel(NhanKhauModel_Lam nhanKhauModel) throws ClassNotFoundException, SQLException {
		this.nhanKhauModel = nhanKhauModel;
		maNhanKhau = nhanKhauModel.getIDNhanKhau();
		tfIDNhanKhau.setText(String.valueOf(maNhanKhau));
		tfHoTen.setText(nhanKhauModel.getHoTen());
		tfCCCD.setText(nhanKhauModel.getCCCD());
		tfGioiTinh.setValue(nhanKhauModel.getGioiTinh());

		String NgaySinh = nhanKhauModel.getNgaySinh();
		if (NgaySinh != null && !NgaySinh.isEmpty()) {
			LocalDate DOB = LocalDate.parse(NgaySinh, DateTimeFormatter.ISO_DATE);
			tfNgaySinh.setValue(DOB);
		} else {
			tfNgaySinh.setValue(LocalDate.parse("01/01/1900", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}

		tfNgheNghiep.setText(nhanKhauModel.getNgheNghiep());
		tfIDHoKhau.setValue(String.valueOf(nhanKhauModel.getIDHoKhau()));
		tfSoPhong.setText(String.valueOf(nhanKhauModel.getSoPhong()));
		tfDanToc.setValue(nhanKhauModel.getDanToc());
		tfQHvsChuHo.setValue(nhanKhauModel.getQHvsChuHo());

		tfQuequancu.setText(nhanKhauModel.getQueQuan());

		initializeLogic();
	}

	private void initializeLogic() {
		try {
			listNhanKhau = new NhanKhauService_Lam().getListNhanKhau();
			for (NhanKhauModel_Lam nhankhau : listNhanKhau) {
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

		if (nhanKhauModel.getQHvsChuHo().equals("Chủ hộ")) {
			IDHoKhau_List.clear();
			IDHoKhau_List.add(String.valueOf(nhanKhauModel.getIDHoKhau()));
			tfIDHoKhau.setItems(IDHoKhau_List);
			tfIDHoKhau.setValue(String.valueOf(nhanKhauModel.getIDHoKhau()));
		}
//		if(!nhanKhauModel.getQHvsChuHo().equals("Chủ hộ")){
//			IDHoKhau_List.clear();
//			IDHoKhau_List.add(String.valueOf(nhanKhauModel.getIDHoKhau()));
//			tfIDHoKhau.setItems(IDHoKhau_List);
//		}

		tfIDHoKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
//			if(nhanKhauModel.getQHvsChuHo().equals("Chủ hộ")){
//				tfIDHoKhau.setValue(String.valueOf(nhanKhauModel.getIDHoKhau()));
//				Alert alert = new Alert(AlertType.WARNING, "Không thiết lập thay đổi ID Hộ khẩu mới cho Chủ hộ!", ButtonType.OK);
//				alert.setHeaderText(null);
//				alert.showAndWait();
//				return;
//			}

			int IDHoKhau = Integer.parseInt(tfIDHoKhau.getValue());
			try {
				tfSoPhong.setText(String.valueOf(NhanKhauService_Lam.getSoPhong(IDHoKhau)));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		tfXacNhanQQ.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(javafx.beans.value.ObservableValue<? extends Boolean> observable, Boolean oldValue,
								Boolean newValue) {
				if (tfXacNhanQQ.isSelected() || newValue) {
					tfQuequanmoi_text.setVisible(true);
					tfCountry.setVisible(true);
					tfCountry.getSelectionModel().selectedItemProperty().addListener((observable2, oldValue2, newValue2) -> {
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
						if (selectedCountry.equals("Việt Nam")) {
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
				} else {
					tfQuequanmoi_text.setVisible(false);
					tfCountry.setVisible(false);
					tfQueQuan.setVisible(false);
					tfTinh.setVisible(false);
					tfHuyen.setVisible(false);
					tfXa.setVisible(false);
					tfProvince.setVisible(false);
					tfDistrict.setVisible(false);
					tfWard.setVisible(false);
				}
			}
		});
	}

	public void updateNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
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
		if (!checkExistedHoKhau) {
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

		if (tfQHvsChuHo.equals("Chủ hộ") && !tfIDHoKhau.equals(nhanKhauModel.getIDHoKhau())) {
			Alert alert = new Alert(AlertType.WARNING, "Không thiết lập thay đổi ID Hộ khẩu mới cho Chủ hộ!", ButtonType.OK);
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

		String QueQuan = nhanKhauModel.getQueQuan();
		if (tfCountry.getValue() != null && tfCountry.getValue().equals("Khác")) {
			if (tfQueQuan.getText() == null || tfQueQuan.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING, "Vui lòng điền đầy đủ quê quán nước ngoài", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
			QueQuan = tfQueQuan.getText();
		}
		if (tfCountry.getValue() != null && tfCountry.getValue().equals("Việt Nam")) {
			if (tfWard.getValue() == null) {
				Alert alert = new Alert(AlertType.WARNING, "Vui lòng điền đầy đủ quê quán trong nước", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}
			QueQuan = tfWard.getValue() + ", " + tfDistrict.getValue() + ", " + tfProvince.getValue() + ", Việt Nam";
		}

		if (!tfXacNhan.isSelected()) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng xác nhận những thông tin cung cấp thay đổi trên là chính xác", ButtonType.OK);
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
		if (tfCCCD.getText() == null || tfIDHoKhau.getValue().isEmpty())
			CCCD = "Chưa cung cấp";
		else {
			CCCD = tfCCCD.getText();
		}
		String NgheNghiep;
		if (tfNgheNghiep.getText() == null || tfNgheNghiep.getText().isEmpty())
			NgheNghiep = "Chưa cung cấp";
		else {
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
		newNhanKhauModel = new NhanKhauModel_Lam(IDNhanKhau, IDHoKhau, QHvsChuHo, HoTen, NgaySinh, CCCD, NgheNghiep, GioiTinh, DanToc, QueQuan);
//		QuanHeModel quanHeModel = new QuanHeModel(mahokhauInt, idInt, quanheString);
		if (NhanKhauModel_Lam.compareNhanKhauModels(newNhanKhauModel, nhanKhauModel)) {
			Alert alert = new Alert(AlertType.WARNING, "Thông tin không thay đổi", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}

		if (!NhanKhauService_Lam.update(newNhanKhauModel)) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ID hộ khẩu đã tồn tại!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
        } else {
			nhanKhauModel = newNhanKhauModel;
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}

}
