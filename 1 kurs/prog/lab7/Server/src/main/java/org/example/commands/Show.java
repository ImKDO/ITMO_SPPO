package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.Car;
import org.example.models.Coordinates;
import org.example.models.Mood;
import org.example.models.WeaponType;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * "show" - вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */

public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
private final Statement statement;

    public Show(Console console, CollectionManager collectionManager, Statement statement) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
        this.statement = statement;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(List<Object> list) {

        String sql = """
                SELECT * FROM collections
                """;
        StringBuilder response = new StringBuilder();
        try {
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                String user_fk = rs.getString("user_fk");
                int id_object = rs.getInt("id_object");
                String name = rs.getString("name");
                Coordinates coordinates = new Coordinates(rs.getLong("x"), rs.getDouble("y"));
                LocalDateTime creationdate = rs.getTimestamp("creationdate").toLocalDateTime();
                boolean realHero = rs.getBoolean("realhero");
                Boolean hastoothpick = rs.getBoolean("hastoothpick");
                double impactspeed = rs.getDouble("impactspeed");
                String soundtrackname = rs.getString("soundtrackname");
                WeaponType weaponType = (WeaponType) rs.getObject("weapontype");
                Object mood = rs.getObject("mood");
                Car car = new Car(rs.getString("car_name"),rs.getBoolean("car_cool"));


                response.append("HumanBeing{"  + "\"user\" : \"" + user_fk + "\"" +
                                " \"id\": " + id_object + ", " +
                                "\"name\": \"" + name + "\", " +
                                "\"coordinates\": \"" + coordinates + "\", " +
                                "\"creationDate:\"" + creationdate + ", " +
                                "\"realHero\": \"" + realHero + "\", " +
                                "\"hasToothpick\": \"" + hastoothpick + "\", " +
                                "\"impactSpeed\": \"" + impactspeed + "\", " +
                                "\"soundtrackName\": \"" + soundtrackname + "\", " +
                                "\"weaponType\": " + (weaponType == null ? "null" : "\"" + weaponType + "\"") + "\", " +
                                "\"mood\": \"" + mood + "\", " +
                                "\"car\": \"" + car + "}" + "\n\n");
            }
            return new ExecutionResponse(collectionManager.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
