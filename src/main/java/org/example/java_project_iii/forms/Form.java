package org.example.java_project_iii.forms;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/***
 * Class Description: an abstract class for all forms, contains error message and method to animate it
 * @author Stan
 */
public abstract class Form extends VBox {
    private Text errorText = new Text();

    // getter and setter
    public Text getErrorText() {
        return errorText;
    }

    public void setErrorText(Text errorText) {
        this.errorText = errorText;
    }

    /***
     * Form constructor \n
     * Styles the <span style="color:red; font-weight: bold;"> error text</span>
     */
    public Form() {
        getErrorText().setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        getErrorText().setFill(Color.RED);
    }

    /**
     * Method to add a cool animation to the errorText :)
     * @param errorText the error Text node
     */
    protected void animateErrorText(Text errorText) {
        TranslateTransition shakeAnimation = new TranslateTransition(Duration.millis(100), errorText);
        shakeAnimation.setByX(10);
        shakeAnimation.setCycleCount(4);
        shakeAnimation.setAutoReverse(true);
        shakeAnimation.play();
    }
}
