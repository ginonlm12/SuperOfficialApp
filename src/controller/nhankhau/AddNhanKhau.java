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
import models.NhanKhauModel;
import services.NhanKhauService;
import services.XuLyLoiService;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;
import java.io.FileInputStream;

public class AddNhanKhau implements Initializable {
	ObservableList<String> ethnicityList = FXCollections.observableArrayList("Không");
	@FXML
	private TextField tfCCCD;
	ObservableList<Integer> IDHoKhau_onlyID = FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> tfDanToc;
	@FXML
	private TextField tfHoTen;
	@FXML
	private Label Error;
	@FXML
	private TextField tfIDNhanKhau;
	@FXML
	private DatePicker tfNgaySinh;
	@FXML
	private TextField tfNgheNghiep;
	@FXML
	private ComboBox<String> tfGioiTinh;
	@FXML
	private TextField tfQueQuan;
	@FXML
	private ComboBox<String> tfQHvsChuHo;
	@FXML
	private ComboBox<String> tfCountry;
	@FXML
	private ComboBox<String> tfProvince;
	@FXML
	private ComboBox<String> tfDistrict;
	@FXML
	private ComboBox<String> tfWard;
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
	private List<NhanKhauModel> listNhanKhau;
	ObservableList<String> genderList = FXCollections.observableArrayList("Nam", "Nữ");
	ObservableList<String> relationshipList = FXCollections.observableArrayList("Vợ/Chồng", "Con cái", "Bố mẹ", "Ông bà", "Cháu chắt", "Khác");
	@FXML
	private ComboBox<String> tfIDHoKhau;
	ObservableList<String> QueQuanList = FXCollections.observableArrayList("Việt Nam", "Khác");
	ObservableList<String> Tinh_List = FXCollections.observableArrayList();
	ObservableList<String> Huyen_List = FXCollections.observableArrayList();
	ObservableList<String> Xa_List = FXCollections.observableArrayList();
	ObservableList<String> IDHoKhau_List = FXCollections.observableArrayList();
	@FXML
	private TextField tfChuHo;

