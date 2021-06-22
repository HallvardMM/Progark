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

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class Tutorial extends State {

    private final Array<Texture> images;
    private int currentPicture;

    private final OrthographicCamera cam;
    private final Viewport viewport;

    private final Texture mainMenuButton;
    private final int mainMenuButtonXPosition;
    private final int mainMenuButtonYPosition;

    private final Texture rightButton;
    private final int rightButtonXPosition;
    private final int rightButtonYPosition;

    private final Texture leftButton;
    private final int leftButtonXPosition;
    private final int leftButtonYPosition;

    public Tutorial(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);

        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.menu_soundtrack_2));

        mainMenuButton = new Texture("main_menu.png");
        mainMenuButtonXPosition = MENU_WIDTH/2 - mainMenuButton.getWidth()/2;
        mainMenuButtonYPosition = MENU_HEIGHT/10;

        rightButton = new Texture("Right_Arrow.png");
        rightButtonXPosition = MENU_WIDTH - rightButton.getWidth();
        rightButtonYPosition = MENU_HEIGHT/10;

        leftButton = new Texture("Left_Arrow.png");
        leftButtonXPosition = 0 ;
        leftButtonYPosition = MENU_HEIGHT/10;

        this.currentPicture =0;
        this.images = new Array<Texture>();
        images.add(new Texture("page1.png"));
        images.add(new Texture("page2.png"));
        images.add(new Texture("page3.png"));
        images.add(new Texture("page4.png"));
    }

    private void checkIfPressedButton() {
        if(Gdx.input.justTouched()) {
            Vector3 tmp = new Vector3(new Vector2(Gdx.input.getX(), Gdx.input.getY()), 0);
            cam.unproject(tmp);
            Rectangle mainMenuBounds = new Rectangle(mainMenuButtonXPosition, mainMenuButtonYPosition, mainMenuButton.getWidth(), mainMenuButton.getHeight());
            Rectangle leftButtonBounds = new Rectangle(leftButtonXPosition, leftButtonYPosition, leftButton.getWidth(), leftButton.getHeight());
            Rectangle rightButtonBounds = new Rectangle(rightButtonXPosition, rightButtonYPosition, rightButton.getWidth(), rightButton.getHeight());

            if(mainMenuBounds.contains(tmp.x,tmp.y)) { toMainMenu(); }
            if(leftButtonBounds.contains(tmp.x,tmp.y)){prevImage();}
            if(rightButtonBounds.contains(tmp.x,tmp.y)){nextImage();}
        }
    }

    private void nextImage(){
        if(currentPicture == images.size - 1){
            currentPicture = 0;
            return;
        }
        currentPicture++;
    }

    private void prevImage() {
        if (currentPicture == 0) {
            currentPicture = images.size - 1;
            return;
        }
        currentPicture--;
    }

    /**
     * Function for returning to the Main Menu
     */
    private void toMainMenu(){
        gsm.set(new MainMenu(gsm));
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
     * Draws the screen and objects
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(images.get(currentPicture), 0,0, MENU_WIDTH, MENU_HEIGHT);
        sb.draw(mainMenuButton, mainMenuButtonXPosition, mainMenuButtonYPosition);
        sb.draw(leftButton, leftButtonXPosition, leftButtonYPosition);
        sb.draw(rightButton, rightButtonXPosition, rightButtonYPosition);
        sb.end();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Tutorial");
        mainMenuButton.dispose();
        rightButton.dispose();
        leftButton.dispose();
        images.forEach(Texture::dispose);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}

