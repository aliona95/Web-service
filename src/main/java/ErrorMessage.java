/**
 * Created by Aliona and Eimantas on 2017-04-19.
 */
public class ErrorMessage {
    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(String message, String... args) {
        this.message = String.format(message, args);
    }

    public ErrorMessage(Exception e) {
        this.message = e.getMessage();
    }
}