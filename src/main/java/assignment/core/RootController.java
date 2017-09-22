package assignment.core;

import assignment.core.modal.ModalDispatcher;
import assignment.core.section.ServicesController;
import assignment.core.section.UISection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Supplier;

public class RootController {

    public ModalDispatcher modalDispatcher;

    @FXML
    private TabPane tabPane;

    public RootController(Stage primaryStage) {
        modalDispatcher = new ModalDispatcher(primaryStage);
    }

    @FXML
    private void initialize() {
        loadSections();
    }

    private void loadSections() {

        loadSection(ServicesController.getAccessTypeName(),
            () -> new ServicesController(this));
        loadSection(ServicesController.getAccessTypeName(),
            () -> new ServicesController(this));
        loadSection(ServicesController.getAccessTypeName(),
            () -> new ServicesController(this));
        loadSection(ServicesController.getAccessTypeName(),
            () -> new ServicesController(this));
        loadSection(ServicesController.getAccessTypeName(),
            () -> new ServicesController(this));
    }

    private void loadSection(String accessTypeName, Supplier<UISection> createSectionControllerRequest) {
        // The section is visible only if the current user has access to it
        // if (authManager.currentUser.hasAccess(accessTypeName)) {
//        try {
        // Initialize the controller
        UISection controller = createSectionControllerRequest.get();
        Tab sectionTab = new Tab(getDisplayString(accessTypeName));

        // Load the template
        Node layout = loadSectionContent(controller);
        sectionTab.setContent(layout);

        tabPane.getTabs().add(sectionTab);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        // }
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
    }

    /*
    *  Helpers
    */
    private String getDisplayString(String string) {

        string = string.replaceAll("_", " ");
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}
