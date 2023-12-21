package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public static boolean checkUser(String username, String password) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        boolean have_first_row = resultSet.next();

        return have_first_row;
    }

    public static boolean changPassword(String username, String password) throws ClassNotFoundException, SQLException {
        Connection connection = MysqlConnection.getMysqlConnection();

        String query = "UPDATE users SET password = ? WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, password);
        preparedStatement.setString(2, username);

        int rowsAffected = preparedStatement.executeUpdate();

        return rowsAffected > 0;
    }
}
