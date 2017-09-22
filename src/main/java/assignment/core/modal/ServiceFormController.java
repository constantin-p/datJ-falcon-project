package assignment.core.modal;

import assignment.model.Service;
import assignment.util.ValidationHandler;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class ServiceFormController extends ModalBaseController {
    private static final String TITLE_CREATE = "Create service";
    private static final String TITLE_EDIT = "Edit service";
    private static final String TEMPLATE_PATH = "templates/modal/service.fxml";

    private Service service;
    private boolean create;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField nameTextField;
    private BooleanProperty isNameValid = new SimpleBooleanProperty(false);

    @FXML
    private TextField minAgeTextField;
    private BooleanProperty isMinAgeValid = new SimpleBooleanProperty(false);

    @FXML
    private TextArea descriptionTextArea;
    private BooleanProperty isDescriptionValid = new SimpleBooleanProperty(false);

    public ServiceFormController(ModalDispatcher modalDispatcher, Stage stage, boolean create,
                                 Service service) {
        super(modalDispatcher, stage);
        this.service = service;
        this.create = create;

        isNameValid.set(ValidationHandler.validateSessionName(service.name.get()).success);
        isMinAgeValid.set(ValidationHandler.validateSessionMinAge(service.minAge.asString().get()).success);
        isDescriptionValid.set(ValidationHandler.validateSessionDescription(service.description.get()).success);
    }

    @Override
    public void initialize() {
        super.initialize();

        super.isDisabled.bind(
                isNameValid.not().or(
                        isMinAgeValid.not().or(
                                isDescriptionValid.not()
                        )
                )
        );

        nameTextField.textProperty().bindBidirectional(service.name);
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isNameValid.set(ValidationHandler.showError(errorLabel,
                    ValidationHandler.validateSessionName(newValue)));
            System.out.println(isNameValid + " " + isMinAgeValid + " " + isDescriptionValid);

        });

        minAgeTextField.textProperty().bindBidirectional(service.minAge, new NumberStringConverter());
        minAgeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            isMinAgeValid.set(ValidationHandler.showError(errorLabel,
                    ValidationHandler.validateSessionMinAge(newValue)));
        });

        descriptionTextArea.textProperty().bindBidirectional(service.description);
        descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            isDescriptionValid.set(ValidationHandler.showError(errorLabel,
                    ValidationHandler.validateSessionDescription(newValue)));
        });
    }

    @Override
    public void handleOKAction(ActionEvent event) {
        if (create) {
//            boolean success = ValidationHandler.showError(errorLabel,
//                    ValidationHandler.validateClientDBOperation(Client.dbInsert(client)));
//
//            if (success) {
//                client = Client.dbGetByEmail(client.email.getValue());
            super.handleOKAction(event);
//            }
        }
    }

    @Override
    public Service result() {
        if (super.isOKClicked && !super.isDisabled.getValue()) {
            return service;
        }
        return null;
    }


    @Override
    public String getTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    public String getTitle() {
        return create
                ?   TITLE_CREATE
                :   TITLE_EDIT;
    }
}
