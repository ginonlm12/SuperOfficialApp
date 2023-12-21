package controller.users;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static application.Main.user;
import static services.MysqlConnection.getMysqlConnection;

public class changepassController {

    @FXML
    private PasswordField newpaass1;

    @FXML
    private PasswordField newpass2;

    @FXML
    private PasswordField oldpass;

    @FXML
    void comfirmClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        String oldpassText = oldpass.getText();
        String newpassText1 = newpaass1.getText();
        String newpassText2 = newpass2.getText();

        // Nếu nhập sai mật khẩu cũ hiện thông báo mật khẩu cũ không chính xác
        if (oldpassText.equals(user.getPasswd()) == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Mật khẩu cũ không chính xác!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        // Nếu pass1 bị rỗng
        if (newpassText1.isEmpty() || newpassText2.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Mật khẩu mới không được rỗng!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Nếu pass2 khác pass1 hiện thông báo pass2 khác pass1
        if (newpassText1.equals(newpassText2) == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Mật khẩu mới lần 2 không giống lần 1!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Nếu thành công hiện thông báo đổi mật khẩu thành công
        Connection conn = getMysqlConnection();
        if (conn!= null) System.out.println("Thanh cong roi");

        // SQL update để cập nhật mật khẩu
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, newpassText1);
        preparedStatement.setString(2, user.getUsername());

        // Execute the update
        preparedStatement.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đổi mật khẩu thành công!!!", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();

    }

}
