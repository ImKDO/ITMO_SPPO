package org.example;

import org.example.commands.*;
import org.example.console.StandardConsole;
import org.example.runner.Runner;
import org.example.util.CommandManager;

import static org.apache.commons.lang3.SerializationUtils.serialize;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        var console = new StandardConsole();

        var commandManager = new CommandManager() {{
            register("update", new Update(console));
            register("help", new Help(console));
            register("info", new Info(console));
            register("show", new Show(console));
            register("add", new Add(console));
            register("remove_by_id", new RemoveById(console));
            register("clear", new Clear(console));
            register("execute_script", new ExecuteScript(console));
            register("remove_greater", new RemoveGreater(console));
            register("remove_last", new RemoveLast(console));
            register("counter_greater_than_soundtrack_name", new CounterGreaterThanSoundtrackName(console));
            register("count_by_mood", new CountByMood(console));
            register("print_field_descending_car", new PrintFieldDescendingCar(console));
            register("sort", new Sort(console));
        }};

        try {
            new Runner(console, commandManager).interactiveMode();
        } catch (Exception ignored) {

        }
    }

}