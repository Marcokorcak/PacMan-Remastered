package application;

public class Orange implements Eatable {
	/**
	 *  @param s
	 * @return the updated score by increasing it by 500
	 */

	public void eaten(Score s)
	{
		s.setScore(s.getScore()+500);
	}
}
