package assignement.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Service {

    private StringProperty name;
    private IntegerProperty ageMinimum;
    private StringProperty description;

    public Service() {
        this.name.set("name here");
        this.ageMinimum.set(0);
        this.description.set("description here");
    }

    public Service(String name, int age, String description) {
        this.name.set(name);
        this.ageMinimum.set(age);
        this.description.set(description);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAgeMinimum() {
        return ageMinimum.get();
    }

    public IntegerProperty ageMinimumProperty() {
        return ageMinimum;
    }

    public void setAgeMinimum(int ageMinimum) {
        this.ageMinimum.set(ageMinimum);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

}
