package edu.univ.erp.util;

import edu.univ.erp.auth.AUTH_DB_Connection;
import edu.univ.erp.data.ERP_DB_Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTest {
    
    public static void main(String[] args) {
        System.out.println("=== ERP Portal Database Test ===");
        
        // Initialize connections
        AUTH_DB_Connection authConn = new AUTH_DB_Connection();
        ERP_DB_Connection erpConn = new ERP_DB_Connection();
        
        // Create triggers (tables should be created in MySQL Workbench first)
        System.out.println("\n1. Creating triggers...");
        AUTH_DB_Connection.createTriggers();
        
        // Test 1: Create Instructor
        System.out.println("\n2. Testing Instructor creation...");
        int instructorId = authConn.createUser("instructor1", "instructor", "password", "Dr. John", "Smith");
        System.out.println("✓ Created instructor with ID: " + instructorId);
        
        // Test 2: Create Student
        System.out.println("\n3. Testing Student creation...");
        int studentId = authConn.createUser("student1", "student", "password", "Alice", "Johnson");
        System.out.println("✓ Created student with ID: " + studentId);
        
        // Test 3: Add Course
        System.out.println("\n4. Testing Course creation...");
        int courseResult = erpConn.addCourse("CS101", "Introduction to Programming", 3, instructorId);
        System.out.println("✓ Course creation result: " + courseResult);
        
        // Test 4: Enroll Student
        System.out.println("\n5. Testing Student enrollment...");
        int enrollmentResult = erpConn.enrollStudent(studentId, 1); // Assuming course_id = 1
        System.out.println("✓ Enrollment result: " + enrollmentResult);
        
        // Test 5: Add Grade
        System.out.println("\n6. Testing Grade addition...");
        int gradeResult = erpConn.addGrade(studentId, 1, "A+", "Fall 2025");
        System.out.println("✓ Grade addition result: " + gradeResult);
        
        // Test 6: Update Setting
        System.out.println("\n7. Testing Settings...");
        int settingResult = erpConn.updateSetting("max_enrollments", "5");
        System.out.println("✓ Setting update result: " + settingResult);
        
        // Test 7: Verify data
        System.out.println("\n8. Verifying data...");
        try {
            ResultSet instructor = erpConn.getInstructor(instructorId);
            if (instructor.next()) {
                System.out.println("✓ Instructor found: " + instructor.getString("first_name") + " " + instructor.getString("last_name"));
            }
            
            ResultSet student = erpConn.getStudent(studentId);
            if (student.next()) {
                System.out.println("✓ Student found: " + student.getString("FirstName") + " " + student.getString("LastName"));
            }
            
            ResultSet grades = erpConn.getStudentGrades(studentId);
            if (grades.next()) {
                System.out.println("✓ Grade found: " + grades.getString("grade") + " in " + grades.getString("course_name"));
            }
            
            String maxEnrollments = erpConn.getSetting("max_enrollments");
            System.out.println("✓ Setting retrieved: max_enrollments = " + maxEnrollments);
            
        } catch (SQLException e) {
            System.err.println("✗ Error verifying data: " + e.getMessage());
        }
        
        System.out.println("\n=== Test Complete ===");
    }
}

