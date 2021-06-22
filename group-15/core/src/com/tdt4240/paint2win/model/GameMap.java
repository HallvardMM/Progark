package com.tdt4240.paint2win.model;

import com.badlogic.gdx.math.Rectangle;

public class GameMap {

    private final Rectangle bounds;

    /**
     * Class that generates a rectangle with the dimensions of the map to
     * check that no objects are placed outside of the map
     * @param width
     * @param height
     */
    public GameMap(int width, int height) {
        bounds = new Rectangle(0,0, width, height);
    }

    /**
     * If object tries to move outside of map it is moved the back onto the map
     * @param visible
     */
    public void ensurePlacementWithinBounds(IVisible visible) {
        float x = visible.getSprite().getX();
        float y = visible.getSprite().getY();

        //Ensure objects can't move outside bounderies
        if(x  < bounds.x) x = bounds.x;
        if(y  < bounds.y) y = bounds.y;
        if(x + visible.getOriginalMeasures().x > bounds.width) x = bounds.width - visible.getOriginalMeasures().x;
        if(y + visible.getOriginalMeasures().y > bounds.height) y = bounds.height - visible.getOriginalMeasures().y;
        visible.getSprite().setPosition(x,y);
    }

}
