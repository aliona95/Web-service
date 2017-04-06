/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        Gson gson = new Gson();

        port(4321);

        get("/people",(req,res) -> gson.toJson(Controller.getAllPeople(req, res, data)));
        get("/people/:id", (req, res) -> gson.toJson(Controller.getPerson(req, res, data)));
        get("/people/name/:name", (req, res) -> gson.toJson(Controller.getByName(req, res, data)));
        get("/people/gender/:gender", (req, res) -> gson.toJson(Controller.getPeopleByGender(req, res, data)));
        put("/people", (req, res) -> Controller.addPerson(req, res, data));
        delete("/people/:id",(req, res) -> Controller.deletePerson(req, res, data));
        post("people/:id", (req, res) -> Controller.updatePerson(req, res, data));

        exception(Exception.class, (e, req, res) -> {
            res.status(HTTP_BAD_REQUEST);
            res.body((gson.toJson(e.getMessage())));
        });

        after((req, rep) -> rep.type("application/json"));
    }
}
