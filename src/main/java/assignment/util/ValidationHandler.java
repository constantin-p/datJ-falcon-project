package assignment.util;

import javafx.scene.control.Control;
import javafx.scene.control.Label;

/**
 * Created by Codrin on 9/18/2017.
 */
public class ValidationHandler {

    public static boolean validateControl(Control control, Label errorLabel, Response validation) {
        if (showError(errorLabel, validation)) {
            control.setStyle("-fx-text-fill: #5a5a5a;");
            return true;
        } else {
            control.setStyle("-fx-text-fill: #e53935;");
            return false;
        }
    }

    public static boolean showError(Label errorLabel, Response validation) {
        if (validation.success) {
            errorLabel.setVisible(false);
            return true;
        } else {
            errorLabel.setText(validation.msg);
            errorLabel.setVisible(true);
            return false;
        }
    }
}
