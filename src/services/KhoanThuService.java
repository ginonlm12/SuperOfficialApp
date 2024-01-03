package services;

import models.KhoanThuModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoanThuService {
	public static boolean add(KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO khoanthu(TenKT, NgayBatDau, NgayKetThuc, TrongSoDienTich, TrongSoSTV, HangSo, LoaiKhoanThu)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, khoanThuModel.getTenKT());
		preparedStatement.setString(2, khoanThuModel.getNgayBatDau());
		preparedStatement.setString(3, khoanThuModel.getNgayKetThuc());
		preparedStatement.setDouble(4, khoanThuModel.getTrongSoDienTich());
		preparedStatement.setDouble(5, khoanThuModel.getTrongSoSTV());
		preparedStatement.setDouble(6, khoanThuModel.getHangSo());
		preparedStatement.setString(7, khoanThuModel.getLoaiKhoanThu());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public static boolean del(int IDKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "DELETE FROM khoanthu WHERE IDKhoanThu = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setInt(1, IDKhoanThu);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public static boolean update(int IDKhoanThu_old, KhoanThuModel khoanThuModel)
			throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "UPDATE khoanthu SET TenKT = ?, NgayBatDau = ?, NgayKetThuc = ?, TrongSoDienTich = ?, TrongSoSTV = ?, HangSo = ?, LoaiKhoanThu = ?, IDKhoanThu = ? WHERE IDKhoanThu = ?";
		PreparedStatement preparedStatement;

		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, khoanThuModel.getTenKT());
		preparedStatement.setString(2, khoanThuModel.getNgayBatDau());
		preparedStatement.setString(3, khoanThuModel.getNgayKetThuc());
		preparedStatement.setDouble(4, khoanThuModel.getTrongSoDienTich());
		preparedStatement.setDouble(5, khoanThuModel.getTrongSoSTV());
		preparedStatement.setDouble(6, khoanThuModel.getHangSo());
		preparedStatement.setString(7, khoanThuModel.getLoaiKhoanThu());
		preparedStatement.setInt(8, khoanThuModel.getIDKhoanThu());
		preparedStatement.setInt(9, IDKhoanThu_old);
		preparedStatement.executeUpdate();
		connection.close();
		return true;
	}

	public static List<KhoanThuModel> getListKhoanThu() throws ClassNotFoundException, SQLException {
		List<KhoanThuModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM khoanthu";
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

			list.add(khoanThuModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}

	public static KhoanThuModel getKhoanThu(int IDKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM khoanthu WHERE IDKhoanThu = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, IDKhoanThu);

		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		KhoanThuModel khoanThuModel = new KhoanThuModel(
				rs.getInt("IDKhoanThu"),
				rs.getString("TenKT"),
				rs.getString("NgayBatDau"),
				rs.getString("NgayKetThuc"),
				rs.getDouble("TrongSoDienTich"),
				rs.getDouble("TrongSoSTV"),
				rs.getDouble("HangSo"),
				rs.getString("LoaiKhoanThu"));

		connection.close();
		return khoanThuModel;
	}

    public static List<KhoanThuModel> getListKhoanThuChuHo(int idHoKhau) throws ClassNotFoundException, SQLException {
        List<KhoanThuModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM khoanthu WHERE IDKhoanThu NOT IN (SELECT IDKhoanThu FROM thuphi WHERE IDHoKhau = ? AND TienDaDong >= SoTienPhaiDong)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, idHoKhau);
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

			list.add(khoanThuModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
    }
}
