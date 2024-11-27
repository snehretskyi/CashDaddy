package org.example.java_project_iii.forms;

import database.Database;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.java_project_iii.scenes.Dashboard;
import services.ImageCreator;
import services.AnimationHelper;
import javax.swing.*;
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
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Constructs a new LoginForm, styles included
     */
    public LoginForm() {
        super();

        // creating nodes
        Text welcomeText = new Text("Welcome To CashDaddy!!...");
        Text greetingText = new Text("Glad to see you again!");
        Text instructionsText = new Text("Please enter your DB credentials");

        Label hostLabel = new Label("Host:");
        TextField hostField = new TextField();

        Label dbNameLabel = new Label("DB Name:");
        TextField dbNameField = new TextField();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button submitButton = new Button("Submit!");

        // Create an ImageView
        ImageView iconImageView = ImageCreator.createImageView("/Images/piggybank1.gif", 270,230);

        //Layout
        VBox headingVbox = new VBox(welcomeText, greetingText);
        HBox headingHBox = new HBox(headingVbox, iconImageView);
        VBox instructionVBox = new VBox(instructionsText,getErrorText());
        VBox hostWrapper = new VBox(hostLabel, hostField);
        VBox dbNameWrapper = new VBox(dbNameLabel, dbNameField);
        VBox usernameWrapper = new VBox(usernameLabel, usernameField);
        VBox passwordWrapper = new VBox(passwordLabel, passwordField);
        VBox credentialVBox = new VBox(hostWrapper, dbNameWrapper, usernameWrapper, passwordWrapper);

        // styling =-)

        //heading styles
        welcomeText.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));
        greetingText.setFont(Font.font("Helvetica", 28));
        headingHBox.setAlignment(Pos.TOP_CENTER);
        headingVbox.setPrefWidth(1200);
        headingVbox.setPadding(new Insets(80, 0, 0, 0));
        headingHBox.setPadding(new Insets(0 ,80, 0,180));
        headingHBox.getStyleClass().add("heading-box");

        //Setting animation
        greetingText.setVisible(false);
        Timeline welcomeAnimation = AnimationHelper.typewriterAnimation(welcomeText, 100);
        Timeline greetingAnimation =  AnimationHelper.typewriterAnimation(greetingText,150);
        AnimationHelper.applySwingRotation( iconImageView, 2000);

        //sequential transition
        SequentialTransition sequentialTransition = new SequentialTransition(welcomeAnimation, greetingAnimation);
        sequentialTransition.play();

        //Instruction and error message styles
        instructionsText.setFont(Font.font("Helvetica", 24));
        instructionVBox.setAlignment(Pos.CENTER);
        instructionVBox.setSpacing(10);
        VBox.setMargin(instructionVBox, new Insets(20,0,0,0));

        //Credintial styles
        credentialVBox.setMaxWidth(1100);
        credentialVBox.setSpacing(20);
        VBox.setMargin(credentialVBox, new Insets(0,200,20,200));

        //Submit button styles
        submitButton.setPrefSize(160 ,20);
        submitButton.getStyleClass().add("submit-button");



        this.setSpacing(5);
        this.setAlignment(Pos.TOP_CENTER);
        this.setWidth(1280);
        this.getStylesheets().add(getClass().getResource("/css/logIn.css").toExternalForm());

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

                } catch (IOException e) {
                    getErrorText().setText("Error saving credentials!");
                    animateErrorText(getErrorText());
                }
            }

            try {
                Database connection = Database.getInstance();

                loadNextScene();
            } catch (Exception e) {
                getErrorText().setText("Wrong DB credentials!");
                e.printStackTrace();
                animateErrorText(getErrorText());
            }
        };

        submitButton.setOnAction(submitEvent);

        this.getChildren().addAll(headingHBox, instructionVBox, credentialVBox, submitButton);
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
    public void loadNextScene() throws Exception {
        Scene nextScene = Dashboard.getDashboardSceneInstance();
        stage.setScene(nextScene);
    }
}
