package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BDIntroScreen extends BDAbstractScreen {
	
	private BDGame game;
	private Rectangle viewport;
	
	private SpriteBatch batch;
	
	private Texture introImage;
	private long timeBegin;
	
	public BDIntroScreen(BDGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		introImage = Images.introImage;
		batch = new SpriteBatch();
		timeBegin = System.nanoTime();
	}

	@Override
	public void hide() {
		introImage.dispose();
	}	

	@Override
	public void render(float delta) {
		float[] color = Images.bgColor;
		Gdx.gl.glClearColor(color[0], color[1], color[2], color[3]);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// set viewport
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);   
		
		batch.begin();
		batch.draw(introImage, 0, 0, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);
		batch.end();
		
		// display intro image for 2 seconds or until user pressed a key or mouse button
		if(((System.nanoTime() - timeBegin) / 1000000000.0f > 20.0f) 
			|| Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
			game.setScreen(game.menuScreen);
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport = super.calcViewPoint(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}