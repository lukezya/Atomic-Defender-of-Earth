package com.adoe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bullet {
	public MyGdxGame game;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public int width;
	public int height; 
	//public float angle;
	public float speed;
	public Texture texture;
	
	public Bullet(MyGdxGame game, float x, float y, int width, int height, float speed, Texture texture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height; 
		this.speed = speed;
		this.texture = texture;
		//angle =0;
		dx=0;
		dy=0;
		//rotation
		float xdist = Gdx.input.getX()-(x+width/2);
		float ydist = (720-Gdx.input.getY())-(y+height/2);
		float m = ydist/xdist;
		//double dist = Math.sqrt(Math.pow(xdist, 2)+Math.pow(ydist, 2));
		double rotation = Math.atan(m);
		//angle = (float)rotation; 
		if (xdist>=0) {
			dx = (float)Math.cos(rotation)*speed;
			dy = (float)Math.sin(rotation)*speed;
		}else {
			dx = -(float)Math.cos(rotation)*speed;
			dy = -(float)Math.sin(rotation)*speed;
		}
		//System.out.println("dx: "+dx+"              dy: "+dy);
	}
	public boolean remove() {
		if (x<0||x>700||y<0||y>720) {
			this.dispose();
			return true;
		}
		return false;
	}
	public void dispose() {
		texture.dispose();
	}
	
	public void move(float newX, float newY) {
		x = newX;
		y = newY;
	}
}
