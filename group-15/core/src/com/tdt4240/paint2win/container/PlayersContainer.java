package com.tdt4240.paint2win.container;

import com.tdt4240.paint2win.model.Bullet;
import com.tdt4240.paint2win.model.Player;
import com.tdt4240.paint2win.model.Shooter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PlayersContainer implements IContainer<Player> {
    private final List<Player> players;

    /**
     * List with players in the map, implements IContainer
     * Used to perform actions on all players
     */
    public PlayersContainer() {
        this(new ArrayList<>());
    }

    /**
     * List with players in the map, implements IContainer
     * Used to perform actions on all players
     * @param players List of players
     */
    public PlayersContainer(List<Player> players) {
        this.players = players;
    }

    @Override
    public void add(Player toAdd) {
        players.add(toAdd);
    }

    @Override
    public List<Player> getAll() {
        return players;
    }

    /**
     * Updates all the current players
     * @param delta delta time used to make updating synchronized
     */
    @Override
    public void update(float delta) {
        players.forEach(player -> player.update());
    }

    /**
     * Returns a stream
     * @return Stream<Shooter>
     */
    public Stream<Shooter> streamShooters() {
        return stream().map(Player::getShooter).filter(Optional::isPresent).map(Optional::get);
    }

    /**
     * Returns a stream
     * @return Stream<Bullet>
     */
    public Stream<Bullet> obtainAndStreamBullets() {
        return streamShooters().map(Shooter::obtainBullet).filter(Optional::isPresent).map(Optional::get);
    }

    public void dispose(){
        players.forEach(player -> player.dispose());
    }
}
