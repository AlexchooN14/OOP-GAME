package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import map.Cell;


public abstract class SpriteBase extends Pane {
    String type;

    Image image;
    ImageView imageView;

    Pane layer;

    double x;
    double y;

    Rectangle rectangle;

    double dx;
    double dy;

    double health;
    double damage;
    double maxHealth;

    boolean removable = false;

    double width;
    double height;

    boolean canMove = true;
    HealthBars healthBar;


    public SpriteBase(Pane layer, Image image, double x, double y, double dx, double dy, double health, double damage, String type) {

        this.layer = layer;
        //System.out.println(image);
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
        rectangle = new Rectangle(x,y,width,height);

        this.dx = dx;
        this.dy = dy;

        this.health = health;
        this.damage = damage;
        maxHealth = health;


        this.imageView = new ImageView(image);
        this.imageView.relocate(rectangle.getX(), rectangle.getY());
        if (!(type == "APPLE"))
            healthBar = new HealthBars(layer,maxHealth,health,x,rectangle.getY()-10);

        addToLayer();

    }

    public void addToLayer() {
        this.layer.getChildren().addAll(imageView);

    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
        //if (type == "APPLE") Apple.apples_count--;
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }


    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move() {

        if (!canMove)
            return;

        rectangle.setX(rectangle.getX()+dx);
        rectangle.setY(rectangle.getY()+dy);

    }

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    public ImageView getView() {
        return imageView;
    }

    public void updateUI() {
        if (!(type == "APPLE")) {
            healthBar.UpdateUI(health, maxHealth);
            healthBar.imageView.relocate(rectangle.getX(), rectangle.getY() - 20);
        }
        imageView.relocate(rectangle.getX(), rectangle.getY());

    }

    public double getCenterX() {
        return x + width * 0.5;
    }

    public double getCenterY() {
        return y + height * 0.5;
    }

    public boolean collidesWith(SpriteBase otherSprite) {
        return rectangle.getBoundsInParent().intersects(otherSprite.rectangle.getBoundsInParent());
    }

    public boolean collidesWithCell(Cell cell) {

        return (cell.getX() + cell.getWidth() >= rectangle.getX() && cell.getY() + cell.getHeight() >= rectangle.getY() && cell.getX() <= rectangle.getX() + rectangle.getWidth() && cell.getY() <= rectangle.getY() + rectangle.getHeight());
    }

    public boolean attackCollides( SpriteBase otherSprite) {
        //Right Attack Check
        if(Player.animation.getOffSetY() == 704) return ((rectangle.getY()+rectangle.getHeight())/2 <= otherSprite.rectangle.getY() && (rectangle.getY()+rectangle.getHeight())/2 <= (otherSprite.rectangle.getY()+otherSprite.rectangle.getHeight()) && (rectangle.getX()+rectangle.getWidth()) <otherSprite.rectangle.getX() && (rectangle.getX()+rectangle.getWidth()) > otherSprite.rectangle.getX()-rectangle.getWidth()-5 );
        //Left Attack Check
        if(Player.animation.getOffSetY() == 576) return ((rectangle.getY()+rectangle.getHeight())/2 <= otherSprite.rectangle.getY() && (rectangle.getY()+rectangle.getHeight())/2 <= (otherSprite.rectangle.getY()+otherSprite.rectangle.getHeight()) && rectangle.getX() > otherSprite.rectangle.getX()+otherSprite.rectangle.getWidth() && rectangle.getX() < otherSprite.rectangle.getX()+otherSprite.rectangle.getWidth()+rectangle.getWidth()+5);
        //Top Attack Check
        if(Player.animation.getOffSetY() == 512) return (rectangle.getY() >= otherSprite.rectangle.getY()+otherSprite.rectangle.getHeight() && rectangle.getY() <= otherSprite.rectangle.getY()+otherSprite.rectangle.getHeight()+rectangle.getHeight()+5 && (rectangle.getX()+(rectangle.getX()+rectangle.getWidth()))/2 <= (otherSprite.rectangle.getX()+otherSprite.rectangle.getWidth()) && (rectangle.getX()+(rectangle.getX()+rectangle.getWidth()))/2 >= otherSprite.rectangle.getX());
        //Bot Attack Check
        if(Player.animation.getOffSetY() == 640) return (rectangle.getY() <= otherSprite.rectangle.getY() && rectangle.getY() >= otherSprite.rectangle.getY()-rectangle.getHeight()-20 && (rectangle.getX()+(rectangle.getX()+rectangle.getHeight()))/2 <= (otherSprite.rectangle.getX()+otherSprite.rectangle.getWidth()) && (rectangle.getX()+(rectangle.getX()+rectangle.getWidth()))/2 >= otherSprite.rectangle.getX());
        else return false;
    }



    public void getDamagedBy(SpriteBase sprite) {
        health -= sprite.getDamage();
    }



}