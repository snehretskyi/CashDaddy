package org.example.java_project_iii;

import database.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java_project_iii.forms.LoginForm;
import org.example.java_project_iii.scenes.Dashboard;
import org.example.java_project_iii.scenes.Login;

public class CashDaddy extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Login.createLoginScene(primaryStage, 1280, 720);

        Scene loginScene = Login.getLoginSceneInstance();

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


        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
