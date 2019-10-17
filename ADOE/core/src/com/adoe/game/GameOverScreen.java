package com.adoe.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverScreen implements Screen {
	private Game game;
	private Stage stage;
	
	SpriteBatch batch;
	Texture gameOver;
	
	private TextButton btnPlayAgain;
	private TextButton btnExit;
	private Label lblHighScore;
	private Label lblScores;
	
	ArrayList<String> list;
	Sound gamestart;
	
	public GameOverScreen(Game g) {
		game = g;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		gameOver = new Texture("LS.png");
		Skin skin = new Skin(Gdx.files.internal("neon-ui.json"));
		gamestart = Gdx.audio.newSound(Gdx.files.internal("play.ogg"));
		
		//btnPlayAgain
		btnPlayAgain = new TextButton("PLAY AGAIN",skin);
		btnPlayAgain.setSize(300, 60);
		btnPlayAgain.setPosition(Gdx.graphics.getWidth()/2-btnPlayAgain.getWidth()/2,150);
		btnPlayAgain.setColor(Color.GREEN);
		
		btnPlayAgain.addListener(new ClickListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int point, int button){
				gamestart.play();
                game.setScreen(new MyGdxGame(game));
            }
		});
		
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
		//lblHighScore
		lblHighScore = new Label("HIGHSCORES",skin);
		lblHighScore.setFontScale(1.5f);
		lblHighScore.setAlignment(1);
		lblHighScore.setPosition(ADOEGame.WIDTH/2-lblHighScore.getWidth()/2,ADOEGame.HEIGHT/2+50);
		
		//lblScores
		list = new ArrayList<String>();
		try {
			Scanner s = new Scanner(new File("scores.txt"));
			
			list = new ArrayList<String>();
			while (s.hasNext()){
				String sLine = s.nextLine();
			    list.add(sLine);
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean bDone = false;
		int i = 0;
		while (!bDone){
			String sOn = list.get(i);
			int iPos = sOn.indexOf('\t');
			String sFileScore = sOn.substring(iPos+1, sOn.length());
			float fileScore = Float.parseFloat(sFileScore);
			if (MyGdxGame.timePassed>fileScore&&!bDone) {
				list.add(i, MenuScreen.PLAYER_NAME+"\t"+String.format("%.2f", MyGdxGame.timePassed));
				list.remove(list.get(5));
				bDone = true;
			}
			if (i<4)
				i++;
			else
				bDone = true;
		}
		String sScores = "";
		for (String sOutput:list) {
			sScores = sScores + sOutput + '\n';
		}
		try {
            FileWriter writer = new FileWriter("scores.txt", false);
            for (String sOutput:list) {
	            writer.write(sOutput+'\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		lblScores = new Label(sScores,skin);
		lblScores.setWidth(300);
		lblScores.setAlignment(1);
		lblScores.setPosition(ADOEGame.WIDTH/2-lblScores.getWidth()/2,ADOEGame.HEIGHT/2-110);
		
		stage.addActor(btnPlayAgain);
		stage.addActor(btnExit);
		stage.addActor(lblHighScore);
		stage.addActor(lblScores);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameOver, Gdx.graphics.getWidth()/2-gameOver.getWidth()/2, Gdx.graphics.getHeight()/3*2);
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
