package org.softwaretalk.blockdestroyer;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Images {
	
	public static Texture introImage;
	
	public static Sprite paddleImage;
	public static Sprite ballImage;
	
	public static Sprite blockImage;
	public static Sprite solidBlockImage;
	public static Sprite semiSolidBlockImage;
	public static Sprite semiSolidBlockImageOneHit;
	
	public static BitmapFont font;
	public static Skin skin;
	public static float[] bgColor;
	
	public final static String PATH = Settings.getSelectedView() + File.separator;

	private Images() {
		// constructor hidden
	}

	public static void load() {
		paddleImage = loadScaledSprite(Gdx.files.internal(PATH + "paddle_medium.png"));
		ballImage = loadScaledSprite(Gdx.files.internal(PATH + "ball_medium.png"));
		blockImage = loadScaledSprite(Gdx.files.internal(PATH + "block_medium.png"));	
		solidBlockImage = loadScaledSprite(Gdx.files.internal(PATH + "block_solid_medium.png"));
		semiSolidBlockImage = loadScaledSprite(Gdx.files.internal(PATH + "block_semi_solid_medium.png"));
		semiSolidBlockImageOneHit = loadScaledSprite(Gdx.files.internal(PATH + "block_semi_solid_one_hit_medium.png"));
		introImage = new Texture(Gdx.files.internal(PATH + "intro.png"));	
		font = new BitmapFont(Gdx.files.internal(PATH + "skin/default.fnt"),
		         Gdx.files.internal(PATH + "skin/default.png"), false);
        skin = new Skin( Gdx.files.internal(PATH +  "skin/uiskin.json" ));
        
        Color color;
        if (Settings.getSelectedView() == "data") {
        	color = Color.WHITE;
        } else {
        	color = Color.BLACK;
        }
    	bgColor = new float[4];
    	bgColor[0] = color.r;
    	bgColor[1] = color.g;
    	bgColor[2] = color.b;
    	bgColor[3] = color.a;
	}
	
	private static Sprite loadScaledSprite(FileHandle fileHandle) {		
		Sprite sprite = new Sprite(new Texture(fileHandle));
		sprite.setScale(Resources.INIT_IMAGE_SCALEDOWN);
		return sprite;
	}

	public static void dispose() {
		paddleImage.getTexture().dispose();
		ballImage.getTexture().dispose();
		blockImage.getTexture().dispose();
		solidBlockImage.getTexture().dispose();
		semiSolidBlockImage.getTexture().dispose();
		semiSolidBlockImageOneHit.getTexture().dispose();
		font.dispose();
		skin.dispose();
	}
}