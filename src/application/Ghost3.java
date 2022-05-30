package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ghost3 extends Ghost {

	/**
	 *  defualt constructor that calls super for Ghost class (parent class) 
	 */
	public Ghost3() {
		super();
	}

	/**
	 *  @param row , col, g, c, v
	 * @returns coordingates to where the ghost should move based on the grid layout of the maze and movement determined  
	 */
	public GhostCoordinates move(int row, int col, GridPane g, char[][] c, boolean v) {

		Rectangle rect = new Rectangle(25, 25);

		StackPane root = new StackPane(rect);

		StackPane root2 = new StackPane(rect);

		// if vulnerable (Pac-Man picked up a power pellet) the ghosts gif switches to blue
		if (v) {
			Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\blueghost.gif");
			ImageView imageView = new ImageView(image);

			imageView.setFitHeight(26);
			imageView.setFitWidth(25);
			root.getChildren().add(imageView);


		}

		else {
			Image image = new Image("C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\bluepacman.gif");
			ImageView imageView = new ImageView(image);

			imageView.setFitHeight(26);
			imageView.setFitWidth(25);
			root.getChildren().add(imageView);

		}

		// randomize the ghosts movement by making their chance of moving left, right, up, or down equally likely
		int max = 3;
		int min = 0;
		int range = max - min + 1;
		int randomNum = (int) (Math.random() * range) + min; // generates random number to determine the movement

		if (randomNum == 0) 
		{
			if (c[row][col + 1] != 'W') {        //W on the 2D array represents a Wall, don't move here
				if (c[row][col + 1] == 'L') {    //L represents a pellet

					if (c[row][col] == 'X') {    //X represents a tile that Pac-Man has already been to, meaning no pellet is there

						c[row][col + 1] = '3';   //updates the position in the maze
						c[row][col ] = 'X';
						
						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col+1 , row);
						g.add(root2, col, row);
						
						
						
						imageView3.setImage(null); //set image to null for garbage collection
					

						GhostCoordinates gC = new GhostCoordinates(row, (col + 1));
						

						return gC;

					} else {  //place down a pellet if path not already traveled by Pacman so ghosts dont eat the pellet

						c[row][col +1 ] = '3';
						c[row][col ] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col+1, row);
						g.add(root2, col, row);
					

						GhostCoordinates gC = new GhostCoordinates(row, (col + 1));


						return gC;
					}
				}

				else if (c[row][col + 1] == 'X') {

					if (c[row][col] == 'L') {

						c[row][col + 1] = '3';
						c[row][col] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);  //places down a pellet if pellet was in that location originally

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col+1 , row);
						g.add(root2, col, row);
					

						GhostCoordinates gC = new GhostCoordinates(row, (col + 1));

								
						return gC;
					}

					else {
						c[row][col + 1] = '3';
						c[row][col ] = 'X';
						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png"); //sets down black image behind ghost
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col +1, row);
						g.add(root2, col, row);
						imageView3.setImage(null);
						

						GhostCoordinates gC = new GhostCoordinates(row, (col + 1));

						return gC;
					}

				}

			}
		} else if (randomNum == 1) {                   //********REPEAT ABOVE CODE FOR EVERY RANDOM NUMBER**********
			if (c[row][col - 1] != 'W') {
				if (c[row][col - 1] == 'L') {

					if (c[row][col] == 'X') {
						
						c[row][col - 1] = '3';
						c[row][col  ] = 'X';

						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col-1 , row);
						
						g.add(root2, col, row);
						imageView3.setImage(null);

						GhostCoordinates gC = new GhostCoordinates(row, (col - 1));

						return gC;

					} else {

						c[row][col - 1] = '3';
						c[row][col ] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col -1, row);
						g.add(root2, col, row);

						GhostCoordinates gC = new GhostCoordinates(row, (col - 1));

						return gC;
					}
				}

				else if (c[row][col - 1] == 'X') {

					if (c[row][col] == 'L') {
						
						c[row][col - 1] = '3';
					c[row][col  ] = 'L';

						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col-1 , row);
						g.add(root2, col, row);

						GhostCoordinates gC = new GhostCoordinates(row, (col - 1));

						return gC;
					} else {
						
						c[row][col - 1] = '3';
						c[row][col  ] = 'X';
						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col -1, row);
						g.add(root2, col, row);
						imageView3.setImage(null);

						GhostCoordinates gC = new GhostCoordinates(row, (col - 1));

						return gC;
					}

				}

			}
		} else if (randomNum == 2) {
			if (c[row + 1][col] != 'W') {
				if (c[row + 1][col] == 'L') {

					if (c[row][col] == 'X') {
						
						c[row+1][col ] = '3';
						c[row ][col ] = 'X';

						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col, row +1);
						g.add(root2, col, row);
						imageView3.setImage(null);

						GhostCoordinates gC = new GhostCoordinates(row + 1, (col));
						return gC;

					} else {
						c[row+1][col ] = '3';
						c[row ][col ] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col, row+1 );
						g.add(root2, col, row);

						GhostCoordinates gC = new GhostCoordinates(row + 1, (col));

						return gC;
					}
				}

				else if (c[row + 1][col] == 'X') {

					if (c[row][col] == 'L') {

						c[row+1][col ] = '3';
						c[row ][col ] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col, row +1);
						g.add(root2, col, row);
						

						GhostCoordinates gC = new GhostCoordinates(row + 1, (col));

						return gC;
					} else {
						c[row+1][col ] = '3';
						c[row ][col ] = 'X';
						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col, row+1);
						g.add(root2, col, row);
						imageView3.setImage(null);

						GhostCoordinates gC = new GhostCoordinates(row + 1, (col));

						return gC;
					}

				}

			}
		} else if (randomNum == 3) {

			if (c[row - 1][col] != 'W') {
				if (c[row - 1][col] == 'L') {

					if (c[row][col] == 'X') {

						c[row-1][col ] = '3';
						c[row ][col ] = 'X';
						
						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col, row-1 );
						g.add(root2, col, row);
						imageView3.setImage(null);

						GhostCoordinates gC = new GhostCoordinates(row - 1, (col));

						return gC;

					} else {
						c[row-1][col ] = '3';
						c[row ][col ] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col, row -1);
						g.add(root2, col, row);

						GhostCoordinates gC = new GhostCoordinates(row - 1, (col));

						return gC;
					}
				}

				else if (c[row - 1][col] == 'X') {

					if (c[row][col] == 'L') {
						c[row-1][col ] = '3';
						c[row ][col ] = 'L';
						Circle circ = new Circle();
						circ.setFill(Color.YELLOW);

						circ.setRadius(3);

						root2.getChildren().add(circ);

						g.add(root, col, row-1 );
						g.add(root2, col, row);

						GhostCoordinates gC = new GhostCoordinates(row - 1, (col));

						return gC;
					} else {
						c[row-1][col ] = '3';
						c[row ][col ] = 'X';
						Image image3 = new Image(
								"C:\\Users\\zaluk\\eclipse-workspace\\PacMan\\src\\transparentCircle.png");
						ImageView imageView3 = new ImageView(image3);

						imageView3.setFitHeight(26);
						imageView3.setFitWidth(25);
						root2.getChildren().add(imageView3);

						g.add(root, col, row -1);
						g.add(root2, col, row);
						imageView3.setImage(null);

						GhostCoordinates gC = new GhostCoordinates(row - 1, (col));

						return gC;
					}

				}

			}

		}

		GhostCoordinates gC = new GhostCoordinates(row, col);
		return gC;
	}

}
