package com.tdt4240.paint2win.model.maps;

import com.badlogic.gdx.graphics.Texture;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.model.IIdentifiable;

import java.util.UUID;

public abstract class AbstractMap implements IIdentifiable {

    private Texture backGround;
    private UUID uuid;
    protected ObstacleContainer obstacleContainer;
    public enum valid_maps {DESERT, URBAN }


    /**
     * Create a new map with a background image
     * @param backGround
     */
    public AbstractMap(Texture backGround) {
        this.backGround = backGround;
        this.uuid = UUID.randomUUID();
        this.obstacleContainer = new ObstacleContainer();
    }

    /**
     * Returns background image
     * @return
     */
    public Texture getBackGround() {
        return backGround;
    }

    /**
     * Disposes the background
     */
    public void dispose(){
        backGround.dispose();
        obstacleContainer.dispose();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public ObstacleContainer getObstacleContainer(){
        return this.obstacleContainer;
    }

    public abstract String toString();
}
