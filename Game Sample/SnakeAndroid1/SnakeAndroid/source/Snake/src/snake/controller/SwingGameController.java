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
import snake.view.IGui;

public class SwingGameController extends AbstractGameController {

	public SwingGameController(IGui gui, IInputcontroller input) {
		super(gui, input);
	}

	/**
	 * starts the game.
	 */
	@Override
	public void play() {
		while (super.isPlaying()) {
			super.playOnce();
			try {
				Thread.sleep(Utilities.getInstance().getSpeed());
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * updates the gui.
	 */
	@Override
	public void updateGui() {
		if (Utilities.debug) {
			Utilities.print(gameField.toString());
		}

		for (int i = 0; i < gameField.getWidth(); i++) {
			for (int j = 0; j < gameField.getHight(); j++) {
				if (!gameField.isWalkable(i, j)) {
					gui.setBorder(i, j);
				} else if (gameField.getBonusAt(i, j) != null) {
					gui.setBoni(i, j);
				} else if (snake.isAt(i, j)) {
					gui.setSnake(i, j);
				} else {
					gui.setEmpty(i, j);
				}
			}
		}

		gui.setScore(super.getScore());
		gui.updateUI();
	}

}
