package com.tdt4240.paint2win.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tdt4240.paint2win.model.Player;
import com.tdt4240.paint2win.model.Shooter;

import java.util.Map;
import java.util.WeakHashMap;


public class PlayerRenderer implements IRenderer{
    private final Map<Shooter, IRenderer> cache;
    private final Player player;

    public PlayerRenderer(Player player, Map<Shooter, IRenderer> cache) {
        this.player = player;
        this.cache = cache;
    }

    /**
     * Logic for rendering player. Remember that a players shooter can change and at times be unavailable.
     * Needs a VisibleRenderer for current Shooter and renderers to hold on to previous shooters to be garbage collected.
     * WeakHashMap will discard entries dedicated for Shooters that do not exist anymore.
     * @param player
     */
    public PlayerRenderer(Player player) {
        this(player, new WeakHashMap<>());
    }

    /**
     * Renders all existing shooters to the spritebatch
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        player.getShooter().ifPresent(shooter -> cache.computeIfAbsent(shooter, VisibleRenderer::new).render(sb));
    }
}
