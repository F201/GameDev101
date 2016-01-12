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

import java.io.IOException;

/**
 * Provides the possibility to play snake in console.
 * <p>
 * For debugging purposes only as System.in needs return to process input.
 */
public class ConsoleInput implements IInputcontroller {

    // 1 = up, 2 = right, 3 = down, 4 = left, other is ignored
    @Override
    public int getInput() {
        int read = -1;
        try {
            read = System.in.read();
            while (read == 10) {
                read = System.in.read();
            }
        } catch (IOException ex) {
        }


        if (read == 119) {
            return 1;
        } else if (read == 97) { 
            return 4;
        } else if (read == 115) {
            return 3;
        } else if (read == 100) {
            return 2;
        } else {
            return read;
        }
    }
}
