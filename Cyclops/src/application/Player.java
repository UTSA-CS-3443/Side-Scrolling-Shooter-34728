package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javafx.scene.Node;


public class Player{
	protected static Node rectBasicTimeline;
	
	private boolean isDying;
    private boolean stop;
	static void movePlayerBy(int dx, int dy) {
    	
    

	    if (dx == 0 && dy == 0) return;
	    
	    final double cx = driverClass.node.getBoundsInLocal().getWidth()  / 2;
	    final double cy = driverClass.node.getBoundsInLocal().getHeight() / 2;
	    double x = cx + driverClass.node.getLayoutX() + dx; //adding y coordinates
	    double y = cy + driverClass.node.getLayoutY() + dy;

	    movePlayerTo(x, y);
	}

	static void movePlayerTo(double x, double y) {
	    final double cx = driverClass.node.getBoundsInLocal().getWidth()  / 2;
	    final double cy = driverClass.node.getBoundsInLocal().getHeight() / 2;
	    
	    
	    //Y limits of the player
	     if (x - cx >= 0 &&
	            x + cx <= 1000 &&
	            y - cy >= -25 &&
	            y + cy <= 725) {
	            driverClass.node.relocate(x - cx, y - cy);
	    }
	}
	
	
	static void shoot() {
		
		final Rectangle rectBasicTimeline = new Rectangle(driverClass.node.getLayoutX()+70,driverClass.node.getLayoutY()+30 , 70, 10);//location and size of bullet
 		rectBasicTimeline.setFill(Color.GREEN);
 		final Timeline timeline = new Timeline();
 		timeline.setCycleCount(1);
 		timeline.setAutoReverse(false);
 		
 		//you can change speed by moving which ever number of the next two lines
 		final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(),6000);
 		final KeyFrame kf = new KeyFrame(Duration.millis(6000), kv);
 		
 		
 		//play the bullet sound effect once
 		driverClass.playShot(1, 1);
 		
 		timeline.getKeyFrames().add(kf);
 		timeline.play();
 		//so it wont be shooting like a machine gun. even if you press space once it will read it more than once.
 		driverClass.shoot = false;
 		
 		
 		//still need to find a way to block the user from leaving SPACE pressed. otherwise it will shoot like a machine gun.
 		//also delete the bullet when it gets to more than 1050 in the x axis to save space
	}
	
    //public void startDeath () { 
        /*isDying = true;
        stop = true;
        EventHandler<ActionEvent> beeDeathEventHandler = e -> {
            int imgDeathIndex;
			if (imgDeathIndex > 6) 
                finishDeath();
            //if (imgDeathIndex < 4) 
                //new Explosion(root, xTopLeftLoc + myRandom.nextInt((int)xSize) - 10, yTopLeftLoc + myRandom.nextInt((int)ySize) - 10);
            this.setOpacity(1-(0.15*imgDeathIndex++));
        };
            
        enemyDeathAnimation = new Timeline(new KeyFrame(Duration.millis(100), beeDeathEventHandler));
        enemyDeathAnimation.setCycleCount(8);
        enemyDeathAnimation.play();
    	*/
    	//this.relocate()
    //}  
    
}