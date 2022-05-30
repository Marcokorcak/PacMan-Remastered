package application;

public class PowerPellet implements Eatable {


    /**
	 * @param s
	 * @return the updated score which was increased by 50 when a power pellet was eaten 
	 */


    public void eaten(Score s)
	{
		s.setScore(s.getScore()+50);
	}

}