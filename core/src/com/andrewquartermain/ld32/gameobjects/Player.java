package com.andrewquartermain.ld32.gameobjects;

import com.andrewquartermain.ld32.Assets;
import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.input.KeyboardInput;
import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends GameObject {

	private static final float YACC = 0.15f, XACC = 0.15f, MAX_VEL_X = 10, MAX_VEL_Y = 3, DAMP = 0.99f, BOMB_DELAY = 1;

	private float velX, velY, accX, accY, bombTime;
	private boolean up, down, left, right, bombDropped;

	public Player(GameScreen screen, TextureRegion region, float x, float y, float width,
			float height) {
		super(screen, Assets.balloon, x, y, width, height);
		
		setRegion(Assets.balloon);
		
		
		Gdx.input.setInputProcessor(new KeyboardInput(this, new int[] {
			Keys.UP, Keys.DOWN, Keys.LEFT, Keys.RIGHT, Keys.Z, Keys.X, Keys.R	
		}));
	}

	@Override
	public void update(float delta) {
		if (left) {
			accX = -XACC;
		}
		if (right) {
			accX = XACC;
		}
		if (!right && !left) {
			accX = 0;
		}
		if (up) {
			accY = YACC;
		}
		if (down) {
			accY = -YACC;
		}
		if (!up && !down) {
			accY = 0;
		}
		
		stepX(delta);
		stepY(delta);
		if (bombDropped) {
			bombTime -= delta;
			if (bombTime <= 0) {
				setBombDropped(false);
			}
		}
	}



	private void stepX(float delta) {
		velX *= DAMP;
		velX += accX;
		if (Math.abs(velX) > MAX_VEL_X) {
			velX = Math.signum(velX) * MAX_VEL_X;
		}
		velX *= delta;
		if ((x + velX) < 1 || (x + width + velX) > GameScreen.LEVEL_WIDTH - 4) {
			velX = 0;
		} else {
			x += velX;
		}
		velX /= delta;
	}

	private void stepY(float delta) {
		velY *= DAMP;
		velY += accY;
		if (Math.abs(velY) > MAX_VEL_Y) {
			velY = Math.signum(velY) * MAX_VEL_Y;
		}
		velY *= delta;
		if ((y + velY) < 5 || (y + velY) > LD32.HEIGHT - 5) {
			velY = 0;
		} else {
			y += velY;
		}		
		velY /= delta;
	}

	public void bomb() {
		if (bombDropped) return;
		Bomb bomb = new Bomb(screen, x + 2, y);
		screen.addObject(bomb);
		setBombDropped(true);
	}

	public void shoot() {
	}

	public void reset() {
		screen.reset();
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	
	
	private void setBombDropped(boolean b) {
		bombDropped = b;
		if(b) bombTime = BOMB_DELAY;
		
	}

	@Override
	public void reset(float x, float y, float width, float height) {
		// TODO Auto-generated method stub
		
	}
	
}
