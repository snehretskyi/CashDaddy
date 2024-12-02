package org.example.java_project_iii.tabs;

import javafx.scene.control.Tab;

/**
 * Tab for updating a selected transaction
 * Singleton design pattern
 */

public class UpdateTransaction extends Tab {

    // Singleton instance
    private static UpdateTransaction instance;

    /**
     * Private constructor
     * sets the title and applies a css style class
     */
    private UpdateTransaction() {
        this.setText("Update Transaction");
        this.getStyleClass().add("tab-add-transaction");
    }

    /**
     * @return singleton instance of UpdateTransaction
     */
    public static UpdateTransaction getInstance() {
        if (instance == null) {
            instance = new UpdateTransaction();
        }
        return instance;
    }
}
