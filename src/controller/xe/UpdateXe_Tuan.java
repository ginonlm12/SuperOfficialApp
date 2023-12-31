package controller.xe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.XeModel_Tuan;
import services.XeService_Tuan;

import java.sql.SQLException;

public class UpdateXe_Tuan {

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

    private XeModel_Tuan xeModel_Tuan;



    public void setXeModel_Tuan (XeModel_Tuan xe) {
    	xeModel_Tuan = xe;
        title.setText("Cập nhật thông tin xe của hộ khẩu #" + xeModel_Tuan.getIDHoKhau());
        Tenchuho.setText("Chủ hộ: "+xeModel_Tuan.getChuHo());
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
            xeModel_Tuan.setOTo(Integer.parseInt(lbsooto.getText()));
            xeModel_Tuan.setXeDap(Integer.parseInt(lbSoxedap.getText()));
            xeModel_Tuan.setXeMay(Integer.parseInt(lbSoxemay.getText()));
            XeService_Tuan.updateXe(xeModel_Tuan);
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
