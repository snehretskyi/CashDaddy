package org.example.java_project_iii.scenes;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.java_project_iii.forms.LoginForm;

/**
 * Class for Login scenne. Uses singleton pattern.
 * I'm <i>sure</i> there's a better way to create a separate class for scenes, but
 * last sem it wasn't explained properly.
 * TODO: check if it can be done better.
 * @author Stan
 */
public class Login {

    private Scene loginScene;
    private static LoginForm loginForm;
    private static Login loginInstance;
    private Stage stage;
    private Scene nextScene;
    // default values
    private int width = 1280;
    private int height = 720;

    // getters and setters
    public static LoginForm getLoginForm() {
        return loginForm;
    }

    public Scene getNextScene() {
        return nextScene;
    }

    public Scene getLoginScene() {
        return loginScene;
    }

    public void setNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Private constructor.
     */
    private Login() {

        BorderPane root = new BorderPane();

        loginForm = new LoginForm();

        root.setCenter(loginForm);

        loginScene = new Scene(root, width, height);
    }

    /**
     * Checks if the Dashboard already exists. If yes, returns the scene. If no return null as it can't construct.
     * @return scene Login scene
     */
    public static Scene getLoginSceneInstance() {
        if (loginInstance == null) {
            loginInstance = new Login();
        }

        return loginInstance.getLoginScene();
    }
}
