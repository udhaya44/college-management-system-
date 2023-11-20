package doa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import databaseConnection.DatabaseConnection;
import models.Teacher;

public class TeacherDAO {

	public List<Teacher> getAllTeachers() {
	    List<Teacher> teachers = new ArrayList<>();
	    String sql = "SELECT * FROM teachers"; 

	    try (Connection connection = DatabaseConnection.getConnection();
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {

	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String name = resultSet.getString("name");
	            String branch = resultSet.getString("branch");

	            Teacher teacher = new Teacher(id, name, branch);
	            teachers.add(teacher);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return teachers;
	}
    // Create or Add new teacher
    public void addTeacher(Teacher teacher) throws SQLException {
        String query = "INSERT INTO teachers (name, branch) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getBranch());
            preparedStatement.executeUpdate();
        }
    }

    // Retrieve all teachers by branch
    public List<Teacher> getTeachersByBranch(String branch) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM teachers WHERE branch = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branch);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Teacher teacher = new Teacher(id, name, branch);
                teachers.add(teacher);
            }
        }
        return teachers;
    }

    // Retrieve a single teacher by ID (Useful for updates or specific views)
    public Teacher getTeacherById(int id) throws SQLException {
        String query = "SELECT * FROM teachers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String branch = resultSet.getString("branch");
                return new Teacher(id, name, branch);
            }
        }
        return null;
    }

    // Update teacher details
    public void updateTeacher(Teacher teacher) throws SQLException {
        String query = "UPDATE teachers SET name = ?, branch = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setString(2, teacher.getBranch());
            preparedStatement.setInt(3, teacher.getId());
            preparedStatement.executeUpdate();
        }
    }

    // Delete teacher by ID
    public void deleteTeacher(int id) throws SQLException {
        String query = "DELETE FROM teachers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
    // Authenticate teacher using name and branch
    public boolean authenticateTeacher(String name, String branch) throws SQLException {
        String query = "SELECT COUNT(*) FROM teachers WHERE name = ? AND branch = ?"; 
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, branch);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }

}
