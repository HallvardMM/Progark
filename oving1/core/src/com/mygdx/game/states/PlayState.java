package com.mygdx.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Oving1;
import com.mygdx.game.sprites.Chopper;


public class PlayState extends State{

    private Chopper chopper;
    private int x;
    private int y;
    BitmapFont posDisplay;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        x=0;
        y=0;
        posDisplay = new BitmapFont();
        chopper = new Chopper(x,y);
        cam.setToOrtho(false, Oving1.WIDTH,Oving1.HEIGHT);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            chopper.move((int) (Gdx.input.getX()), 1600 - 4 * Gdx.input.getY());
            setX(Gdx.input.getX());
            if (Gdx.input.getY() >= 400) {
                setY(0);
            } else {
                setY(1600 - 4 * Gdx.input.getY());
            }
            if (Gdx.input.getDeltaX() < 0 && chopper.getFlipp()) {
                chopper.toggleFlipp();
            }
            if (Gdx.input.getDeltaX() > 0 && !chopper.getFlipp()) {
                chopper.toggleFlipp();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        chopper.update(dt);
        cam.position.x = chopper.getPosition().x + 80;

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        posDisplay.setColor(1F, 1F, 1F, 1F);
        posDisplay.getData().setScale(3);
        posDisplay.draw(sb, "x: " + getX() + " y: " + getY(), 20, 1560);
        //sb.draw(chopper.getTexture(),  chopper.getPosition().x,chopper.getPosition().y);
        sb.draw(chopper.getTexture(),
                chopper.getPosition().x,
                chopper.getPosition().y,
                chopper.getTexture().getWidth(),
                chopper.getTexture().getHeight(),
                0,
                0,
                chopper.getTexture().getWidth(),
                chopper.getTexture().getHeight(),
                chopper.getFlipp(),
                false);
        sb.end();
    }

    @Override
    public void dispose() {
        chopper.dispose();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

