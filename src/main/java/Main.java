import com.google.gson.Gson;

import java.util.ArrayList;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;
/**
 * Created by Aliona and Eimantas
 */

public class Main {
    public static void main(String[] args) {
        Data data = new Data();

        Gson gson = new Gson();

        port(4321);

        get("/people",(req,res) -> gson.toJson(data.getAll()));

/*
        path("/people", () -> {
            get("", (req, res) -> {
                return Controller.getAllPeople(req, res, data);
            } , new JsonTransformer());
        });
*/
        after((req, rep) -> rep.type("application/json"));

    }
}
