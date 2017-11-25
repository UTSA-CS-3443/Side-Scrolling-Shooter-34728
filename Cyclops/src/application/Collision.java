/*package application;



public class Collision {
	
	
	private int checkShapeIntersection(Shape block) {
	    boolean collisionDetected = false;
	    for (Shape static_bloc : nodes) {
	      if (static_bloc != block) {
	        static_bloc.setFill(Color.GREEN);

	        Shape intersect = Shape.intersect(block, static_bloc);
	        if (intersect.getBoundsInLocal().getWidth() != -1) {
	          collisionDetected = true;
	        }
	      }
	    }

	    if (collisionDetected) {
	    	block.setFill(Color.BLUE);
	    	return 1;
	    } else {
	    	block.setFill(Color.GREEN);
	    	return 0;
	    }
	}
}
*/