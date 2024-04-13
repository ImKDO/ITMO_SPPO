package project.lab5.AskManager;

import project.lab5.console.Console;
import project.lab5.models.*;

import java.util.NoSuchElementException;

public class Ask {
    public static class AskBreak extends Exception {
    }

    /**
     * askHumanBeing - Создать объект HumanBeing с проверкой всех ограничений на его элементы
     */
    public static HumanBeing askHumanBeing(Console console, int id) throws AskBreak {
        String name;
        while (true) {
            console.print("Введите name:");
            var line = console.readln().trim();
            if (!line.isEmpty() && line != null && !line.trim().equals("null")) {
                name = line;
                break;
            }
        }

        Integer x;
        while (true) {
            console.print("Введите coor.x:");
            var line = console.readln();
            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);
                    System.out.println(x);
                    if (x > -737) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    console.println("Введите валидный x (> -737 и число int)");
                }
            }
        }

        Double y;
        while (true) {
            console.print("Введите coor.y:");
            var line = console.readln().trim();
            if (!line.isEmpty()) {
                try {
                    y = Double.parseDouble(line);
                    break;
                } catch (NumberFormatException e) {
                    console.println("Введите валидный y (типа double)");
                }
            }
        }

        Boolean realHero;
        while (true) {
            console.print("Введите realHero:");
            var line = console.readln().trim();
            if (!line.isEmpty()) {
                if (Boolean.parseBoolean(line) | line.equals("false")) {
                    realHero = Boolean.parseBoolean(line);
                    break;
                }
            }
        }

        Boolean hasToothpick = null;
        while (true) {
            console.print("Введите hasToothpick:");
            var line = console.readln().trim();
            System.out.println(line.isEmpty());
            if (line.isEmpty()) {
                realHero = null;
                break;
            } else {
                if (Boolean.parseBoolean(line) | line.equals("false")) {
                    realHero = Boolean.parseBoolean(line);
                    break;
                }
            }
        }

        Double impactSpeed;
        while (true) {
            console.print("Введите impactSpeed:");
            var line = console.readln().trim();
            try {
                impactSpeed = Double.parseDouble(line);
                if (impactSpeed < 118) {
                    break;
                }
            } catch (NumberFormatException e) {
                console.println("Введите валидный y (типа double)");
            }
        }

        String soundtrackName;
        while (true) {
            console.print("Введите soundtrackName:");
            var line = console.readln().trim();
            if (!line.isEmpty() && line.equals("null")) {
                name = line;
                break;
            }
        }

        WeaponType weaponType;
        while (true) {
            console.print("Введите weaponType(HAMMER, PISTOL, KNIFE, MACHINE_GUN, BAT):");
            var line = console.readln().trim();
            try {
                if (line.isEmpty()) {
                    weaponType = null;
                    break;
                } else {
                    weaponType = WeaponType.valueOf(line);
                    break;
                }
            } catch (IllegalArgumentException e) {
                console.println("Введите верный weaponType");
            }
        }

        Mood mood;
        while (true) {
            console.print("Введите weaponType (" + Mood.getMood() + "):");
            var line = console.readln().trim();
            try {
                mood = Mood.valueOf(line);
                break;
            } catch (IllegalArgumentException e) {
                console.println("Введите верный Mood");
            }
        }

        String nameCar;
        while (true) {
            console.print("Введите car.name:");
            var line = console.readln().trim();
            if (!line.isEmpty() && line != null) {
                nameCar = line;
                break;
            }
        }

        Boolean coolCar;
        while (true) {
            console.print("Введите cool.car:");
            var line = console.readln().trim();
            if (!line.isEmpty()) {
                if (Boolean.parseBoolean(line) | line.equals("false")) {
                    coolCar = Boolean.parseBoolean(line);
                    break;
                }
            }
        }

