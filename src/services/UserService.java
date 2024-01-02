package services;

import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import models.UserModel;

import java.sql.*;

import static services.MysqlConnection.getMysqlConnection;

public class UserService {

    public static UserModel checkUser(String username, String password) throws ClassNotFoundException, SQLException {
        UserModel user = new UserModel();

        Connection connection = MysqlConnection.getMysqlConnection();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        boolean have_first_row = resultSet.next();
        if (have_first_row) {
            user.setPasswd(resultSet.getString("password"));
            user.setUsername(resultSet.getString("username"));
            user.setName(resultSet.getString("Email"));
            return user;
        }
        return null;
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

    // xuất file excel với đầu vào tương ứng là bảng trong databasse và tên file xuất ra tương ứng
    public static void xuatfileexcel(String bang, String outputFile) throws SQLException, ClassNotFoundException{

        Connection conn = getMysqlConnection();
        Statement sta = conn.createStatement();

        //Select all từ một bảng trong database
        ResultSet resultSet = sta.executeQuery("select * from " + bang);
        // Tạo Workbook và Worksheet
        Workbook workbook = new Workbook();
        Worksheet sheet = workbook.getWorksheets().get(0);

        // Lấy metadata để lấy số cột trong bảng
        int columnCount = resultSet.getMetaData().getColumnCount();

        // In tên các cột trong bảng ra hàng đầu của sheet
        for (int i = 1; i <= columnCount; i++) {

            String columnName = resultSet.getMetaData().getColumnName(i);
            System.out.print(columnName + '\t');
            sheet.get(1, i).setText(columnName);

        }
        System.out.println();

        // In lần lượt các hàng tiếp theo vào sheet
        int row = 2;
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                sheet.get(row, i).setValue(resultSet.getString(i));
                // Định dạng cho cột CCCD
                if (resultSet.getMetaData().getColumnName(i).equals("CCCD")) {
                    sheet.get(row, i).setText(resultSet.getString(i));
                }
                System.out.print(resultSet.getString(i) + '\t');
            }
            System.out.println();
            row++;
        }

        //Tự động fit độ rộng của cột
        sheet.getAllocatedRange().autoFitColumns();

        // Lưu workbook vào file
        workbook.saveToFile(outputFile);
        // Đóng workbook
        workbook.dispose();
    }

}
