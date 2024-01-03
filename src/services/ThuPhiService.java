package services;

import javafx.scene.chart.XYChart;
import models.ThuPhiBean;
import models.ThuPhiModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

	public static boolean del(ThuPhiModel thuPhiModel) throws ClassNotFoundException, SQLException {
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
	public static boolean update(ThuPhiModel ThuPhiModel) throws ClassNotFoundException, SQLException {
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
		connection.close();
		return true;
	}

	public static List<ThuPhiBean> getListThuPhi() throws ClassNotFoundException, SQLException {
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
			thuPhiBean.setTenChuHo(HoKhauService.getTenChuHo(thuPhiModel.getIDHoKhau()));

			list.add(thuPhiBean);
		}
		preparedStatement.close();
		connection.close();
		return list;
	}
	public static int getSoHoDaThu(int IDKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT COUNT(*) FROM thuphi WHERE IDKhoanThu = ? AND TienDaDong >= SoTienPhaiDong";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, IDKhoanThu);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		preparedStatement.close();
		connection.close();
		// System.out.println(IDKhoanThu);
		// System.out.println(count);
		// System.out.println("\n");
		return count;
	}

	public static Double getTongSoTien(int IDKhoanThu) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT SUM(TienDaDong) FROM thuphi WHERE IDKhoanThu = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, IDKhoanThu);
		ResultSet rs = preparedStatement.executeQuery();
		rs.next();
		Double sum = rs.getDouble(1);
		preparedStatement.close();
		connection.close();
		return sum;
	}

    public static XYChart.Series<String, Number> PaymentSatistic(Integer Year) throws SQLException, ClassNotFoundException {
		Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT MONTH(tp.NgayDong) AS Thang, SUM(tp.TienDaDong) AS TongTien FROM thuphi tp INNER JOIN khoanthu kt ON tp.IDKhoanThu = kt.IDKhoanThu WHERE Year(tp.NgayDong) = ? GROUP BY MONTH(tp.NgayDong) ORDER BY Thang";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, Year);
		ResultSet resultSet = preparedStatement.executeQuery();

		XYChart.Series<String, Number> series = new XYChart.Series<>();

		for (int i = 1; i <= 12; i++) {
			series.getData().add(new XYChart.Data<>(String.valueOf(i), 0));
		}

		while (resultSet.next()) {
			int month = resultSet.getInt("Thang");
			String Smonth = String.valueOf(month);
			int totalIncome = resultSet.getInt("TongTien");

			series.getData().add(new XYChart.Data<>(Smonth, totalIncome));
		}
		connection.close();
		return series;
	}

	public static ThuPhiModel getThuPhiModel(int IDKhoanThu, int IDHoKhau) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "SELECT * FROM thuphi WHERE IDKhoanThu = ? AND IDHoKhau = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, IDKhoanThu);
		preparedStatement.setInt(2, IDHoKhau);
		ResultSet rs = preparedStatement.executeQuery();
		// kiem tra xem co ban ghi nao khong
		if (!rs.next()) {
			return null;
		}

		ThuPhiModel thuPhiModel = new ThuPhiModel(
				rs.getInt("IDKhoanThu"),
				rs.getInt("IDHoKhau"),
				rs.getDouble("SoTienPhaiDong"),
				rs.getDouble("TienDaDong"),
				rs.getString("NgayDong"));
		preparedStatement.close();
		connection.close();

		return thuPhiModel;
	}
}
