package com.andrewquartermain.ld32.gameobjects;

import com.andrewquartermain.ld32.screen.GameScreen;

public class Bomb extends GameObject{

	
	private float vel;
	
	public Bomb(GameScreen screen, float x, float y) {
		super(screen, null, x, y, 0.5f, 0.25f);
		
	}

	@Override
	public void update(float delta) {
		vel += -0.2f;
		vel *= delta;
		y += vel;
		vel /= delta;
		
		if (y <= GameScreen.GROUND) {
			explode();
		}
		
	}

	private void explode() {
		screen.explode(this, x,y);
		
	}

}
