package edu.univ.erp.auth;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class AUTH_DB_Connection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/auth_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = System.getenv().getOrDefault("AUTH_DB_USERNAME", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("AUTH_DB_PASSWORD", "thook");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Create triggers for AUTH database
    public static void createTriggers() {
        createUserLogTrigger();
        createUserUpdateTrigger();
    }

    private static void createUserLogTrigger() {
        String sql = "CREATE TABLE IF NOT EXISTS user_log (" +
                    "log_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT, " +
                    "action VARCHAR(50), " +
                    "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
        executeSQL(sql, "User log table");

        String triggerSQL = "CREATE TRIGGER IF NOT EXISTS after_user_insert " +
                           "AFTER INSERT ON users_auth " +
                           "FOR EACH ROW " +
                           "INSERT INTO user_log (user_id, action) VALUES (NEW.user_id, 'USER_CREATED')";
        executeSQL(triggerSQL, "User insert trigger");
    }

    private static void createUserUpdateTrigger() {
        String triggerSQL = "CREATE TRIGGER IF NOT EXISTS after_user_update " +
                           "AFTER UPDATE ON users_auth " +
                           "FOR EACH ROW " +
                           "INSERT INTO user_log (user_id, action) VALUES (NEW.user_id, 'USER_UPDATED')";
        executeSQL(triggerSQL, "User update trigger");
    }

    private static void executeSQL(String sql, String description) {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✓ " + description + " created/verified");
        } catch (SQLException e) {
            System.err.println("✗ Failed to create " + description + ": " + e.getMessage());
        }
    }

    public int createUser(String username, String role, String passwordHash,String FirstName , String LastName) {
        String sql = "INSERT INTO users_auth (username, role, password_hash, status, last_login) VALUES (?, ?, ?, ?, ?)";

        int generatedId = -1;

        try (Connection conn = AUTH_DB_Connection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            pstmt.setString(1, username);
            pstmt.setString(2, role);
            pstmt.setString(3, passwordHash);
            pstmt.setString(4, "Active");
            pstmt.setTimestamp(5, currentTimestamp);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        System.out.println("New user '" + username + "' created with ID: " + generatedId);
                        
                        // If student, auto-create student profile
                        if (role.toLowerCase().equals("student")) {
                            createStudentProfile(generatedId, username,FirstName,LastName);
                        }
                        else if (role.toLowerCase().equals("instructor")) {
                            try (Scanner scanner = new Scanner(System.in)) { // Create scanner object with try-with-resources
                                System.out.print("Department:- ");         // Prompt the user
                                String Department = scanner.nextLine();   // Read user input
                                createinstructorsprofile(generatedId, username, Department, FirstName, LastName);
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error during user creation for " + username + ": " + e.getMessage());
        }
        return generatedId;
    }

    private void createStudentProfile(int userId, String username,String FirstName,String LastName) {
        try (Connection conn = getConnection()) {
            // Create student profile with username as roll_no and default values
            String sql = "INSERT INTO erp_db.students (user_id, roll_no, FirstName, LastName, program, inroll_year) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, username); // Use username as roll_no
                ps.setString(3, FirstName); // Use provided first name
                ps.setString(4, LastName); // Use provided last name
                ps.setString(5, "General"); // Default program
                ps.setInt(6, 2025); // Default enrollment year
                ps.executeUpdate();
                System.out.println("Student profile created for user ID: " + userId);
            }
        } catch (SQLException e) {
            System.err.println("Failed to create student profile: " + e.getMessage());
        }

    }
    private void createinstructorsprofile(int userId, String username,String Department ,String FirstName,String LastName) {
        try (Connection Insconn = getConnection()){
            String sql = "Insert into erp_db.instructors (user_id, Department ,first_name, last_name) VALUES (?, ?, ?, ?)";
            try(PreparedStatement pstmt = Insconn.prepareStatement(sql)){
                pstmt.setInt(1, userId);
                pstmt.setString(2, Department);
                pstmt.setString(3, FirstName);
                pstmt.setString(4, LastName);
                pstmt.executeUpdate();
                System.out.println("Instructors profile created for user ID: " + userId);
            }
        }
        catch (SQLException e){
            System.err.println("Failed to create instructors for user ID: " + username);
        }
    }

}