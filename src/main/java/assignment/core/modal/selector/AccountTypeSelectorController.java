package assignment.core.modal.selector;


import assignment.core.modal.ModalDispatcher;
import assignment.model.AccountType;
import assignment.util.CacheEngine;
import assignment.util.DBOperation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

public class AccountTypeSelectorController extends SelectorBaseController {
    private static final String TITLE = "Select account type";

    private ObservableList<AccountType> accountTypeList = FXCollections.observableArrayList();
    private FilteredList<AccountType> filteredData = new FilteredList<>(accountTypeList, p -> true);

    @FXML
    private TableView<AccountType> tableView;

    public AccountTypeSelectorController(ModalDispatcher modalDispatcher, Stage stage, boolean canCreate) {
        super(modalDispatcher, stage, canCreate);
    }

    @Override
    public void initialize() {
        super.initialize();

        TableColumn<AccountType, String> nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name);

        tableView.getColumns().addAll(nameColumn);
        tableView.setItems(accountTypeList);

        populateTableView();
    }

    @Override
    public AccountType result() {
        AccountType selectedAccountType = tableView.getSelectionModel().getSelectedItem();
        if (super.isOKClicked && selectedAccountType != null) {
            return selectedAccountType;
        }
        return null;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    /*
     *  Helpers
     */
    private void populateTableView() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableView(newValue);
        });

        SortedList<AccountType> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

        CacheEngine.get("accounttypes", new DBOperation<>(() ->
                AccountType.dbGetAll(), (List<AccountType> accountTypes) -> {

            accountTypeList.clear();
            accountTypes.forEach(entry -> {
                accountTypeList.add(entry);
                filterTableView(searchField.getText());
            });
        }));
    }

    private void filterTableView(String searchValue) {
        filteredData.setPredicate(accountType -> {
            // No search term
            if (searchValue == null || searchValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchValue.toLowerCase();

            if (accountType.name.getValue().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            return false;
        });
    }
}
