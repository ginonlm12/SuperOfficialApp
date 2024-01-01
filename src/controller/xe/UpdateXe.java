package controller.xe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.XeModel;
import services.XeService;

import java.sql.SQLException;

public class UpdateXe {

    @FXML
    private Label lbSoxedap;

    @FXML
    private Label lbSoxemay;

    @FXML
    private Label lbsooto;

    @FXML
    private Label title;

    @FXML
    private Label Tenchuho;

    private XeModel xeModel;


    public void setXeModel_Tuan(XeModel xe) {
        xeModel = xe;
        title.setText("Cập nhật thông tin xe của hộ khẩu #" + xeModel.getIDHoKhau());
        Tenchuho.setText("Chủ hộ: " + xeModel.getChuHo());
        lbsooto.setText(String.valueOf(xe.getOTo()));
        lbSoxedap.setText(String.valueOf(xe.getXeDap()));
        lbSoxemay.setText(String.valueOf(xe.getXeMay()));

    }
    @FXML
    void XacNhan(ActionEvent event) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn có chắc chắn muốn cập nhật không?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            xeModel.setOTo(Integer.parseInt(lbsooto.getText()));
            xeModel.setXeDap(Integer.parseInt(lbSoxedap.getText()));
            xeModel.setXeMay(Integer.parseInt(lbSoxemay.getText()));
            XeService.updateXe(xeModel);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void plus_oto(MouseEvent event) {
        plus(lbsooto);
    }

    @FXML
    void plus_xe_dap(MouseEvent event) {
        plus(lbSoxedap);
    }

    @FXML
    void plus_xe_may(MouseEvent event) {
        plus(lbSoxemay);
    }

    @FXML
    void sub_oto(MouseEvent event) {
        sub(lbsooto);
    }

    @FXML
    void sub_xe_dap(MouseEvent event) {
        sub(lbSoxedap);
    }

    @FXML
    void sub_xe_may(MouseEvent event) {
        sub(lbSoxemay);
    }

    public void plus(Label tf) {
    	int value = Integer.parseInt(tf.getText());
    	value++;
    	tf.setText(String.valueOf(value));
    }

    public void sub(Label tf) {
    	int value = Integer.parseInt(tf.getText());
    	if(value > 0) {
    		value--;
    		tf.setText(String.valueOf(value));
    	}
    }
}
