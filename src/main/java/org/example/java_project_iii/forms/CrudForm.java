package org.example.java_project_iii.forms;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.java_project_iii.pojo.*;
import org.example.java_project_iii.tables.*;
import org.example.java_project_iii.tabs.AllTransactions;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Both create and update are almost the same forms. This abstract class provides members and functionality
 * that can be reused. <br> <br>
 *
 * For simplicity's sake, members are protected. Would be rather tedious to refer to nodes via getters and setters. <br>
 * Validates input, won't let to break application.
 */
public abstract class CrudForm extends Form {
    // org.example.java_project_iii.tabs to redirect to
    protected Tab displayTab;
    protected TabPane tabPane;

    // db instances
    protected CategoriesTable categoriesTable = CategoriesTable.getInstance();
    protected AccountsTable accountsTable = AccountsTable.getInstance();
    protected TransactionTypeTable transactionTypeTable = TransactionTypeTable.getInstance();
    protected TransactionsTable transactionsTable = TransactionsTable.getInstance();
    protected RecurringTransactionTable recurringTransactionsTable = RecurringTransactionTable.getInstance();
    protected ArrayList<TransactionTypePOJO> transactionTypes = transactionTypeTable.getAllTransaction_types();
    protected ArrayList<CategoriesPOJO> allCategories = categoriesTable.getAllCategories();
    protected ArrayList<AccountPOJO> allAccounts = accountsTable.getAllAccounts();
    protected ArrayList<TransactionTypePOJO> allTransactionTypes = transactionTypeTable.getAllTransaction_types();

    // some lovely labels
    protected Label dateLabel = new Label("Date:");;
    protected Label amountLabel = new Label("Amount:");;
    protected Label categoryLabel = new Label("Category:");;
    protected Label recurringLabel= new Label("Recurring?");;
    protected Label recurringIntervalLabel = new Label("Interval (in days):");;
    protected Label accountLabel = new Label("Account:");;
    protected Label transactionTypeLabel = new Label("Transaction Type:");;
    protected Label descriptionLabel = new Label("Description:");;

    // form nodes
    protected GridPane formGrid = new GridPane();
    protected DatePicker datePicker = new DatePicker(LocalDate.now());
    protected TextField amountField = new TextField();
    protected ComboBox<CategoriesPOJO> categoryBox = new ComboBox<>();
    protected CheckBox recurringCheckBox = new CheckBox();
    protected VBox recurringIntervalBox = new VBox();
    protected Spinner<Integer> recurringIntervalSpinner = new Spinner<>(0, 365, 1);
    protected ComboBox<AccountPOJO> accountComboBox = new ComboBox<>();
    protected HBox transactionTypeRadioBox = new HBox();
    protected ToggleGroup transactionTypeGroup = new ToggleGroup();
    protected TextArea descriptionArea = new TextArea();
    protected Button confirmButton = new Button("Confirm");
    protected Button cancelButton = new Button("Cancel");


