package com.tdt4240.paint2win.utility;

import com.badlogic.gdx.math.Vector2;

public class Vectors {
    /**
     * @param rotation
     * @return a Vector2 pointed in a direction according to the rotation
     */
    public static Vector2 getDirectionVector(float rotation) {
        return new Vector2(-(float)Math.sin(Math.toRadians(rotation)), (float)Math.cos(Math.toRadians(rotation)));
    }
}
