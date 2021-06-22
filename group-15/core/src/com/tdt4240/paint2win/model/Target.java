package com.tdt4240.paint2win.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.controller.managers.SoundAssets;

import java.util.Optional;
import java.util.Random;

public class Target implements IVisible {

    private static final Random random = new Random();
    public  static final int TARGETWIDTH = 155;
    public static final int TARGETHEIGHT = 155;
    public static final Vector2 MIDDLE = new Vector2(TARGETWIDTH/2, TARGETHEIGHT/2);
    private final Texture texture;
    private final Sprite sprite;
    private Vector2 position;
    private final ObstacleContainer obstacleContainer;
    private final Scoreboard scoreboard;

    public Target(ObstacleContainer obstacleContainer,
                  Scoreboard scoreboard){
        this.texture = new Texture("target.png");
        this.sprite = new Sprite(texture, TARGETWIDTH, TARGETHEIGHT);
        this.sprite.setOrigin(MIDDLE.x, MIDDLE.y);
        this.obstacleContainer = obstacleContainer;
        this.position = randomRespawnPoint(this.obstacleContainer);
        this.sprite.setPosition(this.position.x,this.position.y);
        this.scoreboard = scoreboard;
    }

    @Override
    public Sprite getSprite() {
        return this.sprite;
    }

    @Override
    public Vector2 getPos() {
        return this.position;
    }

    @Override
    public Vector2 getOriginalMeasures() {
        return new Vector2(TARGETWIDTH, TARGETHEIGHT);
    }

    /**
     * Sets the position of the target
     * @param position Vector2(x,y)
     */
    public void setPos(Vector2 position){
        this.position=position;
        this.sprite.setPosition(position.x,position.y);
    }

    /**
     * The target gets hit and is placed random
     */
    public void noticeHit() {
        this.setPos(randomRespawnPoint(obstacleContainer));
        this.scoreboard.increaseScore();
        Paint2Win.AUDIO_MANAGER.playSound(((Paint2Win) Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.target_hit));

    }

    /**
     * Generates a target the does not collide with any of the obstacles
     * @param obstacleContainer contains all obstacles on the map
     * @return Vector2(randomX, randomY)
     */
    private Vector2 randomRespawnPoint(ObstacleContainer obstacleContainer) {
        Rectangle tempRectangel = this.sprite.getBoundingRectangle();
        while(true) {
            Vector2 randomPos = new Vector2(random.nextInt((int)Math.floor(Paint2Win.MAP_WIDTH-TARGETWIDTH)),
                    random.nextInt((int) Math.floor(Paint2Win.MAP_HEIGHT-TARGETHEIGHT)));
            tempRectangel.setPosition(randomPos);
            Optional<Obstacle> collidingObstacle = obstacleContainer.stream()
                    .filter(obstacle -> Intersector.overlaps(tempRectangel,obstacle.getSprite().getBoundingRectangle()))
                    .findAny();
            if (!collidingObstacle.isPresent()) {
                return randomPos;
            }
        }
    }

    /**
     * Draws the target on the spritebatch
     * @param sb spritebatch
     */
    public void draw(SpriteBatch sb) {
        sb.draw(this.sprite.getTexture(),position.x,position.y);
    }

    /**
     * Disposes the used objects
     */
    public void dispose(){
        texture.dispose();
    }
}
