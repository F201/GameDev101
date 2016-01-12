package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BDGameScreen extends BDAbstractScreen {
	
	private BDGame game;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle viewport;
	private ShapeRenderer shapeRenderer;
	private DesktopInput input;

	private Paddle paddle;
	private BlockCollection blockCollection;
	private BallCollection ballCollection;
	private Player player;

	private  Sound pingSound;
	private  Sound wallBounceSound;
	private  Sound paddleBounceSound;
	private  Music music;

	private BitmapFont font;
	private static final Color goldColor = new Color(0.233f, 0.174f, 0.16f, 1);
	
	private int currentLevel;
	
	public BDGameScreen(BDGame game) {
		this.game = game;
		currentLevel = 0;
	}
	
	@Override
	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Resources.SCREEN_WIDTH, Resources.SCREEN_HEIGHT);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		input = new DesktopInput();
		Gdx.input.setInputProcessor(input);

		loadSounds();
		createGameObjects();		
		
		if (Settings.isFullScreen()) {
			Gdx.input.setCursorCatched(true);			
			this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		font = Images.font;
	
		blockCollection = LevelHandler.loadLevel("" + currentLevel);
		//blockCollection = LevelHandler.generateLevel(camera);
	}

	private void createGameObjects() {
		paddle = new Paddle(Images.paddleImage, (Resources.SCREEN_WIDTH / 2) - ((Images.paddleImage.getWidth() * Resources.INIT_IMAGE_SCALEDOWN) / 2), 20);
		blockCollection = new BlockCollection();
		ballCollection = new BallCollection();
		player = new Player();
	}

	private void loadSounds() {
		pingSound = Gdx.audio.newSound(Gdx.files.internal("data/sound/short/ping.wav"));
		wallBounceSound = Gdx.audio.newSound(Gdx.files.internal("data/sound/short/bounce_wall.mp3"));
		paddleBounceSound = Gdx.audio.newSound(Gdx.files.internal("data/sound/short/bounce_paddle.mp3"));

		if (Settings.isMusicEnabled()) {
			music = Gdx.audio.newMusic(Gdx.files.internal("data/sound/long/Egmont_Overture_Finale.mp3"));	      
			// start the playback of the background music immediately
			music.setLooping(true);
			music.setVolume(Settings.getMusicVolume());
			music.play();
		}
	}

	@Override
	public void hide() {
		batch.dispose();
		shapeRenderer.dispose();
		paddle.dispose();
		blockCollection.dispose();
		ballCollection.dispose();

		pingSound.dispose();
		wallBounceSound.dispose();
		paddleBounceSound.dispose();
		if (music != null) {
			music.dispose();
		}
		
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
		
		drawFrame(2, 2, Resources.SCREEN_WIDTH - 2, Resources.SCREEN_HEIGHT - 4, Color.BLACK, goldColor);

		// render objects
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		paddle.render(batch);
		blockCollection.render(batch);
		ballCollection.render(batch);
		batch.end();
		
		// draw frames around collision shape
		if (Settings.isDebug()) {
			//drawCollisionFrames();
		}
	
		// apply input and update game logic
		input.checkOtherInput();
		updateGameLogic();

		printInfos();		
	}
	