	public void initialize(URL url, ResourceBundle resourceBundle) {
		Error.setVisible(false);
		Error.setStyle("-fx-color: linear-gradient(to bottom right, #FF0000, #CC0000);");
		try {
			listNhanKhau = new NhanKhauService().getListNhanKhau();
			Vector<String> household = new Vector<>();
			for (NhanKhauModel nhankhau : listNhanKhau) {
				Integer data = nhankhau.getIDHoKhau();
				if (!IDHoKhau_onlyID.contains(data)) {
					IDHoKhau_onlyID.add(data);
					household.add(data + " - Phòng: " + NhanKhauService.getSoPhong(data));
				}
			}
			NhanKhauService.sortVectorByRoomNumber(household);
			for (String hold : household) {
				IDHoKhau_List.add(hold);
			}
			tfIDHoKhau.setItems(IDHoKhau_List);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		int IDnewNhanKhau = 0;
		try {
			IDnewNhanKhau = NhanKhauService.getNewIDNhanKhau();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		tfIDNhanKhau.setText(String.valueOf(IDnewNhanKhau));
		tfGioiTinh.setItems(genderList);
		tfQHvsChuHo.setItems(relationshipList);
		tfCountry.setItems(QueQuanList);

		try (Scanner scanner = new Scanner(new FileInputStream(new File("database/DanToc.txt")), "UTF-8")) {
			while (scanner.hasNextLine()) {
				ethnicityList.add(scanner.nextLine());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tfDanToc.setItems(ethnicityList);

		tfIDHoKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
			int IDHoKhau = Integer.parseInt(NhanKhauService.extractIdHoKhau(tfIDHoKhau.getValue()));
			try {
				tfSoPhong.setText(String.valueOf(NhanKhauService.getSoPhong(IDHoKhau)));
				tfChuHo.setText(NhanKhauService.getChuHo(IDHoKhau));
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
			if (selectedCountry.equals("Việt Nam")) {
				tfQueQuan.setVisible(false);
				tfTinh.setVisible(true);
				tfHuyen.setVisible(true);
				tfXa.setVisible(true);
				tfProvince.setVisible(true);
				tfDistrict.setVisible(true);
				tfWard.setVisible(true);
				try {
					Tinh_List = NhanKhauService.getProvince();
					tfProvince.setItems(Tinh_List);

					tfProvince.getSelectionModel().selectedItemProperty().addListener((observablee, oldValuee, newValuee) -> {
						try {
							String selectedProvince = tfProvince.getValue();

							if (selectedProvince != null && !selectedProvince.isEmpty()) {
								Huyen_List = NhanKhauService.GetDistrict(selectedProvince);
								tfDistrict.setItems(Huyen_List);

								tfDistrict.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
									try {
										String selectedDistrict = tfDistrict.getValue();

										if (selectedDistrict != null && !selectedDistrict.isEmpty()) {
											Xa_List = NhanKhauService.GetWard(selectedDistrict, selectedProvince);
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

		if (tfHoTen.getText().length() >= 50 || tfHoTen.getText().length() <= 1) {
			XuLyLoiService.xuLyLoi(Error, tfHoTen, "Hãy nhập vào 1 tên hợp lệ!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfHoTen);
		}

		if (tfGioiTinh.getValue() == null || tfGioiTinh.getValue().isEmpty()) {
			XuLyLoiService.xuLyLoi(Error, tfGioiTinh, "Hãy chọn giới tính!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfGioiTinh);
		}

		if (tfNgaySinh.getValue() == null /*|| tfNgaySinh.getValue().isEmpty()*/) {
			XuLyLoiService.xuLyLoi(Error, tfNgaySinh, "Vui lòng chọn ngày sinh", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfNgaySinh);
		}

		LocalDate selectedDate = tfNgaySinh.getValue();
		LocalDate currentDate = LocalDate.now();

		if (selectedDate.isAfter(currentDate)) {
			XuLyLoiService.xuLyLoi(Error, tfNgaySinh, "Vui lòng chọn ngày sinh hợp lệ!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfNgaySinh);
		}

		if (tfIDHoKhau.getValue() == null) {
			XuLyLoiService.xuLyLoi(Error, tfIDHoKhau, "Vui lòng chọn hộ khẩu!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfIDHoKhau);
		}

		if (tfQHvsChuHo.getValue() == null) {
			XuLyLoiService.xuLyLoi(Error, tfQHvsChuHo, "Vui lòng chọn quan hệ với chủ hộ!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfQHvsChuHo);
		}

		if (tfDanToc.getValue() == null) {
			XuLyLoiService.xuLyLoi(Error, tfDanToc, "Vui lòng chọn dân tộc!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfDanToc);
		}

		if (Period.between(selectedDate, currentDate).getYears() >= 18 && !tfDanToc.equals("Không")) {
			Pattern pattern;
			pattern = Pattern.compile("\\d{1,12}");
			if (tfCCCD.getText().isEmpty() || tfCCCD.getText() == null) {
				XuLyLoiService.xuLyLoi(Error, tfCCCD, "Vui lòng điền CCCD hoặc CMND!", 0, -8);
				return;
			} else if (!pattern.matcher(tfCCCD.getText()).matches()) {
				XuLyLoiService.xuLyLoi(Error, tfCCCD, "CCCD phải đúng định dạng!", 0, -8);
				return;
			} else {
				XuLyLoiService.xoaLoi(Error, tfCCCD);
			}
		}

		if(tfCountry.getValue() == null){
			XuLyLoiService.xuLyLoi(Error, tfCountry, "Vui lòng điền quê quán!", 0, -10);
			return;
		} else {
			XuLyLoiService.xoaLoi(Error, tfCountry);
		}
		String QueQuan = "(Chưa nhập)";
		if(tfCountry.getValue().equals("Khác")){
			if(tfQueQuan.getText() == null || tfQueQuan.getText().isEmpty()){
				XuLyLoiService.xuLyLoi(Error, tfQueQuan, "Vui lòng điền đầy đủ quê quán nước ngoài!", 0, -10);
				return;
			} else {
				XuLyLoiService.xoaLoi(Error, tfQueQuan);
			}
			QueQuan = tfQueQuan.getText();
		}
		if(tfCountry.getValue().equals("Việt Nam")){
			if(tfWard.getValue() == null){
				if (tfDistrict.getValue() == null) {
					XuLyLoiService.xuLyLoi(Error, tfDistrict, "Vui lòng điền đầy đủ quê quán trong nước!", 0, 30);
				} else {
					XuLyLoiService.xoaLoi(Error, tfProvince);
				}
				if (tfProvince.getValue() == null) {
					XuLyLoiService.xuLyLoi(Error, tfProvince, "Vui lòng điền đầy đủ quê quán trong nước!", 0, 30);
				} else {
					XuLyLoiService.xoaLoi(Error, tfDistrict);
				}
				XuLyLoiService.xuLyLoi(Error, tfWard, "Vui lòng điền đầy đủ quê quán trong nước!", 0, 30);
				return;
			} else {
				XuLyLoiService.xoaLoi(Error, tfWard);
			}
			QueQuan = tfWard.getValue() + ", " + tfDistrict.getValue() + ", " + tfProvince.getValue() + ", Việt Nam";
		}

		if(!tfXacNhan.isSelected()){
			Error.setVisible(true);
			Error.setLayoutX(tfXacNhan.getLayoutX());
			Error.setLayoutY(tfXacNhan.getLayoutY() - 10);
			Error.setText("Vui lòng xác nhận!");
			return;
		} else {
			Error.setVisible(false);
		}

		// Ghi nhan gia tri ghi tat ca deu da hop le
		int IDNhanKhau = Integer.parseInt(tfIDNhanKhau.getText());
		int IDHoKhau = Integer.parseInt(NhanKhauService.extractIdHoKhau(tfIDHoKhau.getValue()));
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

		NhanKhauService nhanKhauService = new NhanKhauService();

		NhanKhauModel nhanKhauModel = new NhanKhauModel(IDNhanKhau, IDHoKhau, QHvsChuHo, HoTen, NgaySinh, CCCD, NgheNghiep, GioiTinh, DanToc, QueQuan);

		if(!nhanKhauService.add(nhanKhauModel)){
			Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào ID hộ khẩu đã tồn tại!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		else{
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}
	}
}
