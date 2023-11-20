package doa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import databaseConnection.DatabaseConnection;
import models.HOD;

public class HODDAO {

	public List<HOD> getAllHODs() {
	    List<HOD> hods = new ArrayList<>();
	    String sql = "SELECT * FROM hods"; 

	    try (Connection connection = DatabaseConnection.getConnection();
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {

	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            String name = resultSet.getString("name");
	            String password = resultSet.getString("password");

	            HOD hod = new HOD(id, name, password);
	            hods.add(hod);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return hods;
	}
    // Create or Add new HOD
    public void addHOD(HOD hod) throws SQLException {
        String query = "INSERT INTO hods (name, password) VALUES (?, ?)";  
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, hod.getName());
            preparedStatement.setString(2, hod.getPassword());
            preparedStatement.executeUpdate();
        }
    }

    // Retrieve a single HOD by ID 
    public HOD getHODById(int id) throws SQLException {
        String query = "SELECT * FROM hods WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                return new HOD(id, name, password);
            }
        }
        return null;
    }

    // Update HOD details
    public void updateHOD(HOD hod) throws SQLException {
        String query = "UPDATE hods SET name = ?, password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, hod.getName());
            preparedStatement.setString(2, hod.getPassword());
            preparedStatement.setInt(3, hod.getId());
            preparedStatement.executeUpdate();
        }
    }

    // Delete HOD by ID
    public void deleteHOD(int id) throws SQLException {
        String query = "DELETE FROM hods WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }


    // Authenticate HOD using name and password
    public boolean authenticateHOD(String name, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM hods WHERE name = ? AND password = ?";  // NOTE: In real-world scenarios, use hashed passwords.
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
        return false;
    }
}
