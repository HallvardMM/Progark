package com.tdt4240.paint2win.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

/**
 * Scoreboard counts the mount of times the Target gets hit
 */
public class Scoreboard {
    private int score;
    private final int maxScore;

    public Scoreboard(int maxScore) {
        this.score = 0;
        this.maxScore = maxScore;
    }

    /**
     * Render and update function need to ber run every frame
     * @param sb           spritebatch
     * @param textRenderer bitmapfont
     */
    public void render(Vector2 position, Batch sb, BitmapFont textRenderer) {
        textRenderer.draw(sb, "Targets hit: " + score + "/" + maxScore, position.x, position.y);
    }

    /**
     * Is the goal reached of this scoreboard
     * @return true if goal is reached
     */
    public boolean isGoalReached() {
        return score == maxScore;
    }

    /**
     * Adds one to the score
     */
    public void increaseScore() {
        this.score++;
    }
}