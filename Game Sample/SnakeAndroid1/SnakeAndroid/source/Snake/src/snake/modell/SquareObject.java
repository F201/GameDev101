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
 * An object describing a position.
 * x goes from left to right, starting at 0.
 * y goes from top to bottom, starting at 0.
 */
public class SquareObject {
    private int x;
    private int y;

    /**
     * constructs new <code>SquareObject</code>.
     *
     * @param x x
     * @param y y
     */
    public SquareObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * returns x position.
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * sets x position.
     *
     * @param x x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * returns y position.
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * sets y position.
     *
     * @param y y
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SquareObject other = (SquareObject) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.x;
        hash = 23 * hash + this.y;
        return hash;
    }
}
