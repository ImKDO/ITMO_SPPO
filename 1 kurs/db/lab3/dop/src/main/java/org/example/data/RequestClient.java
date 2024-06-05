package org.example.data;

import java.util.Scanner;

public class RequestClient {
    public static String[] dataForRequest() {

        String correction;
        String days;
        String city;
        do {
            System.out.println("Enter the city where you want to see the weather (exit to turn off the program)");
            Scanner scanner = new Scanner(System.in);
            city = scanner.nextLine();
            if (city.trim().equals("exit")) {System.exit(0);}

            System.out.println("Enter the number of days in advance that you want to calculate the average maximum temperature in a given location (from 1 to 14, where 1 is the current day)");
            days = scanner.nextLine();
            if (days.trim().equals("exit")) {System.exit(0);}

            System.out.println("Do you want to edit something? (yes, no)");
            correction = scanner.nextLine();
            if (correction.trim().equals("exit")) {System.exit(0);}
        } while (!(correction.trim().equals("no") | correction.trim().isEmpty()));

        return new String[]{city, days};
    }
}

