package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.KhoanThuModel;

public class KhoanThuService {
	public boolean add(KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO khoanthu(IDKhoanThu, tenKT, ngayBatDau, ngayKetThuc, trongSoDienTich, trongSoSTV, HangSo)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, khoanThuModel.getIDKhoanThu());
		preparedStatement.setString(2, khoanThuModel.gettenKT());
		preparedStatement.setString(3, khoanThuModel.getngayBatDau());
		preparedStatement.setString(4, khoanThuModel.getngayKetThuc());
		preparedStatement.setDouble(5, khoanThuModel.gettrongSoDienTich());
		preparedStatement.setDouble(6, khoanThuModel.gettrongSoSTV());
		preparedStatement.setDouble(7, khoanThuModel.getHangSo());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean del(int IDKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();

		// Delete matching records from thuphi table directly using SQL DELETE statement
		String query = "DELETE FROM thuphi WHERE IDKhoanThu=?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, IDKhoanThu); // Set the parameter
		preparedStatement.executeUpdate(); // Execute the delete statement for thuphi table

		// Delete matching records from khoan_thu table
		query = "DELETE FROM khoanthu WHERE IDKhoanThu = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, IDKhoanThu); // Set the parameter
		preparedStatement.executeUpdate(); // Execute the delete statement for khoan_thu table

		preparedStatement.close();
		connection.close();
		return true;
	}

	// modify the update method to match the khoanthu table
	public boolean update(int IDKhoanThu, String tenKT, String ngayBatDau, String ngayKetThuc, double trongSoDienTich,
			double trongSoSTV, double HangSo) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE khoanthu SET tenKT = ?, ngayBatDau = ?, ngayKetThuc = ?, trongSoDienTich = ?, trongSoSTV = ?, HangSo = ? WHERE IDKhoanThu = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, tenKT);
		preparedStatement.setString(2, ngayBatDau);
		preparedStatement.setString(3, ngayKetThuc);
		preparedStatement.setDouble(4, trongSoDienTich);
		preparedStatement.setDouble(5, trongSoSTV);
		preparedStatement.setDouble(6, HangSo);
		preparedStatement.setInt(7, IDKhoanThu);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();

		return true;
	}

	public List<KhoanThuModel> getListKhoanThu() throws ClassNotFoundException, SQLException {
		List<KhoanThuModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM khoanthu";
		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			KhoanThuModel khoanThuModel = new KhoanThuModel(rs.getInt("IDKhoanThu"), rs.getString("tenKT"),
					rs.getString("ngayBatDau"), rs.getString("ngayKetThuc"), rs.getDouble("trongSoDienTich"),
					rs.getDouble("trongSoSTV"), rs.getDouble("HangSo"));
			list.add(khoanThuModel);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}
}
