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

package snake;

import snake.controller.AbstractGameController;
import snake.controller.IInputcontroller;
import snake.view.IGui;
import android.os.Handler;

public class AndroiGameController extends AbstractGameController {

	private final Handler handler;

	public AndroiGameController(IGui gui, IInputcontroller input,
			Handler handler) {
		super(gui, input);
		this.handler = handler;
	}

	int nextMove = -1;

	/**
	 * sets the next move.
	 * <p>
	 * Only to be used if it is not possible to implement Inputcontroller.
	 * 
	 * @param i
	 */
	public void setNextMove(int i) {
		nextMove = i;
	}

	@Override
	public void play() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(3);
				while (isPlaying()) {
					long executingTime = System.currentTimeMillis();
					try {
						playOnce();
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						// guihandler.sendEmptyMessage(2);
					}
					try {
						Thread.sleep(Utilities.getInstance().getSpeed()
								- (System.currentTimeMillis() - executingTime));
					} catch (InterruptedException ex) {
					}
				}
				// send game over message
				handler.sendEmptyMessage(2);
			}
		}).start();
	}

	@Override
	public void updateGui() {

	}

	public int[] getSnakeHeadPosition() {
		int[] headPosition = new int[2];
		headPosition[0] = super.snake.getHead().getX();
		headPosition[1] = super.snake.getHead().getY();
		return headPosition;
	}

	/**
	 * android does not want the gui to be updated in the main loop, so it gets
	 * its own method.
	 */
	public void externalUpdateGui() {
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
