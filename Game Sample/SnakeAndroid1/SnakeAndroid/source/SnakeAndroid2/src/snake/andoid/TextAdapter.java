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

import snake.Utilities;
import snake.view.IGui;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter implements IGui {

	private final Context context;
	private final String[] texts;

	private final int height;
	private final int width;

	public TextAdapter(Context context) {
		this.context = context;
		this.height = Utilities.getInstance().getHeight();
		this.width = Utilities.getInstance().getWidth();
		texts = new String[height * width];
		for (int i = 0; i < texts.length; i++) {
			texts[i] = " ";
		}
	}

	public int getCount() {
		return height * width;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv;
		if (convertView == null) {
			tv = new TextView(context);
			tv.setLayoutParams(new GridView.LayoutParams(25, 25));
			tv.setTextSize(15);
		} else {
			tv = (TextView) convertView;
		}

		tv.setText(texts[position]);
		return tv;
	}

	/**
	 * returns a two dimensional array with the x / y positions.
	 * 
	 * @param position
	 * @return
	 */
	protected int[] transformPosition(int position) {
		int[] positionArr = new int[2];
		positionArr[0] = position % width;
		positionArr[1] = position / width;
		return positionArr;
	}

	protected int transformPosition(int x, int y) {
		return width * y + x;
	}

	@Override
	public void setSnake(int x, int y) {
		texts[transformPosition(x, y)] = "@";
	}

	@Override
	public void setBoni(int x, int y) {
		texts[transformPosition(x, y)] = "*";
	}

	@Override
	public void setBorder(int x, int y) {
		texts[transformPosition(x, y)] = "#";
	}

	@Override
	public void setEmpty(int x, int y) {
		texts[transformPosition(x, y)] = " ";
	}

	@Override
	public void setScore(int score) {
		// currentScore = score;

	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI() {
	}

}
