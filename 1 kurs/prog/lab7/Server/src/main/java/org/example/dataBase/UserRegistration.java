package org.example.dataBase;

import org.example.dataBase.utils.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserRegistration {
    private static Connection conn;

    public UserRegistration (Connection conn) {
        this.conn = ConnectionManager.open();
    }


    public static void registerUser(String username, String password, Connection connection) throws SQLException {

        String hashedPassword;
        try {
            hashedPassword = PasswordUtil.hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Ошибка хеширования пароля: " + e.getMessage());
            return;
        }

        String sql = "INSERT INTO users (login, password) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
        }
    }

    private static boolean isUserRegistered(String username, Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке пользователя: " + e.getMessage());
        }
        return false;
    }

    public static boolean haveRegisterUser(String username, Connection connection) throws SQLException {
        // Проверка, зарегистрирован ли пользователь
        if (isUserRegistered(username, connection)) {
            return true;
        }
        return false;
    }
}