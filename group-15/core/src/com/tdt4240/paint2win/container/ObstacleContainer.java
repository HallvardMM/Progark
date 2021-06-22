package com.tdt4240.paint2win.container;

import com.tdt4240.paint2win.model.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class ObstacleContainer implements IContainer<Obstacle> {
    private final List<Obstacle> obstacles;

    /**
     * List with obstacles in the map, implements IContainer
     * Used to perform actions on all obstacles
     */
    public ObstacleContainer() {
        this(new ArrayList<>());
    }

    /**
     * List with obstacles in the map implements IContainer
     * Used to perform actions on all obstacles
     * @param obstacles List of obstacles
     */
    public ObstacleContainer(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    @Override
    public void add(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    @Override
    public List<Obstacle> getAll() {
        return obstacles;
    }

    /**
     * No reason to update the objects since they does not move
     * @param delta delta time used to make updating synchronized
     */
    @Override
    public void update(float delta) {
    }

    public void dispose(){
        obstacles.forEach(obstacle -> obstacle.dispose());
    }
}