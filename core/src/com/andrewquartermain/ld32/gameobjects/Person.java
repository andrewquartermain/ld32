package com.andrewquartermain.ld32.gameobjects;

import com.andrewquartermain.ld32.gameobjects.fsm.Behaviour;
import com.andrewquartermain.ld32.gameobjects.fsm.Emotion;
import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Person extends GameObject {

	public static final float VEL = 2, RUN = 10;

	private Emotion emotion;
	private Behaviour behaviour;
	private float loveLevel, velocity;
	private Array<Person> contacts;
	private Array<Person> people;
	private float stateTime;

	public Person(GameScreen screen, TextureRegion region, float x, float y,
			float width, float height) {
		super(screen, region, x, y, width, height);
		loveLevel = MathUtils.random(0.01f, 0.99f);
		setEmotion();
		setBehaviour(Behaviour.WANDER);

		contacts = new Array<Person>();
		people = screen.getPeople();
		stateTime = 0;
	}

	@Override
	public void update(float delta) {
		stateTime += delta;

		velocity *= delta;
		float xx = x + velocity;
		if (xx < 0 || xx + width > GameScreen.LEVEL_WIDTH) {
			velocity *= -1;
		}
		x += velocity;
		velocity /= delta;

		if (behaviour == Behaviour.NONE) {
			for (int i = 0; i < people.size; i++) {
				Person p = people.get(i);
				if (this == p)
					continue;
				if (isContact(x, p.x)) {

					if (!contacts.contains(p, true))
						contacts.add(p);

				}
			}
			for (int i = 0; i < contacts.size; i++) {
				Person c = contacts.get(i);
				if (!isContact(x, c.x)) {
					contacts.removeValue(c, true);
				}
			}
		}
		emotion.update(this, delta);
		behaviour.update(this, delta);
		contacts.clear();

	}

	public void setBehaviour(Behaviour newBehaviour) {
		if (behaviour == Behaviour.DEAD)
			return;
		if (behaviour != null)
			behaviour.exit(this);
		stateTime = 0;
		behaviour = newBehaviour;
		behaviour.enter(this);
	}

	public void setEmotion(Emotion newEmotion) {
		if (emotion != null)
			emotion.exit(this);
		emotion = newEmotion;
		emotion.enter(this);
	}

	public void setEmotion() {
		if (loveLevel < 0) {
			setEmotion(Emotion.DEAD);
		} else if (loveLevel < 0.2f) {
			setEmotion(Emotion.ANGRY);
		} else if (loveLevel < 0.4f) {
			setEmotion(Emotion.FEARFUL);
		} else if (loveLevel < 0.6f) {
			setEmotion(Emotion.NORMAL);
		} else if (loveLevel < 0.8f) {
			setEmotion(Emotion.HAPPY);
		} else if (loveLevel < 1) {
			setEmotion(Emotion.LOVING);
		} else {
			setEmotion(Emotion.DEAD);
		}

	}

	public void setVelocity(float vel) {
		velocity = (MathUtils.randomBoolean() ? vel : -vel);
		velocity *= MathUtils.random(0.75f, 1.25f);
	}

	public boolean hasContacts() {
		return contacts.size > 0;
	}

	private boolean isContact(float x1, float x2) {
		return (x1 + 0.75f > x2 - 0.25f && x1 - 0.25f < x2 + 0.75f);
	}

	public void setInteraction(Person contact) {
		if (contact.getBehaviour() == Behaviour.NONE) {
			interact();
			contact.interact();
		}
	}
	
	private void interact() {
		behaviour.interact(this);		
	}

	public Person getContact() {
		Person p = contacts.get(MathUtils.random(contacts.size - 1));
		return p;
	}

	public void draw(SpriteBatch batch, BitmapFont font) {
		font.draw(batch, Integer.toString((int) (loveLevel * 100)), x * 20,
				(y + 2) * 20);

	}

	public void setLoveLevel(float f) {
		if (behaviour == Behaviour.DEAD)
			return;
		loveLevel += f;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setDead() {
		velocity = 0;
		width = 1;
		height = 0.5f;

	}

	public void remove() {
		screen.removePerson(this);

	}

	public Behaviour getBehaviour() {
		return behaviour;
	}

	public void spreadEmotion(Person contact) {
		emotion.spread(contact);
		
	}

}
