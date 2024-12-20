package KeShe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public static String login(String username, String password) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseUtil.getConnection("admin");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        }
        return null;
    }
}
