package services;

import models.TamVangModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TamVangService {

    public static TamVangModel checkCurrent(int idNhanKhau) throws SQLException, ClassNotFoundException {
        TamVangModel tamvangModel = new TamVangModel(0);
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM tamvang WHERE idNhanKhau = ? AND ? BETWEEN ngayBatDau AND ngayKetThuc";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idNhanKhau);
        preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            tamvangModel.setIDTamVang(resultSet.getInt("IDTamVang"));
            tamvangModel.setIDNhanKhau(resultSet.getInt("IDNhanKhau"));
            tamvangModel.setNgayBatDau(resultSet.getDate("NgayBatDau").toLocalDate());
            tamvangModel.setNgayKetThuc(resultSet.getDate("NgayKetThuc").toLocalDate());
            tamvangModel.setLyDo(resultSet.getString("LyDo"));
        }

        return tamvangModel;
    }

    public static boolean addTamVang(TamVangModel tamVangModel) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO tamvang(IDNhanKhau, NgayBatDau, NgayKetThuc, LyDo) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, tamVangModel.getIDNhanKhau());
        preparedStatement.setDate(2, Date.valueOf(tamVangModel.getNgayBatDau()));
        preparedStatement.setDate(3, Date.valueOf(tamVangModel.getNgayBatDau()));
        preparedStatement.setString(4, tamVangModel.getLyDo());

        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows > 0;
    }

    public static List<TamVangModel> getListTamVang() throws SQLException, ClassNotFoundException {
        List<TamVangModel> tamVangList = new ArrayList<>();
        Connection connection = MysqlConnection.getMysqlConnection();

        // Lựa chọn ra những nhân khẩu còn ở chung cư
        String query = "SELECT * FROM tamvang WHERE IDNhanKhau IN (SELECT IDNhanKhau FROM nhankhau WHERE IDHoKhau IN (SELECT IDHoKhau FROM hokhau WHERE NgayDi = '0001-01-01'))";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            TamVangModel tamVangModel = new TamVangModel();
            tamVangModel.setIDTamVang(resultSet.getInt("IDTamVang"));
            tamVangModel.setIDNhanKhau(resultSet.getInt("IDNhanKhau"));
            tamVangModel.setHoTen(NhanKhauService_Lam.getHoTen(tamVangModel.getIDNhanKhau()));
            tamVangModel.setNgayBatDau(resultSet.getDate("NgayBatDau").toLocalDate());
            tamVangModel.setNgayKetThuc(resultSet.getDate("NgayKetThuc").toLocalDate());
            tamVangModel.setLyDo(resultSet.getString("LyDo"));
            tamVangList.add(tamVangModel);
        }

        return tamVangList;
    }

    public static void del(int idTamVang) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "DELETE FROM tamvang WHERE IDTamVang = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idTamVang);
        preparedStatement.executeUpdate();
    }

    public static void delbyIDNhanKhau(int idnhankhau) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "DELETE FROM tamvang WHERE IDNhanKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idnhankhau);
        preparedStatement.executeUpdate();
    }

    public static boolean updateTamVang(TamVangModel tamVangModel) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE tamvang SET NgayBatDau = ?, NgayKetThuc = ?, LyDo = ? WHERE IDTamVang = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, Date.valueOf(tamVangModel.getNgayBatDau()));
        preparedStatement.setDate(2, Date.valueOf(tamVangModel.getNgayKetThuc()));
        preparedStatement.setString(3, tamVangModel.getLyDo());
        preparedStatement.setInt(4, tamVangModel.getIDTamVang());

        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }

    public static String countTamVang() throws ClassNotFoundException, SQLException {
        LocalDate current = LocalDate.now();
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT Count(IDTamVang) FROM tamvang WHERE NgayBatDau <= ? AND NgayKetThuc >= ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(current));
        preparedStatement.setString(2, String.valueOf(current));
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return String.valueOf(count);
        }
        return "0";
    }
}
