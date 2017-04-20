/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();

        port(4321);

        path("/people", () -> {
            get("", (req, res) -> {
                return Controller.getAllPeople(req, res, data);
            } , new JsonTransformer());

            get("/:id", (req, res) -> {
                return Controller.getPerson(req, res, data);
            } , new JsonTransformer());

            get("/name/:name", (req, res) -> {
                return Controller.getByName(req, res, data);
            } , new JsonTransformer());

            get("/gender/:gender", (req, res) -> {
                return Controller.getPeopleByGender(req, res, data);
            } , new JsonTransformer());

            post("", (req, res) -> {
                return Controller.addPerson(req, res, data);
            } , new JsonTransformer());

            put("/:id", (req, res) -> {
                return Controller.updatePerson(req, res, data);
            } , new JsonTransformer());

            delete("/:id", (req, res) -> {
                return Controller.deletePerson(req, res, data);
            } , new JsonTransformer());


        });

        exception(Exception.class, (e, req, res) -> {
            res.status(HTTP_BAD_REQUEST);
            JsonTransformer jsonTransformer = new JsonTransformer();
            res.body(jsonTransformer.render(new ErrorMessage(e)));
        });

        after((req, rep) -> rep.type("application/json"));

        /*
        get("/people",(req,res) -> gson.toJson(Controller.getAllPeople(req, res, data)));
        get("/people/:id", (req, res) -> gson.toJson(Controller.getPerson(req, res, data)));
        get("/people/name/:name", (req, res) -> gson.toJson(Controller.getByName(req, res, data)));
        get("/people/gender/:gender", (req, res) -> gson.toJson(Controller.getPeopleByGender(req, res, data)));
        post("/people", (req, res) -> Controller.addPerson(req, res, data));
        delete("/people/:id",(req, res) -> Controller.deletePerson(req, res, data));
        put("/people/:id", (req, res) -> Controller.updatePerson(req, res, data));

        exception(Exception.class, (e, req, res) -> {
            res.status(HTTP_BAD_REQUEST);
            res.body((gson.toJson(e.getMessage())));
        });

        after((req, rep) -> rep.type("application/json"));
        */
    }
}
