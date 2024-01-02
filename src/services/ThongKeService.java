package services;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.KhoanThuModel;
import models.ThongKeModel;

public class ThongKeService {
    public static List<ThongKeModel> getListThongKe(String tinhTrang) throws ClassNotFoundException, SQLException {
        List<ThongKeModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query;
        if (tinhTrang.equals("Đã quá hạn")) {
            query = "SELECT * FROM khoanthu WHERE NgayKetThuc <= CURDATE()";
        } else if (tinhTrang.equals("Chưa đến hạn")) {
            query = "SELECT * FROM khoanthu WHERE NgayKetThuc > CURDATE()";
        } else {
            query = "SELECT * FROM khoanthu";
        }

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			KhoanThuModel khoanThuModel = new KhoanThuModel(
					rs.getInt("IDKhoanThu"),
					rs.getString("TenKT"),
					rs.getString("NgayBatDau"),
					rs.getString("NgayKetThuc"),
					rs.getDouble("TrongSoDienTich"),
					rs.getDouble("TrongSoSTV"),
					rs.getDouble("HangSo"),
					rs.getString("LoaiKhoanThu"));

            ThongKeModel thongKeModel = new ThongKeModel();
            thongKeModel.setIDKhoanThu(khoanThuModel.getIDKhoanThu());
            thongKeModel.setTenKhoanThu(khoanThuModel.getTenKT());
            thongKeModel.setLoai(khoanThuModel.getLoaiKhoanThu());
            thongKeModel.setTinhTrang(khoanThuModel.getNgayKetThuc());
            thongKeModel.setSoHoDaThu(ThuPhiService.getSoHoDaThu(khoanThuModel.getIDKhoanThu()));
            thongKeModel.setTongSoTien(ThuPhiService.getTongSoTien(khoanThuModel.getIDKhoanThu()));
            
			list.add(thongKeModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
    }

    public static List<ThongKeModel> timKiemTheoTenKhoanThu(String tinhTrang, String tenKhoanThu) throws ClassNotFoundException, SQLException {
        List<ThongKeModel> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query;
        if (tinhTrang.equals("Đã quá hạn")) {
            query = "SELECT * FROM khoanthu WHERE NgayKetThuc <= CURDATE() AND TenKT LIKE '%" + tenKhoanThu + "%'";
        } else if (tinhTrang.equals("Chưa đến hạn")) {
            query = "SELECT * FROM khoanthu WHERE NgayKetThuc > CURDATE() AND TenKT LIKE '%" + tenKhoanThu + "%'";
        } else {
            query = "SELECT * FROM khoanthu WHERE TenKT LIKE '%" + tenKhoanThu + "%'";
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            KhoanThuModel khoanThuModel = new KhoanThuModel(
                    rs.getInt("IDKhoanThu"),
                    rs.getString("TenKT"),
                    rs.getString("NgayBatDau"),
                    rs.getString("NgayKetThuc"),
                    rs.getDouble("TrongSoDienTich"),
                    rs.getDouble("TrongSoSTV"),
                    rs.getDouble("HangSo"),
                    rs.getString("LoaiKhoanThu"));

            ThongKeModel thongKeModel = new ThongKeModel();
            thongKeModel.setIDKhoanThu(khoanThuModel.getIDKhoanThu());
            thongKeModel.setTenKhoanThu(khoanThuModel.getTenKT());
            thongKeModel.setLoai(khoanThuModel.getLoaiKhoanThu());
            thongKeModel.setTinhTrang(khoanThuModel.getNgayKetThuc());
            thongKeModel.setSoHoDaThu(ThuPhiService.getSoHoDaThu(khoanThuModel.getIDKhoanThu()));
            thongKeModel.setTongSoTien(ThuPhiService.getTongSoTien(khoanThuModel.getIDKhoanThu()));
            
            list.add(thongKeModel);
        }
        preparedStatement.close();
        connection.close();
        return list;
    }


    public static List<ThongKeModel> timKiemTheoIDKhoanThu(String tinhTrang, int IDKhoanThu) throws ClassNotFoundException, SQLException {
        List<ThongKeModel> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query;
        if (tinhTrang.equals("Đã quá hạn")) {
            query = "SELECT * FROM khoanthu WHERE NgayKetThuc <= CURDATE() AND IDKhoanThu = " + IDKhoanThu;
        } else if (tinhTrang.equals("Chưa đến hạn")) {
            query = "SELECT * FROM khoanthu WHERE NgayKetThuc > CURDATE() AND IDKhoanThu = " + IDKhoanThu;
        } else {
            query = "SELECT * FROM khoanthu WHERE IDKhoanThu = " + IDKhoanThu + "";
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            KhoanThuModel khoanThuModel = new KhoanThuModel(
                    rs.getInt("IDKhoanThu"),
                    rs.getString("TenKT"),
                    rs.getString("NgayBatDau"),
                    rs.getString("NgayKetThuc"),
                    rs.getDouble("TrongSoDienTich"),
                    rs.getDouble("TrongSoSTV"),
                    rs.getDouble("HangSo"),
                    rs.getString("LoaiKhoanThu"));

            ThongKeModel thongKeModel = new ThongKeModel();
            thongKeModel.setIDKhoanThu(khoanThuModel.getIDKhoanThu());
            thongKeModel.setTenKhoanThu(khoanThuModel.getTenKT());
            thongKeModel.setLoai(khoanThuModel.getLoaiKhoanThu());
            
            // Convert the string date to a Date object
            Date ngayKetThuc = Date.valueOf(khoanThuModel.getNgayKetThuc());
            
            if (ngayKetThuc.compareTo(Date.valueOf(LocalDate.now())) > 0) {
                thongKeModel.setTinhTrang("Chưa đến hạn");
            } else {
                thongKeModel.setTinhTrang("Đã quá hạn");
            }
            
            thongKeModel.setSoHoDaThu(ThuPhiService.getSoHoDaThu(khoanThuModel.getIDKhoanThu()));
            thongKeModel.setTongSoTien(ThuPhiService.getTongSoTien(khoanThuModel.getIDKhoanThu()));
            
            list.add(thongKeModel);
        }
        preparedStatement.close();
        connection.close();
        return list;
    }
}