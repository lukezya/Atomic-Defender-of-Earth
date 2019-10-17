package com.adoe.game;

import com.badlogic.gdx.Game;

public class ADOEGame extends Game{
	public static final int WIDTH = 900;
    public static final int HEIGHT = 720;
    public static final String TITLE = "Atomic Defender of Earth";
	
	@Override
	public void create () {
		// start with MenuScreen
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		// use render method in Game class
		super.render();
	}
}
