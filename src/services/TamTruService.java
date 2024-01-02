package services;

import models.TamTruModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TamTruService {
    public static List<TamTruModel> getListTamTru() throws SQLException, ClassNotFoundException {
        List<TamTruModel> tamTruList = new ArrayList<>();
        Connection connection = MysqlConnection.getMysqlConnection();

        String query = "SELECT * FROM tamtru";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            TamTruModel tamTruModel = new TamTruModel();
            tamTruModel.setIDHoKhau(resultSet.getInt("IDHoKhau"));
            tamTruModel.setIDTamTru(resultSet.getInt("IDTamTru"));
            tamTruModel.setHoTen(resultSet.getString("HoTen"));
            tamTruModel.setNgayBatDau(resultSet.getDate("NgayBatDau").toLocalDate());
            tamTruModel.setNgayKetThuc(resultSet.getDate("NgayKetThuc").toLocalDate());
            tamTruModel.setNgaySinh(resultSet.getDate("NgaySinh").toLocalDate());
            tamTruModel.setLyDo(resultSet.getString("LyDo"));
            tamTruModel.setCCCD(resultSet.getString("CCCD"));
            tamTruModel.setGioiTinh(resultSet.getString("GioiTinh"));
            tamTruModel.setThuongTru(resultSet.getString("ThuongTru"));

            tamTruList.add(tamTruModel);
        }

        return tamTruList;
    }

    public static void del(int idTamTru) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "DELETE FROM tamtru WHERE IDTamTru = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idTamTru);
        preparedStatement.executeUpdate();
    }

    public static boolean add(TamTruModel tamTruModel) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO tamtru (IDHoKhau, HoTen, ThuongTru, NgayBatDau, NgayKetThuc, " +
                "LyDo, NgaySinh, CCCD, GioiTinh) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, tamTruModel.getIDHoKhau());
        preparedStatement.setString(2, tamTruModel.getHoTen());
        preparedStatement.setString(3, tamTruModel.getThuongTru());
        preparedStatement.setDate(4, Date.valueOf(tamTruModel.getNgayBatDau()));
        preparedStatement.setDate(5, Date.valueOf(tamTruModel.getNgayKetThuc()));
        preparedStatement.setString(6, tamTruModel.getLyDo());
        preparedStatement.setDate(7, Date.valueOf(tamTruModel.getNgaySinh()));
        preparedStatement.setString(8, tamTruModel.getCCCD());
        preparedStatement.setString(9, tamTruModel.getGioiTinh());

        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows > 0;
    }

    public static boolean updateNgayKetThuc(int IDTamTru, LocalDate ngayKetThuc) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE tamtru SET NgayKetThuc = ? WHERE IDTamTru = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDate(1, Date.valueOf(ngayKetThuc));
        preparedStatement.setInt(2, IDTamTru);

        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows > 0;
    }

    public static String countTamTru() throws ClassNotFoundException, SQLException {
        LocalDate current = LocalDate.now();
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT Count(IDTamTru) FROM tamtru WHERE NgayBatDau <= ? AND NgayKetThuc >= ?";

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
