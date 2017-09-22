package assignment.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Service {

    public StringProperty name;
    public IntegerProperty minAge;
    public StringProperty description;

    public Service() {
        name = new SimpleStringProperty("");
        minAge = new SimpleIntegerProperty(0);
        description = new SimpleStringProperty("");
    }

    public Service(String name, int age, String description) {
        this.name = new SimpleStringProperty(name);
        this.minAge = new SimpleIntegerProperty(age);
        this.description = new SimpleStringProperty(description);
    }

}
