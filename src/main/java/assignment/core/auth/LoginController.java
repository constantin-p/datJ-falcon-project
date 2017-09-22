package assignment.core.auth;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    private AuthManager authManager;

//    @FXML
//    public ObservableList<String> options = FXCollections.observableArrayList(
//            "Management",
//            "Employee",
//            "Client"
//    );

    @FXML
    public ChoiceBox accountType = new ChoiceBox(FXCollections.observableArrayList("A", "B", "C"));

    @FXML
    private TextField usernameTestField;
    private BooleanProperty isUsernameValid = new SimpleBooleanProperty(false);

    @FXML
    private PasswordField passwordField;
    private BooleanProperty isPasswordValid = new SimpleBooleanProperty(false);

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;



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

    @FXML
    public void handleRegisterAction(ActionEvent event) {
        authManager.initRootLayoutRequest.run();
    }
}
