package com.tdt4240.paint2win.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.controller.managers.SoundAssets;
import com.tdt4240.paint2win.controller.controls.IJoystickObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Player implements IJoystickObserver,IIdentifiable {
    private final Color color;
    private Optional<Shooter> shooter;
    private ObstacleContainer obstacleContainer;
    private final UUID uuid;

    private List<IDeathObserver> observers = new ArrayList<>();

    /**
     * Creates a player which should observe the joystick
     * @param color used to set color of bullets and player
     * @param obstacleContainer container with all obstacles on the map
     */
    public Player(Color color, ObstacleContainer obstacleContainer) {
        this.uuid = UUID.randomUUID();
        this.color = color;
        this.shooter = Optional.empty();
        this.obstacleContainer = obstacleContainer;
        notifyObservers(uuid,false, this.color);
    }

    /**
     * Creates a shooter which the player can control
     * @param shooter new shooter to control
     */
    public void setShooter(Shooter shooter) {
        this.shooter = Optional.of(shooter);
    }

    /**
     * The player gets hit and shooter is removed
     *
     */
    public void noticeHit(Player playerbullet) {
        if (playerbullet==this) return;
        this.shooter = Optional.empty();
        notifyObservers(playerbullet.getId(), this.color);
        Paint2Win.AUDIO_MANAGER.playSound(((Paint2Win) Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.target_hit));
    }

    /**
     * Updates the shooter if it exists
     */
    public void update() {
        shooter.ifPresent(shooter -> {
            shooter.update();
        });
    }

    /**
     * Gets the shooter
     * @return returns empty Optional instance or a shooter
     */
    public Optional<Shooter> getShooter() {
        return shooter;
    }

    /**
     * Gets the color which is used on the player and it's bullets
     * @return gdx.graphics.Color
     */
    public Color getColor() {
        return color;
    }
    /**
     * Gets the position of the shooter if it exists
     * else returns 0,0
     * @return Vector2 with the position when it exists
     */
    public Vector2 getPos(){
        if(!shooter.isPresent()){
            return new Vector2(0,0);
        }
        else{
            return shooter.get().getPos();
        }
    }

    /**
     * The obstacles of the map. Is used in shooters collision detection.
     * @return obstacleContainer with obstacles in the map.
     */
    public ObstacleContainer getObstacleContainer(){
        return obstacleContainer;
    }

    /**method called by observable class DualJoysticks every frame
     * @author JLK
     * @param position in DualJoysticks for position vector
     * @param rotation in DualJoysticks for rotation
     * @param isUsed is the RotJoystick used?
     */
    @Override
    public void UpdateEvent(Vector2 position, float rotation, boolean isUsed) {
        shooter.ifPresent(shooter -> {
            shooter.setPos(position);
            shooter.setRotation(rotation);
            shooter.setWantsToShoot(isUsed);
        });
    }



    @Override
    public UUID getId() {
        return uuid;
    }

    /**
     * Sends info to observers of IDeathObserver
     */
    private void notifyObservers(UUID ID, boolean dead, Color color) {
        for (IDeathObserver observer: this.observers){
            observer.UpdateEvent(ID, dead, color);
        }
    }

    /**
     * Sends info to observers of IDeathObserver
     */
    private void notifyObservers(UUID ID, Color color) {
        for (IDeathObserver observer: this.observers){
            observer.UpdateEvent(ID, color);
        }
    }



    /**
     * Add a observer to observer list of this class
     * @param observer needs to be a observable object of IDeathObserver
     */
    public void addObserver(IDeathObserver observer) {
        observers.add(observer);
        notifyObservers(uuid,false, color);
    }

    /**
     * Removes the observer from List
     * should be done in desctructors to avoid index errors
     * @param observer needs to be a observable object of IDeathObserver
     */
    public void removeObserver(IDeathObserver observer) {
        this.observers.remove(observer);
    }

    public void dispose(){
        shooter.ifPresent(Shooter::dispose);
    }

}
