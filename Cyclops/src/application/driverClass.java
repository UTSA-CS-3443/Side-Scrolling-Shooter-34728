package application;
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
import javafx.collections.ObservableList;
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

public class driverClass extends Application {
	//////////////////////////////////////////
	//           Alysas code                //
	protected final double	screenX = 800;
	protected final double	screenY = 600;
	protected ArrayList<Enemy> enemyList;
	protected Group enemies;
	protected double startingSpawnRate = 1;
	protected double currentRate = startingSpawnRate;
	protected double rateProgression = 0.995;
	protected int enemyMaxAngle = 15;
	protected Random myRandom = new Random();
	protected AnimationTimer spawnTime;
	private AnimationTimer gameLoop;
	/////////////////////////////////////////////
	protected Scene scene;
	protected Stage mainStage;
	protected Group root;
	public final static String 	BACKGROUND_IMG = "resources/space2.gif";
	protected ImageView imgBackground;
    boolean goNorth, goSouth;
	static boolean shoot;
    private static Image warrior;
    static Node  node;
    
    private Rectangle detectBullet;
    
    public static void main(String[] args) {
    	launch(args);
    }
    		


    public void start(Stage firstStage) {
		//////////////////////////////////
		//Alyssas code
		enemyList = new ArrayList<Enemy>();
		enemies = new Group();
		/////////////////////////
		
		
		
		
		
		//declaring/setting  all the pictures and images used
	    warrior = new Image("resources/E11.png");
	    node = new ImageView(warrior);
	    ((ImageView) node).setFitHeight(75);
	    ((ImageView) node).setFitWidth(75);
		imgBackground = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMG).toString()));
		imgBackground.setFitHeight(850);
		imgBackground.setFitWidth(1000);	
		
       
        
        //declaring group
        Group root = new Group();
		
		
		//adding the pictures to the scene
        root.getChildren().add(imgBackground);
		root.getChildren().add(node);
		root.getChildren().add(enemies);
		//bullet is added inside the if space == true statement
		
	
		
		Stage mainStage = firstStage;
		mainStage.setTitle("Starclops");
		
		//set the player at the middle of the screen.
		Player.movePlayerTo(50, 350);
		//plays the long background music.
		play(0);
		Scene scene = new Scene(root, 1000, 700, Color.BLACK);
		
		//playerControlls
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @SuppressWarnings("incomplete-switch")
			@Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case UP:    goNorth = true; break;
                case DOWN:  goSouth = true; break;
                case SPACE:  shoot = true; break;
                }
            }
        });
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @SuppressWarnings("incomplete-switch")
			@Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case UP:    goNorth = false; break;
                case DOWN:  goSouth = false; break;
                case SPACE: shoot = false;   break;

                }
            }
        });
		
		spawnSprites();
		//starts the show i believe
		 mainStage.setScene(scene);
	     mainStage.show();
	     StartGameLoop();
	     
	     //stops music when the windows is closed, otherwise the music will continue playing
	     mainStage.setOnCloseRequest(e -> {
	         Platform.exit();
	         System.exit(0);
	     });
	     
	     detectBullet = new Rectangle(node.getLayoutX()+70,node.getLayoutY()+30 , 70, 10);
        AnimationTimer timer = new AnimationTimer() {
        	           
            public void handle(long now) {
                //if statements that tells how much to move up and down
            	int dx = 0, dy = 0;
                if (goNorth) dy -= 10; //how fast the player moves north
                if (goSouth) dy += 10; //how fast the player moves south
                
                //method call to move player
                Player.movePlayerBy(dx, dy); 
                
                //everything that is inside the if statement is how the bullet is made
               if (shoot == true){
            	   	final Rectangle rectBasicTimeline = new Rectangle(node.getLayoutX()+70,node.getLayoutY()+30 , 70, 10);//location and size of bullet
            	   	rectBasicTimeline.setFill(Color.LIMEGREEN);
             		final Timeline timeline = new Timeline();
             		timeline.setCycleCount(1);
             		timeline.setAutoReverse(false);
             		
             		//you can change speed by moving which ever number of the next two lines
             		final KeyValue kv = new KeyValue(rectBasicTimeline.xProperty(),6000);
             		final KeyFrame kf = new KeyFrame(Duration.millis(6000), kv);
             		root.getChildren().add(rectBasicTimeline);
             		
             		//play the bullet sound effect once
             		playShot(1, 1);
             		
             		//method call for collision (bullet -> asteroid)
             		
             		
             		timeline.getKeyFrames().add(kf);
             		timeline.play();
             		//so it wont be shooting like a machine gun. even if you press space once it will read it more than once.
             		shoot = false;
             		
             		
             		//still need to find a way to block the user from leaving SPACE pressed. otherwise it will shoot like a machine gun.
             		//also delete the bullet when it gets to more than 1050 in the x axis to save space
                }
               checkBulletIntersection(root.getChildren(), enemyList);
               
            }

			private void checkBulletIntersection(ObservableList<Node> sceneObjs, ArrayList<Enemy> enemyList) {
				for(Enemy asteroid : enemyList) {
					for(Node obj : sceneObjs) {
						if (obj.maxWidth(10) == detectBullet.maxWidth(10)) {
							if(asteroid.getBoundsInParent().intersects(obj.getBoundsInParent())) {
								asteroid.startDeath();
							}
						}
					}
				}
			}
           
		};
		
		timer.start();
		
		gameLoop.start();
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
    	AnimationTimer spawnTime = new AnimationTimer() {
    		long lastSpawn = 0;
    		int randomInt;
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
			//double y = imgBackground.getLayoutY() + backgroundScrollSpeed;
			
			//if(Double.compare(y, 0) >= 0) {
		//		y=0;
			//}
			//imgBackground.setLayoutY(y);
		} //changed background, this code seems to do nothing now
    	};
		
			gameLoop.start();
	}


//background music
public void play( int delay) {
	for (int i = 0; i < 1; i++) {
		new Thread() {
			@Override
			public void run() {
				try {
					File file = new File("resources/Fantasy_Game_Background1.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
					Thread.sleep(clip.getMicrosecondLength());

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}.start();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
//laser music event
public static void playShot(int delay, int numberOfLoops) {
	for (int i = 0; i < numberOfLoops; i++) {
		new Thread() {
			@Override
			public void run() {
				try {
					File file = new File("laser7.wav");
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
					Thread.sleep(clip.getMicrosecondLength());

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}.start();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}





}