package com.tdt4240.paint2win.model;

import com.badlogic.gdx.graphics.Color;
import java.util.UUID;

/** Observer pattern used to observe the death of player
 * @author JLK
 */
public interface IDeathObserver {
    void UpdateEvent(UUID ID, boolean dead, Color Color);
    void UpdateEvent(UUID IdToGivePointsTo, Color Color);
}
