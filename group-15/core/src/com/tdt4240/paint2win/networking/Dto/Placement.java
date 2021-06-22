package com.tdt4240.paint2win.networking.Dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Placement implements IDto {
    private final int placement;
    private final boolean newPr;

    /**
     * Data Transfer Object for posting time used
     * @param placement placement from 1-10 and where -1 is not on highscore board
     */
    @JsonCreator
    public Placement(
            @JsonProperty("placement") int placement,
            @JsonProperty("newPr") boolean newPr
    ) {
        this.placement = placement;
        this.newPr = newPr;
    }

    // GETTERS used in Dto by objectMapper.readValue
    public int getPlacement() {
        return placement;
    }
    public boolean getNewPr() {return newPr; }
}
