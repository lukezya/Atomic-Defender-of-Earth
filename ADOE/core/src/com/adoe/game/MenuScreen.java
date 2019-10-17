package com.adoe.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen{
	//private static final int WIDTH = 600;
    //private static final int HEIGHT = 500;
    
	private Game game;
	private Stage stage;
	
	private TextButton btnPlay;
	private TextButton btnExit;
	private TextField txtInput;
	private Label lblName;
	
	public static String PLAYER_NAME;
	
	SpriteBatch batch;
	Texture texTitle1, texTitle2;
	Sound gamestart;
	
	public MenuScreen(Game g){
		game = g;
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		texTitle1 = new Texture("AD.png");
		texTitle2 = new Texture("OE.png");
		Skin skin = new Skin(Gdx.files.internal("neon-ui.json"));
		gamestart = Gdx.audio.newSound(Gdx.files.internal("play.ogg"));
		
		//btnPlay
		btnPlay = new TextButton("PLAY",skin);
		btnPlay.setSize(300, 60);
		btnPlay.setPosition(Gdx.graphics.getWidth()/2-btnPlay.getWidth()/2,150);
		btnPlay.setColor(Color.GREEN);
		
		btnPlay.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int point, int button){
				gamestart.play();
                PLAYER_NAME = txtInput.getText().toString();
                game.setScreen(new MyGdxGame(game));
            }
		});
		//txtInput
		txtInput = new TextField("",skin);
		txtInput.setSize(300, 60);
		txtInput.setPosition(ADOEGame.WIDTH/2-txtInput.getWidth()/2,ADOEGame.HEIGHT/2-100);
		txtInput.setAlignment(1);
		
		//lblName
		lblName = new Label("PLAYER NAME",skin);
		lblName.setFontScale(1.5f);
		lblName.setAlignment(1);
		lblName.setPosition(ADOEGame.WIDTH/2-lblName.getWidth()/2,ADOEGame.HEIGHT/2-20);
		
		//btnExit
		btnExit = new TextButton("EXIT",skin);
		btnExit.setSize(300, 60);
		btnExit.setPosition(Gdx.graphics.getWidth()/2-btnExit.getWidth()/2,108);
		btnExit.setColor(Color.CYAN);
		
		btnExit.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int point, int button){
				Gdx.app.exit();
            }
		});
		
		stage.addActor(btnPlay);
		stage.addActor(txtInput);
		stage.addActor(lblName);
		stage.addActor(btnExit);
	}
	
	@Override
	public void show() {
		stage.setKeyboardFocus(txtInput);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);			
		batch.begin();
		batch.draw(texTitle1, Gdx.graphics.getWidth()/2-texTitle1.getWidth()/2, Gdx.graphics.getHeight()/3*2+60);
		batch.draw(texTitle2, Gdx.graphics.getWidth()/2-texTitle2.getWidth()/2, Gdx.graphics.getHeight()/3*2-50);
		batch.end();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}

