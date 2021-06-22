package com.tdt4240.paint2win.model.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.model.Obstacle;

public class urban_map extends AbstractMap {

    /**
     * Creates a map designed to look like an urban environment
     */
    public urban_map() {
        super(new Texture("background.png"));
        Texture texture = new Texture("Crate.png");
        Texture texture_2 = new Texture("Iron_crate.png");
        Texture texture_3 = new Texture("Bush.png");
        Texture texture_4 = new Texture("Barrel.png");
        Texture texture_5 = new Texture("Radioactive_barrel.png");
        Texture texture_6 = new Texture("Container.png");
        Texture texture_7 = new Texture("Car.png");
        obstacleContainer.add(new Obstacle(new Vector2(2600,1900), texture));
        obstacleContainer.add(new Obstacle(new Vector2(1740,300), texture_2));
        obstacleContainer.add(new Obstacle(new Vector2(500,300), texture_3));
        obstacleContainer.add(new Obstacle(new Vector2(1440,2000), texture_4));
        obstacleContainer.add(new Obstacle(new Vector2(1540,2200), texture_5));
        obstacleContainer.add(new Obstacle(new Vector2(450,1000), texture_6));
        obstacleContainer.add(new Obstacle(new Vector2(2413,280), texture_7));
    }

    @Override
    public String toString() {
        return "urban";
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
