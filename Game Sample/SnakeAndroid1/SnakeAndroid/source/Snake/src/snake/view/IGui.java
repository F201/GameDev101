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

package snake.view;

/**
 * This interface should be implemented by the gui. The GameController will call
 * each of these methods on every turn (except sendMessage).
 */
public interface IGui {

	/**
	 * informs the gui that the snake is at this position.
	 * <p>
	 * The snake may be at more than one position at a time.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	void setSnake(int x, int y);

	/**
	 * informs the gui that a bonus is at this position.
	 * <p>
	 * There may be more than one bonus in the field.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	void setBoni(int x, int y);

	/**
	 * informs the gui that a border (unwalkable square) is at this position.
	 * <p>
	 * There may be more than one unwalkable square in the field.
	 * <p>
	 * Most of the time it will not be necessary to update the borders in the
	 * gui every time this method is called as borders tend to be fixed.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	void setBorder(int x, int y);

	/**
	 * informs the gui that this position does not hold anything (but it does
	 * exist).
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	void setEmpty(int x, int y);

	/**
	 * informs the gui about the current score of the player.
	 * 
	 * @param score
	 *            score
	 */
	void setScore(int score);

	/**
	 * sends a message to the gui. This may be information about the game state
	 * (for example game over).
	 * 
	 * @param message
	 *            message
	 */
	void sendMessage(String message);

	/**
	 * is always called after all other methods in this interface where called
	 * by the controller for this turn.
	 */
	void updateUI();

}
