package services;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageCreator {
    /**
     * Creates an ImageView with the specified width, height, and image path.
     * @param imagePath The path to the image file (relative to the resources folder)
     * @param width width of the ImageView
     * @param height height of the ImageView
     * @return An ImageView containing the image with the specified size.
     */

    public static ImageView createImageView(String imagePath, double width, double height){

        //load image from the path
        Image image = new Image(ImageCreator.class.getResourceAsStream(imagePath));

        //craete an imageView with the loaded image
        ImageView imageView = new ImageView(image);

        //set width and height
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        return imageView;

    }
}
