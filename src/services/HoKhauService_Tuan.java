package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.HoKhauBean_Tuan;
import models.HoKhauModel_Tuan;
import models.NhanKhauModel_Lam;

public class HoKhauService_Tuan {
    public boolean add(HoKhauModel_Tuan hoKhauModel_Tuan) throws ClassNotFoundException, SQLException{
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO hokhau (IDHoKhau, SoPhong, NgayDen, NgayDi, SDT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, hoKhauModel_Tuan.getIDHoKhau());
        preparedStatement.setInt(2, hoKhauModel_Tuan.getSoPhong());
        preparedStatement.setString(3, hoKhauModel_Tuan.getNgayDen());
        preparedStatement.setString(4, hoKhauModel_Tuan.getNgayDi());
        preparedStatement.setString(5, hoKhauModel_Tuan.getSDT());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }


    public HoKhauBean_Tuan getHoKhauBean(int id_hk) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau AND nk.IDNhanKhau = ? GROUP BY hk.IDHoKhau";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_hk);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            String idhk = rs.getString("IDHoKhau");
            int sotv = rs.getInt("SoTV");
            HoKhauModel_Tuan hoKhauModel_tuan = getHoKhau(idhk);
            HoKhauBean_Tuan hoKhauBean_tuan = new HoKhauBean_Tuan(hoKhauModel_tuan, sotv);
        }
        return null;
    }

    public static List<HoKhauBean_Tuan> getListHoKhau() throws ClassNotFoundException, SQLException {
        List<HoKhauBean_Tuan> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau GROUP BY hk.IDHoKhau";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            String id_hk = rs.getString("IDHoKhau");
            int sotv = rs.getInt("SoTV");
            List<NhanKhauModel_Lam> nhanKhauList;
            nhanKhauList = NhanKhauService_Lam.getListNhanKhau(Integer.parseInt(id_hk));
            HoKhauModel_Tuan hoKhauModel_tuan = getHoKhau(id_hk);
            HoKhauBean_Tuan hoKhauBean_tuan = new HoKhauBean_Tuan(hoKhauModel_tuan, nhanKhauList, sotv);
            list.add(hoKhauBean_tuan);
        }
        return list;
    }

    public static HoKhauModel_Tuan getHoKhau (String id_hk) throws ClassNotFoundException, SQLException {


        Connection connection = MysqlConnection.getMysqlConnection();

        String query = "SELECT * FROM hokhau WHERE IDHoKhau = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, id_hk);

        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            HoKhauModel_Tuan hoKhauModel_tuan = new HoKhauModel_Tuan(rs.getInt("IDHoKhau"), rs.getInt("SoPhong"), rs.getString("NgayDen"), rs.getString("NgayDi"), rs.getString("SDT"));
            return hoKhauModel_tuan;
        }
        else return null;
    }

    public int getSoTV (int id_hk) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT COUNT(IDHoKhau) AS SoTV FROM nhankhau WHERE IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_hk);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            return rs.getInt("SoTV");
        }
        else return 0;
    }

    public static int getIDChuHo(int id_hk) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT IDNhanKhau FROM nhankhau WHERE IDHoKhau = ? AND QHvsChuHo = 'Chủ hộ' ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_hk);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            return rs.getInt("IDNhanKhau");
        }
        else return 0;
    }

    // lay ra id tiep theo = max(id) + 1
    public static Integer getNewIDHoKhau() throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT MAX(IDHoKhau) AS MaxID FROM hokhau WHERE NgayDi = '0001/01/01'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            return 1 + rs.getInt("MaxID");
        }
        else return 0;
    }

    public static boolean delHoKhau(HoKhauBean_Tuan hkb) {
        try {
            Connection connection = MysqlConnection.getMysqlConnection();
            String query = "UPDATE hokhau SET NgayDi = NOW() WHERE IDHoKhau = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            String idHoKhauDel = String.valueOf(hkb.getHoKhauModel_tuan().getIDHoKhau());
            preparedStatement.setInt(1, Integer.parseInt(idHoKhauDel));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lammmmmmmmmmm
    public static void changeChuHo(int IDHoKhau, int new_IDChuho) throws ClassNotFoundException, SQLException{
        Connection connection = MysqlConnection.getMysqlConnection();

        String query1 = "UPDATE nhankhau SET QHvsChuHo = ? WHERE IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, "(Chưa nhập)");
        preparedStatement.setInt(2, IDHoKhau);
        preparedStatement.executeUpdate();
        preparedStatement.close();

        String query2 = "UPDATE nhankhau SET QHvsChuHo = ? WHERE IDHoKhau = ? AND IDNhanKhau = ?";
        preparedStatement = connection.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, "Chủ hộ");
        preparedStatement.setInt(2, IDHoKhau);
        preparedStatement.setInt(3, new_IDChuho);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    // Lammmmm
    public static void updateHoKhau(HoKhauModel_Tuan hoKhauModelTuan) throws SQLException, ClassNotFoundException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "UPDATE hokhau SET SoPhong = ?, NgayDen = ?, SDT = ? WHERE IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, hoKhauModelTuan.getSoPhong());
        preparedStatement.setString(2, hoKhauModelTuan.getNgayDen());
        preparedStatement.setString(3, hoKhauModelTuan.getSDT());
        preparedStatement.setInt(4, hoKhauModelTuan.getIDHoKhau());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}

