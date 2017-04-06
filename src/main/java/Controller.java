import spark.Request;

import spark.Response;

/**
 * Created by Aliona and Eimantas
 */
public class Controller {
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;

    public static Object getAllPeople(Request request, Response response, Data data){
        return data.getAll();
    }
}
