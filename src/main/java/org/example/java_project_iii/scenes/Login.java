package org.example.java_project_iii.scenes;

package org.example.java_project_iii.scenes;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.example.java_project_iii.forms.LoginForm;
import tabs.AddTransaction;
import tabs.DeleteTransaction;
import tabs.SummaryReport;
import tabs.UpdateTransaction;

/**
 * Class for Login scenne. Uses singleton pattern.
 * I'm <i>sure</i> there's a better way to create a separate class for scenes, but
 * last sem it wasn't explained properly.
 * TODO: check if it can be done better.
 * @author Stan
 */
public class Login {

    private Scene loginScene;
    private static Login loginInstance;

    // getter
    public Scene getLoginScene() {
        return loginScene;
    }

    /**
     * Private constructor.
     */
    private Login() {
        BorderPane root = new BorderPane();

        LoginForm loginForm = new LoginForm();

        root.setCenter(loginForm);

        loginScene = new Scene(root);
    }

    /**
     * Checks if the Dashboard already exists. If yes, returns the scene
     * @return scene Dashboard scene
     */
    public Scene getLoginSceneInstance() {
        if (loginInstance == null) {
            loginInstance = new Login();
        }

        return loginInstance.getLoginScene();
    }
}
