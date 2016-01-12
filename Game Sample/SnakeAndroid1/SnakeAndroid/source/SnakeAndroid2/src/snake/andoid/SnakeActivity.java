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

package snake.andoid;

import snake.AndroiGameController;
import snake.Utilities;
import snake.controller.IInputcontroller;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SnakeActivity extends Activity implements IInputcontroller {
	AndroiGameController c;
	TextAdapter textAdapter;
	int lastInput = -1;
	GridView gridview;
	TextView scoreView;

	private void setInput(int input) {
		lastInput = input;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textAdapter = new TextAdapter(this);
		// handler for updating gui
		final Handler guihandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					c.externalUpdateGui();
					textAdapter.notifyDataSetChanged();
					scoreView.setText("Score: " + c.getScore());
				} else if (msg.what == 2) {
					Toast.makeText(gridview.getContext(), "Game Over",
							Toast.LENGTH_LONG).show();
				} else if (msg.what == 3) {
					Toast.makeText(gridview.getContext(), "Start Game",
							Toast.LENGTH_LONG).show();
				}
			}
		};
		c = new AndroiGameController(textAdapter, this, guihandler);

		initGridView();
		initButtonsLabels();

		startGame();
	}

	private void initButtonsLabels() {
		scoreView = (TextView) findViewById(R.id.Score);

		Button returnButton = (Button) findViewById(R.id.Return);
		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				c.stopPlaying();
				startActivity(new Intent(v.getContext(), MenuActivity.class));
			}
		});

		Button restartButton = (Button) findViewById(R.id.Restart);
		restartButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				c.stopPlaying();
				c.resetGame();
				startGame();
			}
		});
	}

	private void startGame() {
		lastInput = -1;
		c.setIsPlaying();
		c.play();
	}

	private void initGridView() {
		gridview = (GridView) findViewById(R.id.GridView1);
		gridview.setAdapter(textAdapter);
		gridview.setNumColumns(Utilities.getInstance().getWidth());

		// input reader (left, right, up, down buttons)
		gridview.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// 1 = up, 2 = right, 3 = down, 4 = left, other is ignored
				if (keyCode == 19) {
					setInput(1);
				} else if (keyCode == 22) {
					setInput(2);
				} else if (keyCode == 20) {
					setInput(3);
				} else if (keyCode == 21) {
					setInput(4);
				} else {
					setInput(-1);
				}
				return true;
			}
		});

		// input reader (touch screen) TODO not very good, but kinda working
		gridview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX();
				float y = event.getY();

				int[] snakeHead = c.getSnakeHeadPosition();
				int snakeHeadTransformed = textAdapter.transformPosition(
						snakeHead[0], snakeHead[1]);
				View snakeview = gridview.getChildAt(snakeHeadTransformed);
				int[] snakeHeadOnScreen = new int[2];
				snakeview.getLocationOnScreen(snakeHeadOnScreen);

				float xDiff = x - snakeHeadOnScreen[0];
				float yDiff = y - snakeHeadOnScreen[1];

				if (Math.abs(xDiff) > Math.abs(yDiff)) {
					if (xDiff < 0) {
						setInput(4);
					} else {
						setInput(2);
					}
				} else {
					if (yDiff < 0) {
						setInput(1);
					} else {
						setInput(3);
					}
				}
				return true;
			}
		});
	}

	@Override
	public int getInput() {
		return lastInput; // this is actually not used as andorid does not allow
							// it
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		c.stopPlaying();
	}
}
