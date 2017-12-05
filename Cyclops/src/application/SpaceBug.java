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

public class SpaceBug extends EnemyController{
	
	public final static double      IMAGE_X_SIZE  = 90;
    public final static double      IMAGE_Y_SIZE  = 90;
    
    public final static double      SIDE_BUFFER   = 80;
    
    protected ArrayList<ImageView> imgEnemy;
    
    protected           Group       enemy;
    
    protected           Timeline    enemyAnimation;
    protected           Timeline    enemyDeathAnimation;
    
    private             int         imgIndex        = 0;
    private             int         imgDeathIndex   = 0;
    
    protected           Random      myRandom      = new Random();
	
    
    private     int     moveCycle       = 0;
    public SpaceBug(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
		super(gRoot, xTopLeftStart, yTopLeftStart, angle);
	}
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
        
        imgEnemy.add(new ImageView(new Image(getClass().getClassLoader().getResource("resources/Enemy2.png").toString())));        

        
        // Draw, move and size the initial frame
        enemy = new Group(imgEnemy.get(0));
        enemy.setTranslateX(950);
        enemy.setTranslateY(yTopLeftLoc);
        enemy.setScaleX(xSize / IMAGE_X_SIZE);
        enemy.setScaleY(ySize / IMAGE_Y_SIZE);
        this.getChildren().add(enemy);
        
        /**
         * Move the sprite on it's set path and velocity.
         */ 
        
       
        // Animate it
        EventHandler<ActionEvent> chopperEventHandler = e -> {
         //   if (imgIndex > 3) 
                imgIndex = 0;
            enemy.getChildren().setAll(imgEnemy.get(imgIndex));
        };
        enemyAnimation = new Timeline(new KeyFrame(Duration.millis(500), chopperEventHandler));
        enemyAnimation.setCycleCount(Timeline.INDEFINITE);
        enemyAnimation.play();
    }  
    /*
	 * Enemies have their own move function so that they dont all move the same.
	 * SpaceBug "suicide bomber", aims directly at the players last known position
	 * @see application.EnemyController#move()
	 */
	 @Override
     public void move () { 
		int randomInt = myRandom.nextInt(20);
     	moveCycle += 1;
 	        if (moveCycle > 3)
 	                moveCycle = 0;
 	        if (moveCycle < 1)
 	        	
 	            moveTo(xCenterLoc-xVel+randomInt, yTopLeftLoc+angle/10);
       
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

