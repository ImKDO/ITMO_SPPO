package project.lab5.AskManager;

import project.lab5.console.Console;
import project.lab5.models.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class Ask {

    private final Console console;

    public Ask(Console console) {
        this.console = console;
    }

    /**
     * askHumanBeing - Создать объект HumanBeing с проверкой всех ограничений на его элементы
     */
    public HumanBeing askHumanBeing(int id) {
        if (console.getFileScanner() == null) {
            String name;
            while (true) {
                console.println("Введите name:");
                var line = console.readln().trim();
                if (!line.isEmpty() && !line.trim().equals("null")) {
                    name = line;
                    break;
                }
            }
            Integer x;
            while (true) {
                console.println("Введите coor.x:");
                var line = console.readln();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        if (x > -737) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        console.println("Введите валидный x (> -737 и число int)");
                    }
                }
            }

            double y;
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

            boolean realHero;
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

            Boolean hasToothpick;
            while (true) {
                console.print("Введите hasToothpick:");
                var line = console.readln().trim();
                if (line.trim().isEmpty()) {
                    hasToothpick = null;
                    break;
                } else {
                    if (Boolean.parseBoolean(line) | line.equals("false")) {
                        hasToothpick = Boolean.parseBoolean(line);
                        break;
                    }
                }
            }

            double impactSpeed;
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
                if (!line.isEmpty() && !line.equals("null")) {
                    soundtrackName = line;
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
                console.print("Введите MOOD (" + Mood.getMood() + "):");
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
                if (!line.isEmpty() && !line.equals("null")) {
                    nameCar = line;
                    break;
                }
            }

            boolean coolCar;
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
            System.out.println(hasToothpick);
            return new HumanBeing(id, name, new Coordinates(x, y), realHero, hasToothpick, impactSpeed, LocalDateTime.now(), soundtrackName, weaponType, mood, new Car(nameCar, coolCar));
        } else {
            String name;
            while (true) {
                var line = console.readln().trim();
                if (!line.isEmpty() && !line.trim().equals("null")) {
                    name = line;
                    break;
                }
            }

            Integer x;
            while (true) {
                var line = console.readln().trim();
                if (!line.isEmpty()){
                    try {
                        x = Integer.parseInt(line);
                        System.out.println(x);
                        if (x > -737) {
                            break;
                        }
                    } catch (NumberFormatException ignored){
                        return null;
                    }
                }
            }

            double y;
            while (true) {
                System.out.println("y:");
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    try {
                        y = Double.parseDouble(line);
                        break;
                    } catch (NumberFormatException ignored) {
                        return null;
                    }
                }
            }

            boolean realHero;
            while (true) {
                System.out.println("realHero:");
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    if (Boolean.parseBoolean(line) | line.equals("false")) {
                        realHero = Boolean.parseBoolean(line);
                        break;
                    } else {
                        return null;
                    }
                }
            }

            Boolean hasToothpick;
            while (true) {
                var line = console.readln().trim();
                System.out.println(line.isEmpty());
                if (line.isEmpty()) {
                    hasToothpick = null;
                    break;
                } else {
                    if (Boolean.parseBoolean(line) | line.equals("false")) {
                        hasToothpick = Boolean.parseBoolean(line);
                        break;
                    }
                }
            }

            double impactSpeed;
            while (true) {
                var line = console.readln().trim();
                try {
                    impactSpeed = Double.parseDouble(line);
                    if (impactSpeed < 118) {
                        break;
                    }
                } catch (NumberFormatException ignored) {
                    return null;
                }
            }

            String soundtrackName;
            while (true) {
                var line = console.readln().trim();
                if (!line.isEmpty() && !line.equals("null")) {
                    soundtrackName = line;
                    break;
                }
            }

            WeaponType weaponType;
            while (true) {
                var line = console.readln().trim();
                try {
                    if (line.isEmpty()) {
                        weaponType = null;
                        break;
                    } else {
                        weaponType = WeaponType.valueOf(line);
                        break;
                    }
                } catch (IllegalArgumentException ignored) {
                    return null;
                }
            }

            Mood mood;
            while (true) {
                var line = console.readln().trim();
                try {
                    mood = Mood.valueOf(line);
                    break;
                } catch (IllegalArgumentException ignored) {
                    return null;
                }
            }

            String nameCar;
            while (true) {
                var line = console.readln().trim();
                if (!line.isEmpty() && line.equals("null")) {
                    nameCar = line;
                    break;
                }
            }

            boolean coolCar;
            while (true) {
                var line = console.readln().trim();
                if (!line.isEmpty()) {
                    if (Boolean.parseBoolean(line) | line.equals("false")) {
                        coolCar = Boolean.parseBoolean(line);
                        break;
                    } else {
                        return null;
                    }
                }
            }

            return new HumanBeing(id, name, new Coordinates(x, y), realHero, hasToothpick, impactSpeed, LocalDateTime.now(), soundtrackName, weaponType, mood, new Car(nameCar, coolCar));
        }

    }

    /**
     * askWeaponType - Создать объект WeaponType с проверкой всех ограничений на его элементы
     */
    public static WeaponType askWeaponType(Console console) {
        try {
            WeaponType r;
            while (true) {
                console.print("WeaponType (" + WeaponType.getWeaponType() + "): ");
                var line = console.readln().trim();
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
    public static Mood askMood(Console console) {
        try {
            Mood r;
            while (true) {
                console.print("Mood (" + Mood.getMood() + "): ");
                var line = console.readln().trim();
                if (!line.equals("")) {
                    try {
                        r = Mood.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException ignored) {
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
     * askId - Создать id с проверкой всех ограничений на его элементы
     */
    public static Integer askId(Console console) {
        int id = 0;
        while (true) {
            var line = console.readln().trim();
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException ignored) {
            }
            if (!line.isEmpty()) return id;
        }
    }
}


