package org.example.java_project_iii.forms;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class Description: a login form for entering database credentials.
 * This form includes fields for the host, database name, username, and password.
 * Validates input, saves result to a file.
 * @author Stan
 */
public class LoginForm extends Form {
    private boolean loginSuccessful = false;
    private Stage stage;
    private Scene nextScene;

    /**
     * @return true if login was successful, false if no.
     */
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    /**
     * @param loginSuccessful set true if login is successful
     */
    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    /**
     * Constructs a new LoginForm, styles included
     */
    public LoginForm(Stage stage, Scene nextScene) {
        super();

        this.stage = stage;
        this.nextScene = nextScene;

        // creating nodes
        Text welcomeText = new Text("Welcome!");
        Text instructionsText = new Text("Please enter your DB credentials");

        Label hostLabel = new Label("Host:");
        TextField hostField = new TextField();
        VBox hostWrapper = new VBox(hostLabel, hostField);

        Label dbNameLabel = new Label("DB Name:");
        TextField dbNameField = new TextField();
        VBox dbNameWrapper = new VBox(dbNameLabel, dbNameField);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        VBox usernameWrapper = new VBox(usernameLabel, usernameField);

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        VBox passwordWrapper = new VBox(passwordLabel, passwordField);

        Button submitButton = new Button("Submit!");

        // styling =-)
        welcomeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 64));
        instructionsText.setFont(Font.font("Helvetica", 24));

        submitButton.setPrefSize(120 ,20);
        VBox.setMargin(submitButton, new Insets(10, 0, 0,0));

        hostWrapper.setAlignment(Pos.BASELINE_LEFT);
        dbNameWrapper.setAlignment(Pos.BASELINE_LEFT);
        usernameWrapper.setAlignment(Pos.BASELINE_LEFT);
        passwordWrapper.setAlignment(Pos.BASELINE_LEFT);

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(640);

        // logic
        EventHandler submitEvent = event -> {
            String host = hostField.getText().trim();
            String dbName = dbNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // refuse to submit if fields are empty
            if (host.isEmpty() || dbName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                getErrorText().setText("All fields are required!");
                animateErrorText(getErrorText());
            } else {
                getErrorText().setText("");
                try {
                    saveCredentials(host, dbName, username, password);

                    loadNextScene();
                } catch (IOException e) {
                    getErrorText().setText("Error saving credentials!");
                    animateErrorText(getErrorText());
                }
            }

            // some pseudocode for the future

            // test connection to db

            /* if (DATABASE CONNECTION FAILED) {
                    errorText.setText("Couldn't connect to DB!");
                    this.setLoginSuccessful(false);
                } else {
                    this.setLoginSuccessful(true)
                }
             */
        };

        submitButton.setOnAction(submitEvent);

        this.getChildren().addAll(welcomeText, instructionsText, getErrorText(),
               hostWrapper, dbNameWrapper, usernameWrapper, passwordWrapper, submitButton);
    }

    /**
     * Saves the provided database credentials to a file.
     * @param host the database host.
     * @param dbName the name of the database.
     * @param userName the username for the database.
     * @param password the password for the database.
     */
    public void saveCredentials(String host, String dbName, String userName, String password) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("src/main/resources/db_credentials.txt"))) {
            fileWriter.write(host + "\n");
            fileWriter.append(dbName + "\n");
            fileWriter.append(userName + "\n");
            fileWriter.append(password);
        } catch (IOException e) {
            System.out.println("UH OH! " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads next scene that was passed in the constructor
     */
    public void loadNextScene() {
        stage.setScene(this.nextScene);
    }
}
