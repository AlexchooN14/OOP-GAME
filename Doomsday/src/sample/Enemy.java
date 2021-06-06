package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends SpriteBase {

    //We need to set enemy's attack
    public Enemy(Pane layer, Image image, double x, double y, double health, double damage) {
        super(layer, image, x, y, 0, 0, health, damage, "PLAYER");
        this.imageView.setViewport(new Rectangle2D(0, 0, 64, 64));
        rectangle.setHeight(imageView.getViewport().getHeight());
        rectangle.setWidth(imageView.getViewport().getWidth());
    }


    public void checkRemovability() {
        //System.out.println(this.health);
        //Here we will set when the sprite remove
        if(this.health <= 0) {
           setRemovable(true);
        }
    }
    public boolean isAlive(){
        return true;
    }

}