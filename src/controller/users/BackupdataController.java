package controller.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static services.UserService.xuatfileexcel;


public class BackupdataController implements Initializable {

    @FXML
    private CheckBox chontatca;

    @FXML
    private CheckBox hokhau;

    @FXML
    private CheckBox khoanthu;

    @FXML
    private CheckBox nhankhau;

    @FXML
    private CheckBox phong;

    @FXML
    private CheckBox thuphi;

    @FXML
    private CheckBox xe;

    @FXML
    private TextField nhankhautf;

    @FXML
    private TextField hokhautf;

    @FXML
    void chontatca_clicked(ActionEvent event) {
        // nếu nút chọn tất cả được click thì tất cả các nút khác được clicked
        boolean isSelected = chontatca.isSelected();

        hokhau.setSelected(isSelected);
        khoanthu.setSelected(isSelected);
        nhankhau.setSelected(isSelected);
        phong.setSelected(isSelected);
        thuphi.setSelected(isSelected);
        xe.setSelected(isSelected);
    }

    @FXML
    void hokhau_clicked(ActionEvent event) {
        // nếu nút còn lại click false thì nút chọn tất cả false
        if (!hokhau.isSelected()) {
            chontatca.setSelected(false);
        }
    }

    @FXML
    void nhankhau_clicked(ActionEvent event) {
        // nếu nút còn lại click false thì nút chọn tất cả false
        if (!nhankhau.isSelected()) {
            chontatca.setSelected(false);
        }
    }

    @FXML
    void hokhau_pick_address(ActionEvent event) {
        // nếu click vào chọn địa chỉ
        openFileChooser(hokhautf.getText());
    }

    @FXML
    void nhankhau_pick_address(ActionEvent event) {
        // nếu click vào chọn địa chỉ
        openFileChooser(nhankhautf.getText());
    }

    public void confirmClicked(ActionEvent event) throws SQLException, ClassNotFoundException {
        // xuất file tương ứng với các nút đã được check
        if (hokhau.isSelected()) xuatfileexcel("hokhau", hokhautf.getText());
        if (nhankhau.isSelected()) xuatfileexcel("nhankhau", nhankhautf.getText());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đã xuất file thành công!!!", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    private String openFileChooser(String filepath) {
        // Lấy parendiretorey của file path này
        File currentFile = new File(filepath);
        File parentDirectory = currentFile.getParentFile();

        // tạo một FileChooser
        FileChooser fileChooser = new FileChooser();

        //Đặt thư mục khởi tạo là thư mục cha
        if (parentDirectory != null && parentDirectory.exists()) {
            fileChooser.setInitialDirectory(parentDirectory);
            fileChooser.setInitialFileName(currentFile.getName());
            System.out.println("file name la:" + currentFile.getName());
        }

        // tạo FileChooser dialog
        Stage stage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(stage);

        // Check if a file was selected
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            // User canceled the operation
            return null;
        }
    }


    void setDefaultFileForTextField(String filename, TextField tf) {
        // set text cho tf tương ứng
        if (filename.equals("")) {
            tf.setText(null);
            return;
        }

        // tạo một file rỗng để lấy path của nó tại vị trí hiện tại
        File file = new File("");
        tf.setText(file.getAbsolutePath() + "\\backup\\" + filename);
    }
    void addListener_auto_get_path(CheckBox checkBox, String fileName, TextField textField) {
        // thêm listener cho các checkbox và tf tương ứng của nó, mỗi khí checkbox được tích thì sẽ hiện chỗ lưu ở trong tf
        // Add a ChangeListener to the CheckBox
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                setDefaultFileForTextField(fileName, textField);
            } else {
                setDefaultFileForTextField("", textField);
            }
        });
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        addListener_auto_get_path(nhankhau, "nhankhau.xlsx", nhankhautf);
        addListener_auto_get_path(hokhau, "hokhau.xlsx", hokhautf);
    }
}
