package sample;
/*
 * Weather_Forecaster
 * @author Adarsh
 * Created On 04, 03, 2022
 * Modified On 06-03-2022, 00:29
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Operations.GetWeather;

import java.io.IOException;
import java.net.URL;

public class Controller {
    @FXML
    Label lbl_display;

    @FXML
    Label lbl_head;

    @FXML
    Button btn_find;

    @FXML
    TextField tf_location;

    @FXML
    ImageView icon_weather;

    @FXML
    Label weather_type;
    @FXML
    static Label lbl_copyright = new Label("\u00a9" + " Ad@rsh619");

    static boolean visible = false;

    @FXML
    public void display(ActionEvent event) throws IOException {
        String displayData = GetWeather.get_Weather(tf_location.getText());
//        System.out.println("Icon : " + GetWeather.getIcon());
//        System.out.println("Weather_Type : " + GetWeather.getWeather_type());
        Image image = get_image(GetWeather.getIcon());
        icon_weather.setBlendMode(BlendMode.MULTIPLY); // To Darken the image to enhance visibility
        icon_weather.setImage(image);
        weather_type.setText(GetWeather.getWeather_type());
        lbl_head.setText(GetWeather.getLocation());
        lbl_display.setText(displayData);

        // Checking if it is in scene 1
        if (visible == false){
            icon_weather.setVisible(true);
            weather_type.setVisible(true);
            lbl_display.setVisible(true);
            lbl_head.setVisible(true);
            visible = true;
        }else{
            System.out.println("Nothing");
        }
    }

    // Method to retrieve the image from the url
    public static Image get_image(String icon){
        Image image = null;
        try {
            // String pathToOpen = "http://...";
            URL url = new URL("http://openweathermap.org/img/wn/"+ icon +"@2x.png");
            image = new Image(url.toString());
            // button.setOnAction(ev -> getHostServices().showDocument(pathToOpen));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
