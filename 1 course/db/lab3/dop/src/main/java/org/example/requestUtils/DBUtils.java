package org.example.requestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtils {

    private static final String URL = "jdbc:postgresql://localhost:5432/studs";

    // Создаем Properties с указанием имени пользователя и пароля
    private static final Properties PROPS = new Properties();

    static {
        PROPS.setProperty("user", "s408891");
        PROPS.setProperty("password", "HlsshGiugV5LnUn9");
        PROPS.setProperty("ssl", "false");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, PROPS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
