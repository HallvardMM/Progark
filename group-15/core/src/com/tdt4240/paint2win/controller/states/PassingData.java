package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.controller.managers.SoundAssets;
import com.tdt4240.paint2win.model.maps.AbstractMap;
import com.tdt4240.paint2win.networking.DataTransfer;
import com.tdt4240.paint2win.networking.Dto.Placement;

import java.io.IOException;

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class PassingData extends State {

    private Texture background;
    private OrthographicCamera cam;
    private Viewport viewport;
    private String username;
    private int time;
    private AbstractMap mapType;
    private boolean firstRun;
    private boolean secondRun;

    public PassingData(GameStateManager gsm, String username, int time, AbstractMap mapType) {
        super(gsm);
        this.username = username;
        this.time = time;
        this.mapType = mapType;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);
        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.menu_soundtrack_2));
        this.background = new Texture("FetchData.png");
        this.firstRun = true;
        this.secondRun = false;
    }

    /**
     * Updates the variables before the render function is called
     * @param dt
     */
    @Override
    public void update(float dt) {
        if(firstRun){
            //Used for drawing backround
            firstRun=false;
            secondRun=true;
        }
        else if(secondRun){
            //Used for making call to database
            secondRun = false;
            try{
                Placement placement = DataTransfer.SendTimeUsed(username,time,mapType);
                gsm.set(new FinishedGame(gsm,false,placement.getPlacement(), placement.getNewPr(), time));
            }
            catch (IOException e) {
                //Database didn't record the placement
                gsm.set(new FinishedGame(gsm,true, -1,false,time));
            }
        }
    }

    /**
     * Draws the screen and objects
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, MENU_WIDTH, MENU_HEIGHT);
        sb.end();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Tutorial");
        background.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
