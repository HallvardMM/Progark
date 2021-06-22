package com.tdt4240.paint2win.controller.controls;

import com.badlogic.gdx.math.Vector2;

/**Observer interfaced used for DualJoysticks observing
 * @author JLK
 */
public interface IJoystickObserver {
    /**
     * @param position in DualJoysticks for position vector
     * @param rotation in DualJoysticks for rotation
     * @param isUsed in DualJoysticks for isUsed
     */
    void UpdateEvent(Vector2 position, float rotation, boolean isUsed);
}
