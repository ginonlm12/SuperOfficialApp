package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import models.NhanKhauModel_Lam;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NhanKhauService_Lam {
    public static NhanKhauModel_Lam loadDatafromID(int idNhanKhau) throws ClassNotFoundException, SQLException {
        NhanKhauModel_Lam nhanKhauModel = new NhanKhauModel_Lam();
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM nhankhau WHERE IDNhanKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, idNhanKhau);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int IDHoKhau = resultSet.getInt("IDHoKhau");
            String QHvsChuHo = resultSet.getString("QHvsChuHo");
            String HoTen = resultSet.getString("HoTen");
            String NgaySinh = resultSet.getString("NgaySinh");
            String MaCCCD = resultSet.getString("CCCD");
            String NgheNghiep = resultSet.getString("NgheNghiep");
            String GioiTinh = resultSet.getString("GioiTinh");
            String DanToc = resultSet.getString("DanToc");
            String QueQuan = resultSet.getString("QueQuan");
            // IDHoKhau, QHvsChuHo, HoTen, NgaySinh, MaCCCD, NgheNghiep, GioiTinh, DanToc, QueQuan
            //String MaCCCD = resultSet.getString("CCCD");
            //String QueQuan = resultSet.getString("QueQuan");
            //String NgheNghiep = resultSet.getString("NgheNghiep");
            //String NoiCongTac = resultSet.getString("NoiCongTac");
            //String DanToc = resultSet.getString("DanToc");
            //String TonGiao = resultSet.getString("TonGiao");
            nhanKhauModel = new NhanKhauModel_Lam(idNhanKhau, IDHoKhau, QHvsChuHo, HoTen, NgaySinh, MaCCCD, NgheNghiep, GioiTinh, DanToc, QueQuan);
            return nhanKhauModel;
        }
        return null;
    }

    public static boolean update(NhanKhauModel_Lam nhanKhauModel) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        PreparedStatement preparedStatement;

        String query = "UPDATE nhankhau SET IDHoKhau = ?, QHvsChuHo = ?, HoTen = ?, NgaySinh = ?," +
                " CCCD = ?, NgheNghiep = ?, GioiTinh = ?, DanToc = ?, QueQuan = ? WHERE IDNhanKhau = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, nhanKhauModel.getIDHoKhau());
        preparedStatement.setString(2, nhanKhauModel.getQHvsChuHo());
        preparedStatement.setString(3, nhanKhauModel.getHoTen());
        preparedStatement.setString(4, nhanKhauModel.getNgaySinh());
        preparedStatement.setString(5, nhanKhauModel.getCCCD());
        preparedStatement.setString(6, nhanKhauModel.getNgheNghiep());
        preparedStatement.setString(7, nhanKhauModel.getGioiTinh());
        preparedStatement.setString(8, nhanKhauModel.getDanToc());
        preparedStatement.setString(9, nhanKhauModel.getQueQuan());
        preparedStatement.setInt(10, nhanKhauModel.getIDNhanKhau());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }

    public static List<NhanKhauModel_Lam> getListNhanKhau(int IDHoKhau) throws ClassNotFoundException, SQLException {
        List<NhanKhauModel_Lam> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM nhankhau WHERE IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, IDHoKhau);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            NhanKhauModel_Lam nhanKhauModel = new NhanKhauModel_Lam();
            nhanKhauModel.setIDNhanKhau(rs.getInt("IDNhanKhau"));
            nhanKhauModel.setIDHoKhau(rs.getInt("IDHoKhau"));
            nhanKhauModel.setQHvsChuHo(rs.getString("QHvsChuHo"));
            nhanKhauModel.setHoTen(rs.getString("HoTen"));
            nhanKhauModel.setNgaySinh(rs.getString("NgaySinh"));
            nhanKhauModel.setCCCD(rs.getString("CCCD"));
            nhanKhauModel.setNgheNghiep(rs.getString("NgheNghiep"));
            nhanKhauModel.setGioiTinh(rs.getString("GioiTinh"));
            nhanKhauModel.setDanToc(rs.getString("DanToc"));
            nhanKhauModel.setQueQuan(rs.getString("QueQuan"));
            list.add(nhanKhauModel);
        }
        preparedStatement.close();
        connection.close();
        return list;
    }

    public static int getNewIDNhanKhau() throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();

        var query = "SELECT MAX(IDNhanKhau) FROM nhankhau";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        int maxID = 0;
        if (resultSet.next()) {
            maxID = resultSet.getInt(1);
        }
        return maxID + 1;
    }

    public static ObservableList<String> getProvince() throws ClassNotFoundException, SQLException {
        ObservableList<String> Tinh_List = FXCollections.observableArrayList();

        Connection connection = MysqlConnection.getMysqlConnection("databaseforvn");
        String query = "SELECT * FROM provinces";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        Tinh_List.clear();
        while (resultSet.next()) {
            String provinceName = resultSet.getString("name");
            Tinh_List.add(provinceName);
        }

        return Tinh_List;
    }

    public static ObservableList<String> GetDistrict(String selectedProvince) throws ClassNotFoundException, SQLException {
        ObservableList<String> Huyen_List = FXCollections.observableArrayList();

        Connection connection = MysqlConnection.getMysqlConnection("databaseforvn");
        String query = "SELECT * FROM districts WHERE province_code = (SELECT code FROM provinces WHERE name = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, selectedProvince);
        ResultSet resultSet = preparedStatement.executeQuery();

        Huyen_List.clear();
        while (resultSet.next()) {
            String districtName = resultSet.getString("name");
            Huyen_List.add(districtName);
        }

        return Huyen_List;
    }

    public static ObservableList<String> GetWard(String selectedDistrict, String selectedProvince) throws ClassNotFoundException, SQLException {
        ObservableList<String> Xa_List = FXCollections.observableArrayList();

        Connection connection = MysqlConnection.getMysqlConnection("databaseforvn");
        String query = "SELECT * FROM wards WHERE district_code = (SELECT code FROM districts WHERE name = ? AND province_code = (SELECT code FROM provinces WHERE name = ?))";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, selectedDistrict);
        preparedStatement.setString(2, selectedProvince);
        ResultSet resultSet = preparedStatement.executeQuery();

        Xa_List.clear();

        while (resultSet.next()) {
            String wardName = resultSet.getString("name");
            Xa_List.add(wardName);
        }

        return Xa_List;
    }

    public static String getChuHo(int IDHoKhau) throws SQLException, ClassNotFoundException {
        String ChuHo = null;
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT HoTen FROM nhankhau WHERE IDHoKhau = ? AND QHvsChuHo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, IDHoKhau);
        preparedStatement.setString(2, "Chủ hộ");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            ChuHo = resultSet.getString("HoTen");
        }
        return ChuHo;
    }

    public static String getHoTen(int IDNhanKhau) throws SQLException, ClassNotFoundException {
        String HoTen = null;
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT HoTen FROM nhankhau WHERE IDNhanKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, IDNhanKhau);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            HoTen = resultSet.getString("HoTen");
        }
        return HoTen;
    }

    public static int getSoPhong(int IDHoKhau) throws ClassNotFoundException, SQLException {
        int SoPhong = 0;
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT SoPhong FROM hokhau where IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, IDHoKhau);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            SoPhong = resultSet.getInt("SoPhong");
        }
        return SoPhong;
    }

    public static String countNhanKhau() throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT COUNT(*) FROM nhankhau WHERE IDHoKhau IN (SELECT IDHoKhau FROM hokhau WHERE NgayDi = '0001-01-01')";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return String.valueOf(count);
        }
        return "0";
    }

    public static ArrayList<PieChart.Data> GenderClassify() throws SQLException, ClassNotFoundException {
        ArrayList<PieChart.Data> GenderD = new ArrayList<>();
        Connection connection = MysqlConnection.getMysqlConnection();
        Statement statement = connection.createStatement();

        String query = "SELECT GioiTinh, COUNT(*) as Count FROM nhankhau GROUP BY GioiTinh";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String gender = resultSet.getString("GioiTinh");
            int count = resultSet.getInt("Count");
            if (gender.equals("(Chưa nhập)"))
                GenderD.add(new PieChart.Data("Chưa điền", count));
            else GenderD.add(new PieChart.Data(gender, count));
        }
        return GenderD;
    }

    public static ArrayList<PieChart.Data> AgeClassify() throws SQLException, ClassNotFoundException {
        ArrayList<PieChart.Data> AgeD = new ArrayList<>();
        int[] mapAge = new int[5];
        Connection connection = MysqlConnection.getMysqlConnection();
        Statement statement = connection.createStatement();

        String query = "SELECT NgaySinh FROM nhankhau";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            LocalDate ngaySinh = resultSet.getDate("NgaySinh").toLocalDate();
            int age = calculateAge(ngaySinh);

            // Phân loại độ tuổi và thêm vào danh sách
            if (age >= 0 && age <= 5) {
                mapAge[0]++;
            } else if (age >= 6 && age <= 18) {
                mapAge[1]++;
            } else if (age >= 19 && age <= 45) {
                mapAge[2]++;
            } else if (age >= 46 && age <= 65) {
                mapAge[3]++;
            } else {
                mapAge[4]++;
            }
        }

        AgeD.add(new PieChart.Data("0-5", mapAge[0]));
        AgeD.add(new PieChart.Data("6-18", mapAge[1]));
        AgeD.add(new PieChart.Data("19-45", mapAge[2]));
        AgeD.add(new PieChart.Data("46-65", mapAge[3]));
        AgeD.add(new PieChart.Data("66+", mapAge[4]));

        return AgeD;
    }

    private static int calculateAge(LocalDate ngaySinh) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(ngaySinh, currentDate);
        return period.getYears();
    }
    // checked
    public boolean add(NhanKhauModel_Lam nhanKhauModel) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "INSERT INTO nhankhau(IDNhanKhau, IDHoKhau, QHvsChuHo, HoTen, NgaySinh, CCCD, NgheNghiep, GioiTinh, DanToc, QueQuan)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, nhanKhauModel.getIDNhanKhau());
        preparedStatement.setInt(2, nhanKhauModel.getIDHoKhau());
        preparedStatement.setString(3, nhanKhauModel.getQHvsChuHo());
        preparedStatement.setString(4, nhanKhauModel.getHoTen());
        preparedStatement.setString(5, nhanKhauModel.getNgaySinh());
        preparedStatement.setString(6, nhanKhauModel.getCCCD());
        preparedStatement.setString(7, nhanKhauModel.getNgheNghiep());
        preparedStatement.setString(8, nhanKhauModel.getGioiTinh());
        preparedStatement.setString(9, nhanKhauModel.getDanToc());
        preparedStatement.setString(10, nhanKhauModel.getQueQuan());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }

    // checked
    public static boolean del(int IDNhanKhau) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();

        String query = "DELETE FROM nhankhau WHERE IDNhanKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, IDNhanKhau);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }

    // checked
    public List<NhanKhauModel_Lam> getListNhanKhau() throws ClassNotFoundException, SQLException {
        List<NhanKhauModel_Lam> list = new ArrayList<>();

        Connection connection = MysqlConnection.getMysqlConnection();

        String query = "SELECT * FROM nhankhau WHERE IDHoKhau IN (SELECT IDHoKhau FROM hokhau WHERE NgayDi = '0001-01-01')";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            NhanKhauModel_Lam nhanKhauModel = new NhanKhauModel_Lam();
            nhanKhauModel.setIDNhanKhau(rs.getInt("IDNhanKhau"));
            nhanKhauModel.setIDHoKhau(rs.getInt("IDHoKhau"));
            nhanKhauModel.setQHvsChuHo(rs.getString("QHvsChuHo"));
            nhanKhauModel.setHoTen(rs.getString("HoTen"));
            nhanKhauModel.setNgaySinh(rs.getString("NgaySinh"));
            nhanKhauModel.setCCCD(rs.getString("CCCD"));
            nhanKhauModel.setNgheNghiep(rs.getString("NgheNghiep"));
            nhanKhauModel.setGioiTinh(rs.getString("GioiTinh"));
            nhanKhauModel.setDanToc(rs.getString("DanToc"));
            nhanKhauModel.setQueQuan(rs.getString("QueQuan"));
            list.add(nhanKhauModel);
        }
        preparedStatement.close();
        connection.close();
        return list;
    }

    public static String extractIdHoKhau(String comboBoxValue) {
        Pattern pattern = Pattern.compile("\\d+");

        Matcher matcher = pattern.matcher(comboBoxValue);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public static void sortVectorByRoomNumber(Vector<String> house) {
        Collections.sort(house, new Comparator<String>() {
            @Override
            public int compare(String house1, String house2) {
                int roomNumber1 = extractRoomNumber(house1);
                int roomNumber2 = extractRoomNumber(house2);

                return Integer.compare(roomNumber1, roomNumber2);
            }
        });
    }

    private static int extractRoomNumber(String houseInfo) {
        int roomIndex = houseInfo.indexOf("Phòng: ");
        if (roomIndex != -1) {
            String roomSubstring = houseInfo.substring(roomIndex + 7);
            return Integer.parseInt(roomSubstring.split(" ")[0]);
        }
        return 0;
    }
}
