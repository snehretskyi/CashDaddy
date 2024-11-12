package services;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
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

    public static Timeline typewriterAnimation(Text textElement, int delayInMillis) {
        String text = textElement.getText();  // Get the text from the Text element
        final StringBuilder displayedText = new StringBuilder();
        final Timeline timeline = new Timeline();

        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(delayInMillis * (index + 1)),
                    event -> {
                        displayedText.append(text.charAt(index));
                        textElement.setText(displayedText.toString());
                        textElement.setVisible(true);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setCycleCount(1);
        return timeline;
    }

        /**
         * Creates a swing-like rotation animation for an ImageView.
         * The image will rotate back and forth around its center.
         *
         * @param imageView The ImageView that will be rotated.
         * @param durationInMillis The duration of one full swing (back and forth) in milliseconds.
         */
        public static void applySwingRotation(ImageView imageView, int durationInMillis) {
            // Create a RotateTransition
            RotateTransition rotateTransition = new RotateTransition();

            // Set the target image (the ImageView)
            rotateTransition.setNode(imageView);

            // Set the angle of rotation
            //rotateTransition.setByAngle(30);
            rotateTransition.setFromAngle(-3);
            rotateTransition.setToAngle(3);

            // Set the duration of one swing (back and forth)
            // Infinite loop to keep swinging
            rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
            rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
            rotateTransition.setAutoReverse(true);

            // Set the time duration in total
            rotateTransition.setDuration(Duration.millis(durationInMillis));

            rotateTransition.play();
        }


}