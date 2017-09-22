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

public class BookingFormController extends ModalBaseController {
    private static final String TITLE_CREATE = "Create booking";
    private static final String TITLE_EDIT = "Edit booking";
    private static final String TEMPLATE_PATH = "templates/modal/booking.fxml";

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

    public BookingFormController(ModalDispatcher modalDispatcher, Stage stage, boolean create,
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

    }

    @Override
    public void handleOKAction(ActionEvent event) {
        if (create) {
            boolean success = ValidationHandler.showError(errorLabel,
                    ValidationHandler.validateServiceDBOperation(Service.dbInsert(service)));

            if (success) {
                service = Service.dbGetByName(service.name.getValue());
                super.handleOKAction(event);
            }
        } else {
            boolean success = ValidationHandler.showError(errorLabel,
                    ValidationHandler.validateServiceDBOperation(
                            Service.dbUpdate(service.id, service.name.get(), service.minAge.get(), service.description.get())
                    ));

            if (success) {
                service = Service.dbGetByName(service.name.getValue());
                super.handleOKAction(event);
            }
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
