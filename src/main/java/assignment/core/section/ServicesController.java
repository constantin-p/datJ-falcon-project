package assignment.core.section;

import assignment.core.Controller;
import assignment.core.RootController;
import assignment.model.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

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

        tableView.getColumns().addAll(nameColumn, minAgeColumn, actionColumn);
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Service example = tableView.getSelectionModel().getSelectedItem();
                    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                    Controller exampleController = new Controller(example);

                    FXMLLoader loader = new FXMLLoader(classLoader.getResource("templates/sample.fxml"));
                    loader.setController(exampleController);
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Stage detailsWindow = new Stage();
                    detailsWindow.setTitle("Hello World");
                    detailsWindow.setScene(new Scene(root, 400, 350));
                    detailsWindow.show();
                    System.out.println(tableView.getSelectionModel().getSelectedItem());
                }
            }
        });
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
            serviceList.add(service);
        }
    }

    /*
     *  Helpers
     */
    private void populateTableView() {
        serviceList.add(new Service("paintball", 12, "--"));
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
                                    serviceList.add(service);
                                }
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
