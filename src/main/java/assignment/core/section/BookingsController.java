package assignment.core.section;

import assignment.core.RootController;
import assignment.model.Booking;

import assignment.util.CacheEngine;
import assignment.util.DBOperation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingsController implements UISection {
    private static final String ACCESS_TYPE_NAME = "bookings";
    private static final String TEMPLATE_PATH = "templates/section/bookings.fxml";
    public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DB_TIME_FORMAT = "HH:mm";

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DB_DATE_FORMAT);
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DB_TIME_FORMAT);


    private RootController rootController;
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    private ToggleGroup modeToggleGroup;

    @FXML
    private TableView<Booking> tableView;

    public BookingsController(RootController rootController) {
        this.rootController = rootController;
    }

    @FXML
    public void initialize() {
        TableColumn<Booking, String> dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(cellData -> {
            return Bindings.createStringBinding(() ->
                    dateFormatter.format(cellData.getValue().startDateTime.getValue()),
                    cellData.getValue().startDateTime);
        });

        TableColumn<Booking, String> startColumn = new TableColumn("Start time");
        startColumn.setCellValueFactory(cellData -> {
            return Bindings.createStringBinding(() ->
                            timeFormatter.format(cellData.getValue().startDateTime.getValue()),
                    cellData.getValue().startDateTime);
        });

        TableColumn<Booking, String> endColumn = new TableColumn("End time");
        endColumn.setCellValueFactory(cellData -> {
            return Bindings.createStringBinding(() ->
                            timeFormatter.format(cellData.getValue().endDateTime.getValue()),
                    cellData.getValue().endDateTime);
        });

        TableColumn<Booking, String> serviceColumn = new TableColumn("Service");
        serviceColumn.setCellValueFactory(cellData -> cellData.getValue().service.getValue().name);

        TableColumn<Booking, String> clientColumn = new TableColumn("Service");
        clientColumn.setCellValueFactory(cellData -> cellData.getValue().account.getValue().username);

        TableColumn<Booking, String> actionColumn = new TableColumn("Actions");
        actionColumn.setCellFactory(getActionCellFactory());
        actionColumn.getStyleClass().add("align-center");


        tableView.getColumns().addAll(dateColumn, startColumn, endColumn, serviceColumn, clientColumn, actionColumn);
        tableView.setItems(bookingList);

        populateTableView();
    }

    public static String getAccessTypeName() {
        return ACCESS_TYPE_NAME;
    }

    public String getTemplatePath() {
        return TEMPLATE_PATH;
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        Booking booking = rootController.modalDispatcher.showCreateBookingModal(null);
        if (booking != null) {
            CacheEngine.markForUpdate("bookings");
        }
    }

    /*
     *  Helpers
     */
    private void populateTableView() {
        CacheEngine.get("bookings", new DBOperation<>(() ->
                Booking.dbGetAll(), (List<Booking> bookings) -> {

            bookingList.clear();
            bookings.forEach(entry -> {
                bookingList.add(entry);
            });
        }));
    }

    private Callback<TableColumn<Booking, String>, TableCell<Booking, String>> getActionCellFactory() {
        return new Callback<TableColumn<Booking, String>, TableCell<Booking, String>>() {
            @Override
            public TableCell call(final TableColumn<Booking, String> param) {
                final TableCell<Booking, String> cell = new TableCell<Booking, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Booking booking = getTableView().getItems().get(getIndex());

                            Button editBtn = new Button("Edit");
                            Button deleteBtn = new Button("Delete");
                            editBtn.setOnAction((ActionEvent event) -> {
                                System.out.println("Open edit");
                                Booking updatedBooking = rootController.modalDispatcher.showEditBookingModal(null, booking);
                                if (updatedBooking != null) {
                                    CacheEngine.markForUpdate("bookings");
                                }
                            });
                            deleteBtn.setOnAction((ActionEvent event) -> {
                                System.out.println("Delete");
                                if (booking.id != null) {
                                    Booking.dbDelete(booking.id);
                                    CacheEngine.markForUpdate("bookings");
                                }
                            });
                            HBox buttons = new HBox(10);
                            buttons.setAlignment(Pos.CENTER);
                            buttons.getChildren().addAll(editBtn, deleteBtn);
                            setGraphic(buttons);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
    }
}
