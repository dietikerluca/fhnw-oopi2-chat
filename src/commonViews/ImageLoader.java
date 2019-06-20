package src.commonViews;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class ImageLoader {
    public static Image loadImage(String path) throws MalformedURLException {
        String url = System.getProperty("user.dir") + path;
        File imageFile = new File(url);
        Image serverImage = new Image(imageFile.toURI().toURL().toString());

        return serverImage;
    }

    public static ImageView loadImageView(String path, int width, int height, String cssClass) throws MalformedURLException {
        String url = System.getProperty("user.dir") + path;
        File imageFile = new File(url);
        Image serverImage = new Image(imageFile.toURI().toURL().toString());

        ImageView imageView = new javafx.scene.image.ImageView(serverImage);
        imageView.setFitHeight(width);
        imageView.setFitWidth(height);
        imageView.getStyleClass().add(cssClass);

        return imageView;
    }
}
