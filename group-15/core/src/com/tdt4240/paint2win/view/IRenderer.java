package com.tdt4240.paint2win.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface IRenderer {

    /**
     * Should render objects to the spritebatch
     * @param sb
     */
    void render(SpriteBatch sb);
}
