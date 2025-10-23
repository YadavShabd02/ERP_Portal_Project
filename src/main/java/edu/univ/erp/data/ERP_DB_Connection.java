package edu.univ.erp.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ERP_DB_Connection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/erp_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = System.getenv().getOrDefault("ERP_DB_USERNAME", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("ERP_DB_PASSWORD", "thook");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public int addStudent(int userId, String rollNo, String Firstname, String LastName,String program, int enrollYear) {
        String sql = "INSERT INTO students (user_id, roll_no,FirstName,LastName, program, inroll_year) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, rollNo);
            ps.setString(3, Firstname);
            ps.setString(4, LastName);
            ps.setString(5, program);
            ps.setInt(6, enrollYear);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add student: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet getStudent(int userId) throws SQLException {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps.executeQuery();
    }
    public int addInstructor(int userinstID, String Department ,String FirstName, String LastName ) throws SQLException {
        String sql = "Insert into Instructors (user_id, Department, First_Name, Last_Name) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userinstID);
            ps.setString(2, Department);
            ps.setString(3, FirstName);
            ps.setString(4, LastName);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println("Failed to add instructor: " + e.getMessage());
            return 0;
        }
    }
    public ResultSet getInstructor(int userId) throws SQLException {
        String sql = "SELECT * FROM instructors WHERE user_id = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps.executeQuery();
    }

    // Course methods
    public int addCourse(String courseCode, String courseName, int credits, int instructorId) {
        String sql = "INSERT INTO courses (course_code, course_name, credits, instructor_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseCode);
            ps.setString(2, courseName);
            ps.setInt(3, credits);
            ps.setInt(4, instructorId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add course: " + e.getMessage());
            return 0;
        }
    }

    // Enrollment methods
    public int enrollStudent(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to enroll student: " + e.getMessage());
            return 0;
        }
    }

    // Grades methods
    public int addGrade(int studentId, int courseId, String grade, String semester) {
        String sql = "INSERT INTO grades (student_id, course_id, grade, semester, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setString(3, grade);
            ps.setString(4, semester);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add grade: " + e.getMessage());
            return 0;
        }
    }

    public ResultSet getStudentGrades(int studentId) throws SQLException {
        String sql = "SELECT g.*, c.course_name FROM grades g JOIN courses c ON g.course_id = c.course_id WHERE g.student_id = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, studentId);
        return ps.executeQuery();
    }

    // Settings methods
    public int updateSetting(String settingKey, String settingValue) {
        String sql = "INSERT INTO settings (setting_key, setting_value, updated_at) VALUES (?, ?, NOW()) ON DUPLICATE KEY UPDATE setting_value = VALUES(setting_value), updated_at = NOW()";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, settingKey);
            ps.setString(2, settingValue);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update setting: " + e.getMessage());
            return 0;
        }
    }

    public String getSetting(String settingKey) throws SQLException {
        String sql = "SELECT setting_value FROM settings WHERE setting_key = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, settingKey);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("setting_value");
            }
            return null;
        }
    }
}