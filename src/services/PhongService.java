package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.PhongModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhongService {
    public static boolean add(PhongModel phongModel) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO phong(SoPhong, DienTich, LoaiPhong) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, phongModel.getSoPhong());
        preparedStatement.setDouble(2, phongModel.getDienTich());
        preparedStatement.setString(3, phongModel.getLoaiPhong());

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }

    public static void del(int soPhong) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "DELETE FROM phong WHERE SoPhong = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, soPhong);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static ObservableList<Integer> getListSoPhong(String TrangThai) throws ClassNotFoundException, SQLException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        String query;
        Connection connection = MysqlConnection.getMysqlConnection();
        if (TrangThai.equals("Duoc su dung")) {
            query = "SELECT SoPhong FROM phong WHERE SoPhong NOT IN (SELECT SoPhong FROM hokhau WHERE NgayDi = '0001/01/01') ";
        } else {
            query = "SELECT SoPhong FROM phong";
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int sophong = rs.getInt("SoPhong");
            list.add(sophong);
        }
        return list;
    }

    public static void updatePhongModel(PhongModel phongModel) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        PreparedStatement preparedStatement;

        String query = "UPDATE phong SET DienTich = ?, LoaiPhong = ? WHERE SoPhong = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, phongModel.getDienTich());
        preparedStatement.setString(2, phongModel.getLoaiPhong());
        preparedStatement.setInt(3, phongModel.getSoPhong());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static String countSoPhong() throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        int total = 0, used = 0;

        String query = "SELECT COUNT(*) FROM phong";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }

        String query1 = "SELECT COUNT(DISTINCT sophong) FROM hokhau WHERE NgayDi = '0001-01-01'";
        preparedStatement = connection.prepareStatement(query1);
        rs = preparedStatement.executeQuery();
        if (rs.next()) {
            used = rs.getInt(1);
        }
        return used + "/" + total;
    }

    public List<PhongModel> getListPhong() throws ClassNotFoundException, SQLException {
        List<PhongModel> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM phong";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            PhongModel phongModel = new PhongModel();
            phongModel.setSoPhong(rs.getInt("SoPhong"));
            phongModel.setDienTich(rs.getDouble("DienTich"));
            phongModel.setLoaiPhong(rs.getString("LoaiPhong"));
            phongModel.setTrangThai(HoKhauService_Tuan.checkSuDung(phongModel.getSoPhong()));
            list.add(phongModel);
        }
        preparedStatement.close();
        connection.close();
        return list;
    }

    public static PhongModel getPhongModel(int soPhong) throws ClassNotFoundException, SQLException {
        PhongModel phongModel = new PhongModel();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM phong WHERE SoPhong = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, soPhong);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            phongModel.setSoPhong(rs.getInt("SoPhong"));
            phongModel.setDienTich(rs.getDouble("DienTich"));
            phongModel.setLoaiPhong(rs.getString("LoaiPhong"));
            phongModel.setTrangThai(HoKhauService_Tuan.checkSuDung(phongModel.getSoPhong()));
        }
        preparedStatement.close();
        connection.close();
        return phongModel;
    }
}
