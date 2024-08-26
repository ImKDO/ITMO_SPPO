package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.HumanBeing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *  "remove_by_id id" - удалить элемент по id
 */
public class RemoveById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Connection connection;

    public RemoveById(Console console, CollectionManager collectionManager, Connection connection) {
        super("remove_by_id id", "удалить элемент из коллекции по ID");
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
        if (list.get(1).toString().isEmpty())
            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        String query = "DELETE FROM collections WHERE id_object = ? AND user_fk = ?";

        int id_object = Integer.parseInt( (String) list.get(1));
        String user = (String) list.get(3);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id_object);
            preparedStatement.setString(2,user);


            preparedStatement.executeUpdate();


            int count = 0;
            for(int i = 0; i < collectionManager.getCollection().size(); ++i){
                if(collectionManager.getCollection().get(i).getId() == id_object & collectionManager.getCollection().get(i).getUser().equals(user)){
                    collectionManager.getCollection().remove(i);
                    ++count;
                }
            }
            if (count == 1){
                return new ExecutionResponse("HumanBeing с id " + id_object + " удален");
            } else {
                return new ExecutionResponse(false, "Не существующий ID, для " + user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ExecutionResponse("");
    }
}
