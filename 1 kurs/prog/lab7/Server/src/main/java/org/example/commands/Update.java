package org.example.commands;


import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.Coordinates;
import org.example.models.HumanBeing;
import org.example.models.Mood;

import java.sql.*;
import java.util.Collection;
import java.util.List;

/**
 * "update id" - обновить значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Connection connection;

    public Update(Console console, CollectionManager collectionManager, Connection connection) {
        super("update id {element}", "Обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
        this.connection = connection;
    }

    public ExecutionResponse apply(List<Object> list) {

        String sqlCheck="SELECT COUNT(id_object) FROM collections " +
                        "WHERE id_object = ? AND user_fk = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sqlCheck)) {
            int id_object = Integer.parseInt((String) list.get(1));
            String user_fk = (String) list.get(3);

            pstmt.setInt(1,id_object);
            pstmt.setString(2, user_fk);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                if(id == 0){
                    return new ExecutionResponse("HumanBeing с id " + id_object + " для пользователя " + user_fk + " не существует!");
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "UPDATE collections SET" +
                     " name = ?," +
                     " x = ?," +
                     " y = ?," +
                     " creationdate = ?, " +
                     "realhero = ?," +
                     " hastoothpick = ?, " +
                     "impactspeed = ?, " +
                     "soundtrackname = ?," +
                     " weapontype = ?," +
                     " mood = ?," +
                     " car_name = ?, " +
                     "car_cool = ? " +
                     "WHERE user_fk = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            HumanBeing humanBeing = (HumanBeing) list.get(2);
            String login = (String) list.get(3);
            humanBeing.setUser(login);

            pstmt.setString(1, humanBeing.getName());
            humanBeing.setName(humanBeing.getName());

            pstmt.setLong(2, humanBeing.getCoordinates().getX());
            pstmt.setDouble(3, humanBeing.getCoordinates().getY());
            Coordinates coordinates = humanBeing.getCoordinates();
            humanBeing.setCoordinates(coordinates);

            pstmt.setTimestamp(4, Timestamp.valueOf(humanBeing.getCreationDate()));
            humanBeing.setCreationDate(humanBeing.getCreationDate());

            pstmt.setBoolean(5, humanBeing.getRealHero());
            pstmt.setObject(6, humanBeing.getHasToothpick());
            pstmt.setDouble(7, humanBeing.getImpactSpeed());
            pstmt.setString(8, humanBeing.getSoundtrackName());
            pstmt.setObject(9, humanBeing.getWeaponType());
            Mood mood = humanBeing.getMood();
            pstmt.setObject(10, mood,java.sql.Types.OTHER);
            pstmt.setString(11, humanBeing.getCar().getName());
            pstmt.setBoolean(12, humanBeing.getCar().getCool());
            pstmt.setString(13, login);

            pstmt.executeUpdate();

            String sqlId = "SELECT id_object FROM collections WHERE user_fk=?";
            try (PreparedStatement pstmt2 = connection.prepareStatement(sqlId)) {
                pstmt2.setString(1, login);
                ResultSet rs = pstmt2.executeQuery();
                while (rs.next()){
                    int id_object = rs.getInt(1);
                    for(HumanBeing e: collectionManager.getCollection()){
                        if(e.getId() == id_object){
                            e.setName(humanBeing.getName());
                            e.setCoordinates(humanBeing.getCoordinates());
                            e.setCreationDate(humanBeing.getCreationDate());
                            e.setRealHero(humanBeing.getRealHero());
                            e.setHasToothpick(humanBeing.getHasToothpick());
                            e.setImpactSpeed(humanBeing.getImpactSpeed());
                            e.setSoundtrackName(humanBeing.getSoundtrackName());
                            e.setWeaponType(humanBeing.getWeaponType());
                            e.setMood(humanBeing.getMood());
                            e.setCar(humanBeing.getCar());
                        }
                    }

                }

            }


            return new ExecutionResponse("HumanBeing успешно Обновлен!");
        } catch (SQLException e) {
            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
        }
        return new ExecutionResponse("HumanBeing успешно Обновлен!");
    }
}
