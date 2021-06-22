package com.tdt4240.paint2win.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Obstacle implements IVisible {

    private final Vector2 position;
    private final Texture texture;
    private final Sprite sprite;
    private final Polygon shape;

    /**
     * Creates an Obstacle to display in the map
     * @param position Vector2 with position to the object
     * @param texture to display in location
     */
    public Obstacle(Vector2 position, Texture texture){
        this.position = position;
        this.texture = texture;
        this.sprite = new Sprite(texture);
        sprite.setPosition(position.x, position.y);
        this.shape = new Polygon(new float[] {
                0, 0,
                sprite.getWidth(),0,
                sprite.getWidth(), sprite.getHeight(),
                0, sprite.getHeight()
        });
        shape.setPosition(position.x, position.y);
    }

    /**
     * Returns the obstacle sprite
     * @return
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Returns the obstacles position
     * @return
     */
    @Override
    public Vector2 getPos() {
        return position;
    }

    /**
     * Returns the obsacles width and heigt
     * @return Vector2(width, height)
     */
    @Override
    public Vector2 getOriginalMeasures() {
        return new Vector2(sprite.getWidth(), sprite.getHeight());
    }

    /**
     * Disposes the obstacles texture
     */
    public void dispose(){
        texture.dispose();
    }
}
