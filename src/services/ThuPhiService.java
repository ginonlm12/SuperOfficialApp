package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.ThuPhiBean;
import models.ThuPhiModel;

public class ThuPhiService {
	public static boolean add(ThuPhiModel ThuPhiModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO thuphi(IDKhoanThu, IDHoKhau, SoTienPhaiDong, TienDaDong, NgayDong)"
				+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, ThuPhiModel.getIDKhoanThu());
		preparedStatement.setInt(2, ThuPhiModel.getIDHoKhau());
		preparedStatement.setDouble(3, ThuPhiModel.getSoTienPhaiDong());
		preparedStatement.setDouble(4, ThuPhiModel.getSoTien());
		preparedStatement.setString(5, ThuPhiModel.getNgayDong());

		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	public boolean del(ThuPhiModel thuPhiModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "DELETE FROM thuphi WHERE IDKhoanThu = ? AND IDHoKhau = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setInt(1, thuPhiModel.getIDKhoanThu());
		preparedStatement.setInt(2, thuPhiModel.getIDHoKhau());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();
		return true;
	}

	// modify the update method to match the thuphi table
	public boolean update(ThuPhiModel ThuPhiModel) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		PreparedStatement preparedStatement;

		String query = "UPDATE thuphi SET TienDaDong = ?, SoTienPhaiDong = ?, NgayDong = ? WHERE IDKhoanThu = ? AND IDHoKhau = ?";
		preparedStatement = connection.prepareStatement(query);

		preparedStatement.setDouble(1, ThuPhiModel.getSoTien());
		preparedStatement.setDouble(2, ThuPhiModel.getSoTienPhaiDong());
		preparedStatement.setString(3, ThuPhiModel.getNgayDong());
		preparedStatement.setInt(4, ThuPhiModel.getIDKhoanThu());
		preparedStatement.setInt(5, ThuPhiModel.getIDHoKhau());

		preparedStatement.executeUpdate();
		return true;
	}

	public List<ThuPhiBean> getListThuPhi() throws ClassNotFoundException, SQLException {
		List<ThuPhiBean> list = new ArrayList<>();

		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM thuphi";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			ThuPhiModel thuPhiModel = new ThuPhiModel(
					rs.getInt("IDKhoanThu"),
					rs.getInt("IDHoKhau"),
					rs.getDouble("SoTienPhaiDong"),
					rs.getDouble("TienDaDong"),
					rs.getString("NgayDong"));
			ThuPhiBean thuPhiBean = new ThuPhiBean();
			thuPhiBean.setThuPhiModel(thuPhiModel);
			thuPhiBean.setTenKhoanThu(KhoanThuService.getKhoanThu(thuPhiModel.getIDKhoanThu()).getTenKT());
			thuPhiBean.setTenChuHo(HoKhauService_Tuan.getTenChuHo(thuPhiModel.getIDHoKhau()));

			list.add(thuPhiBean);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}
}
