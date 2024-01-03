package services;

import models.XeModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XeService {
    public static List<XeModel> getAllXe() throws SQLException, ClassNotFoundException {
        List<XeModel> list = new ArrayList<>();

        Connection conn = MysqlConnection.getMysqlConnection();
        String query = "SELECT IDHoKhau, XeDap, XeMay, Oto FROM xe GROUP BY IDHoKhau;";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            int IDHoKhau = rs.getInt("IDHoKhau");
            if (HoKhauService.getHoKhau(IDHoKhau).getNgayDi().equals("0001-01-01")) {
                int XeDap = rs.getInt("XeDap");
                int XeMay = rs.getInt("XeMay");
                int Oto = rs.getInt("Oto");
                String ChuHo = NhanKhauService.getChuHo(IDHoKhau);
                XeModel xe = new XeModel(IDHoKhau, Oto, XeDap, XeMay, ChuHo);
                list.add(xe);
            }
        }
        conn.close();
        return list;
    }

    public static void updateXe(XeModel xe) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnection.getMysqlConnection();
        String query = "UPDATE xe SET XeDap = ?, XeMay = ?, Oto = ? WHERE IDHoKhau = ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, xe.getXeDap());
        preparedStatement.setInt(2, xe.getXeMay());
        preparedStatement.setInt(3, xe.getOTo());
        preparedStatement.setInt(4, xe.getIDHoKhau());
        preparedStatement.execute();
        conn.close();
    }
    // get XeModel by IDHoKhau
    public static XeModel getXeModel(int IDHoKhau) throws ClassNotFoundException, SQLException {
        XeModel xeModel = new XeModel();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT * FROM xe WHERE IDHoKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, IDHoKhau);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            xeModel.setIDHoKhau(rs.getInt("IDHoKhau"));
            xeModel.setXeDap(rs.getInt("XeDap"));
            xeModel.setXeMay(rs.getInt("XeMay"));
            xeModel.setOTo(rs.getInt("Oto"));
        }
        connection.close();
        return xeModel;
    }
}
