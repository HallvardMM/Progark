package com.tdt4240.paint2win.networking.Dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HighScoreRow implements IDto {

    private final String playerName;
    private final int time;
    private final String mapName;

    /**
     * Data Transfer Object for posting time used
     * @param playerName name of the player
     * @param time time in millisec
     * @param mapName map
     */
    @JsonCreator
    public HighScoreRow(
            @JsonProperty("playerName") String playerName,
            @JsonProperty("time") int time ,
            @JsonProperty("mapName") String mapName) {
        this.playerName = playerName;
        this.time = time;
        this.mapName = mapName;
    }

    // GETTERS used in Dto by objectMapper.readValue
    public String getPlayerName() {
        return playerName;
    }

    public int getTime() {
        return time;
    }

    public String getMapName(){
        return mapName;
    }
}
