package com.adoe.game.desktop;

import com.adoe.game.ADOEGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ADOEGame.WIDTH;
		config.height = ADOEGame.HEIGHT;
		config.title = ADOEGame.TITLE;
		config.y = 0;
		new LwjglApplication(new ADOEGame(), config);
	}
}
