package assignment.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import store.db.Database;
import store.db.Storable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Service implements Storable {
    public static final String DB_TABLE_NAME = "services";
    public static final String[] DB_TABLE_COLUMNS = {"id", "name", "min_age", "description"};


    public String id;
    public StringProperty name;
    public IntegerProperty minAge;
    public StringProperty description;

    public Service() {
        id = null;
        name = new SimpleStringProperty("");
        minAge = new SimpleIntegerProperty(0);
        description = new SimpleStringProperty("");
    }

    public Service(String id, String name, int age, String description) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.minAge = new SimpleIntegerProperty(age);
        this.description = new SimpleStringProperty(description);
    }

    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<>();

        values.put("name", name.getValue());
        values.put("min_age", minAge.getValue().toString());
        values.put("description", description.getValue());

        return values;
    }

    public static Service construct(HashMap<String, String> valuesMap) {

        String id = valuesMap.get("id");
        String name = valuesMap.get("name");
        int minAge = Integer.valueOf(valuesMap.get("min_age"));
        String description = valuesMap.get("description");


        return new Service(id, name, minAge, description);
    }


    /*
     *  DB helpers
     */
    public static List<Service> dbGetAll() {
        List<Service> result = new ArrayList<>();

        try {
            List<HashMap<String, String>> returnList = Database.getTable(DB_TABLE_NAME)
                    .getAll(Arrays.asList(DB_TABLE_COLUMNS),
                            null, null);

            returnList.forEach((HashMap<String, String> valuesMap) -> {
                result.add(Service.construct(valuesMap));
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static Service dbGetByName(String name) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("name", name);

        try {
            HashMap<String, String> returnValues = Database.getTable(DB_TABLE_NAME)
                    .get(Arrays.asList(DB_TABLE_COLUMNS),
                            searchQuery, new HashMap<>());

            if (returnValues.get("name") != null && returnValues.get("name").equals(name)) {
                return Service.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dbInsert(Service service) {
        try {
            return Database.getTable(DB_TABLE_NAME)
                    .insert(service.deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbUpdate(String serviceID, String name, Integer minAge, String description) {
        HashMap<String, String> entry = new HashMap<>();
        entry.put("name", name);
        entry.put("min_age", minAge.toString());
        entry.put("description", description);

        HashMap<String, String> whitelist = new HashMap<>();
        whitelist.put("id", serviceID);

        try {
            return Database.getTable(DB_TABLE_NAME)
                    .update(entry, whitelist, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
