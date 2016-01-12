package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BDOptionsScreen extends BDAbstractScreen {
	
	private BDGame game;
	private Rectangle viewport;
	
	private Stage ui;
	private Table window;
	
	public BDOptionsScreen(BDGame game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		ui = new Stage();
		window = new Table(getSkin());
	    window.setWidth(ui.getWidth());
	    window.setHeight(ui.getHeight());
	    window.setX(0);
	    window.setY(0);
   
	    
	    TextButton toMain = new TextButton("Back", getSkin());
	    toMain.addListener(new ClickListener() {			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.menuScreen);	
			}
		});	
	    window.add( toMain ).size( 300, 60 ).uniform().spaceBottom( 10 );
	    window.row();

	    
        final CheckBox fullScreen = new CheckBox(" Fullscreen", super.getSkin());
        fullScreen.setChecked( Settings.isFullScreen() );
        fullScreen.addListener(new ClickListener() {			
			@Override
			public void clicked(InputEvent event, float x, float y) {
                boolean enabled = fullScreen.isChecked();
                Settings.setFullscreen(enabled);
                if (enabled) {
                	game.changetoFullScreen();
                } else {
                	game.changetoDefaultScreen();
                }
			}
		});
        window.add( fullScreen ).uniform().fill().spaceBottom( 10 );
        window.row();	
        
        final CheckBox backgroundMusic = new CheckBox(" Background Music", super.getSkin());
        backgroundMusic.setChecked( Settings.isMusicEnabled() );
        backgroundMusic.addListener(new ClickListener() {			
			@Override
			public void clicked(InputEvent event, float x, float y) {
                boolean enabled = backgroundMusic.isChecked();
                Settings.setMusicEnabled(enabled);
			}
		});
        window.add( backgroundMusic ).uniform().fill().spaceBottom( 10 );
        window.row();
        
        
        
        // TODO too much space between label and box (box should be moved to the left...)
        Label lookLabel = new Label( "Box:", super.getSkin() );
        window.add( lookLabel );
        final SelectBox lookBox = new SelectBox(new String[] {"data", "data_color", "data_color2"}, super.getSkin());   
        lookBox.setSelection(Settings.getSelectedView());
        lookBox.addListener(new ChangeListener() {			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String selected = lookBox.getSelection();
				Settings.setSelectedView(selected);
				Images.dispose();
				Images.load();
				// exit this screen (Images class is not thread save)
				// TODO new game Images (paddle, ball, block) are not used until restart.
				// 		why? no idea...
				game.setScreen(game.introScreen);
			}
		});
        window.add( lookBox ).uniform().fill().spaceBottom( 10 );
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