package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Oving1;

public class Player implements BallObserver{
    private Vector3 position;
    private Rectangle bounds;
    private ShapeRenderer shapeRenderer;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 100;

    public Player(int x, int y){
        shapeRenderer = new ShapeRenderer();
        position = new Vector3(x, y,0);
        bounds = new Rectangle(x,y,WIDTH,HEIGHT);
        }

    public void update(float dt){

        if(position.y<0){
            position.y = 0;
        }
        else if((position.y+HEIGHT)>=Oving1.HEIGHT){
            position.y = Oving1.HEIGHT-HEIGHT;
        }
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public void move(float y){
            position.y=y;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void draw(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.rect(this.position.x, this.position.y, WIDTH, HEIGHT);
        shapeRenderer.end();
    }

    public void dispose(){
        shapeRenderer.dispose();
    }

    @Override
    public void goalScored(float posx) {
        position.y = 180;
    }
}

