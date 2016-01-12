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
 * A Square of the <code>GameField</code>.
 */
public class GameSquare extends SquareObject {
    
    private boolean isWalkable;

    /**
     * constructs new square for given coordinates.
     * <p>
     * Walkable will be set to true.
     *
     * @param x x
     * @param y y
     */
    public GameSquare(int x, int y) {
        super(x, y);
        isWalkable = true;
    }

    /**
     * returns whether this square is walkable.
     *
     * @return whether this square is walkable
     */
    public boolean isIsWalkable() {
        return isWalkable;
    }

    /**
     * sets whether this square is walkable.
     *
     * @param isWalkable whether this square is walkable
     */
    public void setIsWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }


}
