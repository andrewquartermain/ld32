package com.andrewquartermain.ld32.screen;

import com.andrewquartermain.ld32.Assets;
import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.gameobjects.Bomb;
import com.andrewquartermain.ld32.gameobjects.GameObject;
import com.andrewquartermain.ld32.gameobjects.Person;
import com.andrewquartermain.ld32.gameobjects.Player;
import com.andrewquartermain.ld32.gameobjects.fsm.Behaviour;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends DefaultScreen {
	
	private enum GameState{
		READY, PLAY, PAUSE
	}

	public static final int LEVEL_WIDTH = (int) LD32.WIDTH * 3,
			NUM_PEOPLE = LEVEL_WIDTH / 2, GROUND = 3;;
	private Cameras cameras;
	private Array<GameObject> objects;
	private Array<Person> people;
	private Player player;
	private BitmapFont font;
	private float time, loveLevel, population;
	private String timeString;
	private GameState state;
	private float gWidth, gHeight;
	private String string2;
	private boolean ready;
	
	public GameScreen(LD32 game) {
		super(game);

		cameras = new Cameras(this);
		objects = new Array<GameObject>();
		people = new Array<Person>();
		player = new Player(this, null, 12, 12, 1, 1);
		objects.add(player);

		for (int i = 0; i < NUM_PEOPLE; i++) {
			int x = MathUtils.random(0, LEVEL_WIDTH);
			Person person = new Person(this, null, x, GROUND, 0.5f, 1);
			people.add(person);
		}
		
		state = GameState.READY;
		population = NUM_PEOPLE;
		setTimeString();
		
		gWidth = Assets.ground.getWidth();
		gHeight = Assets.ground.getHeight();
		font = Assets.font;
	}

	@Override
	public void show() {
		Assets.playMusic();
	}

	@Override
	public void update(float delta) {
		
		switch(state) {
		case PAUSE:
			if (Gdx.input.isKeyJustPressed(Keys.Y)){
				reset(true);
			} else if (Gdx.input.isKeyJustPressed(Keys.N) || Gdx.input.isKeyJustPressed(Keys.SPACE)){
				reset(false);
			}
			break;
		case PLAY:
			time += delta;
			for (GameObject obj : objects) {
				obj.update(delta);
			}
			loveLevel = 0;
			for (int i = 0; i < people.size; i++) {
				Person p = people.get(i);
				p.update(delta);
				loveLevel += p.getLoveLevel();
			}
			cameras.update(delta, player);
			break;
		case READY:
			if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				setState(GameState.PLAY);
			}
			break;
		default:
			break;
		
		}
	}

	@Override
	public void draw(SpriteBatch batch, float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		batch.setProjectionMatrix(cameras.bgCombined());
		batch.setColor(1, 1, 1, 0.5f);
		batch.draw(Assets.bg, 0, 0, -LD32.SC_WIDTH, LD32.SC_HEIGHT);
		batch.draw(Assets.bg, 0, 0);
		batch.setColor(1, 1, 1, 1);

		batch.setProjectionMatrix(cameras.bigCombined());
		
		for (int i = 0; i < 3; i++) {
			
			boolean flip = i % 2 == 0;
			batch.draw(Assets.ground, flip ? i * LD32.SC_WIDTH : i * LD32.SC_WIDTH + gWidth, -50, flip ? gWidth : -gWidth, gHeight);
		}

		for (GameObject obj : objects) {
			obj.draw(batch);
		}
		for (Person p : people) {
			p.draw(batch);
			p.draw(batch, font);
		}
		
		Assets.drawExplosion(batch, delta);
		
		batch.setProjectionMatrix(cameras.singleCombined());
		
		String string = "time: " + setTimeString() + "\n" +
				"population: " + getPopulation() + "%\n" +
				"love: " + getLoveLevel() + "%";
		font.draw(batch, string, LD32.SC_WIDTH * 0.05f, LD32.SC_HEIGHT * 0.95f);
		ready = false;
		string2 = "";
		if(state == GameState.PAUSE) {
			string2 = "Reset? Y/N";
		} else if (state == GameState.READY) {
			string2 = "Arrow keys = move\n" +
					"Z = drop bomb, R = reset\n" +
					"Press space to start";
			ready = true;
		} else if (population == 0) {
		
			string2 = "Everyone died";
		} else if (getLoveLevel() > 80) {
			string2 = "All is love";
		} else if (getLoveLevel() < 20) {
			string2 = "Love is Gone";
		}
		
		
		font.draw(batch, string2, LD32.SC_WIDTH * (ready ? 0.3f : 0.4f), LD32.SC_HEIGHT * (ready ? 0.6f : 0.55f));
		
		batch.end();

		
	}

	public void decPopulation() {
		population--;
	}

	public Array<Person> getPeople() {
		return people;
	}

	

	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	public void explode(Bomb bomb, float x, float y) {
		for (int i = 0; i < people.size; i++) {
			Person p = people.get(i);
			if (p.getX() + p.getWidth() > x - 5 && p.getX() < x + 5) {
				float f = MathUtils.random(0.01f, 0.05f);
				p.setLoveLevel(f);
				p.setBehaviour(Behaviour.FLEE);
				p.setEmotion();
			}
		}
		Assets.explode(x, y);
		objects.removeValue(bomb, true);

	}
	
	public String setTimeString() {
		int m, d, s;
		m = (int) time / 60;
		d = (int) time % 60 / 10;
		s = (int) time % 60 % 10;
		timeString = m + ":" + d + s;
		return timeString;
	}
	
	public int getLoveLevel() {
		return (int) ((loveLevel / population) * 100);
	}
	
	public int getPopulation() {
		return (int) (population / NUM_PEOPLE * 100);
	}

	public float getX() {
		return player.getX();
	}
	
	public void reset() {
		setState(GameState.PAUSE);
		
	}
	
	private void setState(GameState newState) {
		state = newState;
	}
	
	private void reset(boolean b) {
		if (b) {
			player.superReset(12, 12, 1, 1);

			for (int i = 0; i < NUM_PEOPLE; i++) {
				int x = MathUtils.random(0, LEVEL_WIDTH);
				people.get(i).superReset(x, GROUND, 0.5f, 1);
			}
			time = 0;
			loveLevel = 0;
			
			population = NUM_PEOPLE;
			setTimeString();
			
		} 
		setState(GameState.PLAY);
		
	}

}
