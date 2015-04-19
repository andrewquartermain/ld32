package com.andrewquartermain.ld32;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Assets {

	private static TextureAtlas atlas;
	public static Texture bg, ground;
	public static TextureRegion balloon, bomb, dead;
	public static Array<AtlasRegion> standing, hearts;
	public static Animation walking, running;
	private static Music music;
	private static Sound[] sounds;
	private static Sound sound;
	private static ParticleEffect explosion;
	public static BitmapFont font;
	
	public static void load() {
		atlas = new TextureAtlas(Gdx.files.internal("atlas.pack"));
		bg = new Texture(Gdx.files.internal("bg.jpg"));
		ground = new Texture(Gdx.files.internal("ground.png"));
		balloon = atlas.findRegion("balloon");
		bomb = atlas.findRegion("bomb");
		dead = atlas.findRegion("dead");
		hearts = atlas.findRegions("heart");
		standing = atlas.findRegions("standing");
		music = Gdx.audio.newMusic(Gdx.files.internal("audio/theme.mp3"));
		music.setVolume(0.25f);
		sounds = new Sound[10];
		for (int i = 0; i < sounds.length; i++) {
			sounds[i] = Gdx.audio.newSound(Gdx.files.internal("audio/chat" + i + ".wav"));
		}
		sound = Gdx.audio.newSound(Gdx.files.internal("audio/sound2.wav"));
		explosion = new ParticleEffect();
		explosion.load(Gdx.files.internal("effects/hearts.p"), Gdx.files.internal("effects"));
		font = new BitmapFont(Gdx.files.internal("font/questrial.fnt"));
		font.setColor(0, 0, 0, 0.84f);
		walking = new Animation(0.1f, atlas.findRegions("walking"), PlayMode.LOOP);
		running = new Animation(0.1f, atlas.findRegions("running"), PlayMode.LOOP);
		
	}
	
	public static void explode(float x, float y) {
		explosion.setPosition(x * LD32.PPU, y * LD32.PPU);
		explosion.start();
	}
	
	public static void drawExplosion(SpriteBatch batch, float delta) {
		explosion.draw(batch, delta);
	}
	
	public static TextureRegion getHeart(boolean filled) {
		return (filled ? hearts.get(0) : hearts.get(MathUtils.random(1, hearts.size - 1)));
	}
	
	public static void playMusic() {
		music.play();
		music.setLooping(true);
	}
	public static void stopMusic() {
		music.stop();
	}
	
	public static void playSound(float volume, float pan) {
		if (volume < 1) return;
		volume *= 0.007f;
		Sound sound = sounds[MathUtils.random(sounds.length - 1)];
		float pitch = MathUtils.random(0.75f, 1.5f);
		sound.play(volume, pitch, pan);
	}
	public static void playSound() {
		sound.play(0.25f);
	}
	
	public static void dispose() {
		atlas.dispose();
		bg.dispose();
		ground.dispose();
		music.dispose();
		for (int i = 0; i < sounds.length; i++) {
			sounds[i].dispose();
		}
		explosion.dispose();
		font.dispose();
		sound.dispose();
	}

}
