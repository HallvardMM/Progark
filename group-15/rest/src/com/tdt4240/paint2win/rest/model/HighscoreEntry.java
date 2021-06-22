package com.tdt4240.paint2win.rest.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tdt4240.paint2win.rest.utils.MapName;


@Entity
@Table(name = "highscores")
public class HighscoreEntry {
    private String playerName;
    private int time;
    private String mapName;

    public HighscoreEntry(){

    }

    public HighscoreEntry(String playerName, int time, String mapName){
        this.playerName = playerName;
        this.time = time;
        this.mapName = mapName;
    }

    public HighscoreEntry(String playerName, int time, MapName mapName) {
        this(playerName, time, mapName.toString());
    }

    @Id
    public String getPlayerName() {
        return playerName;
    }

    public int getTime() {
        return time;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setMapName(MapName mapName) {
        this.mapName = mapName.toString();
    }
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
