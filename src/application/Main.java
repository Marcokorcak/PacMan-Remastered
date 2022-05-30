package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.util.Duration;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.AudioClip;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

public class Main extends Application {

	private final int GRID_SIZE1 = 32; //number of rows of maze
	private final int GRID_SIZE2 = 28; //number of columns of maze

	private int pacRow;
	private int pacCol;

	private int ghost1Row;
	private int ghost1Col;
	private int ghost2Row;   //variables to hold positions of all character objects
	private int ghost2Col;

	private int ghost3Row;
	private int ghost3Col;

	private int ghost4Row;
	private int ghost4Col; 

	private int curScore; //current score of pacman


	private int pelCount = 0; //number of pellets collected

	private char[][] allGrid; //2D array to hold maze

	private char[] letters = new char[896];  //array to hold letters of the text file

	private int displayIndex = 0; //index to increase for displaying HUD

	private boolean isPaused = false; //boolean to check if a game was paused

	private boolean canMove = true; //boolean to check if pacman move

	private GridPane grid = new GridPane(); //new gridpane to create maze

	private boolean newGame = false; //boolean to check if new game was started 

	private boolean mainMenu = true; //boolean to check if main menu is up

	private boolean isLoad = false; //boolean to check if a game was loaded 

	private int curLives;

	AudioClip audio = new AudioClip(getClass().getResource("pacman_background.wav").toExternalForm()); //audio clip that plays during the game

	Scanner input = new Scanner(System.in); // creating scanner object called input

