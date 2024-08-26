package org.example.runner;

import org.example.Collection.ExecutionResponse;
import org.example.commands.Command;
import org.example.managers.CommandManager;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LaunchCommand {
    /**
     * Выполнение команды
     * @param list
     * @param commandManager
     * @return
     */
    public static ExecutionResponse launchCommand(List<Object> list, CommandManager commandManager) {
        if ("".equals(list.get(0).toString())) return new ExecutionResponse("");
        Command command = commandManager.getCommands().get(list.get(0).toString());

        if (command == null)
            return new ExecutionResponse(false, "Команда '" + list.get(0).toString() + "' не найдена.");

//        lock.writeLock().lock();
        try{
            switch (list.get(0).toString()) {
                case "exit":
                    Command save = commandManager.getCommands().get("save");
                    save.setDropServer(true);
                    save.apply(list);
                default:
                    return command.apply(list);
            }
        } finally{
//            lock.writeLock().unlock();
        }
    }
}
