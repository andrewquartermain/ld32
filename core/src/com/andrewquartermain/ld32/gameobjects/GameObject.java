package com.andrewquartermain.ld32.gameobjects;

import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameObject implements Looped {

	private TextureRegion region;
	protected float x, y, width, height;
	protected GameScreen screen;

	protected Color color;

	public GameObject(GameScreen screen, TextureRegion region, float x,
			float y, float width, float height) {
		super();
		this.region = region;
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
			batch.draw(region, x, y);
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

}