    /**
     * Constructor that initializes form behavior.
     * @throws Exception
     */
    public CrudForm() throws Exception {
        // unfortunately I had to turn off text input for date
        // exception handling doesn't work as expected if user inputs invalid date (・へ・)
        datePicker.setEditable(false);

        // no need to create nodes :-), parent class working for us
        recurringIntervalBox.getChildren().addAll(recurringIntervalLabel, recurringIntervalSpinner);
        // hide the box unless it is selected
        recurringIntervalBox.setVisible(false);

        // if checkbox is selected, add ability to enter interval
        recurringCheckBox.setOnAction((event) -> {
            recurringIntervalBox.setVisible(recurringCheckBox.isSelected());
        });
        // set default value to 30 days
        recurringIntervalSpinner.getValueFactory().setValue(30);

        // setting combobox items
        categoryBox.setItems(FXCollections.observableArrayList(categoriesTable.getAllCategories()));

        accountComboBox.setItems(FXCollections.observableArrayList(accountsTable.getAllAccounts()));

        transactionTypes.forEach((TransactionTypePOJO transactionType) -> {
            RadioButton transactionTypeRadio = new RadioButton(transactionType.toString());
            // set org.example.java_project_iii.pojo associated with the button
            transactionTypeRadio.setUserData(transactionType);
            transactionTypeRadioBox.getChildren().add(transactionTypeRadio);
            transactionTypeRadio.setToggleGroup(transactionTypeGroup);
        });

        descriptionArea.setPrefRowCount(3);


        formGrid.add(dateLabel, 0, 1);
        formGrid.add(datePicker, 1, 1);
        formGrid.add(amountLabel, 0, 2);
        formGrid.add(amountField, 1, 2);
        formGrid.add(categoryLabel, 0, 3);
        formGrid.add(categoryBox, 1, 3);
        formGrid.add(recurringLabel, 0, 4);
        formGrid.add(recurringCheckBox, 1, 4);
        formGrid.add(recurringIntervalBox, 1, 5);
        formGrid.add(accountLabel, 0, 6);
        formGrid.add(accountComboBox, 1, 6);
        formGrid.add(transactionTypeLabel, 0, 7);
        formGrid.add(transactionTypeRadioBox, 1, 7);
        formGrid.add(descriptionLabel, 0, 9);
        formGrid.add(descriptionArea, 1, 9);

        formGrid.add(confirmButton, 4, 10);
        formGrid.add(cancelButton, 5, 10);

        // logic for buttons
        confirmButton.setOnAction((event) -> {
            try {
                // refuse to submit if fields are empty
                if (amountField.getText().isEmpty()
                        || categoryBox.getSelectionModel().getSelectedItem() == null
                        || accountComboBox.getValue() == null
                        ||  transactionTypeGroup.getSelectedToggle() == null
                        || descriptionArea.getText().isEmpty()) {
                    getErrorText().setText("All fields are required!");
                    animateErrorText(getErrorText());
                } else if (descriptionArea.getText().length() > 255) {
                    // check if the description is over 255 characters
                    getErrorText().setText("Description is over 255 characters!");
                    animateErrorText(getErrorText());
                } else if (Double.parseDouble(amountField.getText()) > 99999999.99) {
                    getErrorText().setText("We only support transactions with value up to $99,999,999.99");
                    animateErrorText(getErrorText());
                }
                else {
                    // using abstract methods
                    submit();

                    clear();

                    tabPane.getSelectionModel().select(displayTab);

                    AllTransactions.getInstance().refreshTable();

                }

            } catch (DateTimeParseException e) {
                getErrorText().setText("Wrong date format!");
                animateErrorText(getErrorText());
            } catch (NumberFormatException e) {
                getErrorText().setText("Invalid number entered!");
                animateErrorText(getErrorText());
            } catch (Exception e) {
                // generic error handling
                getErrorText().setText("Fatal Error!");
                animateErrorText(getErrorText());
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction((event) -> {
            try {
                clear();
            } catch (Exception e) {
                // generic error handling
                getErrorText().setText("Fatal Error!");
                animateErrorText(getErrorText());
            }
        });

        // styling (˶◕‿◕˶)
        formGrid.getStyleClass().add("form-grid");

        // // Apply CSS style classes in the form
        dateLabel.getStyleClass().add("form-label");
        amountLabel.getStyleClass().add("form-label");
        categoryLabel.getStyleClass().add("form-label");
        recurringLabel.getStyleClass().add("form-label");
        accountLabel.getStyleClass().add("form-label");
        transactionTypeLabel.getStyleClass().add("form-label");
        descriptionLabel.getStyleClass().add("form-label");
        confirmButton.getStyleClass().add("confirm-button");
        cancelButton.getStyleClass().add("clear-button");

        VBox.setMargin(formGrid, new Insets(0, 25, 25, 25));

        // Apply styles for the transaction type radio box
        transactionTypeRadioBox.getStyleClass().add("transaction-type-radio-box");

        // Load the CSS file
        formGrid.getStylesheets().add(getClass().getClassLoader().getResource("css/forms.css").toExternalForm());

        this.getChildren().addAll(getErrorText(),formGrid);

    }

    // getters and setters
    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Tab getDisplayTab() {
        return displayTab;
    }

    public void setDisplayTab(Tab displayTab) {
        this.displayTab = displayTab;
    }

    /**
     * Abstract method that is called when user submits form.  <br>
     * Usually <i>should</i> perform some DB operations.
     * @throws Exception
     */
    public abstract void submit() throws Exception;

    /**
     * Abstract method to clear/cancel the form
     */
    public abstract void clear();
}
