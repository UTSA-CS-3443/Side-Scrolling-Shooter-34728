package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;

	public class OtherController extends Enemy {
		private int  moveCycle   = 0;
	    
	    public OtherController(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
	    	super(gRoot, xTopLeftStart, yTopLeftStart, angle);
	        }
	    
	    /**
	     * Set up defaults
	     */
	
	    @Override
	    public void init () { 
	        radius = 15;
	        xSize = ySize = radius*2;
	        timeToLive = 12;
	        color = Color.BLUE;
	    }
	    
	    /**
	     * Move the sprite on it's set path and velocity.
	     */ 
	    @Override
	    public void move () { 
	        moveCycle += 1;
	        if (moveCycle > 3)
	                moveCycle = 0;
	        if (moveCycle < 1)
	            moveTo(xCenterLoc-xVel, yTopLeftLoc-yVel);
	    }  
	}




