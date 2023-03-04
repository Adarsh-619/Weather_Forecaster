package sample.Operations;
/*
 * Weather_Forecaster
 * @author Adarsh
 * Created On 05, 03, 2022
 * Modified On 06-03-2022, 00:29
 */

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sample.credentials.AccessData;
import sample.credentials.AccessData.*;

public class GetWeather {

    private static HttpURLConnection connection;

    private static String icon;
    private static String weather_type;
    private static String location;

    public static String getLocation() {
        return location;
    }
    public static String getIcon() {
        return icon;
    }

    public static String getWeather_type() {
        return weather_type;
    }


    public static String get_Weather(String cityName) {

        String lat_lon[] = GetLocations.getLat_Lon(cityName).split(" ");

        String lat = lat_lon[0];
        String lon = lat_lon[1];

        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + AccessData.getAPIKey();

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();

            // Request Setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Responses
            int status = connection.getResponseCode();
//            System.out.println("Status : " + status); // 200 is for success

            // Taking out responses from the InputStreams
            if(status > 299) { // Anything above the 299 is a failure
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return (parseJSON(responseContent.toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return "Error";
    }

    public static String parseJSON(String responseBody) {
        JSONArray weather = new JSONObject(responseBody).getJSONArray("weather");
        JSONObject weatherObj = weather.getJSONObject(0);
        icon = weatherObj.getString("icon");
        weather_type = weatherObj.getString("description");
//        System.out.println("Icon : " + icon);
        weather_type = convertToTitleCaseIteratingChars(weather_type);
//        System.out.println("Weather Type : " + weather_type);

        JSONObject base = new JSONObject(responseBody);
        String country = (String)base.getJSONObject("sys").get("country");
        String name = (String)base.get("name");

        JSONObject main = new JSONObject(responseBody).getJSONObject("main");
        location = name + ", " + country;
        String temp = String.valueOf(Math.round(((main.getDouble("temp") - 273) * 100.0))/100.0) + "℃, but feels like " + String.valueOf((Math.round((main.getDouble("feels_like") - 273) * 100.0))/100.0) + "℃";
        String temp_min = String.valueOf(Math.round(((main.getDouble("temp_min") - 273) * 100.0))/100.0) + "℃";
        String temp_max = String.valueOf(Math.round(((main.getDouble("temp_max") - 273) * 100.0))/100.0) + "℃";
        String pressure = String.valueOf(main.getInt("pressure")) + " Pa";
        String humidity = String.valueOf(main.getInt("humidity")) + " gram per cubic meter";

        String combo = "\nTemperature : " + temp + "\nMinimum Temperature : " + temp_min + "\nMaximum Temperature : " + temp_max +
                        "\nPressure : " + pressure + "\nHumidity : " + humidity;

        return combo;
    }

    public static String convertToTitleCaseIteratingChars(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }
}
