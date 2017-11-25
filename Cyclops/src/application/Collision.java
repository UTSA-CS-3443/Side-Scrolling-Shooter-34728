package application;

import java.util.ArrayList;

//import javafx.geometry.Bounds;
import javafx.scene.shape.*;

public class Collision {
	
	
	public int checkBulletIntersection(Rectangle bullet, ArrayList<Enemy> enemies) {
	    //boolean collisionDetected = false;
	    /*for (Shape static_bloc : nodes) {
	      if (static_bloc != block) {
	        static_bloc.setFill(Color.GREEN);

	        Shape intersect = Shape.intersect(block, static_bloc);
	        if (intersect.getBoundsInLocal().getWidth() != -1) {
	          collisionDetected = true;
	        }
	      }
	    }*/
	    for(Enemy asteroid : enemies) {
	    	if(asteroid.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
	          asteroid.startDeath();
	          return 1;
	        }
	    }
	    return 0;
	    /*
	    if (collisionDetected) {
	    	asteroid.startDeath();
	    	return 1;
	    } else {
	    	block.setFill(Color.GREEN);
	    	return 0;
	    }*/
	}
}
