package controller.xe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.XeModel_Tuan;
import services.XeService_Tuan;

import java.sql.SQLException;

public class UpdateXe_Tuan {

    @FXML
    private Label lbTitle;

    @FXML
    private TextField tfSoOto;

    @FXML
    private TextField tfSoXeDap;

    @FXML
    private TextField tfSoXeMay;

    private XeModel_Tuan xeModel_Tuan;

    public void setXeModel_Tuan (XeModel_Tuan xe) {
    	xeModel_Tuan = xe;
        lbTitle.setText("Cập nhật thông tin xe của hộ khẩu #" + xeModel_Tuan.getIDHoKhau());
        tfSoOto.setText(String.valueOf(xe.getOTo()));
        tfSoXeDap.setText(String.valueOf(xe.getXeDap()));
        tfSoXeMay.setText(String.valueOf(xe.getXeMay()));
    }
    @FXML
    void XacNhan(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn có chắc chắn muốn cập nhật không?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            xeModel_Tuan.setOTo(Integer.parseInt(tfSoOto.getText()));
            xeModel_Tuan.setXeDap(Integer.parseInt(tfSoXeDap.getText()));
            xeModel_Tuan.setXeMay(Integer.parseInt(tfSoXeMay.getText()));
            XeService_Tuan.updateXe(xeModel_Tuan);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void plus_oto(ActionEvent event) {
        plus(tfSoOto);
    }

    @FXML
    void plus_xe_dap(ActionEvent event) {
        plus(tfSoXeDap);
    }

    @FXML
    void plus_xe_may(ActionEvent event) {
        plus(tfSoXeMay);
    }

    @FXML
    void sub_oto(ActionEvent event) {
        sub(tfSoOto);
    }

    @FXML
    void sub_xe_dap(ActionEvent event) {
        sub(tfSoXeDap);
    }

    @FXML
    void sub_xe_may(ActionEvent event) {
        sub(tfSoXeMay);
    }

    public void plus(TextField tf) {
    	int value = Integer.parseInt(tf.getText());
    	value++;
    	tf.setText(String.valueOf(value));
    }

    public void sub(TextField tf) {
    	int value = Integer.parseInt(tf.getText());
    	if(value > 0) {
    		value--;
    		tf.setText(String.valueOf(value));
    	}
    }
}
