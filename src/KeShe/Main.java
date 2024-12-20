package KeShe;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入用户名：");
            String username = scanner.nextLine();
            System.out.println("请输入密码：");
            String password = scanner.nextLine();

            try {
                String role = UserService.login(username, password);
                if (role == null) {
                    System.out.println("登录失败，用户名或密码错误，请重新输入。");
                    continue;
                }

                switch (role) {
                    case "admin":
                        adminMenu(scanner);
                        break;

                    case "athlete":
                        athleteMenu(scanner);
                        break;

                    case "judge":
                        judgeMenu(scanner);
                        break;

                    default:
                        System.out.println("未知的角色");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void adminMenu(Scanner scanner) throws SQLException {
        AdminService adminService = new AdminService();
        while (true) {
            System.out.println("\n管理员菜单：");
            System.out.println("1. 添加学院");
            System.out.println("2. 删除学院");
            System.out.println("3. 更新学院信息");
            System.out.println("4. 添加比赛项目");
            System.out.println("5. 删除比赛项目");
            System.out.println("6. 更新比赛项目");
            System.out.println("7. 更新运动员");
            System.out.println("8. 删除运动员");
            System.out.println("9. 添加运动员");
            System.out.println("10. 退出");
            System.out.print("请选择操作：");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("请输入学院名称：");
                    String collegeName = scanner.nextLine();
                    System.out.println("请输入领队名称：");
                    String leaderName = scanner.nextLine();
                    adminService.addCollege(collegeName, leaderName);
                    break;
                case 2:
                    System.out.println("请输入要删除的学院ID：");
                    long collegeId = scanner.nextLong();
                    adminService.deleteCollege(collegeId);
                    break;
                case 3:
                    System.out.println("请输入要更新的学院ID：");
                    collegeId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("请输入新的学院名称：");
                    collegeName = scanner.nextLine();
                    System.out.println("请输入新的领队名称：");
                    leaderName = scanner.nextLine();
                    adminService.manageCollege(collegeId, collegeName, leaderName);
                    break;
                case 4:
                    System.out.println("请输入比赛项目名称：");
                    String eventName = scanner.nextLine();
                    System.out.println("请输入比赛项目类型：");
                    String eventType = scanner.nextLine();
                    adminService.addEvent(eventName, eventType);
                    break;
                case 5:
                    System.out.println("请输入要删除的比赛项目ID：");
                    long eventId = scanner.nextLong();
                    adminService.deleteEvent(eventId);
                    break;
                case 6:
                    System.out.println("请输入要更新的比赛项目ID：");
                    eventId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("请输入新的比赛项目名称：");
                    eventName = scanner.nextLine();
                    System.out.println("请输入新的比赛项目类型：");
                    eventType = scanner.nextLine();
                    adminService.updateEvent(eventId, eventName, eventType);
                    break;
                case 7:
                    System.out.println("请输入运动员ID：");
                    long athleteId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("请输入运动员姓名：");
                    String athleteName = scanner.nextLine();
                    System.out.println("请输入学院ID：");
                    long collegeIdInput = scanner.nextLong();
                    adminService.manageAthlete(athleteId, athleteName, collegeIdInput);
                    break;
                case 8:
                    System.out.println("请输入要删除的运动员ID：");
                    athleteId = scanner.nextLong();
                    adminService.deleteAthlete(athleteId);
                    break;
                case 9:
                    System.out.println("请输入运动员编号：");
                    athleteId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.println("请输入运动员姓名：");
                    athleteName = scanner.nextLine();
                    System.out.println("请输入学院ID：");
                    collegeIdInput = scanner.nextLong();
                    adminService.addAthlete(athleteId, athleteName, collegeIdInput);
                    break;
                case 10:
                    return;
                default:
                    System.out.println("无效的选择，请重新选择。");
            }
        }
    }

    private static void athleteMenu(Scanner scanner) throws SQLException {
        AthleteService athleteService = new AthleteService();
        while (true) {
            System.out.println("\n运动员菜单：");
            System.out.println("1. 查看所在学院信息");
            System.out.println("2. 查看参加的比赛项目及成绩");
            System.out.println("3. 退出");
            System.out.print("请选择操作：");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("请输入运动员ID：");
                    long athleteId = scanner.nextLong();
                    athleteService.viewCollegeInfo(athleteId);
                    break;
                case 2:
                    System.out.println("请输入运动员ID：");
                    athleteId = scanner.nextLong();
                    athleteService.viewAthleteEvents(athleteId);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("无效的选择，请重新选择。");
            }
        }
    }

    private static void judgeMenu(Scanner scanner) throws SQLException {
        JudgeService judgeService = new JudgeService();
        while (true) {
            System.out.println("\n裁判员菜单：");
            System.out.println("1. 记录运动员成绩");
            System.out.println("2. 查看运动员比赛成绩");
            System.out.println("3. 查看各学院总积分和名次");
            System.out.println("4. 退出");
            System.out.print("请选择操作：");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("请输入运动员ID：");
                    long athleteId = scanner.nextLong();
                    System.out.println("请输入比赛项目ID：");
                    long eventId = scanner.nextLong();
                    System.out.println("请输入成绩：");
                    float score = scanner.nextFloat();
                    System.out.println("请输入积分：");
                    int points = scanner.nextInt();
                    judgeService.recordPerformance(athleteId, eventId, score, points);
                    break;
                case 2:
                    System.out.println("请输入运动员ID：");
                    athleteId = scanner.nextLong();
                    judgeService.viewAthletePerformance(athleteId);
                    break;
                case 3:
                    judgeService.viewCollegeScores();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("无效的选择，请重新选择。");
            }
        }
    }
}
