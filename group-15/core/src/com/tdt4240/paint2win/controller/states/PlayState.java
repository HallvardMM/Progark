package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.container.BulletsContainer;
import com.tdt4240.paint2win.container.PlayersContainer;
import com.tdt4240.paint2win.controller.controls.DualJoysticks;
import com.tdt4240.paint2win.controller.managers.Collider;
import com.tdt4240.paint2win.controller.managers.Respawner;
import com.tdt4240.paint2win.controller.managers.SoundAssets;
import com.tdt4240.paint2win.model.Bullet;
import com.tdt4240.paint2win.model.GameMap;
import com.tdt4240.paint2win.model.Target;
import com.tdt4240.paint2win.model.TimerWatch;
import com.tdt4240.paint2win.model.maps.AbstractMap;
import com.tdt4240.paint2win.model.Obstacle;
import com.tdt4240.paint2win.model.Player;
import com.tdt4240.paint2win.model.Scoreboard;
import com.tdt4240.paint2win.model.maps.desert_map;
import com.tdt4240.paint2win.model.maps.urban_map;
import com.tdt4240.paint2win.networking.DataTransfer;
import com.tdt4240.paint2win.networking.Dto.Placement;
import com.tdt4240.paint2win.view.ContainerRenderer;
import com.tdt4240.paint2win.view.PlayerCamera;
import com.tdt4240.paint2win.view.PlayerRenderer;
import com.tdt4240.paint2win.view.VisibleRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.io.IOException;


import static com.tdt4240.paint2win.Paint2Win.MAP_WIDTH;
import static com.tdt4240.paint2win.Paint2Win.MAP_HEIGHT;

public class PlayState extends State {

    private final Viewport viewport;
    private final GameMap gameMap;
    private final PlayersContainer playersContainer;
    private final BulletsContainer bulletsContainer;
    private final Respawner respawner;
    private final Collider collider;
    private final ContainerRenderer<Player> playersRenderer;
    private final ContainerRenderer<Bullet> bulletsRenderer;
    private final ContainerRenderer<Obstacle> obstacleRenderer;
    private final PlayerCamera playerCamera;
    private final Player mainplayer;
    private final Stage stage;
    private final DualJoysticks joysticks;
    private AbstractMap map;
    private final Scoreboard scoreboard;
    private final BitmapFont bitmapFont;
    private final Target target;
    private final TimerWatch timerWatch;
    private final String username;

    /**
     * Constructor, includes all logic that only has to get rendered once
     * @param gsm
     */
    public PlayState(GameStateManager gsm, AbstractMap.valid_maps mapType, String username, Color playerColor) {
        super(gsm);
        this.username = username;
        playerCamera = new PlayerCamera();
        viewport = new StretchViewport(MAP_WIDTH, MAP_HEIGHT);
        gameMap = new GameMap(MAP_WIDTH, MAP_HEIGHT);
        bulletsContainer = new BulletsContainer();
        playersContainer = new PlayersContainer();
        chooseMap(mapType);
        mainplayer = new Player(playerColor, map.getObstacleContainer());
        playersContainer.add(mainplayer);
        respawner = new Respawner(playersContainer, map.getObstacleContainer());
        stage = new Stage(viewport);
        scoreboard = new Scoreboard(Paint2Win.TARGETS_TO_HIT);
        timerWatch = new TimerWatch();
        target = new Target(map.getObstacleContainer(), scoreboard);
        collider = new Collider(playersContainer, bulletsContainer, map.getObstacleContainer(), target);
        playersRenderer = new ContainerRenderer<Player>(playersContainer, PlayerRenderer::new);
        bulletsRenderer = new ContainerRenderer<Bullet>(bulletsContainer, VisibleRenderer::new);
        obstacleRenderer = new ContainerRenderer<Obstacle>(map.getObstacleContainer(), VisibleRenderer::new);
        joysticks = new DualJoysticks(stage);
        this.joysticks.addObserver(mainplayer);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Medium.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 120;
        this.bitmapFont = generator.generateFont(parameter); // font size 120 pixels
        generator.dispose(); // Dispose to avoid memory leaks!
        this.bitmapFont.setColor(mainplayer.getColor());
        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.run_free));
        timerWatch.start();
    }

    /**
     * Updates the local variable map based on the map that was clicked
     * @param chosenMap value of the clicked map
     */
    private void chooseMap(AbstractMap.valid_maps chosenMap) {
        if (chosenMap == AbstractMap.valid_maps.DESERT) {
            map = new desert_map();
        }
        else if (chosenMap == AbstractMap.valid_maps.URBAN) {
            map = new urban_map();
        }
    }

    /**
     * Updates the variables before the render function is called
     * @param delta
     */
    @Override
    public void update(float delta) {
        respawner.respawn();
        collider.checkCollisions();
        playersContainer.update(delta);
        playersContainer.streamShooters().forEach(gameMap::ensurePlacementWithinBounds);
        playersContainer.obtainAndStreamBullets().forEach(bulletsContainer::add);
        bulletsContainer.update(delta);
        map.getObstacleContainer().update(delta);
        map.getObstacleContainer().stream().forEach(gameMap::ensurePlacementWithinBounds);
    }

    /**
     * Draws the map and objects again with updated values
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Drawing with the passed common sprite batch. Used to draw map, player and bullets
        sb.begin();
        sb.setProjectionMatrix(playerCamera.updatedCamera(mainplayer.getPos().x, mainplayer.getPos().y).combined);
        sb.setColor(Color.WHITE);
        sb.draw(map.getBackGround(), 0,0,MAP_WIDTH,MAP_HEIGHT);
        playersRenderer.render(sb);
        bulletsRenderer.render(sb);
        obstacleRenderer.render(sb);
        target.draw(sb);
        sb.end();
        joysticks.render();
        stage.getBatch().begin();
        scoreboard.render(new Vector2 (0,stage.getHeight()),stage.getBatch(), bitmapFont);
        timerWatch.render(new Vector2(stage.getWidth()/1.5f,stage.getHeight()), stage.getBatch(), bitmapFont);
        stage.getBatch().end();

        if(scoreboard.isGoalReached()){
            gsm.set(new PassingData(gsm,username,timerWatch.getElapsedTime(),this.map));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
        playerCamera.resize(width, height);
        stage.setViewport(viewport);
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Play State");
        stage.dispose();
        map.dispose();
        bitmapFont.dispose();
        bulletsContainer.dispose();
        playersContainer.dispose();
        target.dispose();
    }

}