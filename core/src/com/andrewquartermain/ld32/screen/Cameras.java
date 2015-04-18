package com.andrewquartermain.ld32.screen;

import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.gameobjects.Player;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Cameras {

	private OrthographicCamera cam, bigCam;

	public Cameras(GameScreen screen) {
		cam = new OrthographicCamera();
		cam.setToOrtho(false, LD32.WIDTH, LD32.HEIGHT);
		bigCam = new OrthographicCamera();
		bigCam.setToOrtho(false, LD32.SC_WIDTH, LD32.SC_HEIGHT);
	}

	public void update(float delta, Player player) {
		
		float px = player.getX();
		float cx = cam.position.x;
		
		float vx = px - cx;
		cx += (vx * delta);
		if (cx - LD32.HALF_WIDTH < 0 || cx + LD32.HALF_WIDTH > GameScreen.LEVEL_WIDTH) {
			cx = cam.position.x;
		}
		
		cam.position.x = cx;
		bigCam.position.x = cam.position.x * LD32.PPU;
		
		
		
		bigCam.update();
		cam.update();
	}

	public Matrix4 bigCombined() {
		return bigCam.combined;
	}

	public Matrix4 camCombined() {
		return cam.combined;
	}

}
