package application;

public class Cherry implements Eatable {

	/**
	 *  @param s
	 * @return the updated score and increase it by 100
	 */

	public void eaten(Score s)
	{
		s.setScore(s.getScore()+100);
	}
}
