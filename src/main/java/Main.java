/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();

        port(81);

        path("/people", () -> {

            get("", (req, res) -> {
                return Controller.getAllPeople(req, res, data);
            } , new JsonTransformer());

            get("/companies", (req, res) -> {
                return Controller.getCompanies(req, res, data);
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

            get("/:id/company", (req, res) ->{
                return Controller.getPersonCompany(req, res, data);
            } , new JsonTransformer());

            get("/company/:id", (req, res) -> {
                return Controller.getPeopleByCompany(req, res, data);
            } , new JsonTransformer());

            post("", (req, res) -> {
                return Controller.addPerson(req, res, data);
            } , new JsonTransformer());

            post("/company", (req, res) -> {
                return Controller.createCompany(req, res, data);
            }, new JsonTransformer());

            put("/:id", (req, res) -> {
                return Controller.updatePerson(req, res, data);
            } , new JsonTransformer());

            put("/company/:id", (req, res) -> {
                return Controller.updateCompany(req, res, data);
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
    }
}
