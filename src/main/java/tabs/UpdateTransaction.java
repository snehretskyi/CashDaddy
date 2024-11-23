package tabs;

import javafx.scene.control.Tab;

import javafx.scene.control.Tab;

public class UpdateTransaction extends Tab {

    private static UpdateTransaction instance;

    private UpdateTransaction() {
        this.setText("Update Transaction");
    }

    public static UpdateTransaction getInstance() {
        if (instance == null) {
            instance = new UpdateTransaction();
        }
        return instance;
    }
}
