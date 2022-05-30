package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class PacMan extends Entity{

	private int numLives;

	/**
	 * default constructor for PacMan object
	 */
    public PacMan()
    {
    	numLives = 3;
    }
    
    /**
  	 *  @param o and s
  	 * @return eats a specified Eatable object and calls eaten function 
  	 */

    public void eat(Object o, Score s){
        
    	Eatable e = (Eatable)o;
    	e.eaten(s);
    }
    
    /**
	 *  @param g and s
	 * @return increases the score by 200 once a ghost is eaten 
	 */
    
  public void eatGhost(Ghost g, Score s){
        
   
	  s.setScore(s.getScore()+200);
  
    }
    
   
  /**
	 *  
	 * @return decreases number of lives by 1
	 */
    public void defeated(){
    	


   	setNumLives(getNumLives() - 1);

    

    }

    /**
     *  @param l
   	 * @return decreases number of lives by 1
   	 */
    
    
  public void setNumLives(int l){
        
	  numLives = l;
    }
  
  /**
 	 * @return number of lives
 	 */
  
  public int getNumLives(){
      
	  return numLives;
    }
}
