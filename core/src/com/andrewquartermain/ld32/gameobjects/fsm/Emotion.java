package com.andrewquartermain.ld32.gameobjects.fsm;

import com.andrewquartermain.ld32.gameobjects.Person;

public enum Emotion implements FSM {

	ANGRY (-0.02f){

		@Override
		public void update(Person person, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void enter(Person person) {
			person.setColor(0.3f, 0, 0, 1);

		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		
	},
	FEARFUL (-0.01f){

		@Override
		public void update(Person person, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void enter(Person person) {
			person.setColor(0.3f, 0.5f, 0.5f, 1);
		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		
	},
	NORMAL (0){

		@Override
		public void update(Person person, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void enter(Person person) {
			person.setColor(1, 1, 1, 1);


		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		
	},
	HAPPY (0.01f){

		@Override
		public void update(Person person, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void enter(Person person) {
			person.setColor(1, 1, 0, 1);


		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		
	},
	LOVING (0.02f){

		@Override
		public void update(Person person, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void enter(Person person) {
			person.setColor(1, 0.3f, 0.5f, 1);


		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		
	},
	DEAD (0){

		@Override
		public void update(Person person, float delta) {
			// TODO Auto-generated method stub

		}

		@Override
		public void enter(Person person) {
			person.setColor(0, 0, 0, 1);
			person.setBehaviour(Behaviour.DEAD);

		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		

	};
	
	Emotion(float spread) {
		this.spread = spread;
	}
	float spread;
	public void spread(Person person) {
		person.setLoveLevel(spread);
		person.setEmotion();
	}

}
