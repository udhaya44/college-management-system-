package doa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import databaseConnection.DatabaseConnection;
import models.Student;

public class StudentDAO {
    // Create or Add new student
    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (name, age, gender, city, branch, subject1, subject2, subject3, subject4, subject5) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getGender());
            preparedStatement.setString(4, student.getCity());
            preparedStatement.setString(5, student.getBranch());
            for (int i = 0; i < 5; i++) {
                preparedStatement.setInt(6 + i, student.getMarks()[i]);
            }
            preparedStatement.executeUpdate();
        }
    }

    // Retrieve students by branch
    public List<Student> getStudentsByBranch(String branch) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE branch = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branch);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String city = resultSet.getString("city");
                int[] marks = new int[5];
                for (int i = 0; i < 5; i++) {
                    marks[i] = resultSet.getInt("subject" + (i + 1));
                }
                Student student = new Student(id, name, age, gender, city, branch, marks);
                students.add(student);
            }
        }
        return students;
    }

    // Update student details
    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET name = ?, age = ?, gender = ?, city = ?, branch = ?, subject1 = ?, subject2 = ?, subject3 = ?, subject4 = ?, subject5 = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setString(3, student.getGender());
            preparedStatement.setString(4, student.getCity());
            preparedStatement.setString(5, student.getBranch());
            for (int i = 0; i < 5; i++) {
                preparedStatement.setInt(6 + i, student.getMarks()[i]);
            }
            preparedStatement.setInt(11, student.getId());
            preparedStatement.executeUpdate();
        }
    }

    // Delete student by ID
    public void deleteStudent(int id) throws SQLException {
        String query = "DELETE FROM students WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    // Retrieve a single student by ID (Useful for updates or specific views)
    public Student getStudentById(int id) throws SQLException {
        String query = "SELECT * FROM students WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String city = resultSet.getString("city");
                String branch = resultSet.getString("branch");
                int[] marks = new int[5];
                for (int i = 0; i < 5; i++) {
                    marks[i] = resultSet.getInt("subject" + (i + 1));
                }
                return new Student(id, name, age, gender, city, branch, marks);
            }
        }
        return null;
    }

}
