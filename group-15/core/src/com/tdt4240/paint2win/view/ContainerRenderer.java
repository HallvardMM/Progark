package com.tdt4240.paint2win.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tdt4240.paint2win.container.IContainer;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public class ContainerRenderer<T>  implements IRenderer{
    private final IContainer<T> container;
    private final Function<T, IRenderer> rendererFactory;
    private final Map<T, IRenderer> cache;

    public ContainerRenderer(IContainer<T> container,
                             Function<T, IRenderer> rendererFactory, Map<T, IRenderer> cache) {
        this.container = container;
        this.rendererFactory = rendererFactory;
        this.cache = cache;
    }

    /**
     * A generic renderer for the playerRenderer and visibleRenderer
     * @param container
     * @param rendererFactory
     */
    public ContainerRenderer(IContainer<T> container,
                             Function<T, IRenderer> rendererFactory) {
        this(container, rendererFactory, new WeakHashMap<>());
    }

    /**
     * Renders all container-objects if not already rendered
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        container.stream().forEach(thing -> cache
                    .computeIfAbsent(thing, rendererFactory)
                    .render(sb));
    }
}
