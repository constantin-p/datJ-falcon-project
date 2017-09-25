package assignment.util;

import assignment.model.AccountType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

public final class ValidationHandler {
    // Error messages
    public static final String ERROR_DB = "DB error";
    public static final String ERROR_DB_CONNECTION = "DB connection error";

    public static final String ERROR_ACCOUNT_INVALID = "Invalid credentials";
    public static final String ERROR_ACCOUNT_USERNAME_REQUIRED = "Username required";
    public static final String ERROR_ACCOUNT_USERNAME_SHORT = "Username too short";
    public static final String ERROR_ACCOUNT_USERNAME_LONG = "Username too long";
    public static final String ERROR_ACCOUNT_USERNAME_INVALID = "Invalid username (non-alphanumeric)";
    public static final String ERROR_ACCOUNT_USERNAME_NONEXISTENT = "Username not registered";
    public static final String ERROR_ACCOUNT_USERNAME_DUPLICATE = "Username already registered";
    public static final String ERROR_ACCOUNT_ACCOUNT_TYPE_REQUIRED = "Account type required";
    public static final String ERROR_ACCOUNT_PASSWORD_REQUIRED = "Password required";
    public static final String ERROR_ACCOUNT_PASSWORD_SHORT = "Password too short";
    public static final String ERROR_ACCOUNT_PASSWORD_LONG = "Password too long";
    public static final String ERROR_ACCOUNT_PASSWORD_INVALID = "Invalid password (non-alphanumeric)";
    public static final String ERROR_ACCOUNT_PASSWORD_DIFFERENT = "Passwords do not match";

    public static final String ERROR_ACCOUNT_TYPE_NAME_REQUIRED = "Name required";
    public static final String ERROR_ACCOUNT_TYPE_NAME_SHORT = "Name too short";
    public static final String ERROR_ACCOUNT_TYPE_NAME_LONG = "Name too long";
    public static final String ERROR_ACCOUNT_TYPE_NAME_INVALID = "Invalid name (non-alphanumeric)";
    public static final String ERROR_ACCOUNT_TYPE_NAME_DUPLICATE = "Name already registered";

    public static final String ERROR_SERVICE_NAME_REQUIRED = "Name required";
    public static final String ERROR_SERVICE_NAME_DUPLICATE = "Name already registered";
    public static final String ERROR_SERVICE_NAME_SHORT = "Name too short (<2)";
    public static final String ERROR_SERVICE_NAME_LONG = "Name too long (>25)";
    public static final String ERROR_SERVICE_NAME_INVALID = "Invalid name (non-alphanumeric)";

    public static final String ERROR_SERVICE_MIN_AGE_REQUIRED = "Age limit required";
    public static final String ERROR_SERVICE_MIN_AGE_LOW = "Age limit too low (<0)";
    public static final String ERROR_SERVICE_MIN_AGE_HIGH = "Age limit too high (>100)";
    public static final String ERROR_SERVICE_MIN_AGE_INVALID = "Invalid age limit (not a number)";

    public static final String ERROR_SERVICE_DESCRIPTION_REQUIRED = "Description required";
    public static final String ERROR_SERVICE_DESCRIPTION_SHORT = "Description too short (<2)";
    public static final String ERROR_SERVICE_DESCRIPTION_LONG = "Description too long (>25)";
    public static final String ERROR_SERVICE_DESCRIPTION_INVALID = "Invalid description (non-alphanumeric)";

    private ValidationHandler() {}

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

    // TODO: Trim strings
    // Validation filters
    // ACCOUNT fields
    public static Response validateAccountUsername(String username) {
        if (username == null || username.isEmpty()) {
            return new Response(false, ERROR_ACCOUNT_USERNAME_REQUIRED);
        } else if (!username.matches("[a-zA-Z0-9]+")) {
            return new Response(false, ERROR_ACCOUNT_USERNAME_INVALID);
        } else if (username.length() <= 3) {
            return new Response(false, ERROR_ACCOUNT_USERNAME_SHORT);
        } else if (username.length() > 25) {
            return new Response(false, ERROR_ACCOUNT_USERNAME_LONG);
        }
        return new Response(true);
    }

