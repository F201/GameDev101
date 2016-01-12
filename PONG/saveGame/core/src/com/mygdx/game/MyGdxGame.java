package com.mygdx.game;


import com.badlogic.gdx.Game;


public class MyGdxGame extends Game {
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
