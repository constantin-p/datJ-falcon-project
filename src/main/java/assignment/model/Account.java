package assignment.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Sigute on 9/18/2017.
 */
public class Account {

    private String id;
    public StringProperty username;

    public Account() {
        id = null;
        username = new SimpleStringProperty("");
    }

    public Account(String id, String username) {
        this.id = id;
        this.username = new SimpleStringProperty(username);
    }
}
