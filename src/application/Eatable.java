package application;

public interface Eatable {
	
	/**
	 * public method to be included in all classes that implement Eatable
	 * @param s
	 * @return the updated score by increasing it by certain amount
	 */
	public void eaten(Score s);

}