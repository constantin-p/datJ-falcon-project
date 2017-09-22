package assignement;

import assignement.model.Service;
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

        serviceLabel.setText(currentServiceToDisplay.nameProperty().getValue());
        ageMinimumLabel.setText(currentServiceToDisplay.ageMinimumProperty().getValue().toString());
        descriptionLabel.setText(currentServiceToDisplay.descriptionProperty().getValue());
    }

    @FXML
    public void initialize() {
        showDetailsOfService();
    }
}
