package com.tdt4240.paint2win;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240.paint2win.controller.managers.AudioManager;
import com.tdt4240.paint2win.controller.states.GameStateManager;
import com.tdt4240.paint2win.controller.states.MainMenu;
import com.badlogic.gdx.Gdx;
import com.tdt4240.paint2win.controller.managers.SoundAssets;

public class Paint2Win extends Game {
	public static final int MAP_WIDTH = 3200;
	public static final int MAP_HEIGHT = 2400;
	public static final int MENU_WIDTH = 800;
	public static final int MENU_HEIGHT = 480;
	public static final AudioManager AUDIO_MANAGER = AudioManager.getAudioManager();
	public static final int TARGETS_TO_HIT = 20;
	public SoundAssets assets;

	private GameStateManager gsm;
	private SpriteBatch sb;

	/**
	 * Creates a spriteBatch, gameStateManager and sets
	 * MainMenu as the current screen
	 */
	@Override
	public void create () {
		assets = new SoundAssets();
		assets.load(); //starts loading assets
		while(!assets.assetManager.update()) {
			float progress = assets.assetManager.getProgress();
			System.out.println("Loading ... " + progress * 100 + "%");
		}

		sb = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MainMenu(gsm));
	}

	/**
	 * Updates the delta time and rerenders the GameStateManager
	 */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}

}
