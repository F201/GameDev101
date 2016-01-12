package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * a basis block. To create more blocks (e.g. once that are not destroyed after one hit but multiple hits, never destroyed, etc.)
 * extend this class.
 * 
 * @author sam
 *
 */
public class Block extends MovableImageObject {

	public enum BlockType {
		/**
		 * a "normal" block. it is destroyed on first hit and has no bonuses.
		 */
		NORMAL, 
		/**
		 * a block that is never destroyed. 
		 */
		SOLID, 
		/**
		 * a block that has to be hit twice to disapear.
		 */
		TWOHIT,
		// TODO add more types		
	}
	
	private BlockType type;

	public Block(Sprite blockSprite, float x, float y) {
		super(blockSprite, x, y);
		type = BlockType.NORMAL;
	}
	
	public Block(Sprite blockSprite, float x, float y, BlockType type) {
		super(blockSprite, x, y);
		this.type = type;
	}
	
	/**
	 * returns true if the next hit would destroy this block.
	 * 
	 * @return
	 */
	public boolean hitDestroysBlock() {
		return true;
	}
	
	/**
	 * called by BlockCollection when a ball hit this Block and it was not destroyed (ie hitDestroysBlock returned false).
	 */
	public void hit() {
		
	}
	
	public void setType(BlockType type) {
		this.type = type;
	}
	
	public BlockType getType() {
		return type;
	}
}
