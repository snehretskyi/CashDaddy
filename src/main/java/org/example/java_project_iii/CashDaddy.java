package org.example.java_project_iii;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.java_project_iii.scenes.Dashboard;
import org.example.java_project_iii.scenes.Login;

public class CashDaddy extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dashboard.createDashboard(1280, 720);
        Scene dashboard = Dashboard.getDashboardSceneInstance();
        Login.createLoginScene(primaryStage, dashboard, 1280, 720);

        Scene loginScene = Login.getLoginSceneInstance();

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
