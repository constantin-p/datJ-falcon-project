package assignment.core;


import assignment.model.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    private Service currentServiceToDisplay;

    @FXML
    Label serviceLabel;
    @FXML
    Label ageMinimumLabel;
    @FXML
    Label descriptionLabel;
    @FXML
    Button okButton;


    public Controller(Service example) {
        this.currentServiceToDisplay = example;
    }

    public void showDetailsOfService() {

        serviceLabel.setText(currentServiceToDisplay.name.getValue());
        ageMinimumLabel.setText(currentServiceToDisplay.minAge.getValue().toString());
        descriptionLabel.setText(currentServiceToDisplay.description.getValue());
    }

    @FXML
    public void initialize() {
        showDetailsOfService();
    }
}
