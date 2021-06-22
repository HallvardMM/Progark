package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Oving1;

import java.util.Random;

public class Chopper {
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation chopperAnimation;
    private Texture texture;
    private TextureRegion textureRegion;
    private Random rand;
    private Boolean flipp;

    public Chopper(int x, int y){
        position = new Vector3(x, y,0);
        rand = new Random();
        velocity = new Vector3(0,0,0);
        texture = new Texture("attackhelicopter.png");
        textureRegion = new TextureRegion(texture);
        chopperAnimation = new Animation(textureRegion,1,1f);
        bounds = new Rectangle(x,y,texture.getWidth()/1,texture.getHeight());
        flipp = true;
    }

    public void update(float dt){
        chopperAnimation.update(dt);
        velocity.scl(dt);
        position.add(velocity.x,velocity.y,0);
        if(position.y<0){
            position.y = 0;
        }
        else if((position.y+texture.getHeight())>=Oving1.HEIGHT){
            position.y = Oving1.HEIGHT-texture.getHeight();
        }
        if(position.x<0){
            position.x = 0;
        }
        else if((position.x+texture.getWidth())>=Oving1.WIDTH){
            position.x = Oving1.WIDTH-texture.getWidth();
        }
        if (Gdx.input.getX()>position.x){
            if (!flipp){
                toggleFlipp();
            }
        }
        else if (Gdx.input.getX()<position.x){
            if (flipp){
                toggleFlipp();
            }
        }

        velocity.scl(1.1F/dt);
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void toggleFlipp() {
        if(flipp){
            flipp = false;
        }
        else{
            flipp = true;
        }
    }

    public Boolean getFlipp(){
        return flipp;
    }

    public Texture getTexture() {
        return chopperAnimation.getFrame().getTexture();
    }

    public void move(int x, int y){
        position.x = x;
        position.y = y;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
    }
}
