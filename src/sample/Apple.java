package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Apple extends SpriteBase {
    static int apples_count = 0;

    public Apple(Pane layer, Image image, double x, double y) {
        super(layer, image, x, y, 0, 0, 0, 0, "APPLE");

        this.imageView.setViewport(new Rectangle2D(0, 0, 64, 64));
        rectangle.setHeight(imageView.getViewport().getHeight());
        rectangle.setWidth(imageView.getViewport().getWidth());

        this.type = "APPLE";
        apples_count++;
    }

    public static int getApples_count() {
        return apples_count;
    }
}
