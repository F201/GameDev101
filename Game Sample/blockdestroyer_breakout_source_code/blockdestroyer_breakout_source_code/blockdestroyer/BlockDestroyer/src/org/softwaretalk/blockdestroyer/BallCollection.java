package org.softwaretalk.blockdestroyer;

import java.util.Iterator;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * A collection of none to n balls.
 * 
 * @author sam
 * @see Ball
 *
 */
public class BallCollection implements IImageObject {
	Array<Ball> balls;

	public BallCollection() {
		balls = new Array<Ball>();
	}
	
	public Iterator<Ball> getIterator() {
		return balls.iterator();
	}

	public void addBall(Ball ball) {
		balls.add(ball);
	}
	
	public int getNumberOfBalls() {
		return balls.size;
	}
	
	public void moveBalls(Paddle paddle, Sound paddleBounceSound, Sound wallBounceSound) {
		for(Ball ball: balls) {
			ball.moveBall(paddle, paddleBounceSound, wallBounceSound);
			if(!ball.isAlive()) {
				balls.removeValue(ball, true);
			}
		}
	}

	/**
	 * WARNING: dummy method, returns null.
	 */
	@Override
	public Sprite getSprite() {
		return null;
	}

	/**
	 * WARNING: dummy method, does nothing.
	 */
	@Override
	public void setSprite(Sprite sprite) {

	}

	@Override
	public void render(SpriteBatch batch) {
		for(Ball ball: balls) {
			//batch.draw(ball.getTexture(), ball.getX(), ball.getY());
			ball.render(batch);
		}

	}

	@Override
	public void dispose() {
		for(Ball ball: balls) {
			ball.dispose();
		}		
	}

}
