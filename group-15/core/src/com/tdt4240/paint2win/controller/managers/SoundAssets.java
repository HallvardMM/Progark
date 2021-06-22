package com.tdt4240.paint2win.controller.managers;

import com.badlogic.gdx.assets.AssetDescriptor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundAssets implements Disposable {
    public AssetManager assetManager;

    public static final AssetDescriptor<Sound> failSound = new AssetDescriptor<Sound>("failSound.mp3", Sound.class);
    public static final AssetDescriptor<Sound> target_hit = new AssetDescriptor<Sound>("target_hit.mp3", Sound.class);
    public static final AssetDescriptor<Music> menu_soundtrack_1 = new AssetDescriptor<Music>("menu_soundtrack.mp3", Music.class);
    public static final AssetDescriptor<Music> menu_soundtrack_2 = new AssetDescriptor<Music>("menu_soundtrack_2.mp3", Music.class);
    public static final AssetDescriptor<Sound> paintball_sound = new AssetDescriptor<Sound>("paintball_sound.mp3", Sound.class);
    public static final AssetDescriptor<Music> run_free = new AssetDescriptor<Music>("run_free.mp3", Music.class);
    public static final AssetDescriptor<Sound> winSound = new AssetDescriptor<Sound>("winSound.mp3", Sound.class);

    public SoundAssets(){
        assetManager=new AssetManager();
    }

    /**
     * Loads all sound and music files to the assets manager
     */
    public void load() {
        assetManager.load(failSound);
        assetManager.load(target_hit);
        assetManager.load(menu_soundtrack_1);
        assetManager.load(menu_soundtrack_2);
        assetManager.load(paintball_sound);
        assetManager.load(run_free);
        assetManager.load(winSound);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
