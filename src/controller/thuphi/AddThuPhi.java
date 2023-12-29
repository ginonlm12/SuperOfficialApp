package controller.thuphi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.NhanKhauModel_Lam;
import models.ThuPhiBean;
import models.ThuPhiModel;
import services.ThuPhiService;

public class AddThuPhi {
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

	private KhoanThuModel khoanThuModel;
	private NhanKhauModel_Lam nhanKhauModel;

	public void chooseKhoanThu() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/ThuPhi/ChooseKhoanThu.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(home, 800, 600));
		stage.setResizable(false);
		stage.showAndWait();

		ChooseKhoanThu chooseKhoanNop = loader.getController();
		khoanThuModel = chooseKhoanNop.getKhoanThuChoose();
		if (khoanThuModel == null)
			return;

		tfTenKhoanThu.setText(khoanThuModel.getTenKT());
	}

	public void chooseChuHo() throws IOException {
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
	}

	public void addThuPhi(ActionEvent event) throws ClassNotFoundException, SQLException {
		if (tfTenKhoanThu.getText().length() == 0 || tfTenChuHo.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng nhập khoản nộp hợp lí!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			List<ThuPhiBean> listThuPhi = new ThuPhiService().getListThuPhi();
			for (ThuPhiBean thuPhiBean : listThuPhi) {
				if (thuPhiBean.getThuPhiModel().getIDHoKhau() == nhanKhauModel.getIDHoKhau()
						&& thuPhiBean.getThuPhiModel().getIDKhoanThu() == khoanThuModel.getIDKhoanThu()) {
					Alert alert = new Alert(AlertType.WARNING, "Người này đã từng nộp khoản phí này!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}
			}

			new ThuPhiService().add(new ThuPhiModel(
					khoanThuModel.getIDKhoanThu(),
					nhanKhauModel.getIDHoKhau(),
					Double.parseDouble(tfSoTienDong.getText()),
					dpNgayDong.getValue().toString()));
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Thêm thu phí");
		stage.setResizable(false);
		stage.close();
	}
}
