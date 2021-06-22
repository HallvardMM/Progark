package com.tdt4240.paint2win.controller.managers;

import com.tdt4240.paint2win.container.BulletsContainer;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.container.PlayersContainer;
import com.tdt4240.paint2win.model.Target;

public class Collider {
    private final PlayersContainer playersContainer;
    private final BulletsContainer bulletsContainer;
    private final ObstacleContainer obstacleContainer;
    private final Target target;

    /**
     * Manager to check if bullet and players collide (player get shoot),
     * checks if bullet hits obstacle (removes bullet)
     * @param playersContainer Container with players on the map
     * @param bulletsContainer Container with bullets in the map
     * @param obstacleContainer Container with obstacles in the map
     */
    public Collider(PlayersContainer playersContainer, BulletsContainer bulletsContainer, ObstacleContainer obstacleContainer, Target target) {
        this.playersContainer = playersContainer;
        this.bulletsContainer = bulletsContainer;
        this.obstacleContainer = obstacleContainer;
        this.target = target;
    }

    /**
     * checks if bullet and players collide (player get shoot, removes bullet),
     * checks if bullet hits obstacle (removes bullet)
     */
    public void checkCollisions() {
        bulletsContainer.stream().filter(bullet -> bullet.collidesWith(target))
                .findFirst().ifPresent(bullet -> {target.noticeHit(); bullet.noticeHit();});
        bulletsContainer.stream().forEach(bullet -> obstacleContainer.stream()
                .filter(obstacle -> obstacle.collidesWith(bullet))
                .findFirst()
                .ifPresent(obstacle -> {bullet.noticeHit();}));
    }

}
