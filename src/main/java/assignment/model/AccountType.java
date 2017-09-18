package assignment.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Sigute on 9/18/2017.
 */
public class AccountType {
   private static final String DB_TABLE_NAME = "accounttypes";
   private static final String DB_INTERSECTION_TABLE_NAME = "accounttype_accesstype";

    public String id;
    public StringProperty name;
    public ObservableList<AccessType> permissions = FXCollections.observableArrayList();

    public AccountType() {
        id = null;
        name = new SimpleStringProperty("");
    }

    public AccountType(String id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);

        if (this.id != null) {
            permissions.setAll(AccountType.dbGetAllAccessTypes(this.id));
        }
    }

    public void addPermission(AccessType accessType) {
        if (AccountType.dbInsertAccessType(id, accessType.id) == 1) {
            permissions.setAll(AccountType.dbGetAllAccessTypes(id));
        }
    }

    public boolean hasAccess(AccessType accessType) {
        for (AccessType accountAccessType : permissions) {
            if (accountAccessType.name.getValue().equals(accessType.name.getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAccess(String accessTypeName) {
        for (AccessType accountAccessType : permissions) {
            if (accountAccessType.name.getValue().equals(accessTypeName)) {
                return true;
            }
        }
        return false;
    }
}