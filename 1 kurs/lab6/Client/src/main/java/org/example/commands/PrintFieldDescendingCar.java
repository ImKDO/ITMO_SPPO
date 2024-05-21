//package org.example.commands;
//
//
//import org.example.console.Console;
//
//import org.example.models.Car;
//import org.example.models.HumanBeing;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import org.example.commands.abstractCommandClass.Command;
//
///**
// * Команда print_field_descending_car - выдает все car в порядке убывания
// */
//public class PrintFieldDescendingCar extends Command {
//    private final Console console;
//
//
//    public PrintFieldDescendingCar(Console console) {
//        super("print_field_descending_car car", "выдать все car в порядке убывания");
//        this.console = console;
//
//    }
//
//    @Override
//    public ExecutionResponse apply(String arguments) {
//        if (arguments.isEmpty())
//            return new ExecutionResponse(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
//        ArrayList<String> nameCars = new ArrayList<>();
//
//        String nameCar;
//        while (true) {
//            console.print("Введите car.name:");
//            var line = console.readln().trim();
//            if (!line.isEmpty()) {
//                nameCar = line;
//                break;
//            }
//        }
//
//        boolean coolCar;
//        while (true) {
//            console.print("Введите cool.car:");
//            var line = console.readln().trim();
//            if (!line.isEmpty()) {
//                if (Boolean.parseBoolean(line) | line.equals("false")) {
//                    coolCar = Boolean.parseBoolean(line);
//                    break;
//                }
//            }
//        }
//        Car car = new Car(nameCar,coolCar);
//
//        for(HumanBeing element: collectionManager.getCollection()){
//            if(element.getCar().getName().equals(car.getName()) &&  element.getCar().getCool() == car.getCool()){
//                nameCars.add(element.getCar().getName().trim());
//            }
//        }
//        Collections.sort(nameCars);
//        for(String element: nameCars){
//            console.println(element);
//        }
//        return new ExecutionResponse("Команда успешно выполнена");
//    }
//}