package services;

import models.KhoanThuModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class KhoanThuService {
	public boolean add(KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO khoanthu(IDKhoanThu, TenKT, NgayBatDau, NgayKetThuc, TrongSoDienTich, TrongSoSTV, hangSo)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, khoanThuModel.getIDKhoanThu());
		preparedStatement.setString(2, khoanThuModel.getTenKT());
		preparedStatement.setString(3, khoanThuModel.getNgayBatDau());
		preparedStatement.setString(4, khoanThuModel.getNgayKetThuc());
		preparedStatement.setDouble(5, khoanThuModel.getTrongSoDienTich());
		preparedStatement.setDouble(6, khoanThuModel.getTrongSoSTV());
		preparedStatement.setDouble(7, khoanThuModel.getHangSo());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean del(int IDKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		// String query = "SELECT * FROM thutien WHERE IDKhoanThu='"+IDKhoanThu+"';";
	    // PreparedStatement preparedStatement = connection.prepareStatement(query);
	    // ResultSet rs = preparedStatement.executeQuery();
	    // while (rs.next()){
	    // 	query="DELETE FROM thuphi WHERE IDKhoanThu='"+IDKhoanThu+"'";
	    // 	preparedStatement = connection.prepareStatement(query);
	    // 	preparedStatement.executeUpdate();
	    // }
	    String query ="DELETE FROM khoanthu WHERE IDKhoanThu = '"+IDKhoanThu+"'";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// modify the update method to match the khoanthu table
	public boolean update(int IDKhoanThu_old, KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE khoanthu SET TenKT = ?, NgayBatDau = ?, NgayKetThuc = ?, TrongSoDienTich = ?, TrongSoSTV = ?, HangSo = ?, IDKhoanThu = ? WHERE IDKhoanThu = ?";
		preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, khoanThuModel.getTenKT());
		preparedStatement.setString(2, khoanThuModel.getNgayBatDau());
		preparedStatement.setString(3, khoanThuModel.getNgayKetThuc());
		preparedStatement.setDouble(4, khoanThuModel.getTrongSoDienTich());
		preparedStatement.setDouble(5, khoanThuModel.getTrongSoSTV());
		preparedStatement.setDouble(6, khoanThuModel.getHangSo());
		preparedStatement.setInt(7, khoanThuModel.getIDKhoanThu());
		preparedStatement.setInt(8, IDKhoanThu_old);
		preparedStatement.executeUpdate();

		// String query2 = "UPDATE thuphi SET IDKhoanThu = ? WHERE IDKhoanThu = ?";
		// preparedStatement = connection.prepareStatement(query2);
		// preparedStatement.setInt(1, khoanThuModel.getIDKhoanThu());
		// preparedStatement.setInt(2, IDKhoanThu_old);
		// preparedStatement.executeUpdate();
		// preparedStatement.close();
		// connection.close();
		return true;
	}

	public List<KhoanThuModel> getListKhoanThu() throws ClassNotFoundException, SQLException {
		List<KhoanThuModel> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
	    String query = "SELECT * FROM khoanthu";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    ResultSet rs = preparedStatement.executeQuery();
	    while (rs.next()){
	        // KhoanThuModel khoanThuModel = new KhoanThuModel(rs.getInt("MaKhoanThu"),rs.getString("TenKhoanThu"),
	        // 		rs.getDouble("SoTien"),rs.getInt("LoaiKhoanThu"));
			// create a new KhoanThuModel object with attributes from the database
			KhoanThuModel khoanThuModel = new KhoanThuModel(
				rs.getInt("IDKhoanThu"), 
				rs.getString("TenKT"),	
				rs.getString("NgayBatDau"),	
				rs.getString("NgayKetThuc"),
				rs.getDouble("TrongSoDienTich"),
				rs.getDouble("TrongSoSTV"),
				rs.getDouble("HangSo")
			);

	        list.add(khoanThuModel);
	   }
	    preparedStatement.close();
	    connection.close();
		return list;
	}
}
