package com.andrewquartermain.ld32.screen;

import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.gameobjects.Bomb;
import com.andrewquartermain.ld32.gameobjects.GameObject;
import com.andrewquartermain.ld32.gameobjects.Person;
import com.andrewquartermain.ld32.gameobjects.Player;
import com.andrewquartermain.ld32.gameobjects.fsm.Behaviour;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameScreen extends DefaultScreen {

	public static final int LEVEL_WIDTH = (int) LD32.WIDTH * 5,
			NUM_PEOPLE = LEVEL_WIDTH / 5,
			GROUND = 2;;
	private Cameras cameras;
	private Array<GameObject> objects;
	private Array<Person> people;
	private Player player;

	private ShapeRenderer rend;
	private BitmapFont font;

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

		rend = new ShapeRenderer();
		font = new BitmapFont();
	}

	@Override
	public void show() {
	}

	@Override
	public void update(float delta) {
		for (GameObject obj : objects) {
			obj.update(delta);
		}
		for (int i = 0; i < people.size; i++) {
			people.get(i).update(delta);
		}
		cameras.update(delta, player);
	}

	@Override
	public void draw(SpriteBatch batch) {
		Gdx.gl.glClearColor(0, 0.61f, 0.8f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(cameras.bigCombined());
		batch.begin();

		for (GameObject obj : objects) {
			obj.draw(batch);
		}
		for (Person p : people) {
			p.draw(batch);
			p.draw(batch, font);
		}

		batch.end();

		if (LD32.DEBUG)
			drawDebug();
	}

	public void drawDebug() {

		rend.setProjectionMatrix(cameras.camCombined());
		rend.begin(ShapeType.Line);

		for (GameObject obj : objects) {
			obj.drawDebug(rend);
		}
		for (Person p : people) {
			p.drawDebug(rend);
		}

		rend.end();
	}

	public Array<Person> getPeople() {
		return people;
	}

	public void removePerson(Person person) {
		people.removeValue(person, true);

	}

	public void addObject(GameObject obj) {
		objects.add(obj);
	}

	public void explode(Bomb bomb, float x, float y) {
		for (int i = 0; i < people.size; i++) {
			Person p = people.get(i);
			if (p.getX() + p.getWidth() > x - 5 && p.getX() < x + 5) {
				p.setLoveLevel(0.05f);
				p.setBehaviour(Behaviour.FLEE);
				p.setEmotion();
			}
		}
		objects.removeValue(bomb, true);
		
	}

}
