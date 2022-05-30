package application;

public class Score {

	private int score;
	
	/**
	 * Non-default constructor for Score object
	 * @param s
	 */
	public Score(int s)
	{
		score = s ;
	} 
	
	/**
	 * default constructor for Score object
	 */
	public Score()
	{
		score = 0;
	}
	
	/**
	 *  
	 * @return the score value
	 */
	
	public int getScore()
	{
		return score;
	}
	
	/**
	 *  @param s
	 * @return sets score to specified value
	 */
	public void setScore(int s)
	{
		score = s;
	}
}
