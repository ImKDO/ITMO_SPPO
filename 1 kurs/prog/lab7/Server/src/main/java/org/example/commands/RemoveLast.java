package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Команда remove_last - удаляет последний элемент в коллекции
 */
public class RemoveLast extends Command {
    private final Console console;
    private CollectionManager collectionManager;
    private Connection connection;

    public RemoveLast(Console console, CollectionManager collectionManager, Connection connection) {
        super("remove_last", "удаляет элемент коллекции по id");
        this.console = console;
        this.collectionManager = collectionManager;
        this.connection = connection;
    }

    /**
     * Выполнение команды remove_last
     *
     * @param list Аргумент для выполнения
     * @return
     */
    public ExecutionResponse apply(List<Object> list) {
        String sql = "SELECT id_object FROM collections WHERE user_fk = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String login = (String) list.get(3);

            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            int id_last = 0;
            for (int i = 1; rs.next(); i++) {
                id_last = rs.getInt(1);
            }
            int count = 0;
            for (int i = 0; i < collectionManager.getCollection().size(); ++i) {
                if (collectionManager.getCollection().get(i).getId() == id_last & collectionManager.getCollection().get(i).getUser().equals(login)) {
                    collectionManager.getCollection().remove(i);
                    console.println(id_last);
                    ++count;
                }

                if (count == 1) {
                    return new ExecutionResponse("HumanBeing с id " + id_last + " удален");
                } else {
                    return new ExecutionResponse(false, "Не существующий ID, для " + login);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse("");
    }
}