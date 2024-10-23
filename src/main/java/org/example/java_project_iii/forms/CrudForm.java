package org.example.java_project_iii.forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.time.LocalDate;

public class CrudForm extends VBox {
    private String formName;

    public CrudForm(String formName) {
        this.formName = formName;

        GridPane formGrid = new GridPane();

        Label formNameLabel = new Label(this.formName);

        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label categoryLabel = new Label("Category:");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll();

        Label transactionTypeLabel = new Label("Transaction Type:");
        RadioButton incomeRadio = new RadioButton("Income");
        RadioButton expenseRadio = new RadioButton("Expense");
        RadioButton savingsRadio = new RadioButton("Savings");
        ToggleGroup transactionTypeGroup = new ToggleGroup();
        incomeRadio.setToggleGroup(transactionTypeGroup);
        expenseRadio.setToggleGroup(transactionTypeGroup);
        savingsRadio.setToggleGroup(transactionTypeGroup);

        Label descriptionLabel = new Label("Description:");
        TextField descriptionField = new TextField();

        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");

        GridPane transactionTypePane = new GridPane();
        transactionTypePane.setHgap(10);
        transactionTypePane.add(incomeRadio, 0, 0);
        transactionTypePane.add(expenseRadio, 1, 0);
        transactionTypePane.add(savingsRadio, 2, 0);
        
        formGrid.setPadding(new Insets(20));
        formGrid.setVgap(20);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        formGrid.add(dateLabel, 0, 1);
        formGrid.add(datePicker, 1, 1);
        formGrid.add(amountLabel, 0, 2);
        formGrid.add(amountField, 1, 2);
        formGrid.add(categoryLabel, 0, 3);
        formGrid.add(categoryComboBox, 1, 3);
        formGrid.add(transactionTypeLabel, 0, 4);
        formGrid.add(transactionTypePane, 1, 4);
        formGrid.add(descriptionLabel, 0, 5);
        formGrid.add(descriptionField, 1, 5);

        formGrid.add(confirmButton, 4, 6);
        formGrid.add(cancelButton, 5, 6);

        formGrid.setBorder(new Border(new BorderStroke( Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        formGrid.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        formNameLabel.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                new BorderWidths(2, 2, 0, 2)
        )));
        formNameLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        formNameLabel.setPadding(new Insets(5));
        formNameLabel.setTranslateX(50);


        formGrid.setMaxWidth(844);
        formGrid.setMaxHeight(480);


        this.getChildren().addAll(formNameLabel, formGrid);
    }
}
