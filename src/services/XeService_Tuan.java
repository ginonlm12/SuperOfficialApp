package services;

import models.XeModel_Tuan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XeService_Tuan {
    public static List<XeModel_Tuan> getAllXe() throws SQLException, ClassNotFoundException {
        List<XeModel_Tuan> list = new ArrayList<>();

        Connection conn = MysqlConnection.getMysqlConnection();
        String query = "SELECT IDHoKhau, XeDap, XeMay, Oto FROM xe GROUP BY IDHoKhau;";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            int IDHoKhau = rs.getInt("IDHoKhau");
            if(HoKhauService_Tuan.getHoKhau(String.valueOf(IDHoKhau)).getNgayDi().equals("0001-01-01") ){
                int XeDap = rs.getInt("XeDap");
                int XeMay = rs.getInt("XeMay");
                int Oto = rs.getInt("Oto");
                String ChuHo = NhanKhauService_Lam.getChuHo(IDHoKhau);
                XeModel_Tuan xe = new XeModel_Tuan(IDHoKhau, Oto, XeDap, XeMay, ChuHo);
                list.add(xe);
            }
        }
        return list;
    }

    public static void updateXe(XeModel_Tuan xe) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlConnection.getMysqlConnection();
        String query = "UPDATE xe SET XeDap = ?, XeMay = ?, Oto = ? WHERE IDHoKhau = ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, xe.getXeDap());
        preparedStatement.setInt(2, xe.getXeMay());
        preparedStatement.setInt(3, xe.getOTo());
        preparedStatement.setInt(4, xe.getIDHoKhau());
        preparedStatement.execute();
    }
}
