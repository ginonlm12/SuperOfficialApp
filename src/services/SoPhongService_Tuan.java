package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoPhongService_Tuan {
    public static ObservableList<Integer> getListSoPhong() throws ClassNotFoundException, SQLException {
        ObservableList<Integer> list = FXCollections.observableArrayList();

        Connection connection = MysqlConnection.getMysqlConnection();
        String query = "SELECT SoPhong FROM phong WHERE SoPhong NOT IN (SELECT SoPhong FROM hokhau WHERE NgayDi = '0001/01/01') ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            int sophong = rs.getInt("SoPhong");
            list.add(sophong);
        }
        return list;
    }
}
