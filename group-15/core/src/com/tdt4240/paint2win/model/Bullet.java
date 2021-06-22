package com.tdt4240.paint2win.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.controller.managers.SoundAssets;
import com.tdt4240.paint2win.utility.Vectors;

import java.util.UUID;

public class Bullet implements IVisible, IIdentifiable {
    private static final float SPEED = 1000f;
    private static final float RANGE = 1000f;

    private final Player player;
    private final Texture texture;
    private final Sprite sprite;
    private float remainingRange;
    private boolean hasHitSomething;
    private final UUID uuid;

    /**
     * Creates a new bullet object. The bullet has a direction and
     * contains which player that shot the bullet
     * @param player
     * @param startPosition
     * @param rotation
     */
    public Bullet(Player player, Vector2 startPosition,float rotation) {
        this.uuid = UUID.randomUUID();
        this.texture = new Texture("bullet.png");
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(startPosition.x, startPosition.y);
        this.sprite.setRotation(rotation);
        this.sprite.setOrigin(Shooter.WEAPON_X_DIRECTION_REPLACEMENT - Shooter.MIDDLE.x, -Shooter.MIDDLE.y);
        this.player = player;
        this.sprite.setColor(player.getColor());
        this.remainingRange = RANGE;
        Paint2Win.AUDIO_MANAGER.playSound(((Paint2Win) Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.paintball_sound));
    }

    /**
     * Returns the bullet sprite
     * @return
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Returns the current position of the bullet
     * @return
     */
    @Override
    public Vector2 getPos() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    /**
     * Returns the width and height of the bullet texture
     * @return
     */
    @Override
    public Vector2 getOriginalMeasures() {
        return new Vector2(sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Moves the bullet a fixed length in its current direction
     * @param delta
     */
    public void move(float delta) {
        Vector2 direction = Vectors.getDirectionVector(sprite.getRotation());
        Vector2 movement = new Vector2(direction.x * delta * SPEED, direction.y * delta * SPEED);
        remainingRange -= movement.len();
        sprite.translate(movement.x, movement.y);
    }

    /**
     * Returns false if bullet is out of its fixed range
     * @return
     */
    public boolean isInRange() {
        return remainingRange > 0;
    }

    /**
     * Updates hasHitSomething if bullet has hit an object
     */
    public void noticeHit() {
        hasHitSomething = true;
    }

    /**
     * Returns true if bullet has hit something
     * @return
     */
    public boolean hasHitSomething() {
        return hasHitSomething;
    }

    /**
     * Returns true if the bullet is outside of the map area
     * @return boolean
     */
    public boolean isOutOfMap(){         
        // Must use getBoundingRectangle since the position is not taking object rotation into account
        if (sprite.getBoundingRectangle().x < 0 || sprite.getBoundingRectangle().y < 0) return true;
        if (sprite.getBoundingRectangle().x + texture.getWidth() > Paint2Win.MAP_WIDTH) return true;
        if (sprite.getBoundingRectangle().y + texture.getHeight() > Paint2Win.MAP_HEIGHT) return true;
        return false;
    }

    /**
     * Disposes the bullet sound and texture
     */
    public void dispose(){
        texture.dispose();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public Player getPlayer(){return this.player;}
}
