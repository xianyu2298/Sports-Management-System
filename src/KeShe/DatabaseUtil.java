package KeShe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/运动会管理系统";

    public static Connection getConnection(String role) throws SQLException {
        String user;
        String password;

        switch (role) {
            case "athlete":
                user = "athlete";
                password = "athlete_password";
                break;
            case "judge":
                user = "judge";
                password = "judge_password";
                break;
            case "admin":
                user = "admin";
                password = "admin_password";
                break;
            default:
                throw new IllegalArgumentException("非法用户类型: " + role);
        }

        return DriverManager.getConnection(URL, user, password);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
