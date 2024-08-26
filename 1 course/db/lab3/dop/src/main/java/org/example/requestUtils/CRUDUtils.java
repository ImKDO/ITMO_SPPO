package org.example.requestUtils;

import java.sql.*;


public class CRUDUtils {

    //Работа с данными о погоде
    public static double saveWeatherData(String city, double temp, int days) {
        String sql = "INSERT INTO weather (city, temperature, date) VALUES (?, ?, current_date) " +
                "ON CONFLICT (city, date) DO UPDATE SET temperature = EXCLUDED.temperature";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            //Записываем в бд данные
            pstmt.setString(1, city);
            pstmt.setDouble(2, temp / days);
            pstmt.executeUpdate();
            //Возвращаем результат, чтобы пользователь мог увидеть что хочет
            return temp / days;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Дроп триггера с функцией
    public static void dropTriggerAndFunction() {
        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()) {

            // Удаление триггера
            String dropTriggerSql = "DROP TRIGGER IF EXISTS weather_data_trigger ON weather;";
            statement.executeUpdate(dropTriggerSql);

            // Удаление функции
            String dropFunctionSql = "DROP FUNCTION IF EXISTS update_weather_data;";
            statement.executeUpdate(dropFunctionSql);

            System.out.println("Триггер и функция успешно удалены.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //Создание триггера с функцией
    public static void createTriggerAndFunction() {
        try (Connection connection = DBUtils.getConnection()) {
            dropTriggerAndFunction();
            createFunction();
            createTrigger();
            System.out.println("Триггер и функция успешно созданы.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Метод для создания функции
    public static void createFunction() throws SQLException {

        String functionSql = "CREATE OR REPLACE FUNCTION update_weather_data()\n" +
                "RETURNS TRIGGER AS $$\n" +
                "BEGIN\n" +
                "    IF EXISTS (SELECT 1 FROM weather WHERE city = NEW.city AND date = NEW.date) THEN\n" +
                "        UPDATE weather\n" +
                "        SET temperature = NEW.temperature\n" +
                "        WHERE city = NEW.city AND date = NEW.date;\n" +
                "        RETURN NULL;  -- Прерываем выполнение триггера после обновления\n" +
                "    ELSE\n" +
                "        RETURN NEW;  -- Если записи нет, продолжаем вставку новой строки\n" +
                "    END IF;\n" +
                "END;\n" +
                "$$ LANGUAGE plpgsql;";

        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(functionSql);
        }
    }

    //Метод для создания триггера
    public static void createTrigger() throws SQLException {
        String triggerSql = "CREATE TRIGGER weather_data_trigger\n" +
                "BEFORE INSERT ON weather\n" +
                "FOR EACH ROW\n" +
                "EXECUTE FUNCTION update_weather_data();";

        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(triggerSql);
        }
    }
}

