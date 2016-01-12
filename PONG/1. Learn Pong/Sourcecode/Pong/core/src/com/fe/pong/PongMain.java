package com.fe.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PongMain extends Game {

	//class layar gameplay
	GameScreen gameScreen;
	
	@Override
	public void create () {
		setScreen(new GameScreen());//mengganti screen yang aktif ke gamescreen
		
	}

	@Override
	public void render () {
		super.render();
	}
}
