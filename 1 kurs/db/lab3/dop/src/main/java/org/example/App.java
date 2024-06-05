package org.example;

import org.example.data.RequestClient;
import org.example.requestUtils.CRUDUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.example.requestUtils.CRUDUtils.createTriggerAndFunction;
import static org.example.utilts.JsonReader.readJsonFromUrl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class App {
    public static void main(String[] args) {
        while(true) {
            try {
                String APIkey = "999644f3502246a2ba4211948240206";

                String[] data = RequestClient.dataForRequest();
                String city = data[0].trim();
                Integer days = Integer.parseInt(data[1].trim());

                String urlForGetLonAndLet = "https://api.weatherapi.com/v1/forecast.json?days=" + days + "&q=" + city + "&key=" + APIkey;
                JSONObject jsonDate = readJsonFromUrl(urlForGetLonAndLet);

                JSONArray forecastDays = jsonDate.getJSONObject("forecast").getJSONArray("forecastday");
                double sumAverageValueTempOnDays = 0;
                for (int i = 0; i < forecastDays.length(); i++) {
                    JSONObject day = forecastDays.getJSONObject(i);
                    String date = day.getString("date");
                    double tempC = day.getJSONObject("day").getDouble("avgtemp_c"); // Используем avgtemp_c для средней температуры

                    sumAverageValueTempOnDays += tempC;
                    System.out.println("Date: " + date);
                    System.out.println("Average Temperature: " + tempC + " °C");
                }

                createTriggerAndFunction();
                double tempAvg = CRUDUtils.saveWeatherData(city, sumAverageValueTempOnDays, days);
                System.out.println("AVG Temp: " + tempAvg);


            } catch (Exception e) {
                System.out.println("Not valid data");
            }
        }


//        penWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient("ce1ca0ebe1028f954459b8b96c194739");
//        final Forecast forecast = openWeatherMapClient
//                .forecast5Day3HourStep()
//                .byCityName("Minsk")
//                .language(Language.ENGLISH)
//                .unitSystem(UnitSystem.METRIC)
//                .count(41)
//                .retrieve()
//                .asJava();
//        final Forecast forecast2 = openWeatherMapClient
//                .forecast5Day3HourStep()
//                .byCityName("Minsk")
//                .language(Language.ENGLISH)
//                .unitSystem(UnitSystem.METRIC)
//                .count(3)
//                .retrieve()
//                .asJava();
//        for (int i = 0; i < forecast.getWeatherForecasts().size(); i++) {
//            System.out.println(forecast.getWeatherForecasts().get(i));
//        }O
    }
}