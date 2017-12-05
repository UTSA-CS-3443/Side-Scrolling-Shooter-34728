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
	protected int playerY =350;
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
	static boolean menu = false;
    private static Image warrior;
    static Node  node, node2, node3, node4;
    
    private Rectangle detectBullet;
    
    private static Image imgBackground2;
    private static Image imgBackground3;
    private static Image imgBackground4;
    
    private int playerDead, vulnerable;
	//public final static String 	BACKGROUND_IMGS = "resources/StarBackground.gif";
    //public final static String TITLE = "resources/gameTitle.gif";
    //public final static String INSTRUCT = "resources/instructions.gif";
    
    public static void main(String[] args) {
    	launch(args);
    }
    		


    public void start(Stage firstStage) {
		
    	Stage mainStage = firstStage;
		mainStage.setTitle("Starclops");
		Group root = new Group();
		Scene scene = new Scene(root, 1000, 700, Color.BLACK);
		
    	/*////////////////// code to move
    	imgBackgroundS = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMGS).toString()));
		imgBackgroundS.setFitHeight(850);
		imgBackgroundS.setFitWidth(1000);
		root.getChildren().add(imgBackgroundS);
		//////////////////*/
    		
    	
    	
    	//////////////////////////////////
		//Alyssas code
		enemyList = new ArrayList<Enemy>();
		enemies = new Group();
		/////////////////////////
		
		
		
		
		
		//declaring/setting  all the pictures and images used
	    warrior = new Image("resources/nightraiderfixed.png");
	    node = new ImageView(warrior);
	    ((ImageView) node).setFitHeight(75);
	    ((ImageView) node).setFitWidth(75);
	    
	    imgBackground2 = new Image("resources/gameTitle.png");
	    node2 = new ImageView(imgBackground2);
	    ((ImageView) node2).setFitHeight(150);
	    ((ImageView) node2).setFitWidth(800);
	    node2.relocate(100, 100);
	    
	    imgBackground3 = new Image("resources/instructions.png");
	    node3 = new ImageView(imgBackground3);
	    ((ImageView) node3).setFitHeight(75);
	    ((ImageView) node3).setFitWidth(800);
	    node3.relocate(100, 450);
	    
		imgBackground = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMG).toString()));
		imgBackground.setFitHeight(850);
		imgBackground.setFitWidth(1000);	
		
		/*
		imgBackground2 = new ImageView(new Image(getClass().getClassLoader().getResource(TITLE).toString()));
		imgBackground2.setFitHeight(175);
		imgBackground2.setFitWidth(500);
		
		imgBackground3 = new ImageView(new Image(getClass().getClassLoader().getResource(INSTRUCT).toString()));
		imgBackground3.setFitHeight(150);
		imgBackground3.setFitWidth(900);
        //declaring group(moved to top)
        */
		
		
		//imgBackgorundS = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMGS).toString()));
		//imgBackgroundS.setFitHeight(850);
		//imgBackgroundS.setFitWidth(1000);
		/*
		root.getChildren().add(imgBackground2);
		root.getChildren().add(imgBackground3);
		*/
		//adding the pictures to the scene
        root.getChildren().add(imgBackground);
		root.getChildren().add(node);
		root.getChildren().add(node2);
		root.getChildren().add(node3);
		root.getChildren().add(enemies);
		
		
		//bullet is added inside the if space == true statement
		
		
		//set the player at the middle of the screen.
		Player.movePlayerTo(50, 350);
		//plays the long background music.
		play(0);
		
		
		//playerControlls
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @SuppressWarnings("incomplete-switch")
			@Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case UP:    goNorth = true; break;
                case DOWN:  goSouth = true; break;
                case SPACE:  shoot = true; break;
                case ENTER: menu = true; break;
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
                case ENTER: menu = false; break;
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
	     
	     detectBullet = new Rectangle(node.getLayoutX()+70,node.getLayoutY()+30 , 70, 7);
        AnimationTimer timer = new AnimationTimer() {
        	           
            public void handle(long now) {
                //if statements that tells how much to move up and down
            	int dx = 0, dy = 0;

                if (goNorth) {
                	dy -= 10; //how fast the player moves north
                	playerY -=10;
                }
                if (goSouth) {
                	dy += 10; //how fast the player moves south
                	playerY +=10;
                }
                
                //method call to move player
                Player.movePlayerBy(dx, dy); 
                
                if (menu == true) {
                	node2.relocate(100, -200);
                	node3.relocate(100, -200);
                	vulnerable = 1;
                }
                
                //everything that is inside the if statement is how the bullet is made
               if (shoot == true){
            	   	final Rectangle rectBasicTimeline = new Rectangle(node.getLayoutX()+70,node.getLayoutY()+30 , 70, 7);//location and size of bullet
            	   	rectBasicTimeline.setFill(Color.LIMEGREEN);
             		final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.1), e -> {
             			root.getChildren().remove(rectBasicTimeline);
             		}));
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
               if (vulnerable == 1) checkPlayerIntersection(root.getChildren(), enemyList);
               if (playerDead == 1 && vulnerable == 1) {
            	   imgBackground4 = new Image("resources/gameOver.png");
				    node4 = new ImageView(imgBackground4);
				    ((ImageView) node4).setFitHeight(175);
				    ((ImageView) node4).setFitWidth(800);
				    node4.relocate(100, 250);
				    root.getChildren().add(node4);
               }
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
			
			private void checkPlayerIntersection(ObservableList<Node> sceneObjs, ArrayList<Enemy> enemyList) {
				for(Enemy asteroid : enemyList) {
					for(Node obj : sceneObjs) {
						if (obj.maxWidth(75) == 75.0) {
							if(asteroid.getBoundsInParent().intersects(obj.getBoundsInParent())) {
								obj.relocate(-100, -100);
								playerDead = 1;
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
					
					//ASTERIOD SPAWN
					double xAsteroidStart = (double)(myRandom.nextInt((int) (screenY)));
					int xAngleStart = myRandom.nextInt(enemyMaxAngle * 2) + 180 - enemyMaxAngle; 
					
					randomInt = myRandom.nextInt(100);
					if (randomInt < 66 ) {
						enemyList.add(new Asteroid(enemies, xAsteroidStart, -35, xAngleStart));
					}
					
					
					//ENEMY SPAWN
					
					//suicide
					double xSpaceBugStart = (double)(myRandom.nextInt((int) (screenY )));
					int xSpaceBugAngleStart = (int) ((-1*((xSpaceBugStart-playerY))/650)*100); 
					if (randomInt < 69 ) 
						enemyList.add(new SpaceBug(enemies,xSpaceBugStart,-35, xSpaceBugAngleStart));
	    			
					//wall of enemies
	 				if (randomInt < 20 )
	 				{
	 					enemyList.add(new EnemyDefence(enemies,-85,0,0));
	 					enemyList.add(new EnemyDefence(enemies,15,0,0));
	 					enemyList.add(new EnemyDefence(enemies,115,0,0));
	 					enemyList.add(new EnemyDefence(enemies,215,0,0));
	 					enemyList.add(new EnemyDefence(enemies,315,0,0));
	 					enemyList.add(new EnemyDefence(enemies,415,0,0));
	 					enemyList.add(new EnemyDefence(enemies,515,0,0));
	 					
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