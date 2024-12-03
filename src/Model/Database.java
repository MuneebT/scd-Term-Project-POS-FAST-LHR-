package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/firstdb";
    private static final String USER = "root";
    private static final String PASS = "";

    // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Method to validate if the user exists in the logintable
    public static boolean validateUser(String username, String password,String designation) throws SQLException {
        String query = "SELECT * FROM logintable WHERE name = ? AND password = ? AND designation= ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3,designation);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Return true if the user exists
        }
    }

    // Method to insert a new user into the Users table
    public static int insertUser(String username, String password) throws SQLException {
        String query = "INSERT INTO logintable (name, password) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate(); // Return the number of rows affected
        }
    }

    // Method to retrieve a user's profile by username
    public static ResultSet getUserProfile(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE Username = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        return ps.executeQuery(); // Return the result set of the user's profile
    }

    // Method to insert a message with sender and recipient usernames instead of IDs
    public static int insertMessage(String senderName, String recipientName, String messageContent) throws SQLException {
        String query = "INSERT INTO messages (sender, recipient, message_content) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, senderName);
            ps.setString(2, recipientName);
            ps.setString(3, messageContent);
            return ps.executeUpdate(); // Return the number of rows affected
        }
    }

    // Method to retrieve all messages sent to a specific user (Inbox)
    public static ResultSet getUserInbox(String recipientName) throws SQLException {
        String query = "SELECT sender_name, message_content, created_at FROM messages WHERE recipient_name = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, recipientName);
        return ps.executeQuery(); // Return the result set of messages received by the user
    }

    // Method to retrieve all messages sent by a specific user (Sent Box)
    public static ResultSet getUserSentMessages(String senderName) throws SQLException {
        String query = "SELECT recipient_name, message_content, created_at FROM messages WHERE sender_name = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, senderName);
        return ps.executeQuery(); // Return the result set of messages sent by the user
    }

    // Method to fetch notifications for a specific user by user ID
    public static ResultSet getUserNotifications(int userId) throws SQLException {
        String query = "SELECT * FROM Notifications WHERE UserID = ?";
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        return ps.executeQuery(); // Return the result set of notifications for the user
    }

    // Method to get a user's ID by their username (for internal use, such as notifications)
    public static int getUserId(String username) throws SQLException {
        String query = "SELECT ID FROM Users WHERE Username = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID"); // Return the user ID if found
            } else {
                return -1; // Return -1 if the user is not found
            }
        }
    }
}