//	private void drawCollisionFrames() {
//		drawFrame(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), Color.RED, Color.WHITE);
//		java.util.Iterator<Block> blocks = blockCollection.getIterator();
//		while(blocks.hasNext()) {
//			Block block = blocks.next();
//			drawFrame(block.getX(), block.getY(), block.getWidth(), block.getHeight(), Color.RED, Color.WHITE);
//		}
//		
//		java.util.Iterator<Ball> balls = ballCollection.getIterator();
//		while(balls.hasNext()) {
//			Ball ball = balls.next();
//			drawFrame(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight(), Color.RED, Color.WHITE);
//		}
//	}
	
	private void drawFrame(float x, float y, float width, float height, Color colorData, Color colorOther) {
		// draw frame around playing area
		shapeRenderer.setProjectionMatrix(camera.combined);
		 shapeRenderer.begin(ShapeType.Box);
		 if (Settings.getSelectedView() == "data") {
			 shapeRenderer.setColor(colorData);
		 } else {
			 //shapeRenderer.setColor(0.914f, 0.682f, 0.63f, 1);
			 shapeRenderer.setColor(colorOther);
		 }
		 shapeRenderer.box(x, y, 0, width, height, 0);
		 shapeRenderer.end();
	}
		
	private void printInfos() {
		// print infos		
		batch.begin();
		// print ball speed
		font.setScale(1f);
		font.draw(batch, "Speed: " + paddle.getSpeed(), 0, Resources.SCREEN_HEIGHT - 10);
		// print level
		font.draw(batch, "Level " + currentLevel, 200, Resources.SCREEN_HEIGHT - 10);
		font.setScale(0.8f);
		// number balls
		font.draw(batch, "Balls: " + player.getNumberBallsLeft(), 360, Resources.SCREEN_HEIGHT - 10);
		// number of not-solid blocks left
		font.draw(batch, "Blocks: " + blockCollection.getNumberNotSolid(), 410, Resources.SCREEN_HEIGHT - 10);
		// score
		font.draw(batch, "Score: " + player.getScore(), 400, Resources.SCREEN_HEIGHT - 25);
		// print fps 
		font.setScale(0.4f);
		font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 5);   
		font.setScale(1f);
		batch.end();
	}
	
	private void updateGameLogic() {
		if (ta == null) { // no animation running. this will be refactored at some point...
			ballCollection.moveBalls(paddle, paddleBounceSound, wallBounceSound);
			blockCollection.checkDestroyed(ballCollection, pingSound, wallBounceSound);
		}
		
		if (blockCollection.getNumberNotSolid() == 0) {
			finishedLevel();
		}
		if (player.getNumberBallsLeft() < 0) {
			currentLevel = 0;			
			if (animation("Game Over")) {
				// TODO game over should apear when ball leaves field, not when player plays new ball.
				// so: reduce number of balls left in player when ball leaves the field, not when player plays ball. 
				// some other calls to player have to be adjusted as well...S
				game.setScreen(game.menuScreen);
			}
			
		}
	}
	// TODO refactor this shit (animation, etC)
	private void finishedLevel() {	
		if (currentLevel == Resources.NUMBER_HIGHEST_LEVEL) {			
			currentLevel = 0;
			if (animation("You WIN the Game.")) {
				game.setScreen(game.menuScreen);
			}
		} else {
			if (animation("Level Complete")) {
				// load next level
				player.addBall();
				currentLevel++;
				blockCollection = LevelHandler.loadLevel("" + currentLevel);
				//blockCollection = LevelHandler.generateLevel(camera);
				// remove all balls
				ballCollection = new BallCollection();
			}
		}
	}
	
	// TODO move this stuff somewhere else. and rename...
	TextAnimation ta = null;
	BitmapFont afont;
	private boolean animation(String text) {
		if (ta == null) {
			afont = new BitmapFont(Gdx.files.internal(Settings.getSelectedView() + "/skin/default.fnt"),
			         Gdx.files.internal(Settings.getSelectedView() + "/skin/default.png"), false);
			ta = new TextAnimation(afont, text);			
			ta.setRunning();
		}
		if (ta.isRunning()) {
			batch.begin();
			ta.drawFrame(batch);
			batch.end();
		} else {
			ta = null;
			return true; // done animating
		}
		return false; // running
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
		
		Vector3 touchPos;  // stores input position
		/** one cannot play with the keyboard AND the mouse (paddle is always on mouse cursor). 
		 *  only allow one at a time (activated by pressing a key / moving the mouse).
		 */
		boolean playingWithKeyboard = false;
		
		public DesktopInput() {
			super();
			touchPos = new Vector3();
		}
		
		/**
		 * checks the input that is not caught by InputAdapter (keyPressed, etc).
		 */
		public void checkOtherInput() {
			// move paddle (keyboard)
			if(Gdx.input.isKeyPressed(Keys.LEFT)) paddle.moveLeft();
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) paddle.moveRight();	
			
			// exit to main menu TODO change this to in-game menu
			if(Gdx.input.isKeyPressed(Keys.ESCAPE)) game.setScreen(game.menuScreen);	
						
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
				playingWithKeyboard = true;
			} else if (Gdx.input.justTouched()) {
				playingWithKeyboard = false;
			}
			
			if (!playingWithKeyboard) {
				// move paddle (mouse)
				// movePaddle(Gdx.input.getX(), Gdx.input.getY(), 20); // old way, might be too hard
				
				// new way: set paddle to pointer (easier)
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				paddle.setX(touchPos.x - (paddle.getWidth() / 2));
			}
			
			if (Gdx.input.isKeyPressed(Keys.B) && Settings.isDebug()) {
				// go to level builder
				game.setScreen(new BDLevelBuilderScreen(game));
			}
		}		

		@Override
		public boolean keyDown(int keycode) {
			// add ball (keyboard)
			if(keycode == Keys.SPACE) {
				if (ta == null) { // no animation running. this will be refactored at some point...
					spawnPlayerBall();
				}
			}
			// change paddle speed (keyboard)
			else if (keycode == Keys.UP) {
				paddle.increaseSpeed();
			} else if (keycode == Keys.DOWN) {
				paddle.decreaseSpeed();	
			}
			
			// TODO remove dev and implement extras
			if (Settings.isDebug()) {
				if (keycode == Keys.NUM_0) {
					// double paddle width
					paddle.setWidth(paddle.getWidth() * 2);
				}
				if (keycode == Keys.NUM_1) {
					// half paddle width
					paddle.setWidth(paddle.getWidth() / 2);
				}
				if (keycode == Keys.NUM_2) {
					// add ball to player
					player.addBall();
				}
				if (keycode == Keys.NUM_3) {
					// duplicate ball (get x and y of ball or block that produces bonus)
					ballCollection.addBall(new Ball(Images.ballImage, 200, 200));
				}
				if (keycode == Keys.NUM_4) {
					// 4k points
					player.addToScore(4000);
				}
				// other ideas: 
				// increase ball size
				// increase ball speed
				// make that paddle can move left and right, but only so far that ball will hit for sure
			}
			playingWithKeyboard = true;
			return true;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// replaced by movePaddle(Gdx.input.getX(), Gdx.input.getY()); in checkOtherInput() for continuous movement
			// TODO do the same for touchDragged() (android paddle move)
			// move paddle (mouse)
			//movePaddle(screenX, screenY);
			playingWithKeyboard = false;
			return true;
		}
		
		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// spawn ball (mouse and android ( // TODO test on android) )
			spawnPlayerBall();
			return true;
		}
		
		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// move paddle (android ( // TODO test on android) )
			movePaddle(screenX, screenY, 5);
			return true;
		}
		
		/**
		 * moves the paddle left if the given y coordinate indicates that it is left of the paddle and right if it is to the right.
		 * The given tolerance multiplied by two is the space in the middle of the paddle that, if the mouse is there, will cause the paddle to 
		 * not move.
		 * 
		 * @param screenX
		 * @param screenY
		 * @param tolerance
		 */
		private void movePaddle(int screenX, int screenY, int tolerance) {
			touchPos.set(screenX, screenY, 0);
			camera.unproject(touchPos);
			if (touchPos.x < paddle.getX() + (paddle.getWidth() / 2) - tolerance) {
				paddle.moveLeft();
			} else if (touchPos.x > paddle.getX() + (paddle.getWidth() / 2) + tolerance) {
				paddle.moveRight();
			}
		}
		
		/**
		 * adds a new ball to the ballCollection if there are no balls in it.
		 * The ball will spawn in the middle of the paddle.
		 * removeBall will be called on player.
		 */
		private void spawnPlayerBall() {
			if (ballCollection.getNumberOfBalls() == 0) {
				player.removeBall();
				Ball ball = new Ball(Images.ballImage, paddle.getX() + (paddle.getWidth() / 2), paddle.getY() + 10);
				ballCollection.addBall(ball);
			}
		}
	}
}
