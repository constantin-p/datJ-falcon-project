package assignment.core;

import assignment.core.auth.AuthManager;
import assignment.core.modal.ModalDispatcher;
import assignment.core.section.AccountTypesController;
import assignment.core.section.AccountsController;
import assignment.core.section.ServicesController;
import assignment.core.section.UISection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ui.DetailTooltip;

import java.io.IOException;
import java.util.function.Supplier;

public class RootController {

    private AuthManager authManager;
    public ModalDispatcher modalDispatcher;
    public DetailTooltip tooltipDispatcher;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label usernameLabel;

    public RootController(AuthManager authManager, Stage primaryStage) {
        this.authManager = authManager;
        modalDispatcher = new ModalDispatcher(primaryStage);
        tooltipDispatcher = new DetailTooltip(primaryStage);
    }

    @FXML
    private void initialize() {
        if (authManager.currentUser != null) {
            usernameLabel.textProperty().bind(authManager.currentUser.username);

            loadSections();
        }
    }

    private void loadSections() {
        loadSection(AccountTypesController.getAccessTypeName(),
            () -> new AccountTypesController(this));
        loadSection(AccountsController.getAccessTypeName(),
            () -> new AccountsController(this));
        loadSection(ServicesController.getAccessTypeName(),
            () -> new ServicesController(this));
    }

    private void loadSection(String accessTypeName, Supplier<UISection> createSectionControllerRequest) {
        // The section is visible only if the current user has access to it
        if (authManager.currentUser.hasAccess(accessTypeName)) {
            try {
                // Initialize the controller
                UISection controller = createSectionControllerRequest.get();
                Tab sectionTab = new Tab(getDisplayString(accessTypeName));

                // Load the template
                Node layout = loadSectionContent(controller);
                sectionTab.setContent(layout);

                tabPane.getTabs().add(sectionTab);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private Node loadSectionContent(UISection controller) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            FXMLLoader loader = new FXMLLoader(classLoader
                    .getResource(controller.getTemplatePath()));

            loader.setController(controller);
            Parent layout = loader.load();

            return layout;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @FXML
    public void handleSignOutAction(ActionEvent event) {
        authManager.signOut();
    }

    /*
    *  Helpers
    */
    private String getDisplayString(String string) {

        string = string.replaceAll("_", " ");
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}
