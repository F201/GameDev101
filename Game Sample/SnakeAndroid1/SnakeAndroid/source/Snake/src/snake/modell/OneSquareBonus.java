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

package snake.modell;

/**
 * A bonus that is one square big.
 */
public class OneSquareBonus extends SquareObject {

	private final int pointValue;
	private final int growthValue;
	private int timeLeft;

	/**
	 * constructs new <code>OneSquareBonus</code>.
	 * 
	 * @param pointValue
	 *            value of the bonus (for the players score)
	 * @param growthValue
	 *            amount by which the snake will grow if bonus is consumed
	 * @param timeLeft
	 *            number of rounds this bonus exists (-1 for forever)
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	public OneSquareBonus(int pointValue, int growthValue, int timeLeft, int x,
			int y) {
		super(x, y);
		this.pointValue = pointValue;
		this.growthValue = growthValue;
		this.timeLeft = timeLeft;
	}

	/**
	 * if the time left is bigger than 0, it will be reduced by 1.
	 */
	public void reduceTimeLeft() {
		if (timeLeft > 0) {
			timeLeft--;
		}
	}

	/**
	 * returns true if timeLeft is bigger than 0 or equal to -1.
	 * 
	 * @return true if timeLeft is bigger than 0 or equal to -1
	 */
	public boolean isTimeLeft() {
		return timeLeft > 0 || timeLeft == -1;
	}

	/**
	 * returns value of the bonus (for the players score).
	 * 
	 * @return value of the bonus (for the players score)
	 */
	public int getPointValue() {
		return pointValue;
	}

	/**
	 * returns amount by which the snake will grow if bonus is consumed.
	 * 
	 * @return amount by which the snake will grow if bonus is consumed
	 */
	public int getGrowthValue() {
		return growthValue;
	}

	/**
	 * sets time left to zero so this bonus can be deleted in the next turn
	 */
	public void setTimeLeftZero() {
		timeLeft = 0;
	}
}
