package com.andrewquartermain.ld32.screen;

import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.gameobjects.Player;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

public class Cameras {

	private OrthographicCamera cam, bigCam, bgCam, singleCam;

	public Cameras(GameScreen screen) {
		bgCam = new OrthographicCamera();
		bgCam.setToOrtho(false, LD32.SC_WIDTH, LD32.SC_HEIGHT);
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, LD32.WIDTH, LD32.HEIGHT);
		
		bigCam = new OrthographicCamera();
		bigCam.setToOrtho(false, LD32.SC_WIDTH, LD32.SC_HEIGHT);
		
		singleCam = new OrthographicCamera();
		singleCam.setToOrtho(false, LD32.SC_WIDTH, LD32.SC_HEIGHT);
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
		bgCam.position.x = bigCam.position.x * 0.2f;
		
		bgCam.update();
		bigCam.update();
		cam.update();
		singleCam.update();
	}
	
	public Matrix4 bgCombined() {
		return bgCam.combined;
	}

	public Matrix4 bigCombined() {
		return bigCam.combined;
	}

	public Matrix4 camCombined() {
		return cam.combined;
	}
	
	public Matrix4 singleCombined() {
		return singleCam.combined;
	}

}
