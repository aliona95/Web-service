/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static spark.Spark.get;
import static spark.Spark.path;

public class Controller {

    public static Object getAllPeople(Request request, Response response, Data data){
        List<Person> people = data.getAll();
        if(people.size() == 0){
            response.status(HTTP_NOT_FOUND);
            return "Asmenu nera";
        }
        return people;
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
        int id = person.getId();
        if(id == 0){
            if (data.isPersonValid(person)) {
                data.addPerson(person);
                response.header("PATH","/people/" + person.getId());
                return "Sekmingai prideta";
            }
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage("Truksta " + data.personMissedFields(person));
        }else{
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage("Nurodete id");
        }

    }

    public static Object deletePerson(Request request, Response response, Data data){
        try{
            if(!data.checkIdExists(Integer.valueOf(request.params("id")))){
                throw new Exception("Nepavyko rasti vartotojo su id");
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
            int id1 = person.getId();
            if(id1 == 0){
                if(!data.checkIdExists(id)){
                    //notFound = true;
                    throw new Exception("Nepavyko rasti vartotojo su id");
                }
                if (data.isPersonValid(person)){
                    data.update(id, person);
                    return "Sekmingai atnaujintas";
                }
                response.status(HTTP_BAD_REQUEST);
                return new ErrorMessage("Truksta " + data.personMissedFields(person));
            }else{
                response.status(HTTP_BAD_REQUEST);
                return new ErrorMessage("Nurodete id");
            }
        } catch (Exception e) {
            if(notFound){
                response.status(HTTP_NOT_FOUND);
                return new ErrorMessage("Nepavyko rasti vartotojo su id: " + request.params("id"));
            }
            response.status(HTTP_BAD_REQUEST);
            return new ErrorMessage(e.getMessage()); //jei uzklausoje klaida
        }
    }

    public static Object getByName(Request request, Response response, Data data){
        List<Person> people = data.findByName(request.params("name"));
        if(people.size() == 0){
            response.status(HTTP_NOT_FOUND);
            return "Nera asmens su tokiu vardu";
        }
        return people;
    }
    public static Object getPeopleByGender(Request request, Response response, Data data){
        List<Person> people = data.findByGender(request.params("gender"));
        if(request.params("gender").equals("male") || (request.params("gender").equals("female"))){
            if(people.size() == 0){
                response.status(HTTP_NOT_FOUND);
                return "Nera tokios lyties atstovu";
            }
            return people;
        }
        response.status(HTTP_BAD_REQUEST);
        return new ErrorMessage("Klaidingai ivesta lytis");
    }

    public static Object getPersonCompany(Request request, Response response, Data data){
        try{
            int id = Integer.valueOf(request.params("id"));
            Person person = data.getCompany(id);

            if(person == null){
                throw new Exception("Nerasta asmens");
            }

            String company = getCompanyData("http://localhost:80/companies/" + id);

            return company;
        }catch(Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nerasta asmens su id " + request.params("id"));
        }
    }

    private static String getCompanyData(String url) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url1 = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while((line = reader.readLine()) != null){
            result.append(line);
        }
        reader.close();
        return result.toString();
    }

    public static Object getPeopleByCompany(Request request, Response response, Data data){
        try {
            int companyId = Integer.valueOf(request.params("id"));
            List<Person> people = data.getByCompany(companyId);
            if(people.size() == 0){
                throw new Exception("Imoneje niekas nedirba");
            }
            return people;
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nerasta imone su id " + request.params("id"));
        }
    }
}
