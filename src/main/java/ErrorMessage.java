/**
 * Created by Aliona and Eimantas
 */
public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(Exception e) {
        this.message = e.getMessage();
    }
}