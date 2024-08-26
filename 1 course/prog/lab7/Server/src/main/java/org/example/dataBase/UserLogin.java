package org.example.dataBase;

import com.sun.tools.jconsole.JConsoleContext;
import org.example.dataBase.utils.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    /**
     * Чекаем, если ли такой пользователь в базе данных
     * @param username
     * @param connection
     * @return
     * @throws SQLException
     */
//    private static boolean checkLogin(String username, Connection connection) throws SQLException {
//        //SQL запрос
//        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";
//        //Проверка наличия
//
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, username);
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    int count = rs.getInt(1);
//                    return count > 0 ;
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
//        }
//        return false;
//    }

    /**
     * Метод для проверки пароля
     * @param username
     * @param password
     * @param connection
     * @return
     * @throws SQLException
     */
    private static boolean checkPasswordAndLogin(String username, String password, Connection connection) throws SQLException {

        String hashedPassword;
        String sql = "SELECT COUNT(*) FROM users WHERE login = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            //Хешируем пароль
            hashedPassword = PasswordUtil.hashPassword(password);
            //Проставляем параметры для запроса
            pstmt.setString(1, username);
            pstmt.setString(2, hashedPassword);
            //Смотрим, есть ли такой логин + пароль
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0 ;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Ошибка хеширования пароля: " + e.getMessage());
        }
        return false;
    }

    /**
     * Метод для входа пользователя в программу
     * @param username
     * @param password
     * @param connection
     * @throws SQLException
     */
    public static boolean userLogin(String username, String password, Connection connection) throws SQLException {

        return checkPasswordAndLogin(username, password, connection);
    }
}
