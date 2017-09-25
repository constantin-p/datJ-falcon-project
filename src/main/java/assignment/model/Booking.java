package assignment.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import store.db.Database;
import store.db.Storable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Booking implements Storable {
    public static final String DB_TABLE_NAME = "bookings";
    public static final String[] DB_TABLE_COLUMNS = {"id", "date", "start", "end", "service_id", "account_id"};
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DB_TIME_FORMAT = "HH:mm";

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DB_DATE_FORMAT);
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DB_TIME_FORMAT);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DB_DATE_FORMAT + " " + DB_TIME_FORMAT);

    public String id;
    public ObjectProperty<LocalDateTime> startDateTime;
    public ObjectProperty<LocalDateTime> endDateTime;

    public ObjectProperty<Service> service;
    public ObjectProperty<Account> account;

    public Booking() {
        id = null;
        startDateTime = new SimpleObjectProperty<>(LocalDateTime.now());
        endDateTime = new SimpleObjectProperty<>(startDateTime.getValue().plusHours(1));
        service = new SimpleObjectProperty(null);
        account = new SimpleObjectProperty(null);
    }

    public Booking(String id, LocalDateTime startDateTime, LocalDateTime endDateTime,
            Service service, Account account) {
        this.id = id;
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.service = new SimpleObjectProperty(service);
        this.account = new SimpleObjectProperty(account);
    }

    /*
     *  DB integration
     */
    @Override
    public HashMap<String, String> deconstruct() {
        HashMap<String, String> values = new HashMap<>();

        values.put("date", startDateTime.getValue().format(dateFormatter));
        values.put("start", startDateTime.getValue().format(timeFormatter));
        values.put("end", endDateTime.getValue().format(timeFormatter));
        values.put("service_id", service.getValue().id);
        values.put("account_id", account.getValue().id);

        return values;
    }

    public static Booking construct(HashMap<String, String> valuesMap) {

        String id = valuesMap.get("id");

        LocalDateTime startDateTime = LocalDateTime.parse(valuesMap.get("date") + " " + valuesMap.get("start"), dateTimeFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(valuesMap.get("date") + " " + valuesMap.get("end"), dateTimeFormatter);

        Service service = Service.dbGet(valuesMap.get("service_id"));
        Account account = Account.dbGet(valuesMap.get("account_id"));

        return new Booking(id, startDateTime, endDateTime, service, account);
    }


    /*
     *  DB helpers
     */
    public static List<Booking> dbGetAll() {
        List<Booking> result = new ArrayList<>();

        try {
            List<HashMap<String, String>> returnList = Database.getTable(DB_TABLE_NAME)
                    .getAll(Arrays.asList(DB_TABLE_COLUMNS),
                            null, null);

            returnList.forEach((HashMap<String, String> valuesMap) -> {
                result.add(Booking.construct(valuesMap));
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static Booking dbGetByName(String name) {
        HashMap<String, String> searchQuery = new HashMap<>();
        searchQuery.put("name", name);

        try {
            HashMap<String, String> returnValues = Database.getTable(DB_TABLE_NAME)
                    .get(Arrays.asList(DB_TABLE_COLUMNS),
                            searchQuery, new HashMap<>());

            if (returnValues.get("name") != null && returnValues.get("name").equals(name)) {
                return Booking.construct(returnValues);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int dbInsert(Booking booking) {
        try {
            return Database.getTable(DB_TABLE_NAME)
                    .insert(booking.deconstruct());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbUpdate(String bookingID, LocalDateTime startDateTime, LocalDateTime endDateTime,
                               Service service, Account account) {
        HashMap<String, String> entry = new HashMap<>();

        entry.put("date", startDateTime.format(dateFormatter));
        entry.put("start", startDateTime.format(timeFormatter));
        entry.put("end", endDateTime.format(timeFormatter));
        entry.put("service_id", service.id);
        entry.put("account_id", account.id);

        HashMap<String, String> whitelist = new HashMap<>();
        whitelist.put("id", bookingID);

        try {
            return Database.getTable(DB_TABLE_NAME)
                    .update(entry, whitelist, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int dbDelete(String bookingID) {

        HashMap<String, String> whitelist = new HashMap<>();
        whitelist.put("id", bookingID);

        try {
            return Database.getTable(DB_TABLE_NAME)
                    .delete(whitelist, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

