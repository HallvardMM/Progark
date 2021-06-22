package com.tdt4240.paint2win.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class Shooter implements IVisible, IIdentifiable {

    public  static final int SHOOTERWIDTH = 155;
    public static final int SHOOTERHEIGHT = 261;
    public static final int WEAPON_X_DIRECTION_REPLACEMENT = 40;
    public static final Vector2 MIDDLE = new Vector2(SHOOTERWIDTH/2, SHOOTERHEIGHT/2);
    private static final Vector2 BULLET_OUTPUT = new Vector2(SHOOTERWIDTH-WEAPON_X_DIRECTION_REPLACEMENT, SHOOTERHEIGHT);
    private static final Duration SHOT_INTERVAL = Duration.ofMillis(300);
    private final Player player;
    private final Texture texture;
    private final Sprite sprite;
    private Vector2 position;
    private float degrees;
    private Instant lastShot;
    private boolean canShoot;
    private boolean wantsToShoot;

    private List<IDeathObserver> observers = new ArrayList<>();

    /**
     * Creates a player controlled man with a gun to display on the map
     * A new shooter is created for each respawn
     * @param player the player that owns the shooter
     * @param startingPosition where the shooter respawns
     * @param startingRotation the rotation the shooter has when it respawns
     */
    public Shooter(Player player, Vector2 startingPosition, float startingRotation) {
        this.texture = new Texture("shooter.png");
        this.sprite = new Sprite(texture, SHOOTERWIDTH, SHOOTERHEIGHT);
        this.sprite.setOrigin(MIDDLE.x, MIDDLE.y);
        this.sprite.setPosition(startingPosition.x,startingPosition.y);
        this.sprite.rotate(startingRotation);
        this.sprite.setColor(player.getColor());
        this.player = player;
        position = new Vector2(0, 0);
        lastShot = Instant.EPOCH;
    }

    /**
     * Call when one wants to update movement and shooting.
     */
    public void update() {
        applyMovement();
        applyShootingPossibility();
    }

    /**
     * Creates new bullet if shooter canShoot and wantsToShoot
     * @return Bullet if it can and wants to shoot else returns empty
     */
    public Optional<Bullet> obtainBullet() {
        if (canShoot && wantsToShoot) {
            lastShot = Instant.now();
            return Optional.of(new Bullet(
                    player,
                    bulletStartingPosition(),
                    sprite.getRotation())
            );
        }
        return Optional.empty();
    }

    /**
     * @return shooters sprite
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Returns the width and height of the shooter when not rotated
     * @return Vector2(SHOOTERWIDTH, SHOOTERHEIGHT)
     */
    @Override
    public Vector2 getOriginalMeasures() {
        return new Vector2(SHOOTERWIDTH, SHOOTERHEIGHT);
    }

    /**
     * Returns the bottom left corner postion of the shooter
     * @return Vector2(x-value, y-value)
     */
    @Override
    public Vector2 getPos(){
        return new Vector2(sprite.getX(),sprite.getY());
    }

    /**
     * Creates a circle about the same size as the sprite which is used for collision detection
     * with obstacles. Circle is used so rotation won't effect detection.
     * @param x position in x-direction
     * @param y position in y-direciton
     * @return true if shooter collides with an obstacle
     */
    private boolean NextMoveCollides(float x, float y){
        Circle tempCircle = new Circle(MIDDLE, new Vector2(20, 20));
        //20 is used to make the circle a bit smaller then the player
        tempCircle.setPosition(getPos().add(MIDDLE).add(x,y));
        //centers the circle around the sprite and moves the circle to new x, y direction
        AtomicBoolean returnBoolean = new AtomicBoolean(false);
        player.getObstacleContainer().stream().filter(obstacle ->
                Intersector.overlaps(tempCircle,obstacle.getSprite().getBoundingRectangle())).findAny()
                        .ifPresent(obstacle -> returnBoolean.set(true));
        return returnBoolean.get();
    }

    /**
     * Moves the player if the new movement does not create a collision
     */
    private void applyMovement() {
        if(!NextMoveCollides(position.x*10,position.y*10)){
            sprite.translate(position.x*10,position.y*10);
            sprite.setRotation(degrees);
        }
    }

    /**
     * Check if it has passed enough time for the shooter to shoot again
     * Shot_interval is used to reduce or increase shooting speed
     */
    private void applyShootingPossibility() {
        canShoot = Instant.now().isAfter(lastShot.plus(SHOT_INTERVAL));
    }

    /**
     *  The bullet should leave the sprite from where the barrel of the gun is
     * @return Vector with start postion for bullet
     */
    private Vector2 bulletStartingPosition() {
        return new Vector2(sprite.getX(), sprite.getY()).add(BULLET_OUTPUT);
    }

    /**
     * Disposes all resources associated with the shooter
     */
    public void dispose(){
        texture.dispose();
    }

    /** sets to position for the object to be translated by
     * @param position left down in sprite
     */
    public void setPos(Vector2 position){
        this.position=position;
    }

    /**sets the rotation of the shooter
     * @param degrees rotation in the degrees
     */
    public void setRotation(float degrees){
        this.degrees=degrees;
    }
    /**sets if the shooter wants to shoot.
     * Is used when rotation joystick is moved
     * @param b true if shooter wants to shoot
     */
    public void setWantsToShoot(boolean b){this.wantsToShoot=b;}

    /**
     * Returns the players ID
     * @return UUID
     */
    @Override
    public UUID getId() {
        return player.getId();
    }

}