	@Override
	public void start(Stage primaryStage) {

		AudioClip noteMenu = new AudioClip(this.getClass().getResource("elevator_menu.wav").toString()); //music that plays in start menu

		noteMenu.play(); 
		
	
		
		allGrid = new char[GRID_SIZE1][GRID_SIZE2]; //creating the 2D array with the sizes established

		Button oButton = new Button("Resume Old Game"); 
		Button nButton = new Button("Start a New Game");

		Stage loadStage = new Stage(); //creating new stage for popup with buttons

		loadStage.setTitle("Pac-Man"); 

		// create a tile pane
		TilePane tilepaneSave = new TilePane();

		// creating labels for menu
		Label label = new Label("Resume Old Game");
		Label label2 = new Label("Start a New Game");
		Label label3 = new Label("Welcome! What would you like to do?");

		// create a popup
		Popup popup = new Popup();

		// set background
		tilepaneSave.setStyle(" -fx-background-color: yellow;");

		oButton.setMaxWidth(Double.MAX_VALUE);
		nButton.setMaxWidth(Double.MAX_VALUE);

		oButton.setStyle("-fx-font-weight: bold");

		// adding the labels
		popup.getContent().add(label3);
		popup.getContent().add(label);
		popup.getContent().add(label2);

		// setting the size of the labels
		label.setMaxWidth(Double.MAX_VALUE);
		label2.setMaxWidth(Double.MAX_VALUE);
		label3.setMaxWidth(Double.MAX_VALUE);

		//Styling the popup menu and buttons
		oButton.setStyle("-fx-text-fill: white;"

				+ "-fx-background-color: black; " + "-fx-font-weight: bold");
		oButton.setAlignment(Pos.CENTER);
		oButton.setPadding(new Insets(10));
		oButton.setPadding(new Insets(20, 20, 20, 20));
		tilepaneSave.setAlignment(Pos.CENTER);

		nButton.setStyle("-fx-text-fill: white;"

				+ "-fx-background-color: black; " + "-fx-font-weight: bold");
		nButton.setAlignment(Pos.CENTER);
		nButton.setPadding(new Insets(10));
		nButton.setPadding(new Insets(20, 20, 20, 20));

		// action event for object serialization 
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { //event handler for when loading a previous game

			public void handle(ActionEvent e) { 

				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream("saveMaze.obj")); //establishing the output file when game is saved
					allGrid = (char[][]) in.readObject(); //reading in the maze
					curScore = in.readInt(); //reading in score of Pac-Man

					curLives = in.readInt(); //reading in the lives of Pac-Man

					noteMenu.stop();

					in.close();

					mainMenu = false; //main menu is not shown anymore 

					isLoad = true;  //game is loaded 

					loadStage.close();

					loadBoard(primaryStage); //loading primary stage if no longer in main menu  
					

				} catch (Exception e1) { //exception that prints if file could not be read in correctly 
					System.out.println("Something happened when writing to file."); 
					e1.printStackTrace();
					loadBoard(primaryStage);

				}

			}
		};
 
		EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() { //event handler for starting a new game

			public void handle(ActionEvent e) {

				newGame = true;

				mainMenu = false;

				loadStage.close();
				
				noteMenu.stop();

				loadBoard(primaryStage);

			}
		};


		oButton.setOnAction(event);

		nButton.setOnAction(event2);
		//setting the UI/spacing the buttons/styling 

		tilepaneSave.setHgap(10);
		tilepaneSave.setVgap(10);

		label3.setStyle("-fx-text-fill: black;" + "-fx-background-color: yellow;" + "-fx-font-weight: bold;"
				+ "-fx-font-size: 20");
		label3.setPadding(new Insets(20));

		//adding the labels to the tilepane 
		tilepaneSave.getChildren().add(label3);

		tilepaneSave.getChildren().add(oButton);

		tilepaneSave.getChildren().add(nButton);

		Scene scene2 = new Scene(tilepaneSave, 500, 500); //setting the size of the scene

		loadStage.setScene(scene2); //setting the stage to a scene

		loadStage.show(); //showing the stage 

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * @param primaryStage
	 */
	/**
	 * @param primaryStage
	 */
	private void loadBoard(Stage primaryStage) {

		if (mainMenu == false) { //if no longer in the main menu screen 

			Image imageBlack = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\blackImage.png"); 
			ImageView imageViewBlack = new ImageView(imageBlack); 

			//setting height and width of the images
			imageViewBlack.setFitHeight(25);
			imageViewBlack.setFitWidth(25);

			Image imageDown = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pacmanDown.gif"); //gif for pacman going down
			ImageView imageViewDown = new ImageView(imageDown);

			//setting height and width of the images
			imageViewDown.setFitHeight(25);
			imageViewDown.setFitWidth(25);

			Image imageUp = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pacmanUp.gif"); //gif for pacman going up
			ImageView imageViewUp = new ImageView(imageUp);

			//setting height and width of the images
			imageViewUp.setFitHeight(25);
			imageViewUp.setFitWidth(25);

			Image imageRight = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pacmanRight.gif"); //gif for pacman moving to the right
			ImageView imageViewRight = new ImageView(imageRight);

			//setting height and width of the images
			imageViewRight.setFitHeight(25);
			imageViewRight.setFitWidth(25);

			Image imageLeft = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pacmanLeft.gif"); //gif for pacman moving to the left
			ImageView imageViewLeft = new ImageView(imageLeft);

			//setting height and width of the images
			imageViewLeft.setFitHeight(25);
			imageViewLeft.setFitWidth(25);

			AudioClip note = new AudioClip(this.getClass().getResource("pacman_beginning.wav").toString()); //audio clip that plays before game starts

			note.play();

			isPaused = true;

			//timer to set the length of the audio clip 
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					isPaused = false;
				}
			}, 5000);

			final Task task = new Task() {

				@Override
				protected Object call() throws Exception {

					Thread.sleep(4000); //stops all movement and lets audio play 
	
					audio.setVolume(0.4f);
					audio.setCycleCount(AudioClip.INDEFINITE);
					audio.play();
					return null;
				}
			};

			Thread thread = new Thread(task);
			thread.start();

			PacMan packy = new PacMan(); //pacman object 

			Ghost1 ghost1 = new Ghost1(); //ghost 1 object 

			Ghost2 ghost2 = new Ghost2(); //ghost 2 object 

			Ghost3 ghost3 = new Ghost3(); //ghost 3 object 

			Ghost4 ghost4 = new Ghost4(); //ghost 4 object 

			Pellet pelley = new Pellet(); //pellet object 

			Label Pc = new Label(" ");

			Image pause = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pause.jpg"); //pause image 
			ImageView viewPause = new ImageView(pause);

			Image play = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\play3.jpg"); //play image 
			ImageView viewPlay = new ImageView(play);

			Cherry cherry = new Cherry(); //cherry object 

			Strawberry strawberry = new Strawberry(); //strawberry object 

			Orange orange = new Orange(); //orange object 

			Apple apple = new Apple(); //apple object 

			PowerPellet powerPellet = new PowerPellet(); //powerpellet object 

			//if loading old game sets pacman number of lives to his previous number of lives
			if (isLoad == true) {
				packy.setNumLives(curLives);
			}

			System.out.println(newGame);
			if (newGame == true) { 

				Score scorey = new Score(); //if new game then new score object is made

				curLives = 3; 


				try {

					input = new Scanner(new File("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\Maze")); //creates a new maze with default settings
				}

				catch (Exception e) { //exception if file could not be found

					System.out.println("Could not find the file"); 
					e.printStackTrace(); //for error catching 

				}

				int index = 0;

				while (input.hasNext()) { //reading in the new maze

					char letter = input.next().charAt(0); //getting singular char from maze 

					letters[index] = letter; //setting the char to the array of all chars

					if (index <= 896) //repeats until done 
						index++;

				}

				int index2 = 0;
				
				//double for loop for the maze which is 2D array 
				for (int row = 0; row < GRID_SIZE1; row++) {
					for (int col = 0; col < GRID_SIZE2; col++) {

						allGrid[row][col] = letters[index2]; //setting index of the 2d array to a specific letter

						index2++;

					}

				}
			}


			Scene scene = new Scene(grid); //creating the maze scene

			Score scorey = new Score(curScore); //creating new score object 
			Label Sc = new Label(String.valueOf(scorey.getScore())); //displaying the score

			//Styling the labels that display score and current lives
			Sc.setStyle("-fx-font-weight: bold");
			Sc.setFont(new Font("Arial", 11));

			Label Lc = new Label(String.valueOf(curLives));
			Lc.setStyle("-fx-font-weight: bold");

			//creating maze by creating new rectangle objects

			for (int row = 0; row < GRID_SIZE1; row++) {
				for (int col = 0; col < GRID_SIZE2; col++) {

					Rectangle rect = new Rectangle(25, 25);

					StackPane root = new StackPane(rect);

					if (allGrid[row][col] == 'W') { //if letter equals W then it is a wall 
						rect.setFill(Color.BLUE);
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);

					}

					else if (allGrid[row][col] == 'G') { //if letter equals G is a empty space with no pellet
						rect.setFill(Color.rgb(0, 0, 1));
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == 'P') { //if letter equals P then it is a power pellet 
						Circle c = new Circle();
						c.setFill(Color.YELLOW);

						c.setRadius(7);
						pelCount++;
						root.getChildren().add(c);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);

					} else if (allGrid[row][col] == 'M') { //if letter equals M then it is the pause or play button   

						rect.setFill(Color.YELLOW);

						viewPause.setFitHeight(10);
						viewPause.setFitWidth(10);

						Pc.setGraphic(viewPause);

						root.getChildren().add(Pc);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.YELLOW);
					}

					else if (allGrid[row][col] == 'D') { //if letter equals D then it is where the score and number of lives were displayed 

						if (displayIndex == 0) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("S");

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);

							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);

							displayIndex++;
						}
						
						
						else if (displayIndex == 1) { 
							rect.setFill(Color.YELLOW);
							Label b = new Label("C");

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);

							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);

							displayIndex++;
						}

						else if (displayIndex == 2) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("O");
							b.setStyle("-fx-font-weight: bold");

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							root.getChildren().add(b);

							displayIndex++;
						}
						
						else if (displayIndex == 3) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("R");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						}

						else if (displayIndex == 4) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("E");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						}

						else if (displayIndex == 5) {
							rect.setFill(Color.YELLOW);

							root.getChildren().add(Sc);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						}

						else if (displayIndex == 6) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("P");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						} else if (displayIndex == 7) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("A");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						} else if (displayIndex == 8) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("U");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						} else if (displayIndex == 9) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("S");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						} else if (displayIndex == 10) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("E");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						}

						else if (displayIndex == 11) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("(P)");
							b.setStyle("-fx-font-weight: bold");

							root.getChildren().add(b);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						} else if (displayIndex == 12) {
							rect.setFill(Color.YELLOW);

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							displayIndex++;
						}

						else if (displayIndex == 13) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("L");
							b.setStyle("-fx-font-weight: bold");

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							root.getChildren().add(b);

							displayIndex++;
						}

						else if (displayIndex == 14) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("I");
							b.setStyle("-fx-font-weight: bold");

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);

							root.getChildren().add(b);

							displayIndex++;
						}

						else if (displayIndex == 15) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("V");
							b.setStyle("-fx-font-weight: bold");

							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);

							root.getChildren().add(b);

							displayIndex++;
						}

						else if (displayIndex == 16) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("E");
							b.setStyle("-fx-font-weight: bold");
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);

							root.getChildren().add(b);

							displayIndex++;
						}

						else if (displayIndex == 17) {
							rect.setFill(Color.YELLOW);
							Label b = new Label("S");
							b.setStyle("-fx-font-weight: bold");
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);

							root.getChildren().add(b);

							displayIndex++;

						}

						else if (displayIndex == 18) {
							rect.setFill(Color.YELLOW);
							rect.setStrokeWidth(1);
							rect.setStroke(Color.YELLOW);
							root.getChildren().add(Lc);

						}

					}

					else if (allGrid[row][col] == 'C') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\Cherry.png"); //if letter equals C then it is cherry 
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(16);
						root.getChildren().add(imageView);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == 'H') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pacmanRight.gif"); //if letter equals H then it is where Pac-Man starts and it is how we keep track of where he is  
						ImageView imageView = new ImageView(image);

						pacRow = row;

						pacCol = col;
						imageView.setFitHeight(26);
						imageView.setFitWidth(25);
						root.getChildren().add(imageView);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);

					}

					else if (allGrid[row][col] == 'O') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\Orange.png"); //if letter equals O then it is a orange 
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					//checks for ghosts and displays their images
					else if (allGrid[row][col] == '1') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\redghost.gif");
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						ghost1Row = row;
						ghost1Col = col;
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == '2') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\ghost2.gif");
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						ghost2Row = row;
						ghost2Col = col;
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == '3') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\bluepacman.gif");
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						ghost3Row = row;
						ghost3Col = col;
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == '4') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\pinkpacman.gif");
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						ghost4Row = row;
						ghost4Col = col;
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == 'A') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\Apple.png"); //if letter equals A then it is an Apple
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == 'S') {
						Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\Strawberry.png"); //if letter equals S then it is a strawberry 
						ImageView imageView = new ImageView(image);

						imageView.setFitHeight(26);
						imageView.setFitWidth(26);
						root.getChildren().add(imageView);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else if (allGrid[row][col] == 'L') { //if letter equals L then it is a pellet  

						Circle c = new Circle();
						c.setFill(Color.YELLOW);

						c.setRadius(3);

						pelCount++;
						root.getChildren().add(c);

						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					else {
						rect.setFill(Color.BLACK);
						rect.setStrokeWidth(1);
						rect.setStroke(Color.BLACK);
					}

					grid.add(root, col, row);

				} 
 
			}
				
					//event handler for the game over pop up 
			scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> { //take in a key pressed
				
				System.out.println(pelCount);

				if (packy.getNumLives() == 0 || pelCount <= 0) {

					Stage endStage = new Stage(); //creating a new stage 

					//creating new buttons to be displayed
					Button rButton = new Button("Restart");
					Button eButton = new Button("End game");

					endStage.setTitle("Game Over");

					//new tilepane
					TilePane tilepaneReEnd = new TilePane();

					Label label1 = new Label("GAME OVER");

					//creating pop up to be displayed
					Popup popup1 = new Popup();

						//styling the pop up and its features
					tilepaneReEnd.setStyle(" -fx-background-color: yellow;");

					rButton.setMaxWidth(Double.MAX_VALUE);
					rButton.setMaxWidth(Double.MAX_VALUE);

					rButton.setStyle("-fx-font-weight: bold");

					eButton.setMaxWidth(Double.MAX_VALUE);
					eButton.setMaxWidth(Double.MAX_VALUE);

					eButton.setStyle("-fx-font-weight: bold");

					popup1.getContent().add(label1);

					label1.setMaxWidth(Double.MAX_VALUE);
					label1.setMaxWidth(Double.MAX_VALUE);

					rButton.setStyle("-fx-text-fill: white;"

							+ "-fx-background-color: black; " + "-fx-font-weight: bold");
					rButton.setAlignment(Pos.CENTER);
					rButton.setPadding(new Insets(10));
					rButton.setPadding(new Insets(20, 20, 20, 20));

					eButton.setStyle("-fx-text-fill: white;"

							+ "-fx-background-color: black; " + "-fx-font-weight: bold");
					eButton.setAlignment(Pos.CENTER);
					eButton.setPadding(new Insets(10));
					eButton.setPadding(new Insets(20, 20, 20, 20));

					tilepaneReEnd.setAlignment(Pos.CENTER);

					tilepaneReEnd.setHgap(10);
					tilepaneReEnd.setVgap(10);

					//event handler for clicking start a restart
					EventHandler<ActionEvent> eventPause = new EventHandler<ActionEvent>() {

						public void handle(ActionEvent e) {

							primaryStage.close();
							endStage.close();

							//setting the positions for Pac-Man and for respawn 
							pacRow = 23;
							pacCol = 14;

							ghost1Row = 5;
							ghost1Col = 6;

							ghost2Row = 23;
							ghost2Col = 21;

							ghost3Row = 5;
							ghost3Col = 21;

							ghost4Row = 23;
							ghost4Col = 6;

							pelCount = 238;
							displayIndex = 0;

							GridPane grid2 = new GridPane();

							scene.setRoot(grid2);

							start(new Stage()); //showing the new stage
							printGrid(); //creating and showing the maze

						}
					};

					//event handler for when end game is clicked
					EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {

						public void handle(ActionEvent e) {

							System.exit(0); //ends the program 

						}
					};


					rButton.setOnAction(eventPause);

					eButton.setOnAction(event3);
					
					//adding restart and end buttons to a tile pane
					tilepaneReEnd.getChildren().add(rButton);
					tilepaneReEnd.getChildren().add(eButton);

					//creating scene with the tilepane and sizing it 
					Scene sceneEnd = new Scene(tilepaneReEnd, 200, 200);

					AudioClip noteEnd= new AudioClip(this.getClass().getResource("end_music.wav").toString());

					noteEnd.play();
					
					endStage.setScene(sceneEnd); //setting the stage

					endStage.show(); //showing the stage 

				}
				if (key.getCode() == KeyCode.P) {  // if P key was pressed


					isPaused = !isPaused;  //pause, unpause game 

					viewPlay.setFitHeight(15);
					viewPlay.setFitWidth(15);

					//change pause symbol and stop background audio
					Pc.setGraphic(viewPlay);
					audio.stop();

					//creates new stage
					Stage pauseStage = new Stage();

					Button pButton = new Button("Unpause");
					Button sButton = new Button("Save Game");

					pauseStage.setTitle("Paused");

					TilePane tilepane = new TilePane();

					
					//creates new labels for pause menu 
					Label labelPause = new Label("PAUSED");
					Label labelSave = new Label("Save Game");

					Popup popupPause = new Popup();

					tilepane.setStyle(" -fx-background-color: yellow;");

					pButton.setMaxWidth(Double.MAX_VALUE);
					pButton.setMaxWidth(Double.MAX_VALUE);

					pButton.setStyle("-fx-font-weight: bold");

					popupPause.getContent().add(labelPause);
					popupPause.getContent().add(labelSave);

					labelPause.setMaxWidth(Double.MAX_VALUE);
					labelSave.setMaxWidth(Double.MAX_VALUE);

					pButton.setStyle("-fx-text-fill: white;"

							+ "-fx-background-color: black; " + "-fx-font-weight: bold");
					pButton.setAlignment(Pos.CENTER);
					pButton.setPadding(new Insets(10));
					pButton.setPadding(new Insets(20, 20, 20, 20));                         //all stylings for pause menu 
					tilepane.setAlignment(Pos.CENTER);

					sButton.setStyle("-fx-text-fill: white;"

							+ "-fx-background-color: black; " + "-fx-font-weight: bold");
					sButton.setAlignment(Pos.CENTER);
					sButton.setPadding(new Insets(10));
					sButton.setPadding(new Insets(20, 20, 20, 20));

					//event handler for if the unpause button is clicked by the user 
					EventHandler<ActionEvent> eventEnd = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {

							pauseStage.close();
							audio.play();  //play sound again 
							Pc.setGraphic(viewPause);
							isPaused = false;  //unpause app

						}
					};

					
					//event handler for if the save button is clicked by the user 
					EventHandler<ActionEvent> eventSave = new EventHandler<ActionEvent>() {

						public void handle(ActionEvent e) {

							try {
								ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saveMaze.obj"));
								out.writeObject(allGrid);
								out.writeInt(curScore);
								out.writeInt(curLives);
								out.close();
							} catch (Exception ev) {
								System.out.println("Something happened when writing to file.");
								ev.printStackTrace();
							}
							System.exit(0);

						}
					};


					pButton.setOnAction(eventEnd);  //add event handlers to the buttons 

					sButton.setOnAction(eventSave);
					tilepane.setHgap(10);
					tilepane.setVgap(10);

					tilepane.getChildren().add(pButton);

					tilepane.getChildren().add(sButton);  //add buttons to the tilepane

					Scene scenePause = new Scene(tilepane, 200, 200);

					pauseStage.setScene(scenePause);

					pauseStage.show(); // set scene and display pause menu 

				}

				if (isPaused == false && canMove == true) {

					canMove = false;

					Timer timer2 = new Timer();
					timer2.schedule(new TimerTask() {
						@Override
						public void run() {
							canMove = true;
						}
					}, 150);

					Rectangle rect = new Rectangle(25, 25);

					StackPane root = new StackPane(rect);

					StackPane root2 = new StackPane(rect);

					if (key.getCode() == KeyCode.RIGHT) {  //if the right key is pressed

						if (allGrid[pacRow][pacCol + 1] != 'W') {  // if a wall is not present


							root.getChildren().add(imageViewRight);

							grid.add(root, pacCol + 1, pacRow); // add new pacman image in next position

							root2.getChildren().add(imageViewBlack);

							Sc.setText(Integer.toString(scorey.getScore()));  //set scorey value to be displayed in label during game

							grid.add(root2, pacCol, pacRow);

							if (allGrid[pacRow][pacCol + 1] == 'B') { //If blank space encountered
								packy.eat(pelley, scorey);

								curScore = scorey.getScore();  //update current score

								allGrid[pacRow][pacCol] = 'X';  //places an X where pacman has been

							}

							if (allGrid[pacRow][pacCol + 1] == 'L') {
								packy.eat(pelley, scorey);  //eat pellet

								curScore = scorey.getScore(); //update score

								allGrid[pacRow][pacCol] = 'X';

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString()); //play pacman eat noise

								note2.play();

								pelCount--; //decrease 

							}

							else if (allGrid[pacRow][pacCol + 1] == 'P') {
								packy.eat(powerPellet, scorey);
								curScore = scorey.getScore();  //update score

								allGrid[pacRow][pacCol] = 'X';     //place an X where pacman has been

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString());

								note2.play();

								pelCount--;     //decrease

								// four ghosts are vulnerable and their actions are randomized
								ghost1.vulnerable();
								ghost2.vulnerable();
								ghost3.vulnerable();
								ghost4.vulnerable();

							}

							else if (allGrid[pacRow][pacCol + 1] == 'S') {
								packy.eat(strawberry, scorey);
								curScore = scorey.getScore();  //update score after Strawberry is eaten

								allGrid[pacRow][pacCol] = 'X';   //place an X where pacman has been
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow][pacCol + 1] == 'O') {
								packy.eat(orange, scorey);
								curScore = scorey.getScore();  //update score after Orange is eaten

								allGrid[pacRow][pacCol] = 'X';     //place an X where pacman has been
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							} else if (allGrid[pacRow][pacCol + 1] == 'G') {

								allGrid[pacRow][pacCol] = 'X';   //place an X where pacman has been

							}

							else if (allGrid[pacRow][pacCol + 1] == 'X') {

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow][pacCol + 1] == 'A') {
								packy.eat(apple, scorey);
								curScore = scorey.getScore();  //updates score after pacman eats the apple

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}
							// checks if pacman is on the same part of the maze as one of the ghosts
							else if (allGrid[pacRow][pacCol + 1] == '1' || allGrid[pacRow][pacCol + 1] == '2'
									|| allGrid[pacRow][pacCol + 1] == '3' || allGrid[pacRow][pacCol + 1] == '4') {

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow][pacCol + 1] == 'C') {
								packy.eat(cherry, scorey);
								curScore = scorey.getScore();  //updates score after pacman eats a cherry

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							pacCol = pacCol + 1;

							allGrid[pacRow][pacCol] = 'H';

							primaryStage.setScene(scene);
							primaryStage.show();

						}

					}

					if (key.getCode() == KeyCode.LEFT) {  //check if left key was pressed 

						if (allGrid[pacRow][pacCol - 1] != 'W') {


							root.getChildren().add(imageViewLeft);

							grid.add(root, pacCol - 1, pacRow);


							root2.getChildren().add(imageViewBlack); //add black image to stack pane 

							Sc.setText(Integer.toString(scorey.getScore()));

							grid.add(root2, pacCol, pacRow);

							if (allGrid[pacRow][pacCol - 1] == 'L') {
								packy.eat(pelley, scorey);

								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X'; // places an X where pacman has been 

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString());

								note2.play();

								pelCount--; //decreases pellet count by 1

							}

							else if (allGrid[pacRow][pacCol - 1] == 'P') { //checks for a power pellet 
								packy.eat(powerPellet, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X';

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString());

								note2.play();

								pelCount--;

								ghost1.vulnerable();
								ghost2.vulnerable();  //makes all the ghosts vulnerable 
								ghost3.vulnerable();
								ghost4.vulnerable();

							}

							else if (allGrid[pacRow][pacCol - 1] == 'S') { //checks for a strawberry 
								packy.eat(strawberry, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow][pacCol - 1] == 'O') { //checks for an orange 
								packy.eat(orange, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow][pacCol - 1] == 'A') { //checks for an apple 
								packy.eat(apple, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow][pacCol - 1] == 'G') { //checks for a blank space 

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow][pacCol - 1] == '1' || allGrid[pacRow][pacCol - 1] == '2'
									|| allGrid[pacRow][pacCol - 1] == '3' || allGrid[pacRow][pacCol - 1] == '4') {  //checks for a ghost 

								allGrid[pacRow][pacCol] = 'X';

							} else if (allGrid[pacRow][pacCol - 1] == 'X') {

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow][pacCol - 1] == 'C') { //checks for a cherry 
								packy.eat(cherry, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X'; //places an X where pacman was 
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							pacCol = pacCol - 1;

							allGrid[pacRow][pacCol] = 'H';

							primaryStage.setScene(scene);
							primaryStage.show();
						}

					}

					if (key.getCode() == KeyCode.UP) {

						if (allGrid[pacRow - 1][pacCol] != 'W') {//checks for a wall



							root.getChildren().add(imageViewUp);

							grid.add(root, pacCol, pacRow - 1);


							root2.getChildren().add(imageViewBlack); //adds black image to stack pane 

							Sc.setText(Integer.toString(scorey.getScore()));

							grid.add(root2, pacCol, pacRow);

							if (allGrid[pacRow - 1][pacCol] == 'L') { //checks for a pellet in next position 
								packy.eat(pelley, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X';

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString());

								note2.play();

								pelCount--;

							}

							else if (allGrid[pacRow - 1][pacCol] == 'P') { //checks for power pellet 
								packy.eat(powerPellet, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X'; //updates maze

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString());

								note2.play();

								pelCount--;

								ghost1.vulnerable();  //makes ghosts vulnerable when power pellet is eaten
								ghost2.vulnerable();
								ghost3.vulnerable();
								ghost4.vulnerable();

							}

							else if (allGrid[pacRow - 1][pacCol] == 'S') { //checks if strawberry was encountered
								packy.eat(strawberry, scorey);
								curScore = scorey.getScore();

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow - 1][pacCol] == '1' || allGrid[pacRow - 1][pacCol] == '2'
									|| allGrid[pacRow - 1][pacCol] == '3' || allGrid[pacRow - 1][pacCol] == '4') { //checks if ghost was encountered 

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow - 1][pacCol] == 'O') { //checks if orange was encountered 
								packy.eat(orange, scorey);
								curScore = scorey.getScore();  ///eats orange and updates score

								allGrid[pacRow][pacCol] = 'X';  //places an X at that position 
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow - 1][pacCol] == 'G') {  //checks for blank space 

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow - 1][pacCol] == 'X') { //checks if pacman has already been there 

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow - 1][pacCol] == 'A') {  //checks if apple was encountered
								packy.eat(apple, scorey);
								curScore = scorey.getScore(); //eats apple and increases score 

								allGrid[pacRow][pacCol] = 'X'; //places an X where pacman has been 
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow - 1][pacCol] == 'C') {
								packy.eat(cherry, scorey);    
								curScore = scorey.getScore();  //updates score after pacman eats a cherry

								allGrid[pacRow][pacCol] = 'X';
								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}
							

							pacRow = pacRow - 1;

							allGrid[pacRow][pacCol] = 'H';  //places an H where pacman now is 

							primaryStage.setScene(scene);
							primaryStage.show();
						}

					}

					if (key.getCode() == KeyCode.DOWN) { ///if down key was pressed

						if (allGrid[pacRow + 1][pacCol] != 'W') { //checking to see if there is a wall in that position 


							root.getChildren().add(imageViewDown); //changing the gif and movement of pacman 

							grid.add(root, pacCol, pacRow + 1); 


							root2.getChildren().add(imageViewBlack); //adding a black image since it was passed by pac-man

							Sc.setText(Integer.toString(scorey.getScore())); //changing the score label 

							grid.add(root2, pacCol, pacRow);

							if (allGrid[pacRow + 1][pacCol] == 'L') { // checking to see if there is a pellet 
								packy.eat(pelley, scorey); //eating the pellet
								curScore = scorey.getScore(); //changing the current score

								allGrid[pacRow][pacCol] = 'X'; //adding a X to indicate pacman has been there 

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString()); //playing eating noise

								note2.play();

								pelCount--; //decreasing pelcount if pacman passed a pellet 
							}
							
							else if (allGrid[pacRow + 1][pacCol] == 'P') { //checking to see if there is a power pellet in that position
								packy.eat(powerPellet, scorey); //eating the power pellet 
								curScore = scorey.getScore(); //changing the current score

								allGrid[pacRow][pacCol] = 'X'; //indicating pacman has been there

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_chomp.wav").toString());

								note2.play();

								pelCount--; //decreasing pel count 

								//changing the ghosts state to vulnerable 
								ghost1.vulnerable();
								ghost2.vulnerable();
								ghost3.vulnerable();
								ghost4.vulnerable();

							}

							else if (allGrid[pacRow + 1][pacCol] == 'S') { //checking to see if there is a strawberry
								packy.eat(strawberry, scorey); //eating the strawberry
								curScore = scorey.getScore(); //changing the score

								allGrid[pacRow][pacCol] = 'X'; //indicating he's been passed that position 

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow + 1][pacCol] == 'O') { //checking to see if there is a orange 
								packy.eat(orange, scorey); //eating the orange 

								allGrid[pacRow][pacCol] = 'X'; //indicating pac-man has passed that position 

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString());

								note2.play();
							}

							else if (allGrid[pacRow + 1][pacCol] == 'G') { //blank area around the cage

								allGrid[pacRow][pacCol] = 'X'; //showing he's been there

							} else if (allGrid[pacRow + 1][pacCol] == 'X') { //checking if pacman has passed this position already 

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow + 1][pacCol] == '1' || allGrid[pacRow + 1][pacCol] == '2'
									|| allGrid[pacRow + 1][pacCol] == '3' || allGrid[pacRow + 1][pacCol] == '4') { //checking to see if he has passed a ghost

								allGrid[pacRow][pacCol] = 'X';

							}

							else if (allGrid[pacRow + 1][pacCol] == 'A') { //checking if he is moving to an apple
								packy.eat(apple, scorey);
								curScore = scorey.getScore(); //checking score

								allGrid[pacRow][pacCol] = 'X';

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString()); //changing the image to eating gif

								note2.play();
							}

							else if (allGrid[pacRow + 1][pacCol] == 'C') { //checking if he is moving to an cherry
								packy.eat(cherry, scorey);
								curScore = scorey.getScore();//checking score

								allGrid[pacRow][pacCol] = 'X';

								AudioClip note2 = new AudioClip(
										this.getClass().getResource("pacman_eatfruit.wav").toString()); //changing gif

								note2.play();
							}

							pacRow = pacRow + 1; //changing his row variable

							allGrid[pacRow][pacCol] = 'H'; //showing pacman position in the maze
							primaryStage.setScene(scene); //setting the scene
							primaryStage.show(); //showing the stage 

						}
					}
				}
			});

			Timeline timeline;
			timeline = new Timeline(new KeyFrame(Duration.millis(150), (evt) -> {

				if (isPaused == false) {  //if not paused

					GhostCoordinates c = ghost1.move(ghost1Row, ghost1Col, grid, allGrid, ghost1.getVulnerable());  //move each ghost by calling their move functions
					ghost1Row = c.getRow();
					ghost1Col = c.getCol();

					GhostCoordinates b = ghost2.move(ghost2Row, ghost2Col, grid, allGrid, ghost2.getVulnerable());

					ghost2Row = b.getRow();
					ghost2Col = b.getCol();

					GhostCoordinates a = ghost3.move(ghost3Row, ghost3Col, grid, allGrid, ghost3.getVulnerable());
					ghost3Row = a.getRow();
					ghost3Col = a.getCol();

					GhostCoordinates y = ghost4.move(ghost4Row, ghost4Col, grid, allGrid, ghost4.getVulnerable());

					ghost4Row = y.getRow();
					ghost4Col = y.getCol();

					if ((pacRow == ghost1Row && pacCol == ghost1Col) || (pacRow == ghost2Row && pacCol == ghost2Col)  //checks if pacman is at the same location as a ghost, and if the ghost is vulnerable or not
							|| (pacRow == ghost3Row && pacCol == ghost3Col)  
							|| (pacRow == ghost4Row && pacCol == ghost4Col)) {
						if (!(ghost1.getVulnerable()) || !(ghost2.getVulnerable()) || !(ghost3.getVulnerable())
								|| !(ghost4.getVulnerable()))

						{

							packy.defeated(); //call pacman's defeated function

							curLives = packy.getNumLives();  //set current lives to pacman's number of lives

							Lc.setText(Integer.toString(packy.getNumLives()));  //display number of lives in label

							AudioClip note2 = new AudioClip(this.getClass().getResource("pacman_death.wav").toString()); //play pacman death noise

							note2.play(); //play sound

							pacRow = 23; //reset pacman to his starting spawn position
							pacCol = 14;

						}

						else if (pacRow == ghost1Row && pacCol == ghost1Col) { // checking to see if pacman is at the position of a ghost 

							packy.eatGhost(ghost1, scorey); //pacman will eat the ghost
							curScore = scorey.getScore(); //changing the score 

							ghost1Row = 5; //sets the ghosts row
							ghost1Col = 6; //sets the ghosts col 

							AudioClip note3 = new AudioClip(
									this.getClass().getResource("pacman_eat_ghost.wav").toString()); //playing the eating clip

							note3.play();

							AudioClip note4 = new AudioClip(
									this.getClass().getResource("pacman_ghost_respawn.wav").toString()); //playing respawn noise

							note4.play();

						}

						else if (pacRow == ghost2Row && pacCol == ghost2Col) { //checking if pacman is at the same position as a ghost 
							packy.eatGhost(ghost2, scorey); //pac-man eating the ghost 
							curScore = scorey.getScore();

							ghost2Row = 23; //sets the ghosts row
							ghost2Col = 21;//sets the ghosts col

							AudioClip note3 = new AudioClip(
									this.getClass().getResource("pacman_eat_ghost.wav").toString()); //playing eating clip

							note3.play(); 

							AudioClip note4 = new AudioClip(
									this.getClass().getResource("pacman_ghost_respawn.wav").toString()); //playing respwan clip 

							note4.play(); 
						}

						else if (pacRow == ghost3Row && pacCol == ghost3Col) { //checking if the pacman is at the same position as a ghost 
							packy.eatGhost(ghost2, scorey); //pacman eating ghost 
							curScore = scorey.getScore();

							ghost3Row = 5; //sets the ghosts row
							ghost3Col = 21;//sets the ghosts col

							AudioClip note3 = new AudioClip(
									this.getClass().getResource("pacman_eat_ghost.wav").toString()); //playing eating ghost clip

							note3.play();

							AudioClip note4 = new AudioClip(
									this.getClass().getResource("pacman_ghost_respawn.wav").toString()); //playing respawn clip 

							note4.play();
						}

						else if (pacRow == ghost4Row && pacCol == ghost4Col) { //checking if pacman is in the same position as a ghost 
							packy.eatGhost(ghost2, scorey); //pacman eats the ghost 
							curScore = scorey.getScore(); //changes score

							ghost4Row = 23; //sets ghost row
							ghost4Col = 6; //sets ghost col

							AudioClip note3 = new AudioClip(
									this.getClass().getResource("pacman_eat_ghost.wav").toString());

							note3.play();

							AudioClip note4 = new AudioClip(
									this.getClass().getResource("pacman_ghost_respawn.wav").toString());

							note4.play();
						}

					}
				}
			}));
			timeline.setCycleCount(Animation.INDEFINITE); //endlessly loop ghost movements
			timeline.play();

			primaryStage.setScene(scene);
			primaryStage.show();  //show and display primary screen to the user 
		}

	}

	private void printGrid() {  //function to print out current maze

		for (int row = 0; row < GRID_SIZE1; row++) {
			for (int col = 0; col < GRID_SIZE2; col++) {
				System.out.print(allGrid[row][col] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}