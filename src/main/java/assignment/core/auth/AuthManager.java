package assignment.core.auth;

import assignment.model.Account;
import assignment.util.Response;



/**
 * Created by Codrin on 9/18/2017.
 */

public class AuthManager {

    public Account currentUser = null;
    public Runnable initRootLayoutRequest;
    public Runnable initLoginLayoutRequest;

    public AuthManager(Runnable initRootLayoutRequest, Runnable initLoginLayoutRequest) {
        this.initRootLayoutRequest = initRootLayoutRequest;
        this.initLoginLayoutRequest = initLoginLayoutRequest;
    }

    public Response login(String username, String password) {
        return new Response(true);
    }

    public void signOut() {
        currentUser = null;
        initLoginLayoutRequest.run();
    }
}
