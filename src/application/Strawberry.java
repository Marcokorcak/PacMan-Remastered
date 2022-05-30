package application;

public class Strawberry implements Eatable{

	/**
	 *  @param s
	 * @return the updated score which was increased by 300 when a Strawberry was eaten 
	 */

	public void eaten(Score s)
	{
		s.setScore(s.getScore()+300);
	}
}
