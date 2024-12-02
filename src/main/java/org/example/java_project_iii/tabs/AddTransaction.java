package org.example.java_project_iii.tabs;

import javafx.scene.control.Tab;

/**
 * Tab for adding a new transaction.
 */
public class AddTransaction extends Tab {

    /**
     * Creates the "Add Transaction" tab (constructor)
     */
    public AddTransaction() {
        this.setText("Add Transaction");
        this.getStyleClass().add("tab-add-transaction");
    }
}
