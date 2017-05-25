import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import  spark.Request;
import  spark.Response;
import java.util.UUID;


public class TaskManager {

    public static final String URL= "http://people/";
   // public static final String URL= "http://localhost:80/";
    User[] users = new User[20];
    int usersCount = 0;
    
   
    public TaskManager(){
      users[0] = new User();
      users[1] = new User();
      users[2] = new User();
      users[0].setUsername("aliona");
      users[0].password = "aliona123";
      users[0].permissions = "admin";
      users[0].token = "prisijungimas";
      users[1].username = "eimantas";
      users[1].password = "eima123";
      users[1].permissions = "admin";
      users[1].token = "prisijungimas1";
      users[2].username = "jonas";
      users[2].password = "jonas";
      users[2].permissions = "user";
      users[2].token = "prisijungimas2";
      usersCount = 3;
    }
   
    
     public String registerHuman(Request request, Response response) throws Exception{
        UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"); 
        try{
            String body = request.body();
            if("".equals(body)){
                response.status(400);
                throw new Exception("400. Invalid input");
            }
            User tempUser = fromJson(request.body(), User.class);
            for(int i = 0 ; i < usersCount; i++){
                if( tempUser.username == null ||  tempUser.username.equals(users[i].username) ){
                    response.status(400);
                    throw new Exception("400. Error with input! Incorrect or missing fields!");  
                }
            }
            if(tempUser.permissions != null && (tempUser.permissions.equals("admin") || tempUser.permissions.equals("user")) 
                    && !tempUser.password.equals("") && !tempUser.username.equals("")
                   && tempUser.password != null ){
                tempUser.token = String.valueOf(uid.randomUUID());
                users[usersCount] = tempUser;
                usersCount++;
            }
            else{
                response.status(400);
                throw new Exception("400. Invalid input");
            }
        }
        catch(Exception e){
            response.status(400);
            return toJson(e.getMessage());
        }
        System.out.println(usersCount);
        return  users[usersCount-1].token;
    }
     public String loginHuman(Request request, Response response) throws Exception{
       int i;
         String body = request.body();
            if("".equals(body)){
                response.status(400);
                throw new Exception("400. Invalid input");
            }
            User tempUser = fromJson(request.body(), User.class);
         try{
            if(tempUser.username == null) {
                response.status(400);
                throw new Exception("400. Missing username!");
            }
            boolean found = false;
            for(i = 0; i < usersCount; i++){
                if(tempUser.username.equals(users[i].username) &&  tempUser.password.equals(users[i].password)){
                   found = true;
                   break;
                }

            }
            if(!found){
                response.status(400);
                throw new Exception("400. Incorrect username or password!");
            }
         }
         catch(Exception e){
             response.status(400);
            return toJson(e.getMessage());
         }
            return users[i].token;
     }
     public boolean checkToken(String token){
         for(int i = 0; i < usersCount; i++){
             if(users[i].token.equals(token)){
                 return true;
             }
         }
         return false;  
     }
     public boolean checkPermission(String token, String permission){
          for(int i = 0; i < usersCount; i++){
             if(users[i].token.equals(token) && users[i].permissions.equals(permission)){
                 return true;
             }
         }
         return false;  
     }


