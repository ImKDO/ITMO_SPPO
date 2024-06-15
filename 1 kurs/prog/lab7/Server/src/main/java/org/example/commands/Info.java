package org.example.commands;

import org.example.Collection.ExecutionResponse;
import org.example.console.Console;
import org.example.managers.CollectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * "info" - Вывести информацию о коллекции
 */

public class Info extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Connection connection;

    public Info(Console console, CollectionManager collectionManager, Connection connection) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.connection = connection;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public ExecutionResponse apply(List<Object> list) {


//        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
//        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
//                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
//
//        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
//        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
//                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();


    String sql = "SELECT COUNT(*) FROM collections WHERE user_fk = ?";
    int count = 0;
    try(PreparedStatement prstm = connection.prepareStatement(sql)){
            String user = (String) list.get(3);
            prstm.setString(1, user);

            ResultSet rs = prstm.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var s="Сведения о коллекции:\n";
        s+=" Тип: " + collectionManager.getCollection().getClass()+"\n";
        s+=" Количество элементов: " + count+"\n";
//        s+=" Дата последнего сохранения: " + lastSaveTimeString+"\n";
//        s+=" Дата последней инициализации: " + lastInitTimeString;
        return new ExecutionResponse(s);
    }
}
