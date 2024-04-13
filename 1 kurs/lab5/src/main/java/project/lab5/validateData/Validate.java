package project.lab5.validateData;

import project.lab5.models.Coordinates;

public class Validate {
    public static boolean validateId(Integer id){
        if (id != null && id >0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateName(String name){
        if (name != null && !name.isEmpty()){
            return true;
        } else {
            return false;
        }
    }
    public static boolean validateCoordinates(Coordinates coordinates){
        if (coordinates.getX() > -737 && coordinates.equals(null)){
            return true;
        }
        else {
            return false;
        }
    }

}
