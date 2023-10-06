package sample.credentials;

import io.github.cdimascio.dotenv.Dotenv;

public class AccessData {

    static Dotenv dotenv = Dotenv.configure().directory("src/sample/credentials").load();
    private static final String API_KEY = dotenv.get("API_KEY", "Default");
    public static String getAPIKey(){
        return API_KEY;
    }
}
