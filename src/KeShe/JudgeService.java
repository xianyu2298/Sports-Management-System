package KeShe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgeService {

    private static final String ROLE = "judge";

    // 记录运动员成绩
    public void recordPerformance(long athleteId, long eventId, float score, int points) throws SQLException {
        if (!isAthleteExists(athleteId)) {
            System.out.println("运动员ID不存在");
            return;
        }

        if (!isEventExists(eventId)) {
            System.out.println("比赛项目ID不存在");
            return;
        }

        String query = "INSERT INTO 比赛成绩 (运动员编号, 项目编号, 成绩, 积分) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, athleteId);
            statement.setLong(2, eventId);
            statement.setFloat(3, score);
            statement.setInt(4, points);
            statement.executeUpdate();
            System.out.println("成绩记录成功");
        }
    }

    // 查看运动员比赛成绩
    public void viewAthletePerformance(long athleteId) throws SQLException {
        String query = "SELECT e.项目名称, p.成绩, p.积分 " +
                "FROM 比赛成绩 p JOIN 比赛项目 e ON p.项目编号 = e.项目编号 " +
                "WHERE p.运动员编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, athleteId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("项目名称: " + resultSet.getString("项目名称"));
                System.out.println("成绩: " + resultSet.getFloat("成绩"));
                System.out.println("积分: " + resultSet.getInt("积分"));
            }
        }
    }

    // 查看各学院总积分和名次
    public void viewCollegeScores() throws SQLException {
        String query = "SELECT 学院名称, 总积分, 排名 FROM 学院 ORDER BY 排名";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("学院名称: " + resultSet.getString("学院名称"));
                System.out.println("总积分: " + resultSet.getInt("总积分"));
                System.out.println("排名: " + resultSet.getInt("排名"));
            }
        }
    }

    // 检查运动员是否存在
    private boolean isAthleteExists(long athleteId) throws SQLException {
        String query = "SELECT 1 FROM 运动员 WHERE 运动员编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, athleteId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    // 检查比赛项目是否存在
    private boolean isEventExists(long eventId) throws SQLException {
        String query = "SELECT 1 FROM 比赛项目 WHERE 项目编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, eventId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
}
