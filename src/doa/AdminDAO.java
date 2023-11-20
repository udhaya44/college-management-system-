package doa;

import java.sql.*;

import databaseConnection.DatabaseConnection;
import models.Admin;

public class AdminDAO {

    // Create or Add new Admin
    public void addAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO admins (username, password) VALUES (?, ?)"; 
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.executeUpdate();
        }
    }

    // Retrieve Admin by username (Useful for authentication)
    public Admin getAdminByUsername(String username) throws SQLException {
        String query = "SELECT * FROM admins WHERE username = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String password = resultSet.getString("password");
                return new Admin(id, username, password);
            }
        }
        return null;
    }

    // Update Admin details
    public void updateAdmin(Admin admin) throws SQLException {
        String query = "UPDATE admins SET username = ?, password = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setInt(3, admin.getId());
            preparedStatement.executeUpdate();
        }
    }

    // Delete Admin by ID
    public void deleteAdmin(int id) throws SQLException {
        String query = "DELETE FROM admins WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    // Authenticate Admin using username and password
    public boolean authenticateAdmin(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM admins WHERE username = ? AND password = ?";  // NOTE: Use hashed passwords in real-world scenarios.
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
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