//        return HumanBeing(id,name, new Coordinates(x,y), realHero, hasToothpick, impactSpeed, creat, soundtrackName, weaponType, mood, new Car(nameCar,coolCar));
        return null;
    }


    /**
     * askCoordinates - Создать объект Coordinates с проверкой всех ограничений на его элементы
     */
    public static Coordinates askCoordinates(Console console) throws AskBreak {

        return null;
    }

    /**
     * askWeaponType - Создать объект WeaponType с проверкой всех ограничений на его элементы
     */
    public static WeaponType askWeaponType(Console console) throws AskBreak {
        try {
            WeaponType r;
            while (true) {
                console.print("WeaponType (" + WeaponType.getWeaponType() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        r = WeaponType.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                    }
                } else return null;
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * askMood - Создать объект Mood с проверкой всех ограничений на его элементы
     */
    public static Mood askMood(Console console) throws AskBreak {
        try {
            Mood r;
            while (true) {
                console.print("Mood (" + Mood.getMood() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.equals("")) {
                    try {
                        r = Mood.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                    }
                }
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    /**
     * askCar - Создать объект Car с проверкой всех ограничений на его элементы
     */
//    public static Car askCar(Console console) throws AskBreak {
//
//    }

    /**
     * askId - Создать id с проверкой всех ограничений на его элементы
     */
    public static Integer askId(Console console) throws AskBreak {
        Integer id = 0;
        while (true) {
            var line = console.readln().trim();
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException e) {
            }
            if (!line.isEmpty()) return id;
        }
    }
}

// try {
//            String name = null;
//            while (true) {
//                console.print("name: ");
//                var line = console.readln().trim();
//                if (line.equals("exit")) throw new AskBreak();
//                if (!line.isEmpty()) name = line;
//                break;
//            }
//
//            LocalDateTime creationDate;
//            while (true) {
//                console.print("data-time (Example: 2007-12-03T10:15:30)");
//                var line = console.readln().trim();
//                if (line.equals("exit")) throw new AskBreak();
//                if (line.equals("")) { creationDate = null; break;}
//                try { creationDate = LocalDateTime.parse(line, DateTimeFormatter.ISO_DATE_TIME);
//                    break; } catch (DateTimeParseException e) { }
//                try { creationDate = LocalDateTime.parse(line+"T00:00:00.0000",DateTimeFormatter.ISO_DATE_TIME);
//                    break; } catch (DateTimeParseException e) { }
//            }
//
//            Coordinates coordinates = askCoordinates(console);
//
//            Boolean realHero;
//            while (true) {
//                console.print("realHero: ");
//                String line = console.readln().trim();
//                System.out.println(line);
//                if (line != "") {
//                    try {
//                        if (!Boolean.parseBoolean(line) && (!line.equals("false"))) {
//                            continue;
//                        } else {
//                            realHero = Boolean.valueOf(line);
//                            break;
//                        }
//                    } catch (IllegalArgumentException e) {
//                    }
//                }
//                if (line.equals("exit")) throw new AskBreak();
//            }
//
//            Boolean hasToothpick;
//            while (true) {
//                console.print("hasToothpick: ");
//                String line = console.readln().trim();
//                System.out.println(line);
//                if (line != "") {
//                    try {
//                        if (!Boolean.parseBoolean(line) && (!line.equals("false"))) {
//                            continue;
//                        } else {
//                            hasToothpick = Boolean.valueOf(line);
//                            break;
//                        }
//                    } catch (IllegalArgumentException e) {
//                    }
//                }
//                if (line.equals("exit")) throw new AskBreak();
//            }
//
//            Double impactSpeed;
//            while (true) {
//                console.print("impactSpeed: ");
//                var line = console.readln().trim();
//                if (line != "") {
//                    try {
//                        impactSpeed = Double.parseDouble(line);
//                        if (impactSpeed > 118) continue;
//                        break;
//                    } catch (NumberFormatException e) {
//                    }
//                }
//                if (line.equals("exit")) throw new AskBreak();
//            }
//
//
//            String soundtrackName;
//            while (true) {
//                console.print("soundtrackName: ");
//                String line = console.readln().trim();
//                if (!line.equals(null)) {
//                    soundtrackName = line;
//                    break;
//                }
//            if (line.equals("exit")) throw new AskBreak();
//        }
//
//            WeaponType weaponType = askWeaponType(console);
//            Car car = askCar(console);
//            Mood mood = askMood(console);
//            return new HumanBeing(id, name, coordinates, realHero, hasToothpick, impactSpeed, creationDate, soundtrackName, weaponType, mood, car);
//        } catch (NoSuchElementException | IllegalStateException e) {
//            console.printError("Ошибка чтения");
//            return null;
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }



