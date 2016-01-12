package org.softwaretalk.blockdestroyer;

public class Player {
	
	private int numberBallsLeft;
	private int score;
	
	public Player() {
		numberBallsLeft = Resources.NUMBER_BALLS_START;
	}
	
	public void addToScore(int amount) {
		score += amount;
	}
	
	public void resetScore() {
		score = 0;
	}
	
	public void addBall() {
		numberBallsLeft++;
	}
	
	public void removeBall() {
		numberBallsLeft--;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getNumberBallsLeft() {
		return numberBallsLeft;
	}

}
