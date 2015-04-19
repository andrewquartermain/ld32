package com.andrewquartermain.ld32.gameobjects;

import com.andrewquartermain.ld32.Assets;
import com.andrewquartermain.ld32.LD32;
import com.andrewquartermain.ld32.gameobjects.fsm.Behaviour;
import com.andrewquartermain.ld32.gameobjects.fsm.Emotion;
import com.andrewquartermain.ld32.screen.GameScreen;
import com.badlogic.gdx.graphics.Color;
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
	private TextureRegion heart;

	public Person(GameScreen screen, TextureRegion region, float x, float y,
			float width, float height) {
		super(screen, Assets.standing.get(0), x, y, width, height);
		loveLevel = MathUtils.random(0.01f, 0.99f);
		setEmotion();
		setBehaviour(Behaviour.WANDER);
		
		setHeart(Assets.getHeart(false));

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
			flip = !flip;
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
		setLoveLevel(-0.00001f);

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
		flip = MathUtils.randomBoolean();
		velocity = (flip ? -vel : vel);
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
		if (LD32.DEBUG) {
			font.draw(batch, Integer.toString((int) (loveLevel * 100)), x
					* LD32.PPU, (y) * LD32.PPU + tHeight);
		}
		if (heart != null) {
			batch.setColor(color);
			batch.draw(heart, ((x + 0.25f) * LD32.PPU), ((y) * LD32.PPU) + tHeight);
			batch.setColor(Color.WHITE);
		}

	}

	public void setLoveLevel(float f) {
		if (behaviour == Behaviour.DEAD)
			return;
		loveLevel += f;
	}
	
	public float getLoveLevel() {
		return loveLevel;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setDead() {
		velocity = 0;
		width = 1;
		height = 0.5f;
		screen.decPopulation();
		loveLevel = 0;

	}

	

	public Behaviour getBehaviour() {
		return behaviour;
	}

	public void spreadEmotion(Person contact) {
		emotion.spread(contact);
		
	}
	
	public void setHeart(TextureRegion heart) {
		this.heart = heart;
	}



	public void chat() {
		float volume, pan;
		pan = (getX() - screen.getX()) / LD32.WIDTH;
		volume = Math.abs(pan);
		Assets.playSound(volume, pan);
	}



	@Override
	public void reset(float x, float y, float width, float height) {
		loveLevel = MathUtils.random(0.01f, 0.99f);
		setEmotion();
		behaviour = Behaviour.WANDER;
		setBehaviour(Behaviour.WANDER);
		
		setHeart(Assets.getHeart(false));

		contacts.clear();
		people = screen.getPeople();
		stateTime = 0;
		
	}

}
