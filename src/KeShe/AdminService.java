package KeShe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {

    private static final String ROLE = "admin";

    // 增加学院
    public void addCollege(String collegeName, String leaderName) throws SQLException {
        String query = "INSERT INTO 学院 (学院名称, 领队名称) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, collegeName);
            statement.setString(2, leaderName);
            statement.executeUpdate();
            System.out.println("学院添加成功");
        }
    }

    // 增加比赛项目
    public void addEvent(String eventName, String eventType) throws SQLException {
        String query = "INSERT INTO 比赛项目 (项目名称, 项目类型) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, eventName);
            statement.setString(2, eventType);
            statement.executeUpdate();
            System.out.println("比赛项目添加成功");
        }
    }

    // 删除比赛项目
    public void deleteEvent(long eventId) throws SQLException {
        String query = "DELETE FROM 比赛项目 WHERE 项目编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, eventId);
            statement.executeUpdate();
            System.out.println("比赛项目删除成功");
        }
    }

    // 修改比赛项目
    public void updateEvent(long eventId, String eventName, String eventType) throws SQLException {
        String query = "UPDATE 比赛项目 SET 项目名称 = ?, 项目类型 = ? WHERE 项目编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, eventName);
            statement.setString(2, eventType);
            statement.setLong(3, eventId);
            statement.executeUpdate();
            System.out.println("比赛项目更新成功");
        }
    }

    // 管理学院信息
    public void manageCollege(long collegeId, String collegeName, String leaderName) throws SQLException {
        String query = "UPDATE 学院 SET 学院名称 = ?, 领队名称 = ? WHERE 学院编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, collegeName);
            statement.setString(2, leaderName);
            statement.setLong(3, collegeId);
            statement.executeUpdate();
            System.out.println("学院信息更新成功");
        }
    }

    // 管理学院运动员信息
    public void manageAthlete(long athleteId, String athleteName, long collegeId) throws SQLException {
        String query = "UPDATE 运动员 SET 运动员姓名 = ?, 学院编号 = ? WHERE 运动员编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, athleteName);
            statement.setLong(2, collegeId);
            statement.setLong(3, athleteId);
            statement.executeUpdate();
            System.out.println("运动员信息更新成功");
        }
    }

    // 添加运动员
    public void addAthlete(long athleteId, String athleteName, long collegeId) throws SQLException {
        String query = "INSERT INTO 运动员 (运动员编号, 运动员姓名, 学院编号) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, athleteId);
            statement.setString(2, athleteName);
            statement.setLong(3, collegeId);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("运动员添加成功");
            } else {
                System.out.println("运动员添加失败");
            }
        }
    }

    // 删除运动员
    public void deleteAthlete(long athleteId) throws SQLException {
        // 先删除与该运动员相关的所有成绩记录
        String deleteScoresQuery = "DELETE FROM 比赛成绩 WHERE 运动员编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement deleteScoresStatement = connection.prepareStatement(deleteScoresQuery)) {

            deleteScoresStatement.setLong(1, athleteId);
            deleteScoresStatement.executeUpdate();
        }

        // 再删除运动员
        String deleteAthleteQuery = "DELETE FROM 运动员 WHERE 运动员编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement deleteAthleteStatement = connection.prepareStatement(deleteAthleteQuery)) {

            deleteAthleteStatement.setLong(1, athleteId);
            deleteAthleteStatement.executeUpdate();
            System.out.println("运动员删除成功");
        }
    }

    // 删除学院
    public void deleteCollege(long collegeId) throws SQLException {
        // 先删除与该学院相关的所有运动员及其成绩记录
        String selectAthletesQuery = "SELECT 运动员编号 FROM 运动员 WHERE 学院编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement selectAthletesStatement = connection.prepareStatement(selectAthletesQuery)) {

            selectAthletesStatement.setLong(1, collegeId);
            ResultSet resultSet = selectAthletesStatement.executeQuery();
            while (resultSet.next()) {
                long athleteId = resultSet.getLong("运动员编号");
                deleteAthlete(athleteId);
            }
        }

        // 再删除学院
        String deleteCollegeQuery = "DELETE FROM 学院 WHERE 学院编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement deleteCollegeStatement = connection.prepareStatement(deleteCollegeQuery)) {

            deleteCollegeStatement.setLong(1, collegeId);
            deleteCollegeStatement.executeUpdate();
            System.out.println("学院删除成功");
        }
    }
}
