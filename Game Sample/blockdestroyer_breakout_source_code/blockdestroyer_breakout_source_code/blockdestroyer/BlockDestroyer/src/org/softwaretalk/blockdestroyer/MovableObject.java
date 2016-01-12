package org.softwaretalk.blockdestroyer;

import com.badlogic.gdx.math.Rectangle;

public class MovableObject {
	Rectangle position;
	
	public MovableObject() {
		position = new Rectangle();
	}
	
	public MovableObject(float x, float y, float width, float height) {
		position = new Rectangle();
		position.x = x;
		position.y = y;
		position.width = width;
		position.height = height;
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}	
	
	public void setWidth(float width) {
		position.width = width;
	}
	
	public void setHeight(float height) {
		position.height = height;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}	
	
	public float getWidth() {
		return position.width;
	}
	
	public float getHeight() {
		return position.height;
	}	
	
	public Rectangle getRectangle() {
		return position;
	}
	
	public void addToX(float amount) {
		this.position.x += amount;
	}
	
	public void addToY(float amount) {
		this.position.y += amount;
	}
}
