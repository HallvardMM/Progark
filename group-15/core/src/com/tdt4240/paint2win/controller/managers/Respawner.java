package com.tdt4240.paint2win.controller.managers;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.container.ObstacleContainer;
import com.tdt4240.paint2win.container.PlayersContainer;
import com.tdt4240.paint2win.model.Obstacle;
import com.tdt4240.paint2win.model.Shooter;

import java.util.Optional;
import java.util.Random;


public class Respawner {
    private static final Random random = new Random();
    private final PlayersContainer playersContainer;
    private final ObstacleContainer obstacleContainer;

    /**
     * Creates a Respawner which respawns new shooters
     * when player loses its shooter
     * @param playersContainer Container with in game players
     */
    public Respawner(PlayersContainer playersContainer, ObstacleContainer obstacleContainer) {
        this.playersContainer = playersContainer;
        this.obstacleContainer = obstacleContainer;
    }

    /**
     * Sets new respawn location for players without a shooter
     */
    public void respawn() {
        playersContainer.stream().filter(player -> !player.getShooter().isPresent()).forEach(player -> {
            player.setShooter(new Shooter(player, randomRespawnPoint(obstacleContainer), 0));
        });
    }

    /**
     * Returns a random respawn location which doesn't collide with obstacles
     * @param obstacleContainer container with all the obstacles on the map
     * @return random Vector2 from respawn points
     */
    private Vector2 randomRespawnPoint(ObstacleContainer obstacleContainer) {
        Circle tempCircle = new Circle(0,0, (float) Math.sqrt(Math.pow(Shooter.SHOOTERWIDTH, 2) + Math.pow(Shooter.SHOOTERHEIGHT, 2)));
        while(true) {
            Vector2 randomPos = new Vector2(random.nextInt((int)Math.floor(Paint2Win.MAP_WIDTH-Shooter.SHOOTERWIDTH)),
                    random.nextInt((int) Math.floor(Paint2Win.MAP_HEIGHT-Shooter.SHOOTERHEIGHT)));
            tempCircle.setPosition(randomPos.add(Shooter.MIDDLE));
            Optional<Obstacle> collidingObstacle = obstacleContainer.stream()
                    .filter(obstacle -> Intersector.overlaps(tempCircle,obstacle.getSprite().getBoundingRectangle()))
                    .findAny();
            if (!collidingObstacle.isPresent()) {
                //Returns position of where the lower left corner of the player-texture should be. Not the center of the circle
                Vector2 lowerLeftCornerPos = new Vector2(randomPos.x - Shooter.SHOOTERWIDTH/2, randomPos.y - Shooter.SHOOTERHEIGHT/2);
                return lowerLeftCornerPos;
            }
        }
    }

    }
