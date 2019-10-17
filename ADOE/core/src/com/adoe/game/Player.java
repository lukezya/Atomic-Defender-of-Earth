package com.adoe.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
	Sound ghostSound, shootSound, refreshSound;
	public float rotation;
	public int noBullets;
	public int maxBullets;
	public Texture showBullet;
	public float boostTime, boostDuration, rapidFireTime, rapidFireDuration, soundTime;
	public boolean boostGo,boosted, rapidGo, rapidFire, bJustPlayed;
	public Player(MyGdxGame game, float x, float y, int width, int height, float speed, Texture texture) {
		super(game, x, y, width, height, speed, texture);
		rotation = 0f;
		
		bJustPlayed = false;
		
		boostTime = 10f;
		boostDuration = 0f;
		boostGo = false;
		boosted = false;
		soundTime = 0f;
		
		rapidFireTime = 20f;
		rapidFireDuration = 0f;
		rapidGo = false;
		rapidFire = false;
		
		maxBullets = 10;
		noBullets = 10;
		showBullet=new Texture("showbullet.png");
		ghostSound = Gdx.audio.newSound(Gdx.files.internal("ghost.ogg"));
		shootSound = Gdx.audio.newSound(Gdx.files.internal("lasershoot.ogg"));
		refreshSound = Gdx.audio.newSound(Gdx.files.internal("refresh.ogg"));
	}

	@Override
	public void update(float delta) {
		dx = 0;
		dy = 0;
		boostTime+=delta;
		if(boostTime>10f) {
			game.bShift=true;
		}
		if ((boostTime>10f)&&(Gdx.input.isKeyJustPressed(Keys.SHIFT_LEFT))&&(boosted == false)) {
			ghostSound.play();
			speed += 150;
			boosted = true;
			boostGo = true;
			boostTime = 0f;
			game.bShift=false;
			game.bGhost = true;
		}
		if (boostGo) {
			boostDuration +=delta;
			if (boostDuration>3f) {
				boostGo = false;
				boostDuration = 0f;
				speed-=150;
				boosted = false;
				game.bGhost = false;
			}
		}
		if(bJustPlayed) {
			soundTime+=delta;
			if (soundTime>3f) {
				bJustPlayed = false;
				soundTime = 0f;
			}
		}
		rapidFireTime+=delta;
		if(rapidFireTime>20f) {
			game.bRapid=true;
		}
		if ((rapidFireTime>20f)&&(Gdx.input.isKeyJustPressed(Keys.SPACE))&&(rapidFire == false)) {
			game.playerReloadSpeed = 0.1f;
			rapidFire = true;
			rapidGo = true;
			rapidFireTime = 0f;
			game.bRapid=false;
			game.bFire = true;
		}
		if (rapidGo) {
			rapidFireDuration +=delta;
			if (rapidFireDuration>10f) {
				rapidGo = false;
				rapidFireDuration = 0f;
				game.playerReloadSpeed = 0.25f;
				rapidFire = false;
				game.bFire = false;
			}
		}

		// move
		if(Gdx.input.isKeyPressed(Keys.W)) {
			dy = speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			dy = -speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			dx = -speed * delta;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			dx = speed * delta;
		}		
		if((Gdx.input.isButtonPressed((Input.Buttons.LEFT))&&game.bShoot==false)&&noBullets>0) {
			shootSound.play();
			game.bullets.add(new Bullet(game, x+width/2,y+width/2,10,10,10f,new Texture("squarebullet.png")));
			game.bShoot=true;
			game.playerReloadTime=0f;
			noBullets--;
		}	
		//rotation
		float xdist = Gdx.input.getX()-(x+width/2);
		float ydist = (720-Gdx.input.getY())-(y+height/2);
		//System.out.println("dx: "+xdist+"        dy: " + ydist);
		float m = ydist/xdist;
		//double dist = Math.sqrt(Math.pow(xdist, 2)+Math.pow(ydist, 2));
		double angle = Math.toDegrees(Math.atan(m));
		if (xdist>=0) {
			angle = -90+angle;
			//System.out.println("1st quadrant");
		}
		else if (xdist<0) {
			angle = 90+angle; 
			//System.out.println("2nd quadrant");
		}
//		else if (xdist<0&&ydist<0) {
//			angle = 90+angle;
//			//System.out.println("3rdst quadrant");
//		}
//		else if (xdist>=0&&ydist<0) {
//			angle = -90-angle;
//			//System.out.println("4th quadrant");
//		}
		rotation = (float)angle;
		//System.out.println(rotation);
	}
	public void reloadBullets() {
		if ((noBullets != 10)&&(bJustPlayed==false)) {
			refreshSound.play();
			bJustPlayed = true;
		}
		noBullets = 10;
	}
	
}
