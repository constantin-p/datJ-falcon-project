package assignment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sigute on 9/18/2017.
 */
public class AccessType {
    private static final String DB_TABLE_NAME = "accesstypes";

    public String id;
    public StringProperty name;

    public AccessType() {
        id = null;
        name = new SimpleStringProperty("");
    }

    public AccessType(String id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }
}
