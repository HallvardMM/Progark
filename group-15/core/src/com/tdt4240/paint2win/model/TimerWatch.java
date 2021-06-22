package com.tdt4240.paint2win.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class TimerWatch {

    private long startTime;
    private long stopTime;
    private boolean running;

    public TimerWatch (){
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }

    /**
     * Returns a string format of the elapsed time
     * @param elapsedTime elapsed time in milliseconds
     * @return String on the form: min + ":" + sec + ":" + milli
     */
    public static final String fancyString(int elapsedTime) {
        int millisec =  elapsedTime % 1000;
        int seconds = (elapsedTime / 1000) % 60 ;
        int minutes = ((elapsedTime / (1000*60)) % 60);
        String milli = millisec + "";
        String sec = seconds + "";
        String min = minutes + "";
        if (millisec<100) {
            milli = "0" + milli;
        }
        if (millisec<10) {
            milli = "0" + milli;
        }
        if (seconds<10) {
            sec = "0" + sec;
        }
        if (minutes<10) {
            min = "0" + min;
        }
        return min + ":" + sec + ":" + milli;
    }

    /**
     * Starts the timer clock
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    /**
     * Returns the elapsed time in milliseconds
     * @return (Int) elapsed time
     */
    public int getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return (int)elapsed;
    }

    /**
     * Renders the timer text
     * @param position placement on screen
     * @param sb spritebatch
     * @param textRenderer textrenderer
     */
    public void render(Vector2 position, Batch sb, BitmapFont textRenderer) {
        textRenderer.draw(sb, "Time: " +fancyString(this.getElapsedTime()), position.x, position.y);
    }
}