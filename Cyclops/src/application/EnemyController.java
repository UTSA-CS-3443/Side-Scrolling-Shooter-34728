package application;


import javafx.scene.Group;
import javafx.scene.paint.Color;

public class EnemyController extends Enemy {
    
    // How wide (distance) the sidewinder swings back and forth
    protected   double  swingDistance   = 3;
    
    // How fast (speed) the sidewinder swings back and forth
    protected   double  swingSpeed      = 3;
    
    private     int     moveCycle       = 0;
    
    protected   double  xCycleVel       = 0;
    // Used to select the image based on the side movement
    // 0 = center, 1 = move left, 2 = move right
    protected   int     sideImage       = 0;
    
   
    public EnemyController(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        radius=25;
        xSize = ySize = radius*2;
        timeToLive = 12;
        color = Color.PURPLE;
    }
    
    /**
     * Move the sprite on it's set path and velocity.
     */ 
    @Override
    public void move () { 
        moveCycle += swingSpeed;
        if (moveCycle > 360)
                moveCycle = 0;
        
        xCycleVel = Math.sin(Math.toRadians(moveCycle)) * swingDistance;
        if ((xCycleVel/swingDistance) > 0.3)
            sideImage = 2;
        else if ((xCycleVel/swingDistance) < -0.3)
            sideImage = 1;
        else
            sideImage = 0;
                
        moveTo(xTopLeftLoc+xCycleVel-xVel, yTopLeftLoc-yVel);
    }    
        
}

/*

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class EnemyController extends Enemy {
    
    private int  moveCycle   = 0;
    
    public EnemyController(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
/*
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
   // @Override
    //public void move () { 
      //  moveCycle += 1;
       // if (moveCycle > 50)
         //       moveCycle = 0;
       // if (moveCycle < 3)
         //   moveTo(xCenterLoc+xVel, yTopLeftLoc+yVel);
    //}  
//}

