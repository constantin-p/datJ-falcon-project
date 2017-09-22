package assignment.util;

import javafx.scene.control.Label;

public final class ValidationHandler {
    // Error messages

    public static final String ERROR_SERVICE_NAME_REQUIRED = "Name required";
    public static final String ERROR_SERVICE_NAME_SHORT = "Name too short (<2)";
    public static final String ERROR_SERVICE_NAME_LONG = "Name too long (>25)";
    public static final String ERROR_SERVICE_NAME_INVALID = "Invalid name (non-alphanumeric)";

    public static final String ERROR_SERVICE_MIN_AGE_REQUIRED = "Age limit required";
    public static final String ERROR_SERVICE_MIN_AGE_LOW = "Age limit too low (<0)";
    public static final String ERROR_SERVICE_MIN_AGE_HIGH = "Age limit too high (>100)";
    public static final String ERROR_SERVICE_MIN_AGE_INVALID = "Invalid agelimit (not a number)";

    public static final String ERROR_SERVICE_DESCRIPTION_REQUIRED = "Description required";
    public static final String ERROR_SERVICE_DESCRIPTION_SHORT = "Description too short (<2)";
    public static final String ERROR_SERVICE_DESCRIPTION_LONG = "Description too long (>25)";
    public static final String ERROR_SERVICE_DESCRIPTION_INVALID = "Invalid description (non-alphanumeric)";

    private ValidationHandler() {}

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
        } else if (!description.matches("[a-zA-Z0-9]+")) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_INVALID);
        } else if (description.length() <= 1) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_SHORT);
        } else if (description.length() > 25) {
            return new Response(false, ERROR_SERVICE_DESCRIPTION_LONG);
        }
        return new Response(true);
    }
}
