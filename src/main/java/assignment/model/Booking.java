package assignment.model;

import javafx.beans.property.*;
import store.db.Database;
import store.db.Storable;

import java.net.ServerSocket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Booking {
    public static final String DB_TABLE_NAME = "booking.fxml";
    public static final String[] DB_TABLE_COLUMNS = {"id", "service_id", "start", "end"};


    public String id;
    public ObjectProperty<Service> service;
    public ObjectProperty<LocalDateTime> start;
    public ObjectProperty<LocalDateTime> end;

    public Booking() {
        id = null;
        service = new SimpleObjectProperty<>(new Service());
        start = new SimpleObjectProperty<>(LocalDateTime.now());
        end = new SimpleObjectProperty<>(LocalDateTime.now());
    }

    public Booking(String id, Service name, LocalDateTime start , LocalDateTime end) {
        this.id = id;
        this.service = new SimpleObjectProperty<>(name);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
    }

    /*
     *  DB integration
     */

}
