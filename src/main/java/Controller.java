/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class Controller {

    public static Object getAllPeople(Request request, Response response, Data data){
        return data.getAll();
    }

    public static Object getPerson(Request request, Response response, Data data){
        try {
            Person person = data.get(Integer.valueOf(request.params("id")));
            if(person == null){
                throw new Exception("Su tokiu id asmens nera");
            }
            return person;
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti vartotojo su id: "  + request.params("id"));
        }
    }
    public static Object addPerson(Request request, Response response, Data data){
        Person person = JsonTransformer.fromJson(request.body(), Person.class);
        data.addPerson(person);
        return  "OK";
    }

    public static Object deletePerson(Request request, Response response, Data data){
        try{
            data.removePerson(Integer.valueOf(request.params("id")));
            return "Asmuo sekmingai istrintas";
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return  new ErrorMessage("Nepavyko rasti vartotojo su id: " + request.params("id"));
        }
    }

    public static  Object updatePerson(Request request, Response response, Data data){
        try{
            Person person = JsonTransformer.fromJson(request.body(), Person.class);
            data.update(Integer.valueOf(request.params("id")), person);
            return "Sekmingai atnaujinta";
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return "Su tokiu id asmuo neegzistuoja";
        }
    }

    public static Object getByName(Request request, Response response, Data data){
        return data.findByName(request.params("name"));
    }
    public static Object getPeopleByGender(Request request, Response response, Data data){
        return data.findByGender(request.params("gender"));
    }
}
