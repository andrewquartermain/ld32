package com.andrewquartermain.ld32.gameobjects;

import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameObject implements Looped {

	private TextureRegion region;
	protected float x, y, width, height, tWidth, tHeight;
	protected GameScreen screen;
	protected boolean flip;
	protected Color color;

	public GameObject(GameScreen screen, TextureRegion region, float x,
			float y, float width, float height) {
		super();
		setRegion(region);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.screen = screen;
		color = new Color(1, 1, 1, 1);
	}

	public abstract void update(float delta);

	public void draw(SpriteBatch batch) {

		if (region != null)
			batch.draw(region, flip ? x * LD32.PPU + tWidth : x * LD32.PPU, y
					* LD32.PPU, flip ? -tWidth : tWidth,
					tHeight);
	}

	public void drawDebug(ShapeRenderer rend) {
		rend.setColor(color);
		rend.rect(x, y, width, height);
	}

	public void moveX(float xBy) {
		x += xBy;
	}

	public void moveY(float yBy) {
		y += yBy;
	}

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public TextureRegion getRegion() {
		return region;
	}

	public void setRegion(TextureRegion region) {
		this.region = region;
		tWidth = region.getRegionWidth();
		tHeight = region.getRegionHeight();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public GameScreen getScreen() {
		return screen;
	}
	
	public void superReset(float x,
			float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		color.set(1, 1, 1, 1);
		reset(x,y,width,height);
	}
	
	public abstract void reset(float x,
			float y, float width, float height);

}
