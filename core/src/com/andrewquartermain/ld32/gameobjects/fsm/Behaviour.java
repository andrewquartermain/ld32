package com.andrewquartermain.ld32.gameobjects.fsm;

import com.andrewquartermain.ld32.gameobjects.Person;

public enum Behaviour implements FSM {
	
	WANDER {

		@Override
		public void update(Person person, float delta) {
			if (person.getStateTime() > 1) {
				person.setBehaviour(NONE);
			}
		}

		@Override
		public void enter(Person person) {
			person.setVelocity(Person.VEL);

		}

		@Override
		public void exit(Person person) {
			

		}

		@Override
		public void interact(Person person) {
			// TODO Auto-generated method stub
			
		}
	},
	FLEE {

		@Override
		public void update(Person person, float delta) {
			if (person.getStateTime() > 3) {
				person.setBehaviour(NONE);
			}

		}

		@Override
		public void enter(Person person) {
			person.setVelocity(Person.RUN);

		}

		@Override
		public void exit(Person person) {
			person.setVelocity(0);

		}

		@Override
		public void interact(Person person) {
			// TODO Auto-generated method stub
			
		}
	},
	INTERACT {

		@Override
		public void update(Person person, float delta) {
			if (person.getStateTime() > 0.4f) {
				person.setBehaviour(WANDER);
			}

		}

		@Override
		public void enter(Person person) {
			person.setVelocity(0);
		}

		@Override
		public void exit(Person person) {
			// TODO Auto-generated method stub

		}

		@Override
		public void interact(Person person) {
			// TODO Auto-generated method stub
			
		}
	},
	DEAD {

		@Override
		public void update(Person person, float delta) {
			
			if (person.getStateTime() > 10) {
				person.remove();
			}

		}

		@Override
		public void enter(Person person) {
			person.setDead();

		}

		@Override
		public void exit(Person person) {


		}

		@Override
		public void interact(Person person) {
			person.spreadEmotion(person.getContact());
			
		}
	},
	NONE {

		@Override
		public void update(Person person, float delta) {
			if (person.hasContacts()) {
				Person contact = person.getContact();
				if (contact.getBehaviour() == NONE) {
					person.spreadEmotion(contact);
					person.setInteraction(contact);
				}
			}
			
		}

		@Override
		public void enter(Person person) {
			person.setVelocity(Person.VEL);
			
		}

		@Override
		public void exit(Person person) {
			
			
		}

		@Override
		public void interact(Person person) {
			person.setBehaviour(INTERACT);
		}
		
	};
	
	public abstract void interact(Person person);
	

}
