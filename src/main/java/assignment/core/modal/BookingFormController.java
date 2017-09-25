package assignment.core.modal;

import assignment.model.Booking;
import assignment.util.ValidationHandler;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ui.TimeSpinner;


public class BookingFormController extends ModalBaseController {
    private static final String TITLE_CREATE = "Create booking";
    private static final String TITLE_EDIT = "Edit booking";
    private static final String TEMPLATE_PATH = "templates/modal/booking.fxml";

    private Booking booking;
    private boolean create;

    @FXML
    private Label errorLabel;

    @FXML
    private DatePicker dateDatePicker;
    private BooleanProperty isDateValid = new SimpleBooleanProperty(false);

    @FXML
    private TimeSpinner startTimeSpinner;
    private BooleanProperty isStartValid = new SimpleBooleanProperty(false);

    @FXML
    private TimeSpinner endTimeSpinner;
    private BooleanProperty isEndValid = new SimpleBooleanProperty(false);

    @FXML
    private Button selectServiceButton;
    private BooleanProperty isServiceValid = new SimpleBooleanProperty(false);

    @FXML
    private Button selectClientButton;
    private BooleanProperty isClientValid = new SimpleBooleanProperty(false);

    public BookingFormController(ModalDispatcher modalDispatcher, Stage stage, boolean create,
                                 Booking booking) {
        super(modalDispatcher, stage);
        this.booking = booking;
        this.create = create;

//        isNameValid.set(ValidationHandler.validateSessionName(service.name.get()).success);
//        isMinAgeValid.set(ValidationHandler.validateSessionMinAge(service.minAge.asString().get()).success);
//        isDescriptionValid.set(ValidationHandler.validateSessionDescription(service.description.get()).success);
    }

    @Override
    public void initialize() {
        super.initialize();

        super.isDisabled.bind(
                isDateValid.not().or(
                        isStartValid.not().or(
                                isEndValid.not().or(
                                        isServiceValid.not().or(
                                                isClientValid.not()
                                        )
                                )
                        )
                )
        );

//        nameTextField.textProperty().bindBidirectional(service.name);
//        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            isNameValid.set(ValidationHandler.showError(errorLabel,
//                    ValidationHandler.validateSessionName(newValue)));
//        });
//
//        minAgeTextField.textProperty().bindBidirectional(service.minAge, new NumberStringConverter());
//        minAgeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            isMinAgeValid.set(ValidationHandler.showError(errorLabel,
//                    ValidationHandler.validateSessionMinAge(newValue)));
//        });
//
//        descriptionTextArea.textProperty().bindBidirectional(service.description);
//        descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
//            isDescriptionValid.set(ValidationHandler.showError(errorLabel,
//                    ValidationHandler.validateSessionDescription(newValue)));
//        });
    }

    @Override
    public void handleOKAction(ActionEvent event) {
        if (create) {
//            boolean success = ValidationHandler.showError(errorLabel,
//                    ValidationHandler.validateBookingDBOperation(Booking.dbInsert(booking)));

//            if (success) {
                // service = Service.dbGetByName(service.name.getValue());
                super.handleOKAction(event);
//            }
        } else {
//            boolean success = ValidationHandler.showError(errorLabel,
//                    ValidationHandler.validateBookingDBOperation(
//                            Booking.dbUpdate(service.id, service.name.get(), service.minAge.get(), service.description.get())
//                    ));
//
//            if (success) {
//                service = Service.dbGetByName(service.name.getValue());
//                super.handleOKAction(event);
//            }
        }
    }


    @FXML
    public void handleSelectServiceAction(ActionEvent event) {

    }

    @FXML
    public void handleSelectClientAction(ActionEvent event) {

    }

    @Override
    public Booking result() {
        if (super.isOKClicked && !super.isDisabled.getValue()) {
            return booking;
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
