package assignment.core.section;

import assignment.core.RootController;
import assignment.model.Service;

import assignment.util.CacheEngine;
import assignment.util.DBOperation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServicesController implements UISection {
    private static final String ACCESS_TYPE_NAME = "services";
    private static final String TEMPLATE_PATH = "templates/section/services.fxml";


    private RootController rootController;
    private ObservableList<Service> serviceList = FXCollections.observableArrayList();

    @FXML
    private TableView<Service> tableView;

    public ServicesController(RootController rootController) {
        this.rootController = rootController;
    }

    @FXML
    public void initialize() {
        TableColumn<Service, String> nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name);

        TableColumn<Service, String> minAgeColumn = new TableColumn("Age limit");
        minAgeColumn.setCellValueFactory(cellData -> cellData.getValue().minAge.asString());

        TableColumn<Service, String> actionColumn = new TableColumn("Actions");
        actionColumn.setCellFactory(getActionCellFactory());
        actionColumn.getStyleClass().add("align-center");


        tableView.setRowFactory(getRowFactory());
        tableView.getColumns().addAll(nameColumn, minAgeColumn, actionColumn);
        tableView.setItems(serviceList);

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
        Service service = rootController.modalDispatcher.showCreateServiceModal(null);
        if (service != null) {
            CacheEngine.markForUpdate("services");
        }
    }

    /*
     *  Helpers
     */
    private void populateTableView() {
        CacheEngine.get("services", new DBOperation<>(() ->
            Service.dbGetAll(), (List<Service> services) -> {

            serviceList.clear();
            services.forEach(entry -> {
                serviceList.add(entry);
            });
        }));
    }

    private Callback<TableColumn<Service, String>, TableCell<Service, String>> getActionCellFactory() {
        return new Callback<TableColumn<Service, String>, TableCell<Service, String>>() {
            @Override
            public TableCell call(final TableColumn<Service, String> param) {
                final TableCell<Service, String> cell = new TableCell<Service, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Service service = getTableView().getItems().get(getIndex());

                            Button editBtn = new Button("Edit");
                            Button deleteBtn = new Button("Delete");
                            editBtn.setOnAction((ActionEvent event) -> {
                                System.out.println("Open edit");
                                Service updatedService = rootController.modalDispatcher.showEditServiceModal(null, service);
                                if (updatedService != null) {
                                    CacheEngine.markForUpdate("services");
                                }
                            });
                            deleteBtn.setOnAction((ActionEvent event) -> {
                                System.out.println("Delete");
                                if (service.id != null) {
                                    Service.dbDelete(service.id);
                                    CacheEngine.markForUpdate("services");
                                }
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

    private Callback<TableView<Service>, TableRow<Service>> getRowFactory() {
        return new Callback<TableView<Service>, TableRow<Service>>() {
            @Override
            public TableRow<Service> call(TableView<Service> tableView) {

                final TableRow<Service> row = new TableRow<>();
                row.hoverProperty().addListener((observable) -> {
                    final Service service = row.getItem();

                    if (row.isHover() && service != null) {
                        Map<String, String> details = new LinkedHashMap<>();
                        details.put("Name", service.name.getValue());
                        details.put("Age limit", service.minAge.getValue().toString());
                        details.put("Details", service.description.getValue());
                        rootController.tooltipDispatcher.show(row.hoverProperty(), row.localToScreen(row.getBoundsInLocal()), details);
                    }
                });

                return row;
            }

        };
    }
}
