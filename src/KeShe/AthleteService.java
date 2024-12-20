package KeShe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AthleteService {

    private static final String ROLE = "athlete";

    // 查看运动员所在学院的信息
    public void viewCollegeInfo(long athleteId) throws SQLException {
        String query = "SELECT c.学院名称, c.领队名称, c.总积分, c.排名 " +
                "FROM 运动员 a JOIN 学院 c ON a.学院编号 = c.学院编号 " +
                "WHERE a.运动员编号 = ?";
        try (Connection connection = DatabaseUtil.getConnection(ROLE);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, athleteId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("学院名称: " + resultSet.getString("学院名称"));
                System.out.println("领队名称: " + resultSet.getString("领队名称"));
                System.out.println("总积分: " + resultSet.getInt("总积分"));
                System.out.println("排名: " + resultSet.getInt("排名"));
            }
        }
    }

    // 查看运动员参加的比赛项目及成绩
    public void viewAthleteEvents(long athleteId) throws SQLException {
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
}
