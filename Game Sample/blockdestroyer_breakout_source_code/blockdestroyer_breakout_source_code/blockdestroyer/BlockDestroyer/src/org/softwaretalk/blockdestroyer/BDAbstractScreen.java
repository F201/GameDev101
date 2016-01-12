package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class BDAbstractScreen implements Screen {

	public static Rectangle calcViewPoint(int width, int height) {
		// calculate new viewport
		int VIRTUAL_HEIGHT = Resources.SCREEN_HEIGHT;
		int VIRTUAL_WIDTH = Resources.SCREEN_WIDTH;
		float ASPECT_RATIO = Resources.ASPECT_RATIO;

		float aspectRatio = (float)width/(float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		// x crop bug: crop.x sometimes gets a negative value. 
		if(aspectRatio > ASPECT_RATIO
				&& (width - VIRTUAL_WIDTH*((float)height/(float)VIRTUAL_HEIGHT))/2f >= 0) // dirty fix for x crop bug
		{
			scale = (float)height/(float)VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
		}
		else if(aspectRatio < ASPECT_RATIO
				|| (width - VIRTUAL_WIDTH*((float)height/(float)VIRTUAL_HEIGHT))/2f < 0) // dirty fix for x crop bug
		{
			scale = (float)width/(float)VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
		}
		else
		{
			scale = (float)width/(float)VIRTUAL_WIDTH;
			System.out.println("third case");
		}

		float w = (float)VIRTUAL_WIDTH*scale;
		float h = (float)VIRTUAL_HEIGHT*scale;
		Rectangle viewport = new Rectangle(crop.x, crop.y, w, h);
		return viewport;
	}
	
    protected static Skin getSkin()
    {
        return Images.skin;
    }
}
