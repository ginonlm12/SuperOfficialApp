package services;

import java.sql.*;
/**
 *
 * @QLTT
 */
public class MysqlConnection {
    public static Connection getMysqlConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "demo01";
        String userName = "root";
        String password = "";
        return getMysqlConnection(hostName, dbName, userName, password);
    }
    
    public static Connection getMysqlConnection(String dbname_) throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = dbname_;
        String userName = "root";
        String password = "";
        return getMysqlConnection(hostName, dbName, userName, password);
    }

    public static Connection getMysqlConnection(String hostName, String dbName, String userName, String password)
        throws SQLException, ClassNotFoundException{
        String connectionUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName;
        Connection conn = DriverManager.getConnection(connectionUrl, userName, password);
        // if (conn != null) System.out.println("Ket noi CSDL thanh cong"); 
        // else System.out.println("Ket noi CSDLthat bai");
        return conn;
    }
}
