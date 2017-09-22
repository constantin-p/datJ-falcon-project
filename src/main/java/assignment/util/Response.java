package assignment.util;

/**
 * Created by Codrin on 9/18/2017.
 */
public class Response {

    public boolean success;
    public String msg = "";

    public Response (boolean success) {
        this.success = success;
    }

    public Response (boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
