package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;
import org.example.models.HumanBeing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  "clear" - очистить коллекцию
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Connection connection;


    public Clear(Console console, CollectionManager collectionManager, Connection connection) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
        this.connection = connection;
    }
    @Override
    public ExecutionResponse apply(List<Object> list) {

        String clearCollectionSQL = "DELETE FROM collections WHERE user_fk = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(clearCollectionSQL)){
            String login = (String) list.get(3);
            pstmt.setString(1, login);

            pstmt.executeUpdate();

            List<HumanBeing> filteredCollection = collectionManager.getCollection().stream()
                    .filter(human -> !human.getUser().equals(login))
                    .collect(Collectors.toList());


            collectionManager.clearCollection();
            collectionManager.getCollection().addAll(filteredCollection);


            console.println(collectionManager.getCollection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse("Коллекция успешно очищена");
    }
}
