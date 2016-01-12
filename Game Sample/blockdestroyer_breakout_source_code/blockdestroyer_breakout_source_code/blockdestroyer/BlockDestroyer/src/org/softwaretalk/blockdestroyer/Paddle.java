package org.softwaretalk.blockdestroyer;

import org.softwaretalk.blockdestroyer.Resources.PaddleSpeed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Paddle extends MovableImageObject {
	
	private PaddleSpeed speed;

	/**
	 * 
	 * @param texture image texture
	 * @param x x position of paddle
	 * @param y y position of paddle
	 */
	public Paddle(Sprite sprite, float x, float y) {
		super(sprite, x, y);
		speed = Resources.PaddleSpeed.FAST;
	}
	
	public void moveLeft() {
		   super.setX(super.getX() - (speed.getSpeed() * Gdx.graphics.getDeltaTime()));
		   // correct, if paddle went ouside screen
		   if (super.getX() < 0) {
			   super.setX(0);
		   }
	}
	
	public void moveRight() {
		   super.setX(super.getX() + (speed.getSpeed() * Gdx.graphics.getDeltaTime()));
		   // correct, if paddle went ouside screen
		   if (super.getX() > Resources.SCREEN_WIDTH - super.getWidth()) {
			   super.setX(Resources.SCREEN_WIDTH - super.getWidth());
		   }
	}
	
	public void setSpeed(PaddleSpeed speed) {
		this.speed = speed;
	}
	
	public void increaseSpeed() {
		switch(speed) {
		case SLOW:
			speed = PaddleSpeed.NORMAL;
			break;
		case NORMAL:
			speed = PaddleSpeed.FAST;
			break;
		case FAST:
			speed = PaddleSpeed.ULTRA;
			break;
		case ULTRA:
			speed = PaddleSpeed.ULTRA;
			break;
		default:
			speed = PaddleSpeed.NORMAL;
		}
	}
	
	public void decreaseSpeed() {
		switch(speed) {
		case SLOW:
			speed = PaddleSpeed.SLOW;
			break;
		case NORMAL:
			speed = PaddleSpeed.SLOW;
			break;
		case FAST:
			speed = PaddleSpeed.NORMAL;
			break;
		case ULTRA:
			speed = PaddleSpeed.FAST;
			break;
		default:
			speed = PaddleSpeed.NORMAL;
		}
	}
	
	public PaddleSpeed getSpeed() {
		return speed;
	}
}
