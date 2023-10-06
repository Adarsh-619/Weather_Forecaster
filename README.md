# Weather_Forecaster
- Prompts the user to enter a city and shows the current weather details of that place.
- The app makes use Geocoding API to get the latitude and location of the desired location and Current Weather Data API to fetch the detailed information of that location.
- Implemented the GUI using JavaFX and used HttpURLConnection class to make API calls.


## API 
Two APIs are used in this project both of them are free to use. You have to generate your api key in the website and replace it in order to use the app.

Head to [OpenWeatherMap](https://openweathermap.org/), sign up and create your own api key.
Following are the two APIs which have been used in this app:
1. [Current Weather Data](https://openweathermap.org/current)
2. [Geocoding API](https://openweathermap.org/api/geocoding-api)

## *Working Screenshots*
![Alt](/assets/Screenshot-1.png "Screenshot-1")
![Alt](/assets/Screenshot-2.png "Screenshot-2")

## Fixes
1. In order to run the app, you need to include JavaFx, DotENV and JSON libraries (All of them are there in the assets folder)
2. Add the VM options to run the APP `--module-path "D:\Study\Project\demo\Weather_Forecaster\assets\javafx-sdk-17.0.2\lib" --add-modules=javafx.controls,javafx.fxml`
Example for above: `--module-path <path To javaFX lib> --add-modules=javafx.controls,javafx.fxml`

## Installation
1. Clone the Repo
2. Open the project in intellij IDEA
3. Navigate to credentials/AccessData.java and insert your api key
4. You can run build the project and execute the app,
5. Moreover, there is a packaged version of the app in the format of .exe in the folder, You can also build the artifacts and package your jar.
6. Happy Learning 😎😉😃
