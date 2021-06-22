package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.controller.managers.SoundAssets;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class Settings extends State {
    private final OrthographicCamera cam;
    private final Viewport viewport;
    private final Texture header;

    private final Texture colorsImage;
    private final Texture mainMenuButton;
    private final int mainMenuButtonXPosition;
    private final int mainMenuButtonYPosition;

    private final Slider musicSlider;
    private final Slider sfxSlider;
    private final Stage stage;

    /**
     * Constructor, includes all logic that only has to get rendered once.
     * The settings page let's the user control music volume and sfx volume
     * @param gsm
     */
    public Settings(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);
        colorsImage = new Texture("colors.jpg");
        header = new Texture("settings_header_X6.png");

        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.menu_soundtrack_2));

        mainMenuButton = new Texture("main_menu.png");
        mainMenuButtonXPosition = MENU_WIDTH /2 - mainMenuButton.getWidth()/2;
        mainMenuButtonYPosition = MENU_HEIGHT /8;

        stage = new Stage(viewport);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table musicTable = new Table();
        musicSlider = new Slider(0f,1f,0.1f,false, skin);
        musicSlider.setValue(Paint2Win.AUDIO_MANAGER.getMusic_volume());
        Label musicLabel = new Label("Music volume: " + roundDecimal(musicSlider.getValue()), skin);
        musicLabel.setColor(Color.BLACK);
        musicLabel.setAlignment(Align.center);
        musicTable.add(musicLabel);
        musicTable.row();
        musicTable.add(musicSlider);
        musicTable.setPosition(MENU_WIDTH /4 - musicSlider.getWidth()/2, -MENU_HEIGHT /6);
        musicTable.setFillParent(true);

        Table sfxTable = new Table();
        sfxSlider = new Slider(0f,1f,0.1f,false, skin);
        sfxSlider.setValue(Paint2Win.AUDIO_MANAGER.getSfx_volume());
        Label sfxLabel = new Label("Sfx volume: " + roundDecimal(sfxSlider.getValue()), skin);
        sfxLabel.setColor(Color.BLACK);
        sfxLabel.setAlignment(Align.center);
        sfxTable.add(sfxLabel);
        sfxTable.row();
        sfxTable.add(sfxSlider);
        sfxTable.setPosition(-MENU_WIDTH /4 + sfxSlider.getWidth()/2, -MENU_HEIGHT /6);
        sfxTable.setFillParent(true);

        stage.addActor(musicTable);
        stage.addActor(sfxTable);
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                float roundedDecimal = roundDecimal(musicSlider.getValue());
                musicLabel.setText("Music volume: " + roundedDecimal);
                Paint2Win.AUDIO_MANAGER.setMusic_volume(roundedDecimal);
            }
        });
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                float roundedDecimal = roundDecimal(sfxSlider.getValue());
                sfxLabel.setText("Sfx volume: " + roundedDecimal);
                Paint2Win.AUDIO_MANAGER.setSfx_volume(roundedDecimal, ((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.paintball_sound));
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Function to fix problem with too many decimals shows
     * @param number
     * @return
     */
    private float roundDecimal(double number) {
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    /**
     * Function for returning to the Main Menu
     */
    private void toMainMenu(){
        gsm.set(new MainMenu(gsm));
    }

    /**
     * Function for checking if one of the buttons have been pressed.
     * What happens is that when the screen is touched it checks if the area pressed is
     * inside the rectangle of one of the button, and fires the function assosiated with that button
     * if that is the case.
     */
    private void checkIfPressedButton() {
        if(Gdx.input.justTouched()) {
            Vector3 tmp = new Vector3(new Vector2(Gdx.input.getX(), Gdx.input.getY()), 0);
            cam.unproject(tmp);
            Rectangle mainMenuBounds = new Rectangle(mainMenuButtonXPosition, mainMenuButtonYPosition, mainMenuButton.getWidth(), mainMenuButton.getHeight());

            if(mainMenuBounds.contains(tmp.x,tmp.y)) { toMainMenu(); }
        }
    }

    /**
     * Updates the variables before the render function is called
     * @param dt
     */
    @Override
    public void update(float dt) {
        checkIfPressedButton();
    }

    /**
     * Draws the screen and objects again
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(colorsImage, 0,0, MENU_WIDTH, MENU_HEIGHT);
        sb.draw(header, MENU_WIDTH/2 - header.getWidth()/2,2*MENU_HEIGHT/4);
        sb.draw(mainMenuButton, mainMenuButtonXPosition, mainMenuButtonYPosition);
        sb.end();
        stage.draw();
        stage.act();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Settings");
        mainMenuButton.dispose();
        colorsImage.dispose();
        header.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
