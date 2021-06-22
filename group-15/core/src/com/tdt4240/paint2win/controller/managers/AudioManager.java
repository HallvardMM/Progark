package com.tdt4240.paint2win.controller.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private static final AudioManager INSTANCE = new AudioManager();
    private static Music playingMusic;
    private static Sound playingSound;
    private static float music_volume = 0.1f;
    private static float sfx_volume = 0.1f;

    // singleton: prevent instantiation from other classes
    private AudioManager() {
    }


    public static AudioManager getAudioManager() {
        return INSTANCE;
    }

    /**
     * Plays a sfx sound
     * @param sound name of the sfx sound
     */
    public void playSound (Sound sound) {
        playingSound = sound;
        playingSound.play(sfx_volume);
    }

    /**
     * Starts playing a song
     * @param song name of the mp3 song
     */
    public void playMusic (Music song) {
        if(playingMusic != null){
            playingMusic.stop();
        }
        playingMusic = song;
        playingMusic.setLooping(true);
        playingMusic.setVolume(music_volume);
        playingMusic.play();
    }

    /**
     * Stops the current sfx sound
     */
    public void stopSfx(){
        if(playingSound != null){
            playingSound.stop();
        }
    }

    /**
     * Stops the current song
     */
    public void stopMusic(){
        if(playingMusic != null){
            playingMusic.stop();
        }
    }

    /**
     * Returns the music volume
     * @return float - music volume
     */
    public static float getMusic_volume() {
        return music_volume;
    }

    /**
     * Updates the music volume with float between 0 and 1
     * @param volume - float new volume
     */
    public static void setMusic_volume(float volume) {
        music_volume = volume;
        if(playingMusic != null){
            playingMusic.setVolume(music_volume);
        }
    }

    /**
     * Returns the sfx volume
     * @return float - sfx volume
     */
    public static float getSfx_volume() {
        return sfx_volume;
    }

    /**
     * Updates the sfx volume with float between 0 and 1
     * @param volume - float new volume
     */
    public static void setSfx_volume(float volume, Sound sound) {
        sfx_volume = volume;
        if(playingSound != null) {
            playingSound.stop();
        }
        playingSound = sound;
        playingSound.play(sfx_volume);
    }

}
