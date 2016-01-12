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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * A utility class.
 * 
 * TODO this is not good. not good at all... setters and getters should ONLY
 * work on the instance (they are right now, but it should be enforced). ALSO:
 * SAVING IS NOT WORKING!
 * 
 * Also: divide in utilities (never changed) and settings (changable)
 */
public class Utilities implements Serializable {
	private static final long serialVersionUID = -8511088694802875554L;
	private static final String fileName = "settings";
	private static Utilities instance = null;

	/* SETTINGS */

	/** debug. */
	public static final boolean debug = false;

	public enum SPEED {
		VERY_SLOW, SLOW, AVERAGE, FAST, VERY_FAST
	}

	private static final int VERY_SLOW = 1000; // very slow
	private static final int SLOW = 500; // slow
	private static final int AVERAGE = 250; // average
	private static final int FAST = 100; // fast
	private static final int VERY_FAST = 50; // really fast
	/* number of different speeds. */
	public static final int SPEED_LEVEL = 5;

	/** pause between game loops. */
	private int current_speed = AVERAGE;

	private int bonusExistTime = 30;
	private int bonusGrows = 3;
	private int bonusValue = 100;

	private String playerName = "TestPlayer";

	private int width = 20;
	private int height = 20;

	private int numberOfHighScores = 10;

	private Utilities() {
	}

	public static Utilities getInstance() {
		if (instance == null) { // first call
			instance = loadSettings();
			if (instance == null) { // could not load settings
				instance = new Utilities();
			}
		}
		return instance;
	}

	public void setSpeed(SPEED speed) {
		switch (speed) {
		case VERY_SLOW:
			current_speed = VERY_SLOW;
			break;
		case SLOW:
			current_speed = SLOW;
			break;
		case AVERAGE:
			current_speed = AVERAGE;
			break;
		case FAST:
			current_speed = FAST;
			break;
		case VERY_FAST:
			current_speed = VERY_FAST;
			break;
		default:
			current_speed = AVERAGE;
			break;
		}
	}

	public SPEED transformSpeedLevel(int speed) {
		switch (speed) {
		case 1:
			return SPEED.VERY_SLOW;
		case 2:
			return SPEED.SLOW;
		case 3:
			return SPEED.AVERAGE;
		case 4:
			return SPEED.FAST;
		case 5:
			return SPEED.VERY_FAST;
		default:
			return SPEED.AVERAGE;
		}
	}

	public int transformSpeedLevel(SPEED speed) {
		switch (speed) {
		case VERY_SLOW:
			return 1;
		case SLOW:
			return 2;
		case AVERAGE:
			return 3;
		case FAST:
			return 4;
		case VERY_FAST:
			return 5;
		default:
			return 3;
		}
	}

	public SPEED getSpeedLevel() {
		switch (current_speed) {
		case VERY_SLOW:
			return SPEED.VERY_SLOW;
		case SLOW:
			return SPEED.SLOW;
		case AVERAGE:
			return SPEED.AVERAGE;
		case FAST:
			return SPEED.FAST;
		case VERY_FAST:
			return SPEED.VERY_FAST;
		default:
			return SPEED.AVERAGE;
		}
	}

	public int getSpeed() {
		return current_speed;
	}

	/**
	 * @return the bonusExistTime
	 */
	public int getBonusExistTime() {
		return bonusExistTime;
	}

	/**
	 * @param bonusExistTime
	 *            the bonusExistTime to set
	 */
	public void setBonusExistTime(int bonusExistTime) {
		this.bonusExistTime = bonusExistTime;
	}

	/**
	 * @return the bonusGrows
	 */
	public int getBonusGrows() {
		return bonusGrows;
	}

	/**
	 * @param bonusGrows
	 *            the bonusGrows to set
	 */
	public void setBonusGrows(int bonusGrows) {
		this.bonusGrows = bonusGrows;
	}

	/**
	 * @return the bonusValue
	 */
	public int getBonusValue() {
		return bonusValue;
	}

	/**
	 * @param bonusValue
	 *            the bonusValue to set
	 */
	public void setBonusValue(int bonusValue) {
		this.bonusValue = bonusValue;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the numberOfHighScores
	 */
	public int getNumberOfHighScores() {
		return numberOfHighScores;
	}

	/**
	 * @param numberOfHighScores
	 *            the numberOfHighScores to set
	 */
	public void setNumberOfHighScores(int numberOfHighScores) {
		this.numberOfHighScores = numberOfHighScores;
	}

	public static void saveSettings() {
		ObjectOutputStream oop = null;
		OutputStream os = null;
		try {
			os = new FileOutputStream(fileName);
			oop = new ObjectOutputStream(os);
			oop.writeObject(instance);
		} catch (IOException ex) {
			print("saveSettings: " + ex);
		} finally {
			if (oop != null) {
				try {
					oop.close();
				} catch (IOException ex) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	private static Utilities loadSettings() {
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new FileInputStream(fileName);
			ois = new ObjectInputStream(is);
			return (Utilities) ois.readObject();
		} catch (FileNotFoundException fnf) {
			print("loadSettings (file not found): " + fnf);
			saveSettings();
		} catch (Exception ioe) {
			print("loadSettings: " + ioe);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException ex) {
				}
			}
		}
		return null;
	}

	/**
	 * prints to sto.
	 * 
	 * @param s
	 *            string
	 */
	public static void print(String s) {
		System.out.println(s);
	}

	public static void main(String[] args) {

		Utilities.print(Utilities.getInstance().getPlayerName());
		Utilities.getInstance().setPlayerName("bla");
		Utilities.print(Utilities.getInstance().getPlayerName());
		Utilities.getInstance().setPlayerName("other bla");
		Utilities.saveSettings();
		Utilities.print(Utilities.getInstance().getPlayerName());

	}

}
