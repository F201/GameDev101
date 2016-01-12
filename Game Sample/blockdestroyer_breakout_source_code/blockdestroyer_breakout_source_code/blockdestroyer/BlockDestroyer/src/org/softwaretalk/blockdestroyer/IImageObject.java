package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IImageObject {
	Sprite getSprite();
	void setSprite(Sprite sprite);
	void render(SpriteBatch batch);
	void dispose();
}
