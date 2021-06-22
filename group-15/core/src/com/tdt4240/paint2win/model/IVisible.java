package com.tdt4240.paint2win.model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public interface IVisible {
    /**
     * Returns the sprite to visible
     */
    Sprite getSprite();
    /**
     * Returns position to visible
     */
    Vector2 getPos();
    /**
     * Returns width and height since rotation effects this
     */
    Vector2 getOriginalMeasures();

    /**
     * Returns width and height since rotation effects this
     */
    static Polygon getRotatedPolygonFromSprite(Sprite sprite) {
        return new Polygon(new float[] {
                sprite.getVertices()[SpriteBatch.X1], sprite.getVertices()[SpriteBatch.Y1],
                sprite.getVertices()[SpriteBatch.X2], sprite.getVertices()[SpriteBatch.Y2],
                sprite.getVertices()[SpriteBatch.X3], sprite.getVertices()[SpriteBatch.Y3],
                sprite.getVertices()[SpriteBatch.X4], sprite.getVertices()[SpriteBatch.Y4]
        });
    }

    /**
     * Returns true if two IVisibles overlaps
     * @param anotherVisible class that implements IVisible
     * @return boolean if the shapes overlaps
     */
    default boolean collidesWith(IVisible anotherVisible) {
        Polygon p1 = getRotatedPolygonFromSprite(this.getSprite());
        Polygon p2 = getRotatedPolygonFromSprite(anotherVisible.getSprite());
        return Intersector.overlapConvexPolygons(p1, p2);
    }
}
