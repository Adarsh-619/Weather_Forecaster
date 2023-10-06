package sample.Operations;
/*
 * Weather_Forecaster
 * @author Adarsh
 * Created On 03, 03, 2022
 * Modified On 06-03-2022, 00:29
 */
import org.json.JSONArray;
import org.json.JSONObject;
import sample.credentials.AccessData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GetWeather_orig {

    private static HttpURLConnection connection;
    private static String icon;
    private static String weather_type;

    public static void main(String[] args) {
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter the city name: ");
        String cityName = Sc.next();
        String lat_lon[] = GetLocations.getLat_Lon(cityName).split(" ");
//        System.out.println(lat_lon);
        String lat = lat_lon[0];
        String lon = lat_lon[1];
//        System.out.println(lat + " " + lon);

        final String api_key = AccessData.getAPIKey();
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + api_key;

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
            System.out.println("Status : " + status); // 200 is for success

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
            System.out.println(parseJSON(responseContent.toString()));
//            System.out.println(responseContent.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

    }

    public static String parseJSON(String responseBody) {
        JSONArray weather = new JSONObject(responseBody).getJSONArray("weather");
        JSONObject base = new JSONObject(responseBody);
        System.out.println(base);
        String country = (String)base.getJSONObject("sys").get("country");
        String name = (String)base.get("name");
        System.out.println(country + "  " + name);
//        System.out.println(weather);
        JSONObject weatherObj = weather.getJSONObject(0);
//        System.out.println(weatherObj);
        icon = weatherObj.getString("icon");
        weather_type = weatherObj.getString("description");
        System.out.println("Icon : " + icon);
        weather_type = convertToTitleCaseIteratingChars(weather_type);
        System.out.println("Weather Type : " + weather_type);

        JSONObject main = new JSONObject(responseBody).getJSONObject("main");
        String temp = String.valueOf(main.getDouble("temp") - 273 ) + "℃, but feels like " + String.valueOf(main.getDouble("feels_like") - 273) + "℃";
        String temp_min = String.valueOf(main.getDouble("temp_min") - 273) + "℃";
        String temp_max = String.valueOf(main.getDouble("temp_max") - 273) + "℃";
        String pressure = String.valueOf(main.getInt("pressure")) + " Pa";
        String humidity = String.valueOf(main.getInt("humidity")) + " gram per cubic meter";

        String combo = "Temperature : " + temp + "\nMinimum Temperature : " + temp_min + "\nMaximum Temperature : " + temp_max +
                        "\nPressure : " + pressure + "\nHumidity : " + humidity;

        return combo;
    }

    public static void get_image(){
        BufferedImage image = null;
        try {
            URL url = new URL("http://openweathermap.org/img/wn/"+ icon +"@2x.png");
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
