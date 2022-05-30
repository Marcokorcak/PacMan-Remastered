package application;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;

public class Ghost extends Entity {

	private boolean vulnerable;

	/**
	 * default constructor for Ghost object
	 */

	public Ghost() {
		vulnerable = false;
	}

	/**
	 * Sets all ghosts on the maze to vulnerable
	 */
	
	public void vulnerable() {
		
		vulnerable = true;
	
	AudioClip note = new AudioClip(this.getClass().getResource("pacman_power.wav").toString());
   note.setVolume(0.5);

		
		note.play();
		
		  Timer timer = new Timer();
		    timer.schedule(new TimerTask(){
		        @Override
		        public void run() {
		            vulnerable = false;
		        }
		    }, 10000);
		
		
	}
 
  	 /**
	 * @return the vulnerable state for the ghosts 
	 */

	public boolean getVulnerable() {
		return vulnerable;
	}

	/**
	 *  @param o and s
	 * @return calling the defeated method on Pac-Man  
	 */

	public void eat(Object o,Score s) {

		PacMan p = (PacMan) o;
		p.defeated();

	}


}