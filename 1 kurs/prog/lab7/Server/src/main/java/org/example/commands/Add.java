package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.models.HumanBeing;
import org.example.models.Mood;

import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 'add'- добавить новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Connection connection;


    public Add(Console console, CollectionManager collectionManager, Connection connection) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
        this.connection = connection;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(List<Object> list) {
        String sql = "INSERT INTO collections (user_fk,name,x,y,creationdate,realhero,hastoothpick,impactspeed,soundtrackname,weapontype,mood,car_name,car_cool)" +
                     " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            HumanBeing humanBeing = (HumanBeing) list.get(2);
            String login = (String) list.get(3);
            pstmt.setString(1, login);
            pstmt.setString(2, humanBeing.getName());
            pstmt.setLong(3, humanBeing.getCoordinates().getX());
            pstmt.setDouble(4, humanBeing.getCoordinates().getY());
            pstmt.setTimestamp(5, Timestamp.valueOf(humanBeing.getCreationDate()));
            pstmt.setBoolean(6, humanBeing.getRealHero());
            pstmt.setObject(7, humanBeing.getHasToothpick());
            pstmt.setDouble(8, humanBeing.getImpactSpeed());
            pstmt.setString(9, humanBeing.getSoundtrackName());
            pstmt.setObject(10, humanBeing.getWeaponType());
            Mood mood = humanBeing.getMood();
            pstmt.setObject(11, mood,java.sql.Types.OTHER);
            pstmt.setString(12, humanBeing.getCar().getName());
            pstmt.setBoolean(13, humanBeing.getCar().getCool());

            pstmt.executeUpdate();

            String sqlId = "SELECT id_object FROM collections WHERE user_fk=?";
            try (PreparedStatement pstmt2 = connection.prepareStatement(sqlId)) {
                pstmt2.setString(1, login);
                ResultSet rs = pstmt2.executeQuery();
                while (rs.next()){
                    int id_object = rs.getInt(1);
                    humanBeing.setId(id_object);
                    console.println(humanBeing);
                    collectionManager.add(humanBeing);

                }

            }


            return new ExecutionResponse("HumanBeing успешно добавлен!");
        } catch (SQLException e) {
            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
        }
        return new ExecutionResponse("HumanBeing успешно добавлен!");
    }
}
