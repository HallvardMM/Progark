package com.tdt4240.paint2win.container;

import com.tdt4240.paint2win.model.Bullet;

import java.util.ArrayList;
import java.util.List;

public class BulletsContainer implements IContainer<Bullet> {
    private final List<Bullet> bullets;

    /**
     * List of all bullets on the map, implements IContainer
     * Used to perform actions on all obstacles
     */
    public BulletsContainer() {
        this(new ArrayList<>());
    }

    /**
     * List of all bullets on the map, implements IContainer
     * Used to perform actions on all obstacles
     * @param bullets
     */
    public BulletsContainer(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    /**
     * Add a bullet to the list of bullets
     * @param bullet
     */
    @Override
    public void add(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Returns all the current bullets
     * @return list of bullets
     */
    @Override
    public List<Bullet> getAll() {
        return bullets;
    }

    /**
     * First checks if bullets should be removed. This is when the bullet is out of set range, out of map
     * or has hit something. If the bullet is not removed, it get's a new position
     * @param delta delta time used to make updating synchronized
     */
    @Override
    public void update(float delta) {
        bullets.removeIf(bullet -> !bullet.isInRange() || bullet.hasHitSomething() || bullet.isOutOfMap());
        bullets.forEach(bullet -> bullet.move(delta));
    }

    /**
     * Disposes all the bullets in the conatiner
     */
    public void dispose(){
        bullets.forEach(bullet -> bullet.dispose());
    }
}
