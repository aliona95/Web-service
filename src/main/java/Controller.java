/**
 * Created by Aliona and Eimantas
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.DataOutputStream;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static spark.Spark.get;
import static spark.Spark.path;

public class Controller {
    //public static String url = "http://192.168.99.100:80";
    public static String url = "http://company:80";
    public static String url1 = "http://controller:80/people";
    public static Object getAllPeople(Request request, Response response, Data data){
        try{
            getCompanyData(url);
            List<Person> people = data.getAll();
            if(people.size() == 0) {
                response.status(HTTP_NOT_FOUND);
                return "Asmenu nera";
            }
            return people;
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko prisijungti");
        }
    }

    public static Object getPerson(Request request, Response response, Data data){
        try {
            Person person = data.get(Integer.valueOf(request.params("id")));
            if(person == null){
                throw new Exception("Su tokiu id asmens nera");
            }
            FinalJson json = new FinalJson();
            int [] companyId = person.getCompanyId();
            String comp = "";
            List<Company> company = new ArrayList<Company>();
            for(int i = 0; i < companyId.length; i++) {
                Company temp = new Company();
                try{
                    comp = getCompanyData(url + "/companies/" + companyId[i]);
                }catch (Exception e){
                    return person;
                }
                temp = JsonTransformer.fromJson(comp, Company.class);
                company.add(temp);
            }
                json.person = person;
                json.companies = company;
                return json;

            /*
            FinalJson json = new FinalJson();
            String company = getCompanyData("http://192.168.99.100:80/companies/" + request.params("id"));
 	        JsonObject jsonObject = new JsonParser().parse(company).getAsJsonObject();
            String companyName = jsonObject.get("name").getAsString();
            String companyCity =  jsonObject.get("city").getAsString();
            String companyNumber = jsonObject.get("phoneNumber").getAsString();
            json.id = person.getId();
            json.name = person.getName();
            json.surname = person.getSurname();
            json.gender = person.getGender();
            json.address = person.getAddress();
            json.companyName = companyName;
            json.companyCity = companyCity;
            json.companyNumber = companyNumber;
            return json;*/

        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko rasti vartotojo su id: "  + request.params("id"));
        }
    }
    public static Object addPerson(Request request, Response response, Data data) throws Exception{
        Person person = JsonTransformer.fromJson(request.body(), Person.class);
        int id = person.getId();
        response.header("PATH","/people/" + person.getId());
        if(id == 0){
            if (data.isPersonValid(person)) {
                int[] companyId = person.getCompanyId();
                List<Company> companies = new ArrayList<Company>();
                try{
                    companies = JsonTransformer.listFromJson(HttpConnectionHandler.sendGet(url + "/companies")+"", Company.class);
                }catch (Exception e){
                    int[] compId = {-1};
                    person.setCompanyId(compId);
                    data.addPerson(person);
                    return "Sekmingai prideta";
                }

                boolean founded = false;
                int index = 0;
                for(int i = 0; i < companyId.length; i++) {
                    index = i;
                    for (Company c :companies) {
                        if(c.getId() == companyId[i]) {
                            founded = true;
                        }
                    }
                    if(!founded){
                        response.status(HTTP_NOT_FOUND);
                        return new ErrorMessage("Nerasta imone su id: " + companyId[index]);
                    }else{
                        founded = false;
                    }
                }
                data.addPerson(person);
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
                    int[] companyId = person.getCompanyId();
                    List<Company> companies = new ArrayList<Company>();
                    try{
                        companies = JsonTransformer.listFromJson(HttpConnectionHandler.sendGet(url + "/companies")+"", Company.class);
                    }catch (Exception e){
                        int[] compId = {-1};
                        person.setCompanyId(compId);
                        data.addPerson(person);
                        return "Sekmingai atnaujinta";
                    }

                    boolean founded = false;
                    int index = 0;
                    for(int i = 0; i < companyId.length; i++) {
                        index = i;
                        for (Company c :companies) {
                            if(c.getId() == companyId[i]) {
                                founded = true;
                            }
                        }
                        if(!founded){
                            response.status(HTTP_NOT_FOUND);
                            return new ErrorMessage("Nerasta imone su id: " + companyId[index]);
                        }else{
                            founded = false;
                        }
                    }
                    data.update(id, person);
                    return "Sekmingai atnaujinta";
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
            Person person = data.get(id);

            if(person == null){
                throw new Exception("Nerasta asmens");
            }
            int [] companyId = person.getCompanyId();
            try{
                String comp = "";
                List<Company> company = new ArrayList<Company>();
                for(int i = 0; i < companyId.length; i++) {
                    Company temp = new Company();
                    comp = getCompanyData(url + "/companies/" + companyId[i]);
                    temp = JsonTransformer.fromJson(comp, Company.class);
                    company.add(temp);
                }
                return company;
            }catch (Exception e){
                return person.getCompanyId();
            }
        }catch(Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nerasta asmens su id " + request.params("id"));
        }
    }

    private static String getCompanyData(String urlToRead) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
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
    public static Object createCompany(Request request, Response response, Data data) {
        try {
            Object obj = HttpConnectionHandler.sendPost(url + "/companies", request.body());
            ///int companyId = Integer.valueOf(request.params("id"));
            Company company = JsonTransformer.fromJson(request.body(), Company.class);
            response.header("PATH","/company/" + company.getId()); ////Su id pataisyt!!!
            return obj;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko prisijungti");
        }
    }
    public static Object updateCompany(Request request, Response response, Data data) {
        try{
            //Company company = JsonTransformer.fromJson(request.body(), Company.class);
            URL url1 = new URL(url + "/companies/" + request.params("id"));
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(request.body());
            wr.flush();
            wr.close();
            getServiceResponse(conn);
            return "Sekmingai atnaujinta";
        }catch (Exception e){
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko prisijungti");
        }

    }
    public static Object getCompanies(Request request, Response response, Data data) {
        try {
            List<Company> company = new ArrayList<Company>();
            company = JsonTransformer.listFromJson(HttpConnectionHandler.sendGet(url + "/companies")+"", Company.class);
            return company;
        } catch (Exception e) {
            response.status(HTTP_NOT_FOUND);
            return new ErrorMessage("Nepavyko prisijungti");
        }
    }
    static class FinalJson{
	  /*  int id;
        String name;
        String surname;
        String gender;
        String address;
        int [] companyId;*/
	    Person person;
        List<Company> companies;
        /*
        String companyName;
        String companyCity;
        String companyNumber;
         */
    }
    public static void getServiceResponse(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader( conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
    }
}
