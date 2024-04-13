package project.lab5;

import project.lab5.commands.*;
import project.lab5.console.StandardConsole;
import project.lab5.managers.CollectionManager;
import project.lab5.managers.CommandManager;
import project.lab5.managers.DumpManager;
import project.lab5.runner.Runner;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws Exception,IOException {

        var console = new StandardConsole();
        String value = System.getenv("LAB5_FILE");
        var dumpManager = new DumpManager(value, console);
        var collectionManager = new CollectionManager(dumpManager);

        collectionManager.loadCollection();
        System.out.println(collectionManager);




        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(collectionManager));
//            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("remove_last", new RemoveLast(console, collectionManager));
            register("counter_greater_than_soundtrack_name", new CounterGreaterThanSoundtrackName(console, collectionManager));
            register("count_by_mood", new CountByMood (console, collectionManager));
            register("print_field_descending_car", new PrintFieldDescendingCar(console, collectionManager));
//            register("sort", new Sort(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }

}
