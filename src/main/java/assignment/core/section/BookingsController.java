package assignment.core.section;

import assignment.core.RootController;
import assignment.model.Booking;
import assignment.model.Booking;

import assignment.model.Service;
import assignment.util.CacheEngine;
import assignment.util.DBOperation;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingsController implements UISection {
    private static final String ACCESS_TYPE_NAME = "bookings";
    private static final String TEMPLATE_PATH = "templates/section/bookings.fxml";

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private RootController rootController;
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    private TableView<Booking> tableView;

    public BookingsController(RootController rootController) {
        this.rootController = rootController;
    }

    @FXML
    public void initialize() {
        TableColumn<Booking, String> nameColumn = new TableColumn("Booking Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().service.getValue().name);

        TableColumn<Booking, String> startColumn = new TableColumn("Start");
        startColumn.setCellValueFactory(cellData -> {
            return Bindings.createStringBinding(() -> formatter.format(cellData.getValue().start.getValue()),
                    cellData.getValue().start);
        });

        TableColumn<Booking, String> endColumn = new TableColumn("End");
        endColumn.setCellValueFactory(cellData -> {
            return Bindings.createStringBinding(() -> formatter.format(cellData.getValue().end.getValue()),
                    cellData.getValue().end);
        });


        TableColumn<Booking, String> actionColumn = new TableColumn("Actions");
        actionColumn.setCellFactory(getActionCellFactory());
        actionColumn.getStyleClass().add("align-center");

        tableView.getColumns().addAll(nameColumn, startColumn, endColumn, actionColumn);
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
            bookingList.add(booking);

        }
    }

    @FXML
    public void handleDayAction(ActionEvent event) {

    }


    @FXML
    public void handleWeekAction(ActionEvent event) {

    }


    @FXML
    public void handleMonthAction(ActionEvent event) {

    }


    /*
     *  Helpers
     */
    private void populateTableView() {
        bookingList.add(new Booking("0", new Service("0", "test", 0, "desc"), LocalDateTime.now(), LocalDateTime.now()));
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
                            Booking Booking = getTableView().getItems().get(getIndex());

                            Button editBtn = new Button("Edit");
                            Button deleteBtn = new Button("Delete");
                            editBtn.setOnAction((ActionEvent event) -> {
                                System.out.println("Open edit");

                            });
                            deleteBtn.setOnAction((ActionEvent event) -> {
                                System.out.println("Delete");

                            });
                            HBox buttons = new HBox(6);
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
