package assignment.core;

import assignment.core.auth.AuthManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Created by Codrin on 9/18/2017.
 */

public class RootController {

    private AuthManager authManager;

    public RootController(AuthManager authManager, Stage primaryStage) {
        this.authManager = authManager;
    }

    @FXML
    public void handleSignOutAction(ActionEvent event) {
        authManager.signOut();
    }

}
