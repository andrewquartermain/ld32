package com.andrewquartermain.ld32;

import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LD32 extends Game {
	
	public static final boolean DEBUG = false;
	public static final float WIDTH = 32, HEIGHT = 18, PPU = 60, UPP = 1/PPU,
			SC_WIDTH = WIDTH * PPU, SC_HEIGHT = HEIGHT * PPU,
			HALF_WIDTH = WIDTH * 0.5f;
	
	private SpriteBatch batch;

	@Override
	public void create() {		
		batch = new SpriteBatch();
		Assets.load();
		setScreen(new GameScreen(this));
	}
	
	@Override
	public void dispose() {
		getScreen().dispose();
		Assets.dispose();
		batch.dispose();
	}
	

	public SpriteBatch getBatch() {
		return batch;
	}
	
}
