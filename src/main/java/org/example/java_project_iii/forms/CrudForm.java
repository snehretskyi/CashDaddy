package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pojo.AccountPOJO;
import pojo.CategoriesPOJO;
import pojo.Transaction_typePOJO;
import tables.*;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class CrudForm extends Form {
    protected CategoriesTable categoriesTable = CategoriesTable.getInstance();
    protected AccountsTable accountsTable =  AccountsTable.getInstance();
    protected Transaction_typeTable transactionTypeTable = Transaction_typeTable.getInstance();;
    protected TransactionsTable transactionsTable = TransactionsTable.getInstance();;
    protected RecurringTransactionTable recurringTransactionsTable = RecurringTransactionTable.getInstance();;

    GridPane formGrid = new GridPane();

    Label dateLabel = new Label("Date:");
    DatePicker datePicker = new DatePicker(LocalDate.now());

    Label amountLabel = new Label("Amount:");
    TextField amountField = new TextField();

    Label recurringLabel = new Label("Recurring?");
    CheckBox recurringCheckBox = new CheckBox();

    VBox recurringIntervalBox = new VBox();
    Label recurringIntervalLabel = new Label("Interval (in days)");

    Label categoryLabel = new Label("Category:");
    ComboBox<CategoriesPOJO> categoryBox = new ComboBox<>();

    Label accountLabel = new Label("Account:");
    ComboBox<AccountPOJO> accountComboBox = new ComboBox<>();

    Label transactionTypeLabel = new Label("Transaction Type:");

    HBox transactionTypeRadioBox = new HBox();
    ToggleGroup transactionTypeGroup = new ToggleGroup();
    ArrayList<Transaction_typePOJO> transactionTypes = transactionTypeTable.getAllTransaction_types();

    Label descriptionLabel = new Label("Description (up to 255 characters):");
    TextArea descriptionArea = new TextArea();

    Button confirmButton = new Button("Confirm");
    Button cancelButton = new Button("Cancel");

    public CrudForm() throws Exception {
        super();
        datePicker.setEditable(false);

        Spinner<Integer> recurringIntervalSpinner = new Spinner<>(0, 100, 1);
        recurringIntervalBox.getChildren().addAll(recurringIntervalLabel, recurringIntervalSpinner);
        // hide the box unless it is selected
        recurringIntervalBox.setVisible(false);

        // if checkbox is selected, add ability to enter interval
        recurringCheckBox.setOnAction((event) -> {
            recurringIntervalBox.setVisible(recurringCheckBox.isSelected());
        });

        categoryBox.setItems(FXCollections.observableArrayList(categoriesTable.getAllCategories()));
        accountComboBox.setItems(FXCollections.observableArrayList(accountsTable.getAllAccounts()));

        transactionTypes.forEach((Transaction_typePOJO transactionType) -> {
            RadioButton transactionTypeRadio = new RadioButton(transactionType.toString());
            // set pojo associated with the button
            transactionTypeRadio.setUserData(transactionType);
            transactionTypeRadioBox.getChildren().add(transactionTypeRadio);
            transactionTypeRadio.setToggleGroup(transactionTypeGroup);
        });

        descriptionArea.setPrefRowCount(3);
    }


    // Implement detailed setup methods here...
    // (These would contain the common setup logic from CreateForm and UpdateForm)

    protected abstract void setupEventHandlers();

    private void styleComponents() {
        // Common styling logic
        formGrid.getStyleClass().add("form-grid");
        // Add other common styling...
    }

    // Add utility methods like validateFields(), clearForm(), etc.
}