package org.softwaretalk.blockdestroyer;

import java.util.Iterator;

import org.softwaretalk.blockdestroyer.Block.BlockType;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class BlockCollection implements IImageObject {

	private Array<Block> blocks;
	private int numberSolid;
	
	public BlockCollection() {
		blocks = new Array<Block>();
		numberSolid = 0;
	}

	public Iterator<Block> getIterator() {
		return blocks.iterator();
	}

	public void addBlock(Block block) {
		blocks.add(block);
		if (block.getType() == BlockType.SOLID) {
			numberSolid++;
		}
	}
	
	/**
	 * returns how many not solid blocks are left in this collection.
	 * 
	 * @return
	 */
	public int getNumberNotSolid() {
		return blocks.size - numberSolid;
	}
	
	public static Block createBlock(BlockType type, float x, float y) {
		switch(type) {
		case NORMAL:
			return new Block(Images.blockImage, x, y, BlockType.NORMAL);
		case SOLID: 
			return new Block(Images.solidBlockImage, x, y, BlockType.SOLID) {
				@Override
				public boolean hitDestroysBlock() {					
					return false;
				}
			};
		case TWOHIT: 
			return new Block(Images.semiSolidBlockImage, x, y, BlockType.TWOHIT) {
				private int max_hit = 1;
				private int hits = 0;
				
				@Override
				public void hit() {
					hits++;
					super.setSprite(Images.semiSolidBlockImageOneHit);
				}

				@Override
				public boolean hitDestroysBlock() {					
					if (hits >= max_hit) {
						return true;
					} else {
						return false;
					}
				}
			};
		default:
			return new Block(Images.blockImage, x, y);
		}

	}

	/**
	 * checks if the given ball destroyes one or more blocks at its current
	 * position. Removes blocks from collection if they are destroyed.
	 * 
	 * @param ballCollection the collection holding the balls
	 */
	public void checkDestroyed(BallCollection ballCollection, Sound pingSound, Sound bounceSound) {
		// iterate over all balls
		Iterator<Ball> iterball = ballCollection.getIterator();	
		while(iterball.hasNext()) {
			// iterate over all blocks and check against ball
			Ball ball = iterball.next();
			Rectangle ballRectangle = ball.getRectangle();
			for(Block block: blocks) {
				// if ball is inside block, delete block
				if(ballRectangle.overlaps(block.getRectangle())) {
					if (block.hitDestroysBlock()) {
						blocks.removeValue(block, true);
						pingSound.play();
					} else {
						block.hit();
						ball.reflect(block, bounceSound);
					}
				}
			}
		}
	}

	/**
	 * WARNING: dummy method, returns null.
	 */
	@Override
	public Sprite getSprite() {
		return null;
	}

	/**
	 * WARNING: dummy method, does nothing.
	 */
	@Override
	public void setSprite(Sprite sprite) {

	}

	@Override
	public void render(SpriteBatch batch) {
		for(Block block: blocks) {
			//batch.draw(block.getTexture(), block.getX(), block.getY());
			block.render(batch);
		}

	}

	@Override
	public void dispose() {
		for(Block block: blocks) {
			block.dispose();
		}		
	}
}
