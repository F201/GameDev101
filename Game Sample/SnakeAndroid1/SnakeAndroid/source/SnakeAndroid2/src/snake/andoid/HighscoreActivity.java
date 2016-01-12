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

import java.util.List;

import snake.modell.HighScore;
import snake.modell.HighScore.HighScoreEntry;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class HighscoreActivity extends ListActivity {
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.highscore);

		List<HighScoreEntry> entries = HighScore.getInstance().getEntries();
		String[] values = new String[entries.size()];
		for (int i = 0; i < entries.size(); i++) {
			values[i] = entries.get(i).getName() + ": "
					+ entries.get(i).getScore();
		}

		setListAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, values));

		initButtons();
	}

	private void initButtons() {
		Button returnWithoutSaving = (Button) findViewById(R.id.returnToMenu);
		returnWithoutSaving.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), MenuActivity.class));
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// String item = (String) getListAdapter().getItem(position);
		// Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}
}
