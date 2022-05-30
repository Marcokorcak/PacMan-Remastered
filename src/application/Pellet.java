package application;

public class Pellet implements Eatable {

/**
	 *  @param s
	 * @return the updated score after the pellet is eaten by 10 points
	 */
	public void eaten(Score s)
	{
		s.setScore(s.getScore()+10);
	}
	
}