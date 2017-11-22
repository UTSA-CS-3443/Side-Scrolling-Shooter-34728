package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Player {
    protected static Node rectBasicTimeline;


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
 		rectBasicTimeline.setFill(Color.RED);
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
}