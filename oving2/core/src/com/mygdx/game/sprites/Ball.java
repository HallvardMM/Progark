package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Oving1;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Ball {
    private Random rand;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private ShapeRenderer shapeRenderer;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private static volatile Ball instance;

    private final List<BallObserver> observers = new ArrayList<>();

    private void notifyObservers() {
        for (int i= 0; i<observers.size();i++){
            BallObserver observer = observers.get(i);
            observer.goalScored(position.x);
        }
    }

    public void addObserver(BallObserver observer) {
        observers.add(observer);
    }

    private Ball(){
        rand = new Random();
        shapeRenderer = new ShapeRenderer();
        centerPosition();
        bounds = new Rectangle(position.x,position.y,WIDTH,HEIGHT);
        setVelocity();
    }

    public static Ball getInstance() {
        //Threadsafe singelton
        Ball result = instance;
        if (result != null) {
            return result;
        }
        synchronized(Ball.class) {
            if (instance == null) {
                instance = new Ball();
            }
            return instance;
        }
    }

    public void setVelocity(){
        velocity = new Vector2(150*(rand.nextBoolean() ? 1 : -1),-rand.nextInt(100)*(rand.nextBoolean() ? 1 : -1));
    }

    public void centerPosition(){
        position = new Vector2(290, 150);
    }

    public void update(float dt){
        if(position.y<=0){
            velocity.y = -velocity.y;
        }
        else if((position.y+HEIGHT)>= Oving1.HEIGHT){
            velocity.y = -velocity.y;
        }

        velocity.scl(dt);
        position.add(velocity.x,velocity.y);
        if(position.y<0){
            position.y = 0;
        }
        else if((position.y+HEIGHT)>=Oving1.HEIGHT){
            position.y = Oving1.HEIGHT-HEIGHT;
        }
        if(position.x<0){
            position.x = 0;
        }
        else if((position.x+WIDTH)>=Oving1.WIDTH){
            position.x = Oving1.WIDTH-WIDTH;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y);
        if(position.x <= 0){
            notifyObservers();
        }
        else if(position.x+WIDTH>=Oving1.WIDTH){
            notifyObservers();
        }
    }

    public void switchVelocity(){
        velocity.x = -velocity.x*1.1F;
        velocity.y = velocity.y*1.1F;
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
}

