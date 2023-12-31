package controller.thuphi;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class UpdateThuPhi {
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

	private ThuPhiBean thuPhiBean;

    public void setThuPhiBean(ThuPhiBean thuPhiBean) {
        this.thuPhiBean = thuPhiBean;
        tfTenKhoanThu.setText(thuPhiBean.getTenKhoanThu());
        tfTenChuHo.setText(thuPhiBean.getTenChuHo());
        tfSoTienPhaiDong.setText(Double.toString(thuPhiBean.getSoTienPhaiDong()));
        tfSoTienDong.setText(Double.toString(thuPhiBean.getSoTienDong()));
        String NgayDong = thuPhiBean.getNgayDong();
		if (NgayDong != null && !NgayDong.isEmpty()) {
			LocalDate DOB = LocalDate.parse(NgayDong, DateTimeFormatter.ISO_DATE);
			dpNgayDong.setValue(DOB);
		} else {
		    dpNgayDong.setValue(LocalDate.parse("01/01/1900", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
    }

	public void updateThuPhi(ActionEvent event) throws ClassNotFoundException, SQLException {
		if (tfTenKhoanThu.getText().length() == 0 || tfTenChuHo.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Vui lòng nhập khoản nộp hợp lí!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			ThuPhiModel thuPhiModel =thuPhiBean.getThuPhiModel();
            ThuPhiService thuPhiService = new ThuPhiService();
            thuPhiService.update(thuPhiModel);
            
            Alert alert = new Alert(AlertType.INFORMATION, "Cập nhật thành công!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Cập nhật thu phí");
            stage.setResizable(false);
            stage.close();
		}
	}
}
