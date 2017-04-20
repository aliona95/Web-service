/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
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
        if (data.isPersonValid(person)) {
            data. addPerson(person);
            response.header("PATH","/people/" + person.getId());
            return "Sekmingai prideta";
        }
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("Klaidingi duomenys");
    }

    public static Object deletePerson(Request request, Response response, Data data){
        try{
            if(!data.checkIdExists(Integer.valueOf(request.params("id")))){
                throw new Exception("Nepavyko rasti vartotjo su id");
            }
            data.removePerson(Integer.valueOf(request.params("id")));
            return "Asmuo sekmingai istrintas";
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return  new ErrorMessage("Nepavyko rasti vartotojo su id: " + request.params("id"));
        }
    }

    public static  Object updatePerson(Request request, Response response, Data data){
        boolean notFound = false;
        try {

            Person person = JsonTransformer.fromJson(request.body(), Person.class);
            int id = Integer.valueOf(request.params("id"));
            if(!data.checkIdExists(id)){
                notFound = true;
                throw new Exception("Nepavyko rasti vartotjo su id");
            }
            if (data.isPersonValid(person)){
                data.update(id, person);
                return "Sekmingai atnaujintas";
            }
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage("Klaidingi duomenys");
        } catch (Exception e) {
            if(notFound){
                response.status(HTTP_NOT_FOUND);
                return new ErrorMessage("Nepavyko rasti vartotojo su id: " + request.params("id"));
            }
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(e.getMessage());
        }
    }

    public static Object getByName(Request request, Response response, Data data){
        return data.findByName(request.params("name"));
    }
    public static Object getPeopleByGender(Request request, Response response, Data data){
        return data.findByGender(request.params("gender"));
    }
}
