package assignment.core.auth;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private AuthManager authManager;
    @FXML
    private TextField usernameTestField;
    private BooleanProperty isUsernameValid = new SimpleBooleanProperty(false);

    @FXML
    private PasswordField passwordField;
    private BooleanProperty isPasswordValid = new SimpleBooleanProperty(false);

    @FXML
    private Button loginButton;

    public LoginController(AuthManager authManager) {
        this.authManager = authManager;
    }

    @FXML
    public void initialize() {


    }

    @FXML
    public void handleLoginAction(ActionEvent event) {
        authManager.initRootLayoutRequest.run();
    }
}
