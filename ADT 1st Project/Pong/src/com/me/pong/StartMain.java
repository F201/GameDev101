package com.me.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class StartMain {
	Papan p1, p2;
	Bola bola;
	
	public StartMain(){
		
	}
	
	public void Create(){
		p1 = new Papan(new Rectangle(0, 400, 113, 33), new Texture(Gdx.files.internal("data/papan1_sample.PNG")), 1);
		p2 = new Papan(new Rectangle(200, 400, 113, 33), new Texture(Gdx.files.internal("data/papan2_sample.PNG")), 1);
		bola = new Bola(new Rectangle(200, 400, 73, 69), new Texture(Gdx.files.internal("data/pong_bola.png")));
	}
	
	public void Update(SpriteBatch b, OrthographicCamera c){
		p1.Update(b, c);
		p2.Update(b, c);
		bola.Update(b, p1, p2);
	}
}
