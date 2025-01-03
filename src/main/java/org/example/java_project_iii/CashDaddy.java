package org.example.java_project_iii;

import org.example.java_project_iii.database.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java_project_iii.scenes.Login;

/**
 * Main application class for the CashDaddy app
 * Initializes login scene and connects to the database
 * After a successful connection, it redirects to the dashboard scene
 */
public class CashDaddy extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene loginScene = Login.getLoginSceneInstance();
        // set stage for login form
        Login.getLoginForm().setStage(primaryStage);


        // try connecting to db
        Scene dashboard = null;
        try {
            Database connection = Database.getInstance();
            // load dashboard, skip login
            Login.getLoginForm().loadNextScene();
            // if unsuccessful, either there is no credentials file, or they're wrong
        } catch (Exception e) {
            primaryStage.setScene(loginScene);
        }

        primaryStage.setTitle("CashDaddy");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
