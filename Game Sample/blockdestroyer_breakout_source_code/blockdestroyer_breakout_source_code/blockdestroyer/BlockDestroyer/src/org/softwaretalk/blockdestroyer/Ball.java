package org.softwaretalk.blockdestroyer;

import org.softwaretalk.blockdestroyer.Resources.BallSpeed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * A Ball that moves with a certain speed in a certain direction.
 * 
 * @author sam
 *
 */
public class Ball extends MovableImageObject {
	
	private BallSpeed speed;
	private Vector2 ballDirection;
	private boolean isAlive;

	/**
	 * 
	 * @param texture image texture
	 * @param x x position of paddle
	 * @param y y position of paddle
	 */
	public Ball(Sprite sprite, float x, float y) {
		super(sprite, x, y);
		speed = Resources.BallSpeed.NORMAL;
		ballDirection = new Vector2(0f, 0.5f);
		isAlive = true;
	}
	
	/**
	 * returns if the ball is still alive. A ball is considered alive, if it 
	 * has not yet left the bottom of the screen. 
	 * 
	 * @return is ball alive?
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * if a ball hits a block that is not destroyed (eg a solid block) it will be reflected.
	 * It should be checked if the ball hit the given block before calling this method.
	 * 
	 * @param block
	 */
	public void reflect(Block block, Sound bounceSound) {
		// ball might be inside block, so add a tolerance
		float tolerance = speed.getSpeed() * Gdx.graphics.getDeltaTime();
		if (super.getY() + tolerance < block.getY() + block.getHeight() //ball not above block
				&& super.getY() + super.getHeight() > block.getY() + tolerance) {	// ball not below block
			// ball hit block left or right
			ballDirection.x = -ballDirection.x;
			if (super.getX() > block.getX() + (block.getWidth() / 2)) { // hit on right
				super.setX(super.getX() + tolerance + 1);
			} else { // hit on left
				super.setX(super.getX() - (tolerance + 1));
			}
			bounceSound.play();
		}		
		else if (super.getX() + super.getWidth() > block.getX() + tolerance // ball not left of block
				&& super.getX() + tolerance < block.getX() + block.getWidth()) {	// ball not right of block
			// hit block on top or bottom		
			ballDirection.y = -ballDirection.y;
			if (super.getY() > block.getY() + (block.getHeight() / 2)) { // hit on top
				super.setY(super.getY() + tolerance);
			} else { // hit on bottom
				super.setY(super.getY() - (tolerance + 1));
			}
			bounceSound.play();
		} 

	}
	
	/**
	 * moves the ball along its path at the defined speed.
	 * If the ball falls outside the game, the isAlive field will be set to false.
	 * If this field is false, the ball will not be moved anymore.
	 * If the ball hits the end of the screen it will be reflected.
	 * If the ball hits the given paddle, it will be reflected as well, depending on the position 
	 * on the paddle.
	 * 
	 * @param paddle the paddle
	 */
	// TODO do not pass music all the way down (maybe load in static Music class...). 
	public void moveBall(Paddle paddle, Sound paddleBounceSound, Sound wallBounceSound) {
		if (isAlive) {
			// move ball straight in the direction it is going this moment
			float speedTimesDelta = speed.getSpeed() * Gdx.graphics.getDeltaTime();
			float addToX = speedTimesDelta * ballDirection.x;
			float addToY = speedTimesDelta * ballDirection.y;
			super.addToX(addToX);
			super.addToY(addToY);
			
			// if the ball hit a wall, reflect
			if (super.getY() > Resources.SCREEN_HEIGHT - super.getHeight()) {
				ballDirection.y = -ballDirection.y;
				super.setY(Resources.SCREEN_HEIGHT - super.getHeight());
				wallBounceSound.play();
			}   
			if (super.getX() > Resources.SCREEN_WIDTH - super.getWidth()) {
				ballDirection.x = -ballDirection.x;
				super.setX(Resources.SCREEN_WIDTH - super.getWidth());
				wallBounceSound.play();
			}            
			if (super.getX() < 0) {
				ballDirection.x = -ballDirection.x;
				super.setX(0);
				wallBounceSound.play();
			}
			
			// if ball falls down it dies
			if (super.getY() < 0) {
				isAlive = false;
			}
			
			// reflect ball from paddle
			if(super.getRectangle().overlaps(paddle.getRectangle())
					&& super.getY() > paddle.getY() + paddle.getHeight() - speedTimesDelta) { // do not reflect from side of paddle
				// offset ball and reflect vertically
				super.setY(paddle.getY() + speedTimesDelta);            
				ballDirection.y = -ballDirection.y;
				
				// TODO if paddle is bigger, this sometimes leads to ball going horizontal right above paddle
				// if it hit paddle on far left (and right?)
				// adjust horizontal movement depending on position of paddle being hit
				float ballPositionOnPaddle = (super.getX() + (super.getWidth() / 2))
					- (paddle.getX() + (paddle.getWidth() / 2));
				float sign = Math.signum(ballPositionOnPaddle);
				ballDirection.x = sign * Math.abs(ballPositionOnPaddle) / 30;
				ballDirection.nor();
				
				paddleBounceSound.play();
			}
			
			// if ball moves directly horizontal, change direction slightly
			// TODO see above (maybe. ball should not go exactly horizontal...)
			
		}
	}
	
	

}
