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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import snake.Utilities;

/**
 * a highscore. it is always sorted by scores.
 * 
 */
public class HighScore implements Serializable {

	private static final long serialVersionUID = 4759476774332194829L;
	private static final String fileName = "highscore";
	private static HighScore instance = null;

	private final List<HighScoreEntry> entries;

	/**
	 * saves if old highscore file could be successfully loaded. This is done so
	 * that the highscore file will not be overriden because of an io exception
	 * on read.
	 * <p>
	 * On File not found this will be set to true as well.
	 */
	private static boolean couldLoadFile = false;

	private HighScore() {
		entries = new ArrayList<HighScoreEntry>();
	}

	public List<HighScoreEntry> getEntries() {
		return entries;
	}

	public static HighScore getInstance() {
		if (instance == null) { // first call
			instance = loadHighScore();
			if (instance == null) { // could not load highscore
				instance = new HighScore();
			}
		}
		return instance;
	}

	public void addAndSave(String name, int score) {
		entries.add(new HighScoreEntry(name, score));
		Collections.sort(entries, new EntryComparator());
		if (entries.size() >= Utilities.getInstance().getNumberOfHighScores()) {
			entries.removeAll(entries.subList(Utilities.getInstance()
					.getNumberOfHighScores(), entries.size()));
		}
		saveHighScore();
	}

	public void clearAndSave() {
		entries.clear();
		saveHighScore();
	}

	private static void saveHighScore() {
		if (couldLoadFile) {
			ObjectOutputStream oop = null;
			OutputStream os = null;
			try {
				os = new FileOutputStream(fileName);
				oop = new ObjectOutputStream(os);
				oop.writeObject(instance);
			} catch (IOException ex) {
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
	}

	private static HighScore loadHighScore() {
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new FileInputStream(fileName);
			ois = new ObjectInputStream(is);
			couldLoadFile = true;
			return (HighScore) ois.readObject();
		} catch (FileNotFoundException fnf) {
			couldLoadFile = true;
			saveHighScore();
		} catch (Exception ioe) {
			couldLoadFile = false;
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

	public class HighScoreEntry implements Serializable {
		private static final long serialVersionUID = 4759476774332194829L;

		private final String name;
		private final int score;

		/**
		 * @param name
		 * @param score
		 */
		public HighScoreEntry(String name, int score) {
			super();
			this.name = name;
			this.score = score;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the score
		 */
		public int getScore() {
			return score;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		List<HighScoreEntry> entries = HighScore.getInstance().getEntries();
		String values = "";
		for (int i = 0; i < entries.size(); i++) {
			values += entries.get(i).getName() + ": "
					+ entries.get(i).getScore() + "\n";
		}
		return values;
	}

	private class EntryComparator implements Comparator<HighScoreEntry> {

		@Override
		public int compare(HighScoreEntry t, HighScoreEntry t1) {
			return t1.getScore() - t.getScore();
		}
	}

	public static void main(String[] args) {
		// HighScore.getInstance().clearAndSave();
		HighScore.getInstance().addAndSave("TEST", 1);
		HighScore.getInstance().addAndSave("TEST", 200);
		for (int i = 0; i < 12; i++) {
			HighScore.getInstance().addAndSave("TEST", i);
		}
		Utilities.print(HighScore.getInstance().toString());

	}
}
