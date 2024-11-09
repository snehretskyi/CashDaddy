package services;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Class description : Applies a different type of animation effect to the specified element
 */

public class AnimationHelper {

    /**
     * Method to apply a typewriter animation effect to the specified text element
     * @param textElement The Text element whose content will be animated
     * @param delayInMillis The delay between each character being displayed, in milliseconds
     */

    public static void typewriterAnimation(Text textElement, int delayInMillis) {
        String text = textElement.getText();  // Get the text from the Text element
        final StringBuilder displayedText = new StringBuilder();
        final Timeline timeline = new Timeline();

        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(delayInMillis * (index + 1)),
                    event -> {
                        displayedText.append(text.charAt(index));
                        textElement.setText(displayedText.toString()); // Update the Text element
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(1);
        timeline.play();
    }
}
