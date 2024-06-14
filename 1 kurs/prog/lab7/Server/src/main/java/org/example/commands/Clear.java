package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

            collectionManager.clearCollection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ExecutionResponse("Коллекция успешно очищена");
    }
}
