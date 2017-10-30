package application;

import javafx.application.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.Scene;

public class driverClass extends Application {
	
	protected final double	screenX = 800;
	protected final double	screenY = 600;
	
	public final static String 	BACKGROUND_IMG = "resources/space.png";
	public final static double	BACKGROUND_SIZE_X = 1000;
	public final static double 	BACKGROUND_SIZE_Y = 800;
	public final double backgroundScaleX = screenX / BACKGROUND_SIZE_X;
	public final double backgroundScaleY = screenY / BACKGROUND_SIZE_Y;
	protected ImageView imgBackground;
	// TODO MORE Variables declared here
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage firstStage) {
		// TODO Auto-generated method stub
		Stage mainStage = firstStage;
		mainStage.setTitle("Greetings");
		
		Group root = new Group();
		Scene scene = new Scene(root, screenX, screenY, Color.BLACK);
		mainStage.setScene(scene);
		
		
		imgBackground = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMG).toString()));
		imgBackground.getTransforms().add(new Scale(backgroundScaleX, backgroundScaleY, 0, 0));
		
		root.getChildren().add(imgBackground);
		//add more things here (bullets/guns/enemies)
		//add instructions/score
		
		mainStage.show();
		
	}
	
}