package com.tdt4240.paint2win.view;

import com.badlogic.gdx.graphics.Camera;
import com.tdt4240.paint2win.Paint2Win;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerCamera {
    private final OrthographicCamera cam;

    /**
     * Creates a Camera
     */
    public PlayerCamera() {
        this.cam = new OrthographicCamera();
        cam.setToOrtho(false, Paint2Win.MAP_WIDTH/1.5f,Paint2Win.MAP_HEIGHT/1.5f);
    }

    /**
     * Updates the camera position and returns the updated camera
     * @param x x-position
     * @param y y-position
     * @return Camera
     */
    public Camera updatedCamera(float x, float y){
        cam.position.x = x;
        cam.position.y = y;
        cam.update();
        return cam;
    }

    /**
     * Resize when screen is resized
     * @param width width of screen
     * @param height height of screen
     */
    public void resize(int width, int height) {
        cam.viewportWidth=width;
        cam.viewportHeight=height;
        cam.update();
    }
}
