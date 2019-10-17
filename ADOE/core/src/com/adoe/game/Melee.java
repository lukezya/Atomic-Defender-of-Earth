package com.adoe.game;

import com.badlogic.gdx.graphics.Texture;

public class Melee {
	public MyGdxGame game;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public float lives;
	public int width;
	public int height; 
	public float speed;
	public Texture texture;
	
	public Melee(MyGdxGame game, float x, float y, int width, int height, float speed, Texture texture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.texture = texture;
		dx = 0;
		dy = 0;
		lives = 3;
	}
	public void update(float delta, float playerX, float playerY) {
		float xdist = playerX-(x);
		float ydist = playerY-(y);
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
		
	}
	public void move(float newX, float newY) {
		x = newX;
		y = newY;
	}
	
}
