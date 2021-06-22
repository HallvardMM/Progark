package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.controller.managers.SoundAssets;
import com.tdt4240.paint2win.model.TimerWatch;

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class FinishedGame extends State {

    private OrthographicCamera cam;
    private final Texture backgroundImage;
    private final Texture mainMenuButton;
    private final int mainMenuButtonXPosition;
    private final int mainMenuButtonYPosition;
    private BitmapFont textRenderer;
    private final int timeUsed;
    private final int placement;
    private final boolean newPR;
    private final boolean postProblem;
    private final Viewport viewport;

    public FinishedGame(GameStateManager gsm, boolean postProblem,int placement, boolean newPR, int timeUsed) {
        super(gsm);
        this.textRenderer = new BitmapFont();
        this.timeUsed = timeUsed;
        this.newPR = newPR;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        textRenderer = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        textRenderer.setColor(Color.WHITE);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);
        backgroundImage = new Texture("WinOrLose.png");
        mainMenuButton = new Texture("main_menu.png");
        mainMenuButtonXPosition = MENU_WIDTH /2 - mainMenuButton.getWidth()/2;
        mainMenuButtonYPosition = MENU_HEIGHT /6;
        Paint2Win.AUDIO_MANAGER.stopMusic();
        this.placement = placement;
        this.postProblem = postProblem;
            
        if(postProblem || newPR || placement<=10){
            Paint2Win.AUDIO_MANAGER.playSound(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.winSound));
        }
        else {
            Paint2Win.AUDIO_MANAGER.playSound(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.failSound));
        }
    }

    /**
     * Helper function to center the highscore list
     * @param bitmapFont used for displaying text
     * @param value the time used
     * @return
     */
    private static final float getPositionOffset(BitmapFont bitmapFont, String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return (MENU_WIDTH - glyphLayout.width)/2 ;
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

            if(mainMenuBounds.contains(tmp.x,tmp.y)) {
                Paint2Win.AUDIO_MANAGER.stopSfx();
                toMainMenu();
            }
        }
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
        sb.draw(backgroundImage, 0,0, MENU_WIDTH, MENU_HEIGHT);
        textRenderer.setColor(Color.OLIVE);
        if(postProblem){
            textRenderer.draw(sb, "Connection issue...\nRound wasn't recorded\nTime used: "+TimerWatch.fancyString(this.timeUsed), getPositionOffset(textRenderer,"Connection issue\nRound wasn't recorded\nTime: "+TimerWatch.fancyString(this.timeUsed)), MENU_HEIGHT /1.5f);
        }
        else if(newPR){
            textRenderer.draw(sb, "New personal best!\nYou placed: "+placement+"\nTime: "+TimerWatch.fancyString(this.timeUsed), getPositionOffset(textRenderer,"New personal best!\nYou placed: "+placement+"\nTime: "+TimerWatch.fancyString(this.timeUsed)), MENU_HEIGHT /1.5f);
        }
        else{
            textRenderer.draw(sb, "You placed: "+placement+"\nTime: "+TimerWatch.fancyString(this.timeUsed), getPositionOffset(textRenderer,"You placed: "+placement+"\nTime: "+TimerWatch.fancyString(this.timeUsed)),MENU_HEIGHT /1.5f);
        }
        sb.draw(mainMenuButton, mainMenuButtonXPosition, mainMenuButtonYPosition);
        sb.end();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed finishedGame");
        backgroundImage.dispose();
        mainMenuButton.dispose();
        textRenderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
