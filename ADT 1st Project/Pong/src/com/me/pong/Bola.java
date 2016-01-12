package com.me.pong;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bola {
	public Texture teksBola;
	public Rectangle posBola;
	public int speedX = -10, speedY = 10;
	
	public Bola(Rectangle posBola, Texture teksBola){
		this.teksBola = teksBola;
		this.posBola = posBola;
	}
	
	public void Update(SpriteBatch b, Papan p1, Papan p2){
		this.posBola.x += speedX;
		this.posBola.y += speedY;
		if(this.posBola.x < 0 || this.posBola.x > 403){
			speedX *= -1;
		}
		if(this.posBola.y < 0 || this.posBola.y > 723){
			speedY *= -1;
		}
		if(this.posBola.overlaps(p1.posPapan) || this.posBola.overlaps(p2.posPapan)){
			speedY *= -1;
		}
		b.begin();
		b.draw(teksBola, posBola.x, posBola.y);
		b.end();
	}
}
