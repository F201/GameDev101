package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovableImageObject extends MovableObject implements IImageObject {
	
	private Sprite sprite;
	
	public MovableImageObject(Sprite sprite, float x, float y) {
		super(x, y, sprite.getWidth() * Resources.INIT_IMAGE_SCALEDOWN, sprite.getHeight() * Resources.INIT_IMAGE_SCALEDOWN);
		this.sprite = sprite;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(sprite, super.getX(), super.getY(), super.getWidth(), super.getHeight());
	}

	@Override
	public void dispose() {
		// TODO recheck all disposes. when should these be disposed? the object (Block, etc) should be set to null or something, otherwhise 
		// if it is used again, texture cannot be rendered......
		//sprite.getTexture().dispose();
	}
}