    public static Response validateAccountPassword(String password) {
        if (password == null || password.isEmpty()) {
            return new Response(false, ERROR_ACCOUNT_PASSWORD_REQUIRED);
        } else if (!password.matches("[a-zA-Z0-9]+")) {
            return new Response(false, ERROR_ACCOUNT_PASSWORD_INVALID);
        } else if (password.length() <= 3) {
            return new Response(false, ERROR_ACCOUNT_PASSWORD_SHORT);
        } else if (password.length() > 25) {
            return new Response(false, ERROR_ACCOUNT_PASSWORD_LONG);
        }
        return new Response(true);
    }

    public static Response validateAccountRepeatPassword(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            return new Response(false, ERROR_ACCOUNT_PASSWORD_DIFFERENT);
        }
        return new Response(true);
    }

    public static Response validateAccountAccountType(AccountType accountType) {
        if (accountType == null || accountType.id == null) {
            return new Response(false, ERROR_ACCOUNT_ACCOUNT_TYPE_REQUIRED);
        }
        return new Response(true);
    }

    // ACCOUNT TYPE fields
    public static Response validateAccountTypeName(String name) {
        if (name == null || name.isEmpty()) {
            return new Response(false, ERROR_ACCOUNT_TYPE_NAME_REQUIRED);
        } else if (!name.matches("[a-zA-Z0-9 ]+")) {
            return new Response(false, ERROR_ACCOUNT_TYPE_NAME_INVALID);
        } else if (name.length() <= 3) {
            return new Response(false, ERROR_ACCOUNT_TYPE_NAME_SHORT);
        } else if (name.length() > 25) {
            return new Response(false, ERROR_ACCOUNT_TYPE_NAME_LONG);
        }
        return new Response(true);
    }

    public static Response validateAccountTypeDBOperation(int returnValue) {
        if (returnValue == 1) {
            return new Response(true);
        } else if (returnValue == -1) {
            return new Response(false, ERROR_ACCOUNT_TYPE_NAME_DUPLICATE);
        }
        return new Response(false, ValidationHandler.ERROR_DB_CONNECTION);
    }

    // SESSION fields
    public static Response validateSessionName(String name) {
        if (name == null || name.isEmpty()) {
            return new Response(false, ERROR_SERVICE_NAME_REQUIRED);
        } else if (!name.matches("[a-zA-Z0-9]+")) {
            return new Response(false, ERROR_SERVICE_NAME_INVALID);
        } else if (name.length() <= 1) {
            return new Response(false, ERROR_SERVICE_NAME_SHORT);
        } else if (name.length() > 25) {
            return new Response(false, ERROR_SERVICE_NAME_LONG);
        }
        return new Response(true);
    }

    public static Response validateSessionMinAge(String minAge) {
        if (minAge == null) {
            return new Response(false, ERROR_SERVICE_MIN_AGE_REQUIRED);
        } else {
            try {
                int minAgeValue = Integer.parseInt(minAge);
                if (minAgeValue < 0) {
                    return new Response(false, ERROR_SERVICE_MIN_AGE_LOW);
                } else if (minAgeValue > 100) {
                    return new Response(false, ERROR_SERVICE_MIN_AGE_HIGH);
                }
            } catch (NumberFormatException e) {
                return new Response(false, ERROR_SERVICE_MIN_AGE_INVALID);
            }
        }
        return new Response(true);
    }

    public static Response validateSessionDescription(String description) {
        if (description == null || description.isEmpty()) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_REQUIRED);
        } else if (!description.matches("[a-zA-Z0-9 ]+")) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_INVALID);
        } else if (description.length() <= 1) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_SHORT);
        } else if (description.length() > 25) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_LONG);
        }
        return new Response(true);
    }

    public static Response validateServiceDBOperation(int returnValue) {
        if (returnValue == 1) {
            return new Response(true);
        } else if (returnValue == -1) {
            return new Response(false, ERROR_SERVICE_NAME_DUPLICATE);
        }
        return new Response(false, ValidationHandler.ERROR_DB_CONNECTION);
    }
}
