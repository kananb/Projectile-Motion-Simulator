package engine.components;

import java.awt.Graphics;
import java.awt.Rectangle;

import engine.PEngine;

public abstract class PComponent {

	protected int x, y;
	protected int width, height;
	protected int floorHeight;
	protected int sideWidth;
	protected Rectangle hitbox;
	protected PEngine engine;

	public PComponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.hitbox = new Rectangle(x, y, width, height);
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	public boolean collided(PComponent c) {
		return hitbox.intersects(c.hitbox);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getFloorHeight() {
		return floorHeight;
	}
	
	public void setFloorHeight(int floorHeight) {
		this.floorHeight = floorHeight;
	}
	
	public int getSideWidth() {
		return sideWidth;
	}
	
	public void setSideWidth(int sideWidth) {
		this.sideWidth = sideWidth;
	}
	
	public void setPEngine(PEngine engine) {
		this.engine = engine;
	}
}
