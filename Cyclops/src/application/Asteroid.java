

package application;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Asteroid extends OtherController {

    public final static String[]    ASTEROID_FILES = {"resources/a10000.png", 
                                                      "resources/a10001.png", 
                                                      "resources/a10002.png"};
    public final static double      IMAGE_X_SIZE  = 47;
    public final static double      IMAGE_Y_SIZE  = 47;
    
    // The buffer on the sides of the screen for the enemies start. 
    public final static double      SIDE_BUFFER   = 80;
    
    protected           ArrayList<ImageView>   imgEnemy;
    
    protected           Group       enemy;
    
    protected           Timeline    enemyAnimation;
    protected           Timeline    enemyDeathAnimation;
    
    private             int         imgIndex        = 0;
    private             int         imgDeathIndex   = 0;
    
    protected           Random      myRandom      = new Random();
    
    public Asteroid(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        radius = 35;
        xSize = ySize = radius*2;
        timeToLive = 30;
    }

    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        // Load up our image files
        imgEnemy = new ArrayList<ImageView>();
        for(String file : ASTEROID_FILES)
            imgEnemy.add(new ImageView(new Image(getClass().getClassLoader().getResource(file).toString())));        
        imgEnemy.add(new ImageView(new Image(getClass().getClassLoader().getResource(ASTEROID_FILES[1]).toString())));
        
        // Draw, move and size the initial frame
        enemy = new Group(imgEnemy.get(0));
        enemy.setTranslateX(xTopLeftLoc);
        enemy.setTranslateY(yTopLeftLoc);
        enemy.setScaleX(xSize / IMAGE_X_SIZE);
        enemy.setScaleY(ySize / IMAGE_Y_SIZE);
        this.getChildren().add(enemy);
        
        // Animate it
        EventHandler<ActionEvent> chopperEventHandler = e -> {
            if (imgIndex > 3) 
                imgIndex = 0;
            enemy.getChildren().setAll(imgEnemy.get(imgIndex++));
        };
        enemyAnimation = new Timeline(new KeyFrame(Duration.millis(30), chopperEventHandler));
        enemyAnimation.setCycleCount(Timeline.INDEFINITE);
        enemyAnimation.play();
    }  
    
    /**
     * Start the death process.
     * Start death animations
     */ 
    @Override
    public void startDeath () { 
        isDying = true;
        stop = true;
        EventHandler<ActionEvent> beeDeathEventHandler = e -> {
            if (imgDeathIndex > 6) 
                finishDeath();
            //if (imgDeathIndex < 4) 
                //new Explosion(root, xTopLeftLoc + myRandom.nextInt((int)xSize) - 10, yTopLeftLoc + myRandom.nextInt((int)ySize) - 10);
            this.setOpacity(1-(0.15*imgDeathIndex++));
        };
            
        enemyDeathAnimation = new Timeline(new KeyFrame(Duration.millis(100), beeDeathEventHandler));
        enemyDeathAnimation.setCycleCount(8);
        enemyDeathAnimation.play();
    }      
}
