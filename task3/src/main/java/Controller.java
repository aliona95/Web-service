import com.google.gson.Gson;
import static spark.Spark.*;
public class Controller {
    public static void main(String [ ] args){
        TaskManager task  = new TaskManager();
        port(888);
         path("", () -> {
            post("/login", (request, response) -> {
                    return toJson(task.loginHuman(request, response));
                } );
          
            post("/register", (request, response) -> {
                    return toJson(task.registerHuman(request, response));
                } ); 


            get("/people", (request, response) -> {
                    return task.getAllPeoples(request,response);
                } );


           get("/people/:id", (request, response) -> {
                    return task.getPeopleById(request,response);
                } );
           
            get("/people/name/:name", (request, response) -> {
                    return task.getPeopleByName(request,response);
                } );
            
            get("/people/gender/:gender", (request, response) -> {
                    return task.getPeopleByGender(request,response);
                } );
            
            get("/people/companies", (request, response) -> {
                    return task.getCompanies(request,response);
                } );

             get("/people/:id/company", (req, res) ->{
                 return task.getPersonCompany(req, res);
             });
            
            get("/people/company/:id", (request, response) -> {
                    return task.getPeopleByCompany(request, response);
                } );
            
            post("/people", (request, response) -> {
                 return task.createPeople(request, response);
             } );

             post("/people/company", (request, response) -> {
                 return task.createCompany(request, response);
             } );

             delete("/people/:id", (request, response) -> {
                 return task.deletePeople(request, response);
             } );


            put("/people/:id", (request, response) -> {
                    return task.updatePeople(request, response);
                } );

        });

         after((request, response) -> response.type("application/json")); 
     }
     public static String toJson(Object value){
         Gson gson = new Gson();
        String json = gson.toJson(value);
        return json;
     }
     
}
