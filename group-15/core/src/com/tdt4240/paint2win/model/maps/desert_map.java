package com.tdt4240.paint2win.model.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.model.Obstacle;

public class desert_map extends AbstractMap {

    /**
     * Creates a map designed to look like a desert environment
     */
    public desert_map() {
        super(new Texture("sand_bg.png"));
        Texture texture_container = new Texture("dark_container.png");
        Texture texture_medic_car = new Texture("medic_car.png");
        Texture texture_sand_car = new Texture("sand_car.png");
        Texture texture_army_car = new Texture("army_car.png");
        Texture texture_rock = new Texture("Rock.png");
        obstacleContainer.add(new Obstacle(new Vector2(1300,1300), texture_container));
        obstacleContainer.add(new Obstacle(new Vector2(2000,700), texture_container));
        obstacleContainer.add(new Obstacle(new Vector2(400,750), texture_medic_car));
        obstacleContainer.add(new Obstacle(new Vector2(900,0), texture_sand_car));
        obstacleContainer.add(new Obstacle(new Vector2(2600,980), texture_army_car));
        obstacleContainer.add(new Obstacle(new Vector2(1440,2800), texture_rock));
        obstacleContainer.add(new Obstacle(new Vector2(2640,300), texture_rock));
        obstacleContainer.add(new Obstacle(new Vector2(450,1900), texture_rock));
    }

    @Override
    public String toString() {
        return "desert";
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}