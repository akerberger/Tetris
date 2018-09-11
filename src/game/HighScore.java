package game;


public class HighScore implements Comparable <HighScore> {
	private int score;
	private String name;
	
	public HighScore(int score, String name) {	//plus datum!
		this.score = score;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	
	public int getScore() {
		return score;
	}
	
	public int compareTo(HighScore other) {
		if(score > other.score) {
			return 1;
		}
		else if(score < other.score) {
			return -1;
		}
		else return 0;
	}
	
	public String toString() {
		return score+","+name;
	}
	
	
	
	
}


