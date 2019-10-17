package com.adoe.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame implements Screen {
	public static final int WIDTH = 900;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Atomic Defender of Earth";
	
    public static float timePassed;
    float timeStarted;
	SpriteBatch batch;
	int screenWidth;
	int screenHeight;
	BitmapFont font;
	GlyphLayout layout;
	Sound exitSound;
    
	// 1 = block
	// 0 = empty
	// the x and y coordinate system is not what it seems
	// visually x goes down and y across
	// this will make more sense when you compare it to what is drawn
	//36x36
	int[][] map = {									//left
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,1 ,1,1,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,-3,-3,-3 ,-3,-3,-3,1,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,0,0,0,0,-3,-3 ,-3,-3,0,0,0,0,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,1,1,1,0,0,0,0,0,0,0,0,-3 ,-3,0,0,0,0,0,0,0,0,1,1,1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1,-1,-1}, 
		{-1,-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1,-1}, 
		{-1,-1,-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,-1,-1,-1}, 
		{-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1}, 
		{-1,-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,-1,-1}, 
		{-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1}, 
		{-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1}, 
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1}, 
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1}, 
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1}, 
		{1,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,1}, 
		{1,-2,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,-2,1}, 
		{1,-2,-2,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,-2,-2,1}, 
//bot																					//top		
		{1,-2,-2,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,-2,-2,1}, 
		{1,-2,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,-2,1}, 
		{1,-2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-2,1}, 
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1}, 
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1}, 
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1}, 
		{-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1}, 
		{-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1}, 
		{-1,-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,-1,-1}, 
		{-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1}, 
		{-1,-1,-1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,-1,-1,-1}, 
		{-1,-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1,-1}, 
		{-1,-1,-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,1,1,0,0,0,0,0,0,0,0,0,0,0 ,0,0,0,0,0,0,0,0,0,0,0,1,1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,1,1,1,0,0,0,0,0,0,0,0,-2 ,-2,0,0,0,0,0,0,0,0,1,1,1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,0,0,0,0,-2,-2 ,-2,-2,0,0,0,0,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,-2,-2,-2 ,-2,-2,-2,1,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, 
		{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,1 ,1,1,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, 
	};											//right
//	int[][] map = {
//			{1,1,1,1,1,1,1, 1 ,1,1,1,1,1,1,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			
//			{1,0,1,1,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,1,1, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,1, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,0,0,0,0,0,0, 0 ,0,0,0,0,0,0,1}, 
//			{1,1,1,1,1,1,1, 1 ,1,1,1,1,1,1,1}, 
//		};
	int mapWidth = 36;
	int mapHeight = 36;
	int tileSize = 20;
	int lives;
	Texture tileTexture, spaceTexture, voidTexture, playerbaseTexture,enemybaseTexture, resourceTexture, livesBorderTexture;
	Texture heartTexture, shiftTexture, ghostTexture, spacebarTexture, rapidFireTexture, timeBordTexture;
	Texture shiftOpaque, ghostOpaque, spaceOpaque, rapidOpaque, atomicTexture, defenderTexture;
	float playerReloadTime, meleeRespawnTime, playerReloadSpeed;
	boolean bShoot, bShift, bGhost, bRapid, bFire;
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<EnemyBullet> enemybullets = new ArrayList<EnemyBullet>();
	ArrayList<Melee> melees1 = new ArrayList<Melee>();
	ArrayList<Melee> melees2 = new ArrayList<Melee>();
	ArrayList<Range> ranges = new ArrayList<Range>();
	
	enum Axis { X, Y };
	enum Direction { U, D, L, R };
	private Game game;

  public MyGdxGame (Game g) {
	  game =g;
	  font = new BitmapFont(Gdx.files.internal("font.fnt"));
	  layout = new GlyphLayout();
	  batch = new SpriteBatch();
	  tileTexture = new Texture("orangeborder.png");  
	  spaceTexture = new Texture("xd.jpg");  
	  voidTexture = new Texture("DarkGrey.png");
	  playerbaseTexture = new Texture("fountain.png"); 
	  enemybaseTexture = new Texture("base.png"); 
	  resourceTexture = new Texture("Resources.png"); 
	  livesBorderTexture = new Texture("livesborder.png");
	  shiftTexture = new Texture("shift.png");
	  ghostTexture = new Texture("ghosticon.png");
	  heartTexture = new Texture("heart.png");
	  spacebarTexture = new Texture("space.png");
	  rapidFireTexture = new Texture("rapidfire.png");
	  shiftOpaque = new Texture("shiftopaque.png");
	  ghostOpaque = new Texture("ghostopaque.png");
	  spaceOpaque = new Texture("spaceopaque.png");
	  rapidOpaque = new Texture("rapidopaque.png");
	  atomicTexture = new Texture("Atomic.png");
	  defenderTexture = new Texture("Defender.png");
	  timeBordTexture = new Texture("timeborder.png");
	  screenWidth = Gdx.graphics.getWidth();
	  screenHeight = Gdx.graphics.getHeight();
	  
	  melees1.add(new Melee(this,350,670,20,20,1f,new Texture("melee.png")));
	  melees2.add(new Melee(this,350,50,20,20,1f,new Texture("melee.png")));
	  ranges.add(new Range(this,670,350,20,20,1f,new Texture("range.png")));
	  
	  bShoot = false; 
	  bShift = true;
	  bGhost = false;
	  bRapid = false;
	  bFire = false;
	  playerReloadTime = 0f;
	  meleeRespawnTime = 0f;
	  playerReloadSpeed = 0.25f;
	  lives = 3;
	  

	  exitSound = Gdx.audio.newSound(Gdx.files.internal("exit.ogg"));
	  
	  // add some entities including a player
	  entities.add(new Player(this, 100, 150, 20, 20, 120.0f, new Texture("realplayer.png")));
	  timeStarted = System.nanoTime(); 
	  timePassed = System.nanoTime()-timeStarted;
//	  entities.add(new Entity(this, 50, 150, 20, 20, 120.0f, new Texture("enemy.png")));
//	  entities.add(new Entity(this, 200, 200, 20, 20, 120.0f, new Texture("enemy.png")));
//	  entities.add(new Entity(this, 180, 50, 20, 20, 120.0f, new Texture("enemy.png")));
  }
  
  public void moveEntity(Entity e, float newX, float newY) {
	  // just check x collisions keep y the same
	  moveEntityInAxis(e, Axis.X, newX, e.y);
	  // just check y collisions keep x the same
	  moveEntityInAxis(e, Axis.Y, e.x, newY);
  }
  
  public void moveEntityInAxis(Entity e, Axis axis, float newX, float newY) {
	  Direction direction;
	  
	  // determine axis direction
	  if(axis == Axis.Y) {
		  if(newY - e.y < 0) direction = Direction.U;
		  else direction = Direction.D;
	  }
	  else {
		  if(newX - e.x < 0) direction = Direction.L;
		  else direction = Direction.R;
	  }

	  if(!tileCollision(e, direction, newX, newY) && !entityCollision(e, direction, newX, newY)) {
		  // full move with no collision
		  e.move(newX, newY);
	  }
	  // else collision with wither tile or entity occurred 
  }
  
  public boolean tileCollision(Entity e, Direction direction, float newX, float newY) {
	  boolean collision = false;
	  // determine affected tiles
	  int x1 = (int) Math.floor(Math.min(e.x, newX) / tileSize);
	  int y1 = (int) Math.floor(Math.min(e.y, newY) / tileSize);
	  int x2 = (int) Math.floor((Math.max(e.x, newX) + e.width - 0.1f) / tileSize);
	  int y2 = (int) Math.floor((Math.max(e.y, newY) + e.height - 0.1f) / tileSize);
	  
	  // todo: add boundary checks...

	  // tile checks
	  for(int x = x1; x <= x2; x++) {
		  for(int y = y1; y <= y2; y++) {
			  if(map[x][y] == 1) {
				  collision = true;	        
				  e.tileCollision(map[x][y], x, y, newX, newY, direction);
			  }
			  if((map[x][y] == -3)&&(e instanceof Player)) {
				  ((Player)e).reloadBullets();
			  } 
		  }
	  }
	  
	  return collision;
  }
  
  public boolean entityCollision(Entity e1, Direction direction, float newX, float newY) {
	  boolean collision = false;
	  
	  for(int i = 0; i < entities.size(); i++) {
		  Entity e2 = entities.get(i);
		  
		  // we don't want to check for collisions between the same entity
		  if(e1 != e2) {
			  // axis aligned rectangle rectangle collision detection
			  if(newX < e2.x + e2.width && e2.x < newX + e1.width &&
				  newY < e2.y + e2.height && e2.y < newY + e1.height) {
				  collision = true;
				  
				  e1.entityCollision(e2, newX, newY, direction);
			  }
		  }
	  }
	  
	  return collision;
  }
  public boolean enemyBulletCollision(Melee m) {
		//any collisions with melees
		 boolean collision = false;
		  
		  for(int i = 0; i < bullets.size(); i++) {
			  Bullet b = bullets.get(i);
			  

			  // axis aligned rectangle rectangle collision detection
			  if(m.x < b.x + b.width && b.x < m.x + m.width &&
				  m.y < b.y + b.height && b.y < m.y + b.height) {
				  collision = true;
				  bullets.remove(b);
				  break;
				  
			  }
			  
		  }
		  return collision;
  }
  public boolean rangeBulletCollision(Range r) {
		//any collisions with ranges
		 boolean collision = false;
		  
		  for(int i = 0; i < bullets.size(); i++) {
			  Bullet b = bullets.get(i);
			  

			  // axis aligned rectangle rectangle collision detection
			  if(r.x < b.x + b.width && b.x < r.x + r.width &&
				  r.y < b.y + b.height && b.y < r.y + b.height) {
				  collision = true;
				  bullets.remove(b);
				  break;
				  
			  }
			  
		  }
		  return collision;
}
  public boolean playerBulletCollision(Player p) {
		//any collisions with ranges
		 boolean collision = false;
		  
		  for(int i = 0; i < enemybullets.size(); i++) {
			  EnemyBullet b = enemybullets.get(i);
			  

			  // axis aligned rectangle rectangle collision detection
			  if(p.x < b.x + b.width && b.x < p.x + p.width &&
				  p.y < b.y + b.height && b.y < p.y + b.height) {
				  collision = true;
				  enemybullets.remove(b);
				  break;
				  
			  }
			  
		  }
		  return collision;
}
  public boolean playerEnemyCollision(Player p) {
		 boolean collision = false;
		  
		  for(int i = 0; i < melees1.size(); i++) {
			  Melee m = melees1.get(i);
			  

			  // axis aligned rectangle rectangle collision detection
			  if(p.x < m.x + m.width && m.x < p.x + p.width &&
				  p.y < m.y + m.height && m.y < p.y + m.height) {
				  collision = true;
				  melees1.remove(m);
				  break;
				  
			  }
			  
		  }
		  for(int i = 0; i < melees2.size(); i++) {
			  Melee m = melees2.get(i);
			  

			  // axis aligned rectangle rectangle collision detection
			  if(p.x < m.x + m.width && m.x < p.x + p.width &&
				  p.y < m.y + m.height && m.y < p.y + m.height) {
				  collision = true;
				  melees2.remove(m);
				  break;
				  
			  }
			  
		  }
		  return collision;
}
 public void playerRangeCollision(Player p) {
		  for(int i = 0; i < ranges.size(); i++) {
			  Range m = ranges.get(i);
			  

			  // axis aligned rectangle rectangle collision detection
			  if(p.x < m.x + m.width && m.x < p.x + p.width &&
				  p.y < m.y + m.height && m.y < p.y + m.height) {
				  ranges.remove(m);
				  break;
				  
			  }
			  
		  }
}
  public void enemyspawn(float delta) {
	  meleeRespawnTime+= delta;
	  if (meleeRespawnTime>5f) {
		  melees1.add(new Melee(this,350,670,20,20,1f,new Texture("melee.png")));
		  melees2.add(new Melee(this,350,50,20,20,1f,new Texture("melee.png")));
		  ranges.add(new Range(this,670,350,20,20,1f,new Texture("range.png")));
		  meleeRespawnTime = 0;
	  }
	  
  }
  public void handleInput() {
	  if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
		  exitSound.play();
		  game.setScreen(new GameOverScreen(game));
		}
  }
  
  @Override
  public void render (float delta) {
	  handleInput();
	  // update
	  this.enemyspawn(delta);
	  playerReloadTime +=delta;
	  if (playerReloadTime>playerReloadSpeed) {
			bShoot = false;
		}
	  // update all entities
	  float playerX=0;
	  float playerY=0;
	  for(int i = entities.size() - 1; i >= 0; i--) {
		  Entity e = entities.get(i);
		  // update entity based on input/ai/physics etc
		  // this is where we determine the change in position
		  if (e instanceof Player) {
			  playerX = e.x;
			  playerY = e.y;
			  //update player lives
			  this.playerRangeCollision(((Player)e));
			  if (playerEnemyCollision((Player)e)){
				  if (lives<=1) {
					  exitSound.play();
					  game.setScreen(new GameOverScreen(game));
				  }else {
					  lives--;
				  }
			  }
			  if (playerBulletCollision((Player)e)){
				  if (lives<=1) {
					  exitSound.play();
					  game.setScreen(new GameOverScreen(game));
				  }else {
					  lives--;
				  }
			  }
		  }
		  e.update(delta);
		  // now we try move the entity on the map and check for collisions
		  moveEntity(e, e.x + e.dx, e.y + e.dy);
	  }	  
	  //update all bullets
	  int jLoop=0;
	  while (jLoop!=bullets.size()) {
		  Bullet b = bullets.get(jLoop);
		  if (b.remove()) {
			  bullets.remove(b);
		  }else {
			  b.move(b.x+b.dx, b.y+b.dy);
			  jLoop++;
		  }
	  }
	  int gLoop=0;
	  while (gLoop!=enemybullets.size()) {
		  EnemyBullet e = enemybullets.get(gLoop);
		  if (e.remove()) {
			  enemybullets.remove(e);
		  }else {
			  e.move(e.x+e.dx, e.y+e.dy);
			  gLoop++;
		  }
	  }
//	  for (int j=0;j<bullets.size();j++) {
//		  Bullet b = bullets.get(j);
//		 // b.update(delta);
//		  b.move(b.x+b.dx, b.y+b.dy);
//	  }
	  //check enemy bullet collisions and update enemy
	  int lLoop=0;
	  while (lLoop != melees1.size()) {
		  Melee mCol = melees1.get(lLoop);
		  if (this.enemyBulletCollision(mCol)) {
			  mCol.lives--;
			  if (mCol.lives<=0) {
				  melees1.remove(mCol);
			  }else {
				  mCol.update(delta, playerX, playerY);
				  mCol.move(mCol.x+mCol.dx, mCol.y+mCol.dy);
				  lLoop++;
			  }
		  }else {
			  mCol.update(delta, playerX, playerY);
			  mCol.move(mCol.x+mCol.dx, mCol.y+mCol.dy);
			  lLoop++;  
		  }
		  
	  }
	  int kLoop=0;
	  while (kLoop != melees2.size()) {
		  Melee mCol = melees2.get(kLoop);
		  if (this.enemyBulletCollision(mCol)) {
			  mCol.lives--;
			  if (mCol.lives<=0) {
				  melees2.remove(mCol);
			  }else {
				  mCol.update(delta, playerX, playerY);
				  mCol.move(mCol.x+mCol.dx, mCol.y+mCol.dy);
				  kLoop++;
			  }
		  }else {
			  mCol.update(delta, playerX, playerY);
			  mCol.move(mCol.x+mCol.dx, mCol.y+mCol.dy);
			  kLoop++;
		  }
		  
	  }
	  int hLoop=0;
	  while (hLoop != ranges.size()) {
		  Range r = ranges.get(hLoop);
		  if (this.rangeBulletCollision(r)) {
			  r.lives--;
			  if (r.lives<=0) {
				  ranges.remove(r);
			  }else {
				  r.update(delta, playerX, playerY);
				  r.move(r.x+r.dx, r.y+r.dy);
				  hLoop++;
			  }
		  }else {
			  r.update(delta, playerX, playerY);
			  r.move(r.x+r.dx, r.y+r.dy);
			  hLoop++;
		  }
		  
	  }
	  

	  
	  // draw
	  // ---

	  
	  // to offset where your map and entities are drawn change the viewport
	  // see libgdx documentation
	  
	  Gdx.gl.glClearColor(0, 0, 0, 1);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  batch.begin();
    
	  // draw tile map
	  // go over each row bottom to top
	  for(int y = 0; y < mapHeight; y++) {
		  // go over each column left to right		
		  for(int x = 0; x < mapWidth; x++) {
			  // tile
			  if(map[x][y] == 1) {
				  batch.draw(tileTexture, x * tileSize, y * tileSize);
			  }
			  if(map[x][y] == 0) {
				  batch.draw(voidTexture, x * tileSize, y * tileSize);
			  }
			  if(map[x][y] == -1) {
				  batch.draw(spaceTexture, x * tileSize, y * tileSize);
			  }
			  if(map[x][y] == -2) {
				  batch.draw(enemybaseTexture, x * tileSize, y * tileSize);
			  }
			  if(map[x][y] == -3) {
				  batch.draw(playerbaseTexture, x * tileSize, y * tileSize);
			  }
			  // draw other types here...
		  }
	  }
	  batch.draw(resourceTexture, 730, 500);
	  batch.draw(livesBorderTexture,730,440);
	  batch.draw(timeBordTexture,730,580);
	  //draw bullets
	  for (int j=0;j<bullets.size();j++) {
		  Bullet b = bullets.get(j);
		  batch.draw(b.texture, b.x, b.y);
	  }
	  
	  for (int g=0;g<enemybullets.size();g++) {
		  EnemyBullet e = enemybullets.get(g);
		  batch.draw(e.texture, e.x, e.y);
	  }
	  
	  // draw all entities
	  for(int i = entities.size() - 1; i >= 0; i--) {
		  Entity e = entities.get(i);
		  if (e instanceof Player) {
			  batch.draw(e.texture, e.x, e.y, 10, 10, 20f, 20f, 1f, 1f,((Player) e).rotation, 0, 0, 20, 20, false, false);
			  //draw resources
			  int xRes=747;
			  int yRes = 507;
			  boolean bSecRow = false;
			  for (int n=0;n<((Player)e).noBullets;n++) {
				  if ((n>=5)&&(bSecRow==false)) {
					  xRes=747;
					  yRes=532;
					  bSecRow=true;
				  }
				  batch.draw(((Player)e).showBullet,xRes,yRes);
				  xRes+=25;
			  }
			  
		  } else
		  batch.draw(e.texture, e.x, e.y);
	  }
	  //draw all enemies
	  for (int k=0;k<melees1.size();k++) {
		  Melee m = melees1.get(k);
		  batch.draw(m.texture, m.x, m.y);
	  }
	  for (int k=0;k<melees2.size();k++) {
		  Melee m = melees2.get(k);
		  batch.draw(m.texture, m.x, m.y);
	  }
	  for (int h=0;h<ranges.size();h++) {
		  Range r = ranges.get(h);
		  batch.draw(r.texture, r.x, r.y);
	  }
	  //draw lives
	  int xLives = 747;
	  for(int o=0;o<lives;o++) {
		  batch.draw(heartTexture, xLives ,447 );
		  xLives+=25;
	  }
	  //draw boost
	  if (bShift) {
		  batch.draw(shiftTexture, 732 ,355 );
	  } else {
		  batch.draw(shiftOpaque, 732 ,355);
	  }
	  if (bGhost) {
		  batch.draw(ghostTexture, 832 ,355 );
	  } else {
		  batch.draw(ghostOpaque, 832 ,355 );
	  }
	  //draw rapid fire
	  if (bRapid) {
		  batch.draw(spacebarTexture, 732 ,267 );
	  } else {
		  batch.draw(spaceOpaque, 732 ,267 );
	  }
	  if (bFire) {
		  batch.draw(rapidFireTexture, 832 ,267 );
	  } else {
		  batch.draw(rapidOpaque, 832 ,267 );
	  }
	  //draw title and name
	  float txtWidth;
	  timePassed = (System.nanoTime()-timeStarted)/1000000000;
	  String timeDisplay = String.format("%.2f",timePassed);
	  batch.draw(atomicTexture, 732, 177);
	  batch.draw(defenderTexture, 700, 117);
	  batch.draw(resourceTexture, 730, 37);
	  layout.setText(font, "Time:");
	  txtWidth = layout.width;
	  font.draw(batch, "Time", 810-txtWidth/2, 670);
	  layout.setText(font, MenuScreen.PLAYER_NAME);
	  txtWidth = layout.width;
	  font.draw(batch, MenuScreen.PLAYER_NAME, 810-txtWidth/2, 77);
	  layout.setText(font, timeDisplay);
	  txtWidth = layout.width;
	  font.draw(batch, timeDisplay, 810-txtWidth/2, 630);
	  batch.end();
  }

@Override
public void resize(int width, int height) {
	
}

@Override
public void show() {
	
}

@Override
public void hide() {
	
}

@Override
public void pause() {
	
}

@Override
public void resume() {
	
}

@Override
public void dispose() {
	tileTexture.dispose(); spaceTexture.dispose(); voidTexture.dispose();
	playerbaseTexture.dispose(); enemybaseTexture.dispose(); resourceTexture.dispose();
	livesBorderTexture.dispose();heartTexture.dispose(); shiftTexture.dispose();
	ghostTexture.dispose(); spacebarTexture.dispose(); rapidFireTexture.dispose();
	shiftOpaque.dispose(); ghostOpaque.dispose(); spaceOpaque.dispose();
	rapidOpaque.dispose(); atomicTexture.dispose(); defenderTexture.dispose();;
}
}
