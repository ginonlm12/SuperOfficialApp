package controller.hokhau;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.HoKhauBean_Tuan;
import models.NhanKhauModel_Lam;

import java.io.IOException;
import java.util.List;


public class DetailHoKhau_Tuan {
    @FXML
    private Label LbTitle;

    @FXML
    private TextField tfIDChuHo;

    @FXML
    private TextField tfIDHoKhau;

    @FXML
    private TextField tfNgayDen;

    @FXML
    private TextField tfNgayDi;

    @FXML
    private TextField tfSDTLienHe;

    @FXML
    private TextField tfSoPhong;

    @FXML
    private TextField tfSoTV;

    @FXML
    private TextField tfTenChuHo;

    private final Label lbXemChiTiet = new Label();

    private HoKhauBean_Tuan hoKhauBean;

    private List<NhanKhauModel_Lam> ListThanhVien;


    @FXML
    public void ShowThanhVien(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/hokhau/ShowThanhVien_Tuan.fxml"));
        Parent home = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(home, 600, 400));
        ShowThanhVien_Tuan showThanhVienTuan = loader.getController();

        showThanhVienTuan.setLbTitle("Danh sách thành viên hộ khẩu #" + hoKhauBean.getHoKhauModel_tuan().getIDHoKhau());
        showThanhVienTuan.setTable(ListThanhVien);
        stage.setResizable(true);
        stage.showAndWait();
    }


    public void setHoKhauModel(HoKhauBean_Tuan hoKhauBean) {
        // pass thong tin ho khau bean tu HoKhauControler vao day
        this.hoKhauBean = hoKhauBean;
        this.ListThanhVien = hoKhauBean.getListNhanKhau();

        //Set thong tin hien thi
        LbTitle.setText("Thông tin hộ khẩu #" + hoKhauBean.getHoKhauModel_tuan().getIDHoKhau());
        tfIDHoKhau.setText(String.valueOf(hoKhauBean.getHoKhauModel_tuan().getIDHoKhau()));
        tfSoPhong.setText(String.valueOf(hoKhauBean.getHoKhauModel_tuan().getSoPhong()));
        tfNgayDen.setText(String.valueOf(hoKhauBean.getHoKhauModel_tuan().getNgayDen()));
        if (hoKhauBean.getHoKhauModel_tuan().getNgayDi().equals("0001-01-01")) {
            tfNgayDi.setText("Chưa chuyển đi");
        } else tfNgayDi.setText(String.valueOf(hoKhauBean.getHoKhauModel_tuan().getNgayDi()));
        tfSDTLienHe.setText(String.valueOf(hoKhauBean.getHoKhauModel_tuan().getSDT()));
        tfSoTV.setText(String.valueOf(hoKhauBean.getSoTV()));

        for (NhanKhauModel_Lam nhanKhauModel_lam : ListThanhVien) {
            if (nhanKhauModel_lam.getQHvsChuHo().equals("Chủ hộ")) {
                tfIDChuHo.setText(String.valueOf(nhanKhauModel_lam.getIDNhanKhau()));
                tfTenChuHo.setText(String.valueOf(nhanKhauModel_lam.getHoTen()));
            }
        }

//        // click xem chi tiet
//        lbXemChiTiet.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
//            try {
//                System.out.println("lammmmmm");
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/view/hokhau/ShowThanhVien_Tuan.fxml"));
//                Parent home = loader.load();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(home, 700, 600));
//                ShowThanhVien_Tuan showThanhVienTuan = loader.getController();
//
//                showThanhVienTuan.setTable(ListThanhVien);
//                stage.setResizable(true);
//                stage.showAndWait();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });
    }
}
