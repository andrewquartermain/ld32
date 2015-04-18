package com.andrewquartermain.ld32;

import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LD32 extends Game {
	
	public static final boolean DEBUG = true;
	public static final float WIDTH = 45, HEIGHT = 30, PPU = 20, UPP = 1/PPU,
			SC_WIDTH = WIDTH * PPU, SC_HEIGHT = HEIGHT * PPU,
			HALF_WIDTH = WIDTH * 0.5f;
	
	private AssetManager assets;
	private SpriteBatch batch;

	@Override
	public void create() {		
		assets = new AssetManager(new InternalFileHandleResolver());
		batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}
	
	@Override
	public void dispose() {
		getScreen().dispose();
		assets.dispose();
		batch.dispose();
	}
	
	public AssetManager getAssets() {
		return assets;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
}
