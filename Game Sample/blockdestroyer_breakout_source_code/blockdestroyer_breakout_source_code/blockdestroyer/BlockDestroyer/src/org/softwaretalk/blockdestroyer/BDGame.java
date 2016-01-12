package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
/**
 * // TODO : 
 * - TODOs...
 * - redesign level (solid blocks must be closer, it is possible, to get a ball in between)
 * - sometimes game crashes. no exception, just crash. no idea why
 * - refactoring...
 * - different ui themes: changing theme sucks...
 * 
 * @author sam
 *
 */
public class BDGame extends Game {

	public BDGameScreen gameScreen;
	public BDMenuScreen menuScreen;
	public BDIntroScreen introScreen;
	public BDOptionsScreen optionsScreen;

	@Override
	public void create() {
		Images.load();
		gameScreen = new BDGameScreen(this);
		menuScreen = new BDMenuScreen(this);
		introScreen = new BDIntroScreen(this);
		optionsScreen = new BDOptionsScreen(this);
		
		if (Settings.isFullScreen()) {
			changetoFullScreen();
		}		
		setScreen(introScreen);
	}
	
	public void changetoFullScreen() {
		Gdx.graphics.setDisplayMode(800, 600, true);
	}
	public void changetoDefaultScreen() {
		Gdx.graphics.setDisplayMode(Resources.SCREEN_HEIGHT, Resources.SCREEN_WIDTH, false);
	}
}
