package org.softwaretalk.blockdestroyer;

import java.util.Iterator;

import org.softwaretalk.blockdestroyer.Block.BlockType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class LevelHandler {
	public static void saveLevel(String fileName, BlockCollection blockCollection) {
		FileHandle file = Gdx.files.local("level/" + fileName);
		file.writeString("", false);
		Iterator<Block> blocks = blockCollection.getIterator();
		while (blocks.hasNext()) {
			Block block = blocks.next();
			file.writeString("" + block.getX() + "," + block.getY() + "," + block.getType() + "\n", true);
		}
	}
	
	public static BlockCollection loadLevel(String fileName) {
		System.out.println("loading level");
		BlockCollection blockCollection = new BlockCollection();
		FileHandle file = Gdx.files.internal("level/" + fileName);
		String[] fileContent = file.readString().split("\n");
		for(int i = 0; i < fileContent.length; i++) {
			String[] blockData = fileContent[i].split(",");
			if (blockData.length == 3) {
				float x;
				float y;
				BlockType type;
				try {
					x = Float.valueOf(blockData[0]);
					y = Float.valueOf(blockData[1]);
					type = BlockType.valueOf(blockData[2]);
					blockCollection.addBlock(BlockCollection.createBlock(type, x, y));
				} catch (NumberFormatException nfe) {
					// malformed block data. ignore this block
				}
			}
		}
		return blockCollection;
	}

	public static BlockCollection generateLevel(OrthographicCamera camera) {
		BlockCollection blockCollection = new BlockCollection();
		Sprite blockSprite = Images.blockImage;
		//int rows = MathUtils.random(9, 12);
		int rows = 12;
		for(int i = 0; i < rows; i++) {
			for(int j = 1; j < 7; j++) {
				int random = MathUtils.random(0, 100);
				float x = (j * (blockSprite.getWidth() * Resources.INIT_IMAGE_SCALEDOWN)) + 20;
				float y = ((i * (blockSprite.getHeight() * Resources.INIT_IMAGE_SCALEDOWN)) + 100);
//				Vector3 touchPos = new Vector3();
//				touchPos.set(x, y, 0);
//				camera.unproject(touchPos);	
//				touchPos.set(touchPos.x, touchPos.y, 0);
//				x = touchPos.x;
//				y = touchPos.y;
				if (random > 50) { // 50% probability that no block is build
					
				} else if (random > 30) { // 20% 
					blockCollection.addBlock(BlockCollection.createBlock(BlockType.TWOHIT, x, y));		
				} else if (random > 5) { // 25%
					blockCollection.addBlock(BlockCollection.createBlock(BlockType.NORMAL, x, y));		
				} else { // 5%
					blockCollection.addBlock(BlockCollection.createBlock(BlockType.SOLID, x, y));			
				}
			}
			
//			if (i == 2) {
//				// build a solid row
//				for(int j = 1; j < 5; j++) {
//						float x = (j * (blockSprite.getWidth() * Resources.INIT_IMAGE_SCALEDOWN)) + 20;
//						float y = ((i * (blockSprite.getHeight() * Resources.INIT_IMAGE_SCALEDOWN)) + 200);
//						blockCollection.addBlock(BlockCollection.createBlock(BlockType.SOLID, x, y));					
//				}
//			} else if (i == 3 || i == 1) {
//				// build a semi-solid row
//				for(int j = 1; j < 5; j++) {
//						float x = (j * (blockSprite.getWidth() * Resources.INIT_IMAGE_SCALEDOWN)) + 20;
//						float y = ((i * (blockSprite.getHeight() * Resources.INIT_IMAGE_SCALEDOWN)) + 200);
//						blockCollection.addBlock(BlockCollection.createBlock(BlockType.TWOHIT, x, y));					
//				}
//			} else {			
//				// build a row
//				int blocks = MathUtils.random(5, 7);
//				for(int j = 0; j < blocks; j++) {
//					// 90% probability that block is build
//					if(MathUtils.random(1, 100) > 10) {
//						float x = (j * (blockSprite.getWidth() * Resources.INIT_IMAGE_SCALEDOWN)) + 20;
//						float y = (i * (blockSprite.getHeight() * Resources.INIT_IMAGE_SCALEDOWN)) + 200;
//						blockCollection.addBlock(BlockCollection.createBlock(BlockType.NORMAL, x, y));
//					}
//				}
//			}
			
		}
		return blockCollection;
	}

}
