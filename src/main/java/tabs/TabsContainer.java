package tabs;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabsContainer {
    private static TabsContainer instance;
    private TabPane tabPane;

    private TabsContainer(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public static TabsContainer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("TabsContainer not initialized");
        }
        return instance;
    }

    public static void initialize(TabPane tabPane) {
        if (instance == null) {
            instance = new TabsContainer(tabPane);
        }
    }

    public void addOrActivateTab(Tab tab) {
        if (!tabPane.getTabs().contains(tab)) {
            tabPane.getTabs().add(tab);
        }
        tabPane.getSelectionModel().select(tab);
    }
}

