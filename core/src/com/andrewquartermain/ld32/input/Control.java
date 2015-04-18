package com.andrewquartermain.ld32.input;

import com.andrewquartermain.ld32.gameobjects.Player;

public enum Control {
	UP {

		@Override
		public void keyDown(Player player) {
			player.setUp(true);
			
		}

		@Override
		public void keyUp(Player player) {
			player.setUp(false);
			
		}},
	DOWN {

			@Override
			public void keyDown(Player player) {
				player.setDown(true);
				
			}

			@Override
			public void keyUp(Player player) {
				player.setDown(false);
				
			}},
	LEFT {

		@Override
		public void keyDown(Player player) {
			player.setLeft(true);
			
		}

		@Override
		public void keyUp(Player player) {
			player.setLeft(false);
			
		}},
	RIGHT {

		@Override
		public void keyDown(Player player) {
player.setRight(true);			
		}

		@Override
		public void keyUp(Player player) {
			player.setRight(false);
			
		}},
	BOMB {

		@Override
		public void keyDown(Player player) {
			player.bomb();
			
		}

		@Override
		public void keyUp(Player player) {
			// TODO Auto-generated method stub
			
		}},
	SHOOT{

		@Override
		public void keyDown(Player player) {
			player.shoot();
			
		}

		@Override
		public void keyUp(Player player) {
			// TODO Auto-generated method stub
			
		}},
	RESET{

		@Override
		public void keyDown(Player player) {
			player.reset();
			
		}

		@Override
		public void keyUp(Player player) {
			// TODO Auto-generated method stub
			
		}};
	
	public abstract void keyDown(Player player);
	public abstract void keyUp(Player player);
}
