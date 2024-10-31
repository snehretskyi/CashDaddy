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
    private static Login loginInstance;
    private Stage stage;
    private Scene nextScene;
    private int width;
    private int height;

    // getters and setters
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
    private Login(Stage stage, Scene nextScene, int width, int height) {

        BorderPane root = new BorderPane();

        LoginForm loginForm = new LoginForm(stage, nextScene);

        root.setCenter(loginForm);

        loginScene = new Scene(root, width, height);
    }

    /**
     * As the class needs some arguments (width and height), it's necessary to first set it up
     */
    public static void createLoginScene(Stage stage, Scene nextScene, int width, int height) {
        loginInstance = new Login(stage, nextScene, width, height);
    }

    /**
     * Checks if the Dashboard already exists. If yes, returns the scene. If no return null as it can't construct.
     * @return scene Login scene
     */
    public static Scene getLoginSceneInstance() {
        if (loginInstance == null) {
            System.out.println("Please ensure you have already created the scene.");
            return null;
        }

        return loginInstance.getLoginScene();
    }
}
