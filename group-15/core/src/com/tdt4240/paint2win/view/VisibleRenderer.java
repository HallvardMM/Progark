package com.tdt4240.paint2win.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240.paint2win.model.IVisible;

public class VisibleRenderer implements IRenderer{
    private final IVisible visible;

    /**
     * Render every object/class that implements IVisible,
     * as we can directly obtain all the information needed to render it directly from IVisible itself.
     * @param visible
     */
    public VisibleRenderer(IVisible visible) {
        this.visible = visible;
    }

    /**
     * Draws all visible objects on spritebatch
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        visible.getSprite().draw(sb);
    }
}
