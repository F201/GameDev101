package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BDMenuScreen extends BDAbstractScreen {
	
	private BDGame game;
	private Rectangle viewport;
	
	private Stage ui;
	private Table window;
	
	public BDMenuScreen(BDGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		ui = new Stage();
		window = new Table();
	    window.setWidth(ui.getWidth());
	    window.setHeight(ui.getHeight());
	    window.setX(0);
	    window.setY(0);	    

	    TextButton newGame = new TextButton("Start", super.getSkin());
	    newGame.addListener(new ClickListener() {			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.gameScreen);	
			}
		});	    
	    window.add( newGame ).size( 300, 60 ).uniform().spaceBottom( 10 );
	    window.row();
	    
	    TextButton optionsButton = new TextButton( "Options", super.getSkin() );
	    optionsButton.addListener(new ClickListener() {			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.optionsScreen);	
			}
		});	    
	    window.add( optionsButton ).uniform().fill().spaceBottom( 10 );
	    window.row();
	    
	    TextButton exitButton = new TextButton( "Exit", super.getSkin() );
	    exitButton.addListener(new ClickListener() {			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});	    
	    window.add( exitButton ).uniform().fill().spaceBottom( 10 );
	    window.row();
	    
	    Gdx.input.setInputProcessor(ui);
	    ui.addActor(window);
	}

	@Override
	public void render(float arg0) {
		float[] color = Images.bgColor;
		Gdx.gl.glClearColor(color[0], color[1], color[2], color[3]);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// set viewport
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);   
	    
	   // ((Label)ui.findActor("fps")).setText("fps: " + Gdx.graphics.getFramesPerSecond());  
	    ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    ui.draw();
	    //Table.drawDebug(ui);
	}
	@Override
	public void resize(int width, int height) {
	    //ui.setViewport(width, height, true);
		viewport = super.calcViewPoint(width, height);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
