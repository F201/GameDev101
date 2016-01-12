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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SettingsActivity extends Activity {
	EditText name;
	int chosenSpeed;
	Utilities util;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		initButtons();

		util = Utilities.getInstance();

		name = (EditText) findViewById(R.id.textFieldName);
		name.setText(util.getPlayerName());

		chosenSpeed = util.transformSpeedLevel(util.getSpeedLevel());

		SeekBar speed = (SeekBar) findViewById(R.id.seekBar1);
		speed.setMax(Utilities.SPEED_LEVEL - 1);
		speed.setProgress(chosenSpeed - 1);
		speed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				chosenSpeed = progress;

			}
		});

	}

	private void initButtons() {
		Button saveAndReturn = (Button) findViewById(R.id.saveandreturn);
		saveAndReturn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				util.setPlayerName(name.getText().toString());
				util.setSpeed(util.transformSpeedLevel(chosenSpeed + 1));
				Utilities.saveSettings();
				startActivity(new Intent(v.getContext(), MenuActivity.class));
			}
		});

		Button returnWithoutSaving = (Button) findViewById(R.id.returnwithoutsaving);
		returnWithoutSaving.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(v.getContext(), MenuActivity.class));
			}
		});
	}

}
