package com.andrewquartermain.ld32.input;

import com.andrewquartermain.ld32.gameobjects.Player;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.IntMap;



public class KeyboardInput extends InputAdapter {
	
	private IntMap<Control> controls;
	private Player player;

	public KeyboardInput(Player player, int[] keycodes) {
		assert (keycodes.length == Control.values().length);
		this.player = player;
		controls = new IntMap<Control>();
		for (int i = 0; i < keycodes.length; i++) {
			controls.put(keycodes[i], Control.values()[i]);
		}
		
	}

	@Override
	public boolean keyDown(int keycode) {
		
		try {
			controls.get(keycode).keyDown(player);
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		try {
			controls.get(keycode).keyUp(player);
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
	
	

}
