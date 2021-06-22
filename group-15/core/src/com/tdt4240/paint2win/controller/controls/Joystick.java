package com.tdt4240.paint2win.controller.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/** represents a joystick
 *  is not meant be used alone but in DualJoysticks class
 * @author JLK
 */
public class Joystick {
    private Stage stage;
    private Touchpad joystick;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Texture background;
    private Texture knob;
    private boolean isUsed;
    private float lastDegree;

    public Joystick(Stage stage){
        this.stage = stage;
        Gdx.input.setInputProcessor(stage);

        //Creates the textures used in the joystick (this is the default skin if nothing is specefied later)
        knob = new Texture("move_knob.png");
        //System.out.println("Width: " + knob.getWidth()+ " Height: "+knob.getHeight());
        background = new Texture("knob_background.png");

        //creates the skin that is turned into the style
        Skin skin = new Skin();
        skin.add("touchBackground", background);
        skin.add("touchKnob", knob);

        //creates the style wich is aplied to the joystick
        touchpadStyle = new Touchpad.TouchpadStyle(skin.getDrawable("touchBackground"), skin.getDrawable("touchKnob"));

        //creates the joystick object onscreen
        joystick = new Touchpad(10,touchpadStyle);

        //set position and size
        joystick.setBounds(150, 450, 500, 500);

        stage.addActor(joystick);
    }

    public void setBounds(float x, float y, float width,float height){joystick.setBounds(x,y,width,height);}


    /**
     * Sets the images used for the joystick
     * @param skin skin that has a knob and background texture
     */
    public void setTouchpadStyle(Skin skin){
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = skin.getDrawable("touchBackground");
        touchpadStyle.knob = skin.getDrawable("touchKnob");
        joystick.setStyle(touchpadStyle);
    }

    /**
     * The render method that needs to be run every frame
     */
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (joystick.getKnobPercentX()==0 && joystick.getKnobPercentY()==0){
            isUsed=false;
        }
        else { isUsed=true; }
    }

    /**
     * @return retuns angle the joystick is set to
     */
    public float getDegrees(){
        if (joystick.getKnobPercentX()!=0 && joystick.getKnobPercentY()!=0) {
            lastDegree=(float) ((Math.atan2(joystick.getKnobPercentY(), joystick.getKnobPercentX())) * (180 / Math.PI) - 90);
            return lastDegree;
        }
        return lastDegree;
    }

    /**
     * Joystick circle can be represented as a unit circle
     * @return coordinates th knob is set to on the unit circle
     */
    public Vector2 getJoystickCoordinates(){return  new Vector2(joystick.getKnobPercentX(),joystick.getKnobPercentY());}

    public boolean isUsed(){return isUsed;}





}
