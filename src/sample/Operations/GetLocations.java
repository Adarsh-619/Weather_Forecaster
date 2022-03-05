package sample.Operations;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;

public class GetLocations {
    private static HttpURLConnection connection;
    public static String getLat_Lon(String cityName) {
        final String api_key = "b9d82d47340ecf4456de5ef1ccb929ef";
        final String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=2&appid=" + api_key;
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
//			System.out.println(responseContent.toString());
            return (parseJSON(responseContent.toString()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    public static String parseJSON(String responseBody) {
        JSONArray location = new JSONArray(responseBody);

        JSONObject element = location.getJSONObject(0);
        double lat = element.getDouble("lat");
        double lon = element.getDouble("lon");

        return (lat + " " + lon);
    }
}
