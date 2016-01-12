/*    
    Copyright (C) 2012 http://software-talk.org/ (developer@software-talk.org)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package snake.controller;

import snake.Utilities;
import snake.modell.GameField;
import snake.modell.HighScore;
import snake.modell.OneSquareBonus;
import snake.modell.Snake;
import snake.modell.SquareObject;
import snake.view.IGui;

/**
 * The <code>AbstractGameController</code> holds the game loop and executes the
 * game logic.
 */
public abstract class AbstractGameController {

	protected GameField gameField;
	protected Snake snake;
	private final IInputcontroller input;
	protected final IGui gui;
	private boolean playing = true;
	private int score;

	/**
	 * constructs new game controller.
	 * 
	 * @param gui
	 *            the gui
	 * @param input
	 *            input controller
	 */
	public AbstractGameController(IGui gui, IInputcontroller input) {
		resetGame();
		this.gui = gui;
		this.input = input;
	}

	/**
	 * constructs new game field and snake and resets score.
	 */
	public void resetGame() {
		gameField = new GameField(Utilities.getInstance().getWidth(), Utilities
				.getInstance().getHeight());
		gameField.setBorders();
		snake = new Snake(Utilities.getInstance().getWidth() / 2, Utilities
				.getInstance().getHeight() / 2);
		score = 0;
	}

	/**
	 * executes one round of the game loop.
	 */
	protected void playOnce() {
		processInput(getInput());

		moveSnake();
		addBoni();
		checkBoni();
		gameField.updateBoni();

		if (isGameOver()) {
			playing = false;
			gui.sendMessage(" game over");
			saveHighScore();
		}

		updateGui();
	}

	private void saveHighScore() {
		HighScore.getInstance().addAndSave(
				Utilities.getInstance().getPlayerName(), score);
	}

	/**
	 * returns true if the player hit a wall or the snake.
	 * 
	 * @return true if the player hit a wall or the snake
	 */
	private boolean isGameOver() {
		SquareObject head = snake.getHead();
		boolean walkable = gameField.isWalkable(head.getX(), head.getY());
		return !walkable || snake.isHit();
	}

	/**
	 * moves the snake in its current direction.
	 */
	private void moveSnake() {
		snake.moveInCurrentDirection();
	}

	/**
	 * checks if the snake is at a bonus field.
	 */
	private void checkBoni() {
		SquareObject head = snake.getHead();
		OneSquareBonus bonusAt = gameField.getBonusAt(head.getX(), head.getY());
		if (bonusAt != null) {
			score += snake.eat(bonusAt);
		}
	}

	/**
	 * adds a bonus if the game field does not currently have a bonus.
	 */
	private void addBoni() {
		if (gameField.hasNoBonus()) {
			gameField.addBonus(Utilities.getInstance().getBonusValue(),
					Utilities.getInstance().getBonusGrows(), Utilities
							.getInstance().getBonusExistTime(), snake);
		}
	}

	/**
	 * updates the gui.
	 */
	public abstract void updateGui();

	/**
	 * this method should be called to run the game.
	 */
	public abstract void play();

	/**
	 * returns the current score.
	 * 
	 * @return current score
	 */
	public int getScore() {
		return score;
	}

	public boolean isPlaying() {
		return playing;
	}

	/**
	 * enables the game loop.
	 */
	public void setIsPlaying() {
		playing = true;
	}

	/**
	 * stops the current game.
	 */
	public void stopPlaying() {
		playing = false;
	}

	/**
	 * returns width of game field.
	 * 
	 * @return width of game field
	 */
	public int getWidth() {
		return gameField.getWidth();
	}

	/**
	 * returns height of game field.
	 * 
	 * @return height of game field
	 */
	public int getHight() {
		return gameField.getHight();
	}

	/**
	 * reads input from <code>Inputcontroller</code>.
	 */
	private int getInput() {
		return input.getInput();
	}

	/**
	 * applies the last read or recieved input to the game.
	 * 
	 * @param inputMove
	 */
	private void processInput(int inputMove) {
		switch (inputMove) {
		case 1:
			snake.moveUp();
			break;
		case 2:
			snake.moveRight();
			break;
		case 3:
			snake.moveDown();
			break;
		case 4:
			snake.moveLeft();
			break;
		default:
			break;
		}
	}
}
