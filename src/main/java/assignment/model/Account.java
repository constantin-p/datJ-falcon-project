package assignment.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sigute on 9/18/2017.
 */
public class Account {
    private static final String DB_TABLE_NAME = "accesstypes";

    private String id;
    public StringProperty username;
    public ObjectProperty<AccountType> type;

    public Account() {
        id = null;
        username = new SimpleStringProperty("");
        type = new SimpleObjectProperty<>(null);
    }

    public Account(String id, String username, AccountType type) {
        this.id = id;
        this.username = new SimpleStringProperty(username);
        this.type = new SimpleObjectProperty(type);
    }
}
