package com.andrewquartermain.ld32.gameobjects.fsm;

import com.andrewquartermain.ld32.gameobjects.Person;

public interface FSM {
	
	public void update(Person person, float delta);
	public void enter(Person person);
	public void exit(Person person);

}
