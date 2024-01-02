package controller.hokhau;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.HoKhauBean;
import models.HoKhauModel;
import models.NhanKhauModel;
import services.HoKhauService;
import services.PhongService;
import services.XuLyLoiService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UpdateHoKhau {

	ObservableList<Integer> IDNhanKhauinHoKhau = FXCollections.observableArrayList();
	ObservableList<Integer> SoPhong = FXCollections.observableArrayList();

    @FXML
    private Pane PaneField;
    @FXML
    private TextField tfHoTen;
    @FXML
    private TextField tfIDHoKhau;
    @FXML
      private ComboBox<Integer> tfIDNhanKhau;
    @FXML
    private DatePicker tfNgayDen;
    @FXML
    private TextField tfSDT;
    @FXML
    private ComboBox<Integer> tfSoPhong;

	private HoKhauBean hoKhauBean;
	private HoKhauBean newhoKhauBean;
	private List<NhanKhauModel> nhanKhauList = new ArrayList<>();
	private HoKhauModel hoKhauModel;
	private NhanKhauModel chuHo;
	@FXML
	private Label xuliloi;

	@FXML
	void updateHoKhau(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

		if(xuliloi.isVisible()){
			Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng sửa lỗi trước khi cập nhật!", ButtonType.OK);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();
        }

		else{
			// ghi nhan gia tri ghi tat ca deu da hop le
			HoKhauModel newHoKhauModel = new HoKhauModel();
			newHoKhauModel = hoKhauModel;

			newhoKhauBean.setSoTV(hoKhauBean.getSoTV());
			newhoKhauBean.setListNhanKhau(hoKhauBean.getListNhanKhau());
			newhoKhauBean.setHoKhauModel_tuan(newHoKhauModel);
			//System.out.println("Số phòng cũ 1: " + hoKhauModel.getSoPhong());
			newHoKhauModel.setSoPhong(tfSoPhong.getValue());
			//System.out.println("Số phòng cũ 2: " + hoKhauModel.getSoPhong());

			newHoKhauModel.setNgayDen(String.valueOf(tfNgayDen.getValue()));
			newHoKhauModel.setSDT(tfSDT.getText());
			//System.out.println("Số phòng cũ 3: " + hoKhauModel.getSoPhong());
//		System.out.println("ID Nhân khẩu mới: " + tfIDNhanKhau);
//		System.out.println("ID Nhân khẩu cũ: " + chuHo.getIDNhanKhau());
			if (!tfIDNhanKhau.getValue().equals(chuHo.getIDNhanKhau())) {
				int IDHoKhau = hoKhauBean.getHoKhauModel_tuan().getIDHoKhau();
				//int old_IDChuho = chuHo.getIDNhanKhau();
				int new_IDChuho = tfIDNhanKhau.getValue();
//				System.out.println("haha");
				// HoKhauService.changeChuHo(IDHoKhau, new_IDChuho);

				Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng cập nhật lại quan hệ với chủ hộ mới", ButtonType.OK, ButtonType.NO);
				alert.setHeaderText(null);
				Optional<ButtonType> result = alert.showAndWait();

				if (result.isPresent() && result.get() == ButtonType.NO) {
					return;
				} else {
					HoKhauService.changeChuHo(IDHoKhau, new_IDChuho);

					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/views/hokhau/UpdateQHvsChuHo.fxml"));
					Parent home = loader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(home, 500, 470));
					UpdateQHvsChuHo updateQHvsChuHo = loader.getController();

					if (updateQHvsChuHo == null)
						return;
					updateQHvsChuHo.setHoKhauBean(hoKhauBean);
					stage.setResizable(false);
					stage.showAndWait();
					nhanKhauList = updateQHvsChuHo.getNhanKhauList();
				}
			}
			HoKhauService.updateHoKhau(newHoKhauModel);
			hoKhauBean.setHoKhauModel_tuan(newHoKhauModel);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}


	}

	public void setHoKhauModel(HoKhauBean hoKhauBean) throws ClassNotFoundException, SQLException {


		this.hoKhauBean = hoKhauBean;
		this.nhanKhauList = hoKhauBean.getListNhanKhau();
		this.hoKhauModel = hoKhauBean.getHoKhauModel_tuan();
		this.newhoKhauBean = hoKhauBean;
		System.out.println(hoKhauBean.getSoTV());

		tfIDHoKhau.setText(String.valueOf(hoKhauModel.getIDHoKhau()));
		tfSoPhong.setValue(hoKhauModel.getSoPhong());
		LocalDate DOB = LocalDate.parse(hoKhauModel.getNgayDen(), DateTimeFormatter.ISO_DATE);
		tfNgayDen.setValue(DOB);
		tfSDT.setText(hoKhauModel.getSDT());
		for (NhanKhauModel nhankhau : nhanKhauList) {
			if (nhankhau.getQHvsChuHo().equals("Chủ hộ")) {
				chuHo = nhankhau;
			}
			IDNhanKhauinHoKhau.add(nhankhau.getIDNhanKhau());
		}
		tfIDNhanKhau.setValue(chuHo.getIDNhanKhau());
		tfHoTen.setText(chuHo.getHoTen());

		tfIDNhanKhau.setItems(IDNhanKhauinHoKhau);
		SoPhong = PhongService.getListSoPhong("Duoc su dung");
		SoPhong.add(hoKhauModel.getSoPhong());
		tfSoPhong.setItems(SoPhong);

		tfIDNhanKhau.getSelectionModel().selectedItemProperty().addListener((observableee, oldValueee, newValueee) -> {
			for (NhanKhauModel nhankhau : nhanKhauList) {
				if (nhankhau.getIDNhanKhau() == tfIDNhanKhau.getValue()) {
					tfHoTen.setText(nhankhau.getHoTen());
				}
			}
		});

		// add listener for tfNgayDen, tfSDT
		tfNgayDen.valueProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null) {
				XuLyLoiService.xuLyLoi(xuliloi, tfNgayDen, "Vui lòng chọn ngày chuyển đến!", 0, 40);
			}
			else if(newValue != null && tfNgayDen.getValue().isAfter(java.time.LocalDate.now())){
				XuLyLoiService.xuLyLoi(xuliloi, tfNgayDen, "Ngày chuyển đến không hợp lệ!", 0, 40);
			}
			else {
				XuLyLoiService.xoaLoi(xuliloi, tfNgayDen);
			}
		});

		tfSDT.textProperty().addListener((observableValue, oldValue, newValue) -> {
			Pattern pattern = Pattern.compile("\\d{1,15}");
			if (!pattern.matcher(newValue).matches()) {
				XuLyLoiService.xuLyLoi(xuliloi, tfSDT, "Hãy nhập vào SĐT hợp lệ!", 0, 40);
			} else {
				XuLyLoiService.xoaLoi(xuliloi, tfSDT);
			}
		});
	}
}
