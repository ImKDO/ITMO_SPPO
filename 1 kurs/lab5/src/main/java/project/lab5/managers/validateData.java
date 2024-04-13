package project.lab5.managers;

import java.util.ArrayList;

public class validateData {

    public static boolean stringIsNotEmpty(String string) {
        if (!string.isEmpty() && string != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateId(String id, ArrayList<String> notFreeId) {
        for (int numberId = 0; numberId < notFreeId.size(); numberId++) {
            if (!(id == notFreeId.get(numberId))) {
                return false;
            }
        }
        return true;
    }

}
