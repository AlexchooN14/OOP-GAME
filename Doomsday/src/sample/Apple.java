package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Apple {
    Pane layer;
    Image apple_image;
    ImageView apple_imageView;
    Input input;

    public Apple(Pane layer, Image apple_image, Input input, double apple_X, double apple_Y) {
        this.layer = layer;
        this.apple_image = apple_image;
        this.apple_imageView = new ImageView(apple_image);
        this.apple_imageView.relocate(apple_X, apple_Y);
        this.input = input;
        addToLayer();
    }

    public void addToLayer(){
        layer.getChildren().add(apple_imageView);
    }
}
