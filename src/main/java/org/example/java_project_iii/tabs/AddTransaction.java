package org.example.java_project_iii.tabs;

import javafx.scene.control.Tab;

public class AddTransaction extends Tab {
    public AddTransaction(){
        this.setText("Add Transaction");
        this.getStyleClass().add("tab-add-transaction");
    }
}