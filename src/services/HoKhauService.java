package services;

import models.HoKhauBean;
import models.HoKhauModel;
import models.NhanKhauModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoKhauService {
    public static String getTenChuHo(int id_hk) throws ClassNotFoundException, SQLException {
        int id_chuho = getIDChuHo(id_hk);
        NhanKhauModel nhanKhauModel = NhanKhauService.loadDatafromID(id_chuho);
        return nhanKhauModel.getHoTen();
    }

    public static List<HoKhauBean> getListHoKhau() throws ClassNotFoundException, SQLException {
        List<HoKhauBean> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau GROUP BY hk.IDHoKhau";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String id_hk = rs.getString("IDHoKhau");
            int sotv = rs.getInt("SoTV");
            List<NhanKhauModel> nhanKhauList;
            nhanKhauList = NhanKhauService.getListNhanKhau(Integer.parseInt(id_hk));
            HoKhauModel hoKhauModel = getHoKhau(Integer.parseInt(id_hk));
            HoKhauBean hoKhauBean_ = new HoKhauBean(hoKhauModel, nhanKhauList, sotv);
            list.add(hoKhauBean_);
        }
        return list;
    }

    public static List<HoKhauBean> getListHoKhau(String keySearch, String typeSearchString) throws ClassNotFoundException, SQLException {
        List<HoKhauBean> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "";
        if (typeSearchString == "Tên chủ hộ") {
            query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk INNER JOIN nhankhau AS nk2 ON nk2.IDHoKhau = hk.IDHoKhau WHERE nk.IDHoKhau = hk.IDHoKhau AND nk2.QHvsChuHo = 'Chủ hộ' AND nk2.HoTen LIKE ? GROUP BY hk.IDHoKhau;";
        } else if (typeSearchString == "SĐT liên hệ") {
            query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau AND hk.SDT LIKE ? GROUP BY hk.IDHoKhau;";
        } else {
            query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau AND hk.IDHoKhau LIKE ? GROUP BY hk.IDHoKhau;";
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%" + keySearch + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String id_hk = rs.getString("IDHoKhau");
            int sotv = rs.getInt("SoTV");
            List<NhanKhauModel> nhanKhauList;
            nhanKhauList = NhanKhauService.getListNhanKhau(Integer.parseInt(id_hk));
            HoKhauModel hoKhauModel = getHoKhau(Integer.parseInt(id_hk));
            HoKhauBean hoKhauBean = new HoKhauBean(hoKhauModel, nhanKhauList, sotv);
            list.add(hoKhauBean);
        }
        return list;
    }

    public static HoKhauModel getHoKhau(int i) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();

        String query = "SELECT * FROM hokhau WHERE IDHoKhau = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(i));

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            HoKhauModel hoKhauModel = new HoKhauModel(rs.getInt("IDHoKhau"), rs.getInt("SoPhong"), rs.getString("NgayDen"), rs.getString("NgayDi"), rs.getString("SDT"));
            return hoKhauModel;
        } else return null;
    }

    public static int getIDChuHo(int id_hk) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT IDNhanKhau FROM nhankhau WHERE IDHoKhau = ? AND QHvsChuHo = 'Chủ hộ'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_hk);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("IDNhanKhau");
        } else return 0;
    }

    // lay ra id tiep theo = max(id) + 1
    public static Integer getNewIDHoKhau() throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT MAX(IDHoKhau) AS MaxID FROM hokhau";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return 1 + rs.getInt("MaxID");
        } else return 0;
    }

    public static HoKhauModel addHoKhau(HoKhauModel hoKhauModel) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO hokhau (IDHoKhau, SoPhong, NgayDen, NgayDi, SDT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, hoKhauModel.getIDHoKhau());
        preparedStatement.setInt(2, hoKhauModel.getSoPhong());
        preparedStatement.setString(3, hoKhauModel.getNgayDen());
        preparedStatement.setString(4, hoKhauModel.getNgayDi());
        preparedStatement.setString(5, hoKhauModel.getSDT());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return hoKhauModel;
    }

    // Lammmmmmmmmmm
    public static void changeChuHo(int IDHoKhau, int new_IDChuho) throws ClassNotFoundException, SQLException {
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
    public static void updateHoKhau(HoKhauModel hoKhauModelTuan) throws SQLException, ClassNotFoundException {
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

    public static List<HoKhauBean> getListHoKhau(String TrangThai) throws ClassNotFoundException, SQLException {
        List<HoKhauBean> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "";
        if (TrangThai == "Đang cư trú") {
            query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau AND hk.NgayDi = '0001/01/01' GROUP BY hk.IDHoKhau";
        } else {
            query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau AND hk.NgayDi != '0001/01/01' GROUP BY hk.IDHoKhau";
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String id_hk = rs.getString("IDHoKhau");
            int sotv = rs.getInt("SoTV");
            List<NhanKhauModel> nhanKhauList;
            nhanKhauList = NhanKhauService.getListNhanKhau(Integer.parseInt(id_hk));
            HoKhauModel hoKhauModel = getHoKhau(Integer.parseInt(id_hk));
            HoKhauBean hoKhauBean = new HoKhauBean(hoKhauModel, nhanKhauList, sotv);
            list.add(hoKhauBean);
        }
        return list;
    }

    public static boolean delHoKhau(HoKhauBean hkb) {
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

    public static HoKhauBean getHoKhauBean(int id_hk) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT hk.IDHoKhau, COUNT(nk.IDHoKhau) AS SoTV FROM nhankhau AS nk, hokhau AS hk WHERE nk.IDHoKhau = hk.IDHoKhau AND nk.IDNhanKhau = ? GROUP BY hk.IDHoKhau";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_hk);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String idhk = rs.getString("IDHoKhau");
            int sotv = rs.getInt("SoTV");
            HoKhauModel hoKhauModel = getHoKhau(Integer.parseInt(idhk));
            HoKhauBean hoKhauBean = new HoKhauBean(hoKhauModel, sotv);
        }
        connection.close();
        return null;
    }

    public boolean add(HoKhauModel hoKhauModel) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO hokhau (IDHoKhau, SoPhong, NgayDen, NgayDi, SDT) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, hoKhauModel.getIDHoKhau());
        preparedStatement.setInt(2, hoKhauModel.getSoPhong());
        preparedStatement.setString(3, hoKhauModel.getNgayDen());
        preparedStatement.setString(4, hoKhauModel.getNgayDi());
        preparedStatement.setString(5, hoKhauModel.getSDT());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }

    public static int getSoTV(int id_hk) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT COUNT(IDHoKhau) AS SoTV FROM nhankhau WHERE IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id_hk);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return rs.getInt("SoTV");
        } else return 0;
    }

    public static String checkSuDung(int soPhong) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM hokhau WHERE SoPhong = ? AND NgayDi = '0001-01-01'";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, soPhong);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return "Đang được sử dụng";
        }
        connection.close();
        return "Chưa được sử dụng";
    }

    public static String countHoKhau() throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT Count(IDHoKhau) FROM hokhau WHERE NgayDi = '0001-01-01'";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return String.valueOf(count);
        }
        connection.close();
        return "0";
    }

}

