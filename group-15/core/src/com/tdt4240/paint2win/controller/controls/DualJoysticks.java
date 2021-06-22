package com.tdt4240.paint2win.controller.controls;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents both joysticks on the screen
 * @author JLK
 */
public class DualJoysticks {

    private Joystick PosJoystick;
    private Joystick RotJoystick;

    /**
     * List of observers that observes both joysticks
     */
    private List<IJoystickObserver> observers = new ArrayList<>();

    public DualJoysticks(Stage stage){
        PosJoystick = new Joystick(stage);
        RotJoystick = new Joystick(stage);
        Skin skin = new Skin();
        skin.add("touchBackground", new Texture("knob_background.png"));
        skin.add("touchKnob", new Texture("shoot_knob.png"));
        RotJoystick.setTouchpadStyle(skin);
        RotJoystick.setBounds(stage.getWidth()-600, 450, 500, 500);
    }

    /**
     * Render method that need to be run every frame
     */
    public void render(){
        PosJoystick.render();
        RotJoystick.render();
        notifyObservers(PosJoystick.getJoystickCoordinates(),RotJoystick.getDegrees(), RotJoystick.isUsed());
    }


    /**
     * Sends info to observers of DualJoysticks
     * @param position sends PosJoysticks position with getJoystickCoordinates()
     * @param rotation send RotJoysticks rotation with getDegrees()
     * @param usingRotJoystick send a boolean true if the RotJoystick is beeing used
     */
    private void notifyObservers(Vector2 position, float rotation, boolean usingRotJoystick) {
        for (IJoystickObserver observer: this.observers){
            observer.UpdateEvent(position, rotation, usingRotJoystick);
        }
    }

    /**
     * Add a observer to observer list of this class
     * @param observer needs to be a observable object of IObserver
     */
    public void addObserver(IJoystickObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes the observer from List
     * should be done in desctructors to avoid index errors
     * @param observer needs to be a observable object of IObserver
     */
    public void removeObserver(IJoystickObserver observer) {
        this.observers.remove(observer);
    }
}