     public String getAllPeoples(Request request, Response response) throws IOException{
         try{
          String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             return getRequest("people");
         }
         else{
            response.status(401);
             throw new Exception("401. Unauthorized request!");
            }
         }
         catch(Exception e){
             return toJson(e.getMessage());
         }
     }


     public String getPeopleById(Request request, Response response) throws IOException, Exception{
         try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "people/" + request.params("id");
             //System.out.println(getRequest(tempRequest));
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             return toJson("400. Invalid id");
         }
     }
     public String getPeopleByName(Request request, Response response){
         try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "people/name/" + request.params("name");
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400.Invalid name!");
         }
         
     }
     public String getPeopleByGender(Request request, Response response){
          try{
         String tokenToCheck = request.headers("Authorization");
         if(checkToken(tokenToCheck)){
             String tempRequest = "people/gender/" + request.params("gender");
            return getRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request!");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400.Invalid gender!");
         }
     }
     public String getCompanies(Request request, Response response){
         try{
             String tokenToCheck = request.headers("Authorization");
             if(checkToken(tokenToCheck)){
                 return getRequest("people/companies");
             }
             else{
                 response.status(401);
                 throw new Exception("401. Unauthorized request!");
             }
         }
         catch(Exception e){
             return toJson(e.getMessage());
         }
     }
    public String getPersonCompany(Request request, Response response){
        try{
            String tokenToCheck = request.headers("Authorization");
            if(checkToken(tokenToCheck)){
                String tempRequest = "people/" + request.params("id") + "/company" ;
                return getRequest(tempRequest);
            }
            else{
                response.status(401);
                throw new Exception("401. Unauthorized request!");
            }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400.Invalid id!");
        }
    }

    public String getPeopleByCompany(Request request, Response response){
        try{
            String tokenToCheck = request.headers("Authorization");
            if(checkToken(tokenToCheck)){
                String tempRequest = "people/company/" + request.params("id");
                return getRequest(tempRequest);
            }
            else{
                response.status(401);
                throw new Exception("401. Unauthorized request!");
            }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400.Invalid id!");
        }
    }
     public String deletePeople(Request request, Response response){
          try{
         String tokenToCheck = request.headers("Authorization");
         if(checkPermission(tokenToCheck,"admin")){
             String tempRequest = "people/" + request.params("id");
            return deleteRequest(tempRequest);
         }
         else{
             response.status(401);
             return toJson("401. Unauthorized request(invalid token or you have no permission");
         }
         }
         catch(Exception e){
             response.status(400);
             return toJson("400. Invalid id!");
         }
     }              
    public String getRequest(String arg) throws IOException{
        String otherService = "";
        String url = URL + arg;
      
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();
    
		while ((inputLine = in.readLine()) != null) {
                     
			response1.append(inputLine);
		}
		in.close(); 
                otherService = response1.toString();
          return otherService;
    }

    public String deleteRequest(String arg) throws ProtocolException, MalformedURLException, IOException{

        String otherService = "";
        String url = URL + arg;
      
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("DELETE");

        con.getResponseCode();

        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response1 = new StringBuffer();
    
		while ((inputLine = in.readLine()) != null) {
                     
			response1.append(inputLine);
		}
		in.close();
                otherService = response1.toString();
          return otherService;
    }
    public String createPutRequest(String arg, String requestString, String requestParam) throws IOException{       
        String otherService = "";
        String url = URL + arg;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if(requestParam.equals("PATCH")){
            con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            con.setRequestMethod("POST");
        }
        else{
           con.setRequestMethod(requestParam); 
        }
         con.setDoOutput(true);
      DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      wr.writeBytes(requestString);
      wr.flush();
      wr.close();
      
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response1 = new StringBuffer();
    
		while ((inputLine = in.readLine()) != null) {
                     
			response1.append(inputLine);
		}
		in.close(); 

                otherService = response1.toString();
                //System.out.println("Kitas serveris grazina" + otherService);
          return otherService;
    }
    public String createPeople(Request request, Response response) throws Exception{
        String answer = " ";
        try{
            String tokenToCheck = request.headers("Authorization");
            if(checkPermission(tokenToCheck,"admin")){
                String tempRequest = "people";
                String tempBody = request.body();
                if("".equals(tempBody)){
                    response.status(400);
                    throw new Exception("400. Invalid input!");
                 }
            //jei ka cia gali riekt uzsetint headeri

                answer = createPutRequest(tempRequest,request.body(),"POST");
                return answer;
            }
            else{
                response.status(401);
                return toJson("401. Unauthorized request(invalid token or you have no permission");
            }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400. Bad request!");
        }

    }
    public String createCompany(Request request, Response response) throws Exception{
        String answer = " ";
        try{
            String tokenToCheck = request.headers("Authorization");
            if(checkPermission(tokenToCheck,"admin")){
                String tempRequest = "people/company";
                String tempBody = request.body();
                if("".equals(tempBody)){
                    response.status(400);
                    throw new Exception("400. Invalid input!");
                }
                //jei ka cia gali riekt uzsetint headeri

                answer = createPutRequest(tempRequest,request.body(),"POST");
                return answer;
            }
            else{
                response.status(401);
                return toJson("401. Unauthorized request(invalid token or you have no permission");
            }
        }
        catch(Exception e){
            response.status(400);
            return toJson("400. Bad request!");
        }

    }

    public String updatePeople(Request request, Response response){
          try{
              String tokenToCheck = request.headers("Authorization");
              if(checkPermission(tokenToCheck,"admin")){
                  String tempRequest = "people/" + request.params("id");
                  String tempBody = request.body();
                  if("".equals(tempBody)){
                        response.status(400);
                        throw new Exception("400. Invalid input!");
                  }

                  return createPutRequest(tempRequest, request.body(),"PUT");
              }
              else{
                  response.status(401);
                  return toJson("401. Unauthorized request(invalid token or you have no permission");
              }
          }
          catch(Exception e){
            response.status(400);
            return toJson("400. Bad request!");
          }
    }
     public static String toJson(Object value){
         Gson gson = new Gson();
        String json = gson.toJson(value);
        return json;
     }

    public static <T extends Object> T  fromJson(String json, Class<T> classe) {
        Gson gson = new Gson();
        gson.fromJson(json, classe);
        return gson.fromJson(json, classe);

    }
}
