package sample;
/*
 * Weather_Forecaster
 * @author Adarsh
 * Created On 04, 03, 2022
 * Modified On 06-03-2022, 00:29
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Scenes/Scene.fxml"));
        // Setting the icon and title
        primaryStage.getIcons().add(new Image("sample/assets/icon.png"));
        primaryStage.setTitle("  Weather Forecaster");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.setResizable(false);
//        Controller.lbl_copyright.setText("\u00a9" + " Ad@rsh619");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
