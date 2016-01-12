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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * The <code>GameField</code> class holds <code>GameSquare</code>, 
 * <code>OneSquareBonus</code> and provides methods to manipulate the 
 * game field as well as boni.
 * <p>
 * Please note that updateBoni() should be called once in the game loop as
 * it reduces the amount of time the boni will stay in the field by 1.
 * @see GameSquare
 * @see OneSquareBonus
 */
public class GameField {

    private GameSquare[][] gameField;
    private List<OneSquareBonus> bonuses;
    private int width;
    private int hight;

    /**
     * constructs new game field with the given size.
     *
     * @param width width (number of <code>GameSquare</code>s in a row)
     * @param hight hight (number of <code>GameSquare</code>s in a column)
     */
    public GameField(int width, int hight) {
        this.width = width;
        this.hight = hight;
        initGameField();
        initBonuses();
    }

    /**
     * returns the height of this field.
     *
     * @return height of this field
     */
    public int getHight() {
        return hight;
    }

    /**
     * returns the width of this field.
     *
     * @return width of this field
     */
    public int getWidth() {
        return width;
    }

    /**
     * returns the bonus at that position or null if no bonus exists.
     *
     * @param x x
     * @param y y
     * @return the bonus at that position or null if no bonus exists
     */
    public OneSquareBonus getBonusAt(int x, int y) {
        if (!isWalkable(x, y)) {
            return null;
        }
        for (OneSquareBonus bonus : bonuses) {
            if (bonus.getX() == x && bonus.getY() == y) {
                return bonus;
            }
        }
        return null;
    }

    /**
     * returns true if the specified coordinates are inside the game field
     * and the field is walkable, false otherwise.
     *
     * @param x x
     * @param y y
     * @return true if the specified coordinates are inside the game field
     * and the field is walkable, false otherwise
     */
    public boolean isWalkable(int x, int y) {
        return isOutsideGame(x, y) ? false : gameField[x][y].isIsWalkable();
    }

    /**
     * returns whether or not this game field currently holds at least one
     * bonus.
     *
     * @return whether or not this game field currently holds at least one
     * bonus
     */
    public boolean hasNoBonus() {
        return bonuses.isEmpty();
    }

    /**
     * initiates the game field.
     */
    private void initGameField() {
        gameField = new GameSquare[width][hight];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                 gameField[i][j] = new GameSquare(i, j);         
            }            
        }
    }

    /**
     * sets the outer <code>GameSquare</code>s to not walkable.
     */
    public void setBorders() {
        for (int i = 0; i < width; i++) {
            gameField[i][0].setIsWalkable(false);
            gameField[i][hight - 1].setIsWalkable(false);
        }

        for (int j = 0; j < hight; j++) {
            gameField[0][j].setIsWalkable(false);
            gameField[width - 1][j].setIsWalkable(false);
        }
    }

    /**
     * initiates bonuses.
     */
    private void initBonuses() {
        bonuses = new ArrayList<OneSquareBonus>();
    }

    /**
     * adds the bonus to the bonus list. There will be no check if the
     * bonus should be placed where it is.
     *
     * @param bonus bonus
     */
    public void addBonus(OneSquareBonus bonus) {
        bonuses.add(bonus);
    }

    /**
     * adds a new bonus with given values at random position.
     * Bonus will not be added at snake position, unwalkable terrain or where another bonus exists.
     *
     * @param pointValue value of the bonus (for the players score)
     * @param growthValue amount by which the snake will grow if bonus is consumed
     * @param timeLeft number of turns the bonus will exist
     * @param snake the snake associated with the current game
     */
    public void addBonus(int pointValue, int growthValue, int timeLeft, Snake snake) {
        Random r = new Random();
        boolean added = false;
        // TODO it is not guaranteed that this loop will ever end
        while (!added) {
            int x = r.nextInt(width);
            int y = r.nextInt(hight);
            if (isWalkable(x, y)
                    && !snake.isAt(x, y)
                    && getBonusAt(x, y) == null) {
                bonuses.add(new OneSquareBonus(pointValue, growthValue, timeLeft, x, y));
                added = true;
            }
        }
    }

    /**
     * returns true if the given coordinate is outside the game field.
     *
     * @param x x
     * @param y y
     * @return true if the given coordinate is outside the game field
     */
    private boolean isOutsideGame(int x, int y) {
        return x < 0 || y < 0 || x > width - 1 || y > hight - 1;

        }

    /**
     * removes a bonus if it has no more time left. Reduces the time of all boni.
     * <p>
     * This method is to be called on every loop.
     */
    public void updateBoni() {
        for (Iterator<OneSquareBonus> it = bonuses.iterator(); it.hasNext();) {
            OneSquareBonus bonus = it.next();
            bonus.reduceTimeLeft();
            if (!bonus.isTimeLeft()) {
                it.remove();
            }
        }
    }

    @Override
    public String toString() {
       StringBuffer sb = new StringBuffer();

        for (int j = 0; j < hight; j++) {
            for (int i = 0; i < width; i++) {
                 if (gameField[i][j].isIsWalkable()) {
//                     if (snake.isAt(i, j)) {
//                        sb.append("@");
//                     } else 
                         if (getBonusAt(i, j) != null) {
                         sb.append('*');
                     } else {
                        sb.append(' ');
                     }
                 } else {
                     sb.append('#');
                 }
            }
            sb.append('\n');
        }
       return sb.toString();
    }
}
