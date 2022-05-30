package application;

public class Apple implements Eatable { 
	
	
	/**
	 * @param s
	 * @return the updated score by increasing it by 700
	 */
	public void eaten(Score s)
	{
		s.setScore(s.getScore()+700); 
	}
}


