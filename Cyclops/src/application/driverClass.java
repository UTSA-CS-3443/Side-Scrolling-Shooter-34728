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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.animation.*;
import javafx.scene.Scene;

public class driverClass extends Application {
	
	protected final double	screenX = 800;
	protected final double	screenY = 600;
	
	protected ArrayList<Enemy> enemyList;
	
	protected Group enemies;
	
	protected double startingSpawnRate = 1;
	
	protected double currentRate = startingSpawnRate;
	
	protected double rateProgression = 0.995;
	
	protected int enemyMaxAngle = 15;
	
	protected Scene scene;
	
	protected Stage mainStage;
	
	protected Group root;
	
	protected Random myRandom = new Random();
	
	protected AnimationTimer spawnTime;
	
	
	
	
	
	public final static String 	BACKGROUND_IMG = "resources/space.png";
	public final static double	BACKGROUND_SIZE_X = 1000;
	public final static double 	BACKGROUND_SIZE_Y = 800;
	public final double backgroundScaleX = screenX / BACKGROUND_SIZE_X;
	public final double backgroundScaleY = screenY / BACKGROUND_SIZE_Y;
	protected ImageView imgBackground;
	// TODO MORE Variables declared here
	private AnimationTimer gameLoop;
	double backgroundScrollSpeed = 0.5;
	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage firstStage) {
		Stage mainStage = firstStage;
		mainStage.setTitle("Starclops");
		
		Group root = new Group();
		Scene scene = new Scene(root, screenX, screenY, Color.BLACK);
		mainStage.setScene(scene);
		
		enemyList = new ArrayList<Enemy>();
		enemies = new Group();
		
		imgBackground = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMG).toString()));
		imgBackground.getTransforms().add(new Scale(backgroundScaleX, backgroundScaleY, 0, 0));
		
		root.getChildren().add(imgBackground);
		root.getChildren().add(enemies);
		//add more things here (bullets/guns/enemies)
		//add instructions/score
		spawnSprites();
		
		mainStage.show();
		
		StartGameLoop();
		
	}
	
	public void reset_game() {
		currentRate = startingSpawnRate;
		clearSprites();
	}
	
	public void clearSprites() {
		for (Iterator<Enemy> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();) {
			Enemy enemyNode = enemyIterator.next();
			enemies.getChildren().remove(enemyNode);
			enemyIterator.remove();
		}
	}
	
	private void spawnSprites() {
		spawnTime = new AnimationTimer() {
			long lastSpawn = 0;
			int randomInt;
		@Override
		public void handle(long timestamp) {
			if ((timestamp - lastSpawn >= currentRate * (long)1000000000)) {
				currentRate *= rateProgression;
				
			
				double xAsteroidStart = (double)(myRandom.nextInt((int) (screenY- (Asteroid.SIDE_BUFFER *2))) + Asteroid.SIDE_BUFFER);
				int xAngleStart = myRandom.nextInt(enemyMaxAngle * 2) + 180 - enemyMaxAngle; 
				
				randomInt = myRandom.nextInt(100);
				if (randomInt < 50 ) {
					enemyList.add(new Asteroid(enemies, xAsteroidStart, -35, xAngleStart));
				}
				lastSpawn = timestamp;
				
				}
			}
		};
		spawnTime.start();
	}
	
	private void StartGameLoop() {
	gameLoop = new AnimationTimer() {
		@Override
		public void handle(long l) {
			double y = imgBackground.getLayoutY() + backgroundScrollSpeed;
			
			if(Double.compare(y, 0) >= 0) {
				y=0;
			}
			imgBackground.setLayoutY(y);
			}
		};
		
		gameLoop.start();
	}
}