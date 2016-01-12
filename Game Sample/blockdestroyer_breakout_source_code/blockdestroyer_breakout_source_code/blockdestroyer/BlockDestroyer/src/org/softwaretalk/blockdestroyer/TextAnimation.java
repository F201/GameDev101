package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextAnimation {
	
	//private static final float total_animation_time = 5000f;
	private static final float maxSize = 1.5f;	
	private static final float stepSize = 0.3f;
	
	private float currentScale;
	
	private BitmapFont font;
	private boolean isRunning;
	private String text;
	
	// TODO do not pass font, all changes must be reset...
	public TextAnimation(BitmapFont font, String text) {
		this.font = font;
		this.isRunning = false;
		this.text = text;
		this.currentScale = 0.0f;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void setRunning() {
		isRunning = true;
	}
	
	public void drawFrame(SpriteBatch batch) {		
		if (isRunning) {
			font.setScale(currentScale);
			// TODO correct y position (see tests below. they all print at the same position...)
			font.draw(batch, text, (Resources.SCREEN_WIDTH / 2) - font.getScaleX(), Resources.SCREEN_HEIGHT / 2 - font.getCapHeight());
			//font.draw(batch, "Y = 0", (Resources.SCREEN_WIDTH / 2) - font.getScaleX(), 0);
			//font.draw(batch, "Y = 100", (Resources.SCREEN_WIDTH / 2) - font.getScaleX(), 100);
			//font.draw(batch, "Y = 400", (Resources.SCREEN_WIDTH / 2) - font.getScaleX(), 400);
			//font.draw(batch, "Y = " + Resources.SCREEN_HEIGHT / 2, (Resources.SCREEN_WIDTH / 2) - font.getScaleX(), Resources.SCREEN_HEIGHT / 2);
			//font.draw(batch, "Y = " + Resources.SCREEN_HEIGHT, (Resources.SCREEN_WIDTH / 2) - font.getScaleX(), Resources.SCREEN_HEIGHT);
			currentScale += (stepSize * Gdx.graphics.getDeltaTime());
			if (currentScale > maxSize) {
				// animation done
				isRunning = false;
			}
		}
	}

}
