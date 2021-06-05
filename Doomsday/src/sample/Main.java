package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import map.Cell;
import map.Grid;
//import map.MovingObstacle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends Application {

    Input input;
    Player player;
    Enemy enemy;
    Enemy enemy1;
    //Bullet bullet;
    Treasure treasure;
    Apple apple;
    Apple apple1;
    Apple apple2;
   // MovingObstacle movingObstacle1,movingObstacle2;


    Pane playfieldLayout;
    Pane scoreLayout;

    Image playerImage;
    Image enemyImage;
    Image borderImage;
    //Image bulletImage;
    Image treasureImage;
    Image treasureOpenedImage;

    Image AppleImage;


    Group root;
    Stage primaryStage;
    Scene scene;
    Grid map;

    int[][] gameMap ={
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//araylist elemanları sütunsal olarak sayıyor obstacle hareketinde farkedersiniz 48.eleman bi border
            {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
            {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
            {0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},//hareketsiz elemalar için bunun daha uygun olduğunu düşündüm
    };

    //Creating lists
    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();
    List<Treasure> treasures = new ArrayList<>();
    List<Apple> apples = new ArrayList<>();

    /*
    Here collision text we will change it later and
    Make it health bar and whenever we attack an enemy
    it's health bar will be decrease if it attacks vice versa*/

    Text collisionText = new Text();
    boolean player_EnemyCollision = false;
    boolean player_AppleCollision = false;
    boolean player_MapObstacleCollision=false;
    boolean attackCollision = false;

    boolean spawned = false;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        root = new Group();


        // create layers
        playfieldLayout = new Pane();
        scoreLayout = new Pane();

        root.getChildren().add(playfieldLayout);
        root.getChildren().add(scoreLayout);



        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setTitle("Room & Doom");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Създаване на мапа
        createMap();
        loadGame();
        //Създаване на обектите
        createPlayers();
        createEnemy();


        AnimationTimer gameLoop = new AnimationTimer() {
             private long lastUpdate =0;

            @Override

            public void handle(long now) {
                if (enemies.isEmpty()) {
                    if (!spawned) {
                        createTreasure();
                        createApples();
                        spawned = true;
                    }
                }

                //input
                players.forEach(sprite -> sprite.processInput());
                if (enemies.isEmpty())
                    treasures.forEach(sprite -> treasure.processInput());
                // movement
                players.forEach(sprite -> sprite.move());
                //System.out.println(enemies.isEmpty());


                // check collisions
                player_CheckCollisionWithEnemy();
                player_enemyBlock();

                player_CheckCollisionWithMapObstacle();
                player_mapObstacleBlock();


                checkAttackCollisionWithEnemy();

                // update sprites in scene
                players.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites(enemies);
                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

    }


    //Image resources
    private void loadGame() {
        playerImage = new Image(getClass().getResource("/spritesheet.png").toExternalForm());
        enemyImage = new Image(getClass().getResource("/dle_2.jpg").toExternalForm());
       // bulletImage = new Image(getClass().getResource("/plasmaball.png").toExternalForm());
        AppleImage = new Image(getClass().getResource("/apple_new.jpg").toExternalForm());
        borderImage = new Image(getClass().getResource("/border.jpg").toExternalForm());
        treasureImage = new Image(getClass().getResource("/treasure.png").toExternalForm());
        treasureOpenedImage = new Image(getClass().getResource("/treasureOpened.png").toExternalForm());

    }


    private void createMap(){
        map=new Grid(gameMap);//for special map
        map.Draw(playfieldLayout);//which draws map to our pane
    }
    //blocks for player
    public void player_enemyBlock(){
        if(player_EnemyCollision){
            getAfterCollision();
        }
    }
    public void player_AppleBlock(){
        if(player_AppleCollision){
            getAfterCollision();
        }
    }

    public void player_mapObstacleBlock(){
        if(player_MapObstacleCollision){
            getAfterCollision();
        }
    }

    //Creating a player here
    private void createPlayers() {
        //For inputs
        input = new Input(scene);
        input.addListeners();

        Image image = playerImage;
        //Setting players' qualities
        player = new Player(playfieldLayout, image, 100, 10, 0.5, input);
        //Add all players in a list so it will be easier to work
        players.add(player);

    }

    //Creating an enemy here
    private void createEnemy() {
        Image image = enemyImage;
        //Setting enemies' qualities
        enemy = new Enemy(playfieldLayout, image, 300, 300, 100, 10);
        //Add all enemies in a list so it will be easier to work
        enemies.add(enemy);

    }


    //Creating treasures here
    private void createTreasure(){
        //For inputs
        input = new Input(scene);
        input.addListeners();

        Image image = treasureImage;
        Image image_Opened = treasureOpenedImage;

        treasure = new Treasure(playfieldLayout, image, image_Opened, 300, 500, input);

        treasures.add(treasure);
    }
    private void createApples(){
        //For inputs
        input = new Input(scene);
        input.addListeners();

        Image image = AppleImage;

        apple = new Apple(playfieldLayout, image, input, 200, 600);
        apple1 = new Apple(playfieldLayout, image, input, 700, 500);
        apple2 = new Apple(playfieldLayout, image, input, 750, 200);
        apples.add(apple);
        apples.add(apple1);
        apples.add(apple2);
    }


    private void removeSprites(List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iterator = spriteList.iterator();
        while (iterator.hasNext()) {
            SpriteBase sprite = iterator.next();

            if (sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();
                sprite.layer.getChildren().remove(sprite.healthBar.imageView);

                // remove from list
                iterator.remove();
            }
        }
    }

    private void player_CheckCollisionWithEnemy() {
        player_EnemyCollision = false;

        for (Player player : players) {
            for (Enemy enemy : enemies) {
                if (player.collidesWith(enemy)) {
                    player_EnemyCollision = true;
                }
            }
        }
    }
    private void player_CheckCollisionWithApple() {
        player_AppleCollision = false;

        for (Player player : players) {
            for (Apple apple : apples) {
                if (player.collidesWith(enemy)) {
                    player_AppleCollision = true;
                }
            }
        }
    }

    private void checkAttackCollisionWithEnemy(){
        attackCollision = false;

        for (Player player: players){
            for(Enemy enemy : enemies){
                if(player.attackCollides(enemy)){
                    attackCollision = true;
                }
            }
        }
    }

    private void player_CheckCollisionWithMapObstacle() {
        player_MapObstacleCollision = false;

        for (Player player : players) {
            for (Cell cell:map.mapArraylist) {
                if (cell.getType()==1&&player.collidesWithCell(cell)) {
                    player_MapObstacleCollision = true;
                }
            }
        }
    }


    private void updateScore() {
        if (attackCollision && input.isAttack() && !Input.getIsAttacking()) {
            Input.setIsAttacking(true);
            System.out.println("Before the attack: " + enemy.health);
            enemy.getDamagedBy(player);//Enemy's health decreasing
            System.out.println("After the attack: " + enemy.health);

        } else {
            collisionText.setText("");
        }
    }

    private void getAfterCollision(){
        player.rectangle.setX(player.rectangle.getX()-player.getDx());
        player.rectangle.setY(player.rectangle.getY()-player.getDy());
    }
    public static void main(String[] args) {
        launch(args);
    }

}