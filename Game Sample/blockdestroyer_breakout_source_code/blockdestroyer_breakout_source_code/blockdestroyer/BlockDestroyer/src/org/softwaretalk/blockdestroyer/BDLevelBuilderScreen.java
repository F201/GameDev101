package org.softwaretalk.blockdestroyer;

import org.softwaretalk.blockdestroyer.Block.BlockType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BDLevelBuilderScreen  extends BDAbstractScreen {
	
	private BDGame game;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle viewport;
	private ShapeRenderer shapeRenderer;
	private DesktopInput input;

	private BlockCollection blockCollection;

	private BitmapFont font;
	
	public BDLevelBuilderScreen(BDGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		input = new DesktopInput();
		Gdx.input.setInputProcessor(input);

		createGameObjects();		
		
		if (Settings.isFullScreen()) {
			Gdx.input.setCursorCatched(true);			
			this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	
		font = Images.font;
	}

	private void createGameObjects() {
		blockCollection = new BlockCollection();
	}

	@Override
	public void hide() {
		batch.dispose();
		shapeRenderer.dispose();
		blockCollection.dispose();
		
		Gdx.input.setCursorCatched(false);
	}

	@Override
	public void render(float delta) {
		camera.update();
		camera.apply(Gdx.gl10);

		// set viewport
		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);       

		// clear previous frame
		float[] color = Images.bgColor;
		Gdx.gl.glClearColor(color[0], color[1], color[2], color[3]);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//drawFrame();

		// render objects
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		blockCollection.render(batch);
		batch.end();
	
		input.checkOtherInput();

		printInfos();	
	}

	private void printInfos() {
		// print infos		
		batch.begin();
		//font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		// print ball speed
		//font.draw(batch, "speed: " + paddle.getSpeed(), 0, Resources.SCREEN_HEIGHT - 10);
		// number balls
		//font.draw(batch, "balls: " + ballCollection.getNumberOfBalls(), 400, Resources.SCREEN_HEIGHT - 10);
		// print fps 
		font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 0, Resources.SCREEN_HEIGHT - 10);  
		// TODO check why 0,0 etc are outside screen (or why else they are not drawn)
//		font.setScale(0.5f);
//		font.draw(batch, "q: save lvl", 0, 0);  
//		font.draw(batch, "w: load lvl", 0, 10);  
//		font.draw(batch, "b: build normal block", 0, 20);  
//		font.draw(batch, "s: build solid block", 0, 30);  
//		font.draw(batch, "t: build two hit block", 0, 40);  
//		font.draw(batch, "c: remove all blocks", 0, 50);   
//		font.setScale(1f);
		batch.end();
	}
		
	@Override
	public void resize(int width, int height) {
		viewport = super.calcViewPoint(width, height);
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
	
	private class DesktopInput extends InputAdapter {

		public DesktopInput() {
			super();
		}
		
		public void checkOtherInput() {
			if(Gdx.input.isKeyPressed(Keys.ESCAPE)) game.setScreen(game.menuScreen);	
			
			// generate blocks
//			if(Gdx.input.isKeyPressed(Keys.L)) {
//				blockCollection = LevelHandler.generateLevel();			
//			}
			
			// load and save level
			if(Gdx.input.isKeyPressed(Keys.Q)) {
				blockCollection = LevelHandler.loadLevel("test");
			}
			if(Gdx.input.isKeyPressed(Keys.W)) {
				LevelHandler.saveLevel("test", blockCollection);
			}
			// clear level
			if(Gdx.input.isKeyPressed(Keys.C)) {
				blockCollection = new BlockCollection();
			}
		}		

		@Override
		public boolean keyDown(int keycode) {
			// set one block
			if (keycode == Keys.B || keycode == Keys.S || keycode == Keys.T) {
				BlockType type = BlockType.NORMAL;
				if(keycode == Keys.B) {
					type = BlockType.NORMAL;
				}
				if(keycode == Keys.S) {
					type = BlockType.SOLID;
				}
				if(keycode == Keys.T) {
					type = BlockType.TWOHIT;
				}
				Vector3 touchPos = new Vector3();
				//touchPos.set(roundToEighty(Gdx.input.getX()), roundToTwenty(Gdx.input.getY()), 0);
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);	
				touchPos.set(roundToEighty(touchPos.x), roundToTwenty(touchPos.y), 0);
				blockCollection.addBlock(BlockCollection.createBlock(type, touchPos.x, touchPos.y));
			}
			return true;
		}
		
		private int roundToEighty(float in) {
			return (int)(Math.round( in / 70.0) * 70);
		}
		
		private int roundToTwenty(float in) {
			return (int)(Math.round( in / 20.0) * 20);
		}
	}
}