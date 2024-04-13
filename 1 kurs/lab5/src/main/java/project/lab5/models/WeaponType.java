package project.lab5.models;

public enum WeaponType {
    HAMMER,
    PISTOL,
    KNIFE,
    MACHINE_GUN,
    BAT;
    public static String getWeaponType(){
        StringBuilder listWeaponType = new StringBuilder();
        for (var elementWeaponType: values()){
            listWeaponType.append(elementWeaponType.name()).append(", ");
        }
        return listWeaponType.substring(0,listWeaponType.length() - 2);
    }
}

