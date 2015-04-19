package com.andrewquartermain.ld32.screen;

import com.andrewquartermain.ld32.LD32;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class DefaultScreen implements Screen {

	protected LD32 game;
	private SpriteBatch batch;
	
	public DefaultScreen(LD32 game) {
		this.game = game;
		batch = game.getBatch();
	}

	@Override
	public void render(float delta) {
		delta = Math.min(delta, 0.1f);
		update(delta);
		draw(batch, delta);
	}
	
	
	public abstract void update(float delta);
	
	public abstract void draw(SpriteBatch batch, float delta);
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
