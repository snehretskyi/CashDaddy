package org.example.java_project_iii;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.java_project_iii.forms.CrudForm;

/**
 * Description: A class to test your panes/nodes/scenes. <b>for testing purposes only</b>
 */
public class TestApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        CrudForm loginForm = new CrudForm("Generic Form");

        root.setRight(loginForm);

        Scene scene = new Scene(root, 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}