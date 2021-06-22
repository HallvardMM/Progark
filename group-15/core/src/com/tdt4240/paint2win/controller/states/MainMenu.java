package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.controller.managers.SoundAssets;

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class MainMenu extends State {
    private OrthographicCamera cam;
    private final Texture backgroundImage;
    private final Texture logo;

    private final Texture highscoreButton;
    private final int highscoreButtonXPosition;
    private final int highscoreButtonYPosition;
    private final Texture createGameButton;
    private final int createGameButtonXPosition;
    private final int createGameButtonYPosition;
    private final Texture settingsButton;
    private final int settingsButtonXPosition;
    private final int settingsButtonYPosition;
    private final Texture tutorialButton;
    private final int tutorialButtonXPosition;
    private final int tutorialButtonYPosition;
    private final Viewport viewport;

    /**
     * Initiates Main Menu. Sets camera position. Also logic for placing the different buttons
     * @param gsm
     */
    public MainMenu(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);
        backgroundImage = new Texture("paintball_players.png");
        logo = new Texture("paint2win.png");

        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.menu_soundtrack_1));

        highscoreButton = new Texture("highscore.png");
        highscoreButtonXPosition = MENU_WIDTH /4 - highscoreButton.getWidth()/2;
        highscoreButtonYPosition = MENU_HEIGHT /4;

        createGameButton = new Texture("create_game.png");
        createGameButtonXPosition = 3* MENU_WIDTH /4 - createGameButton.getWidth()/2;
        createGameButtonYPosition = MENU_HEIGHT /4;

        settingsButton = new Texture("settings.png");
        settingsButtonXPosition = MENU_WIDTH /4 - settingsButton.getWidth()/2;
        settingsButtonYPosition = 2* MENU_HEIGHT /4;

        tutorialButton = new Texture("tutorial.png");
        tutorialButtonXPosition = 3* MENU_WIDTH /4 - tutorialButton.getWidth()/2;
        tutorialButtonYPosition = 2* MENU_HEIGHT /4;
    }

    /**
     * Sends the user to the Join game page
     */
    private void toHighScore (){
        gsm.set(new Highscore(gsm));
    }

    /**
     * Sends the user to the Create game page
     */
    private void toCreateGame (){
        gsm.set(new CreateGame(gsm));
    }

    /**
     * Sends the user to the Settings page
     */
    private void toSettings (){
        gsm.set(new Settings(gsm));
    }

    /**
     * Sends the user to the Tutorial page
     */
    private void toTutorial (){ gsm.set(new Tutorial(gsm)); }

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
            Rectangle joinGameBounds = new Rectangle(highscoreButtonXPosition, highscoreButtonYPosition, highscoreButton.getWidth(), highscoreButton.getHeight());
            Rectangle createGameBounds = new Rectangle(createGameButtonXPosition, createGameButtonYPosition, createGameButton.getWidth(), createGameButton.getHeight());
            Rectangle settingsBounds = new Rectangle(settingsButtonXPosition, settingsButtonYPosition, settingsButton.getWidth(), settingsButton.getHeight());
            Rectangle tutorialBounds = new Rectangle(tutorialButtonXPosition, tutorialButtonYPosition, tutorialButton.getWidth(), tutorialButton.getHeight());

            if(joinGameBounds.contains(tmp.x,tmp.y)) { toHighScore(); }
            else if(createGameBounds.contains(tmp.x,tmp.y)) { toCreateGame(); }
            else if(settingsBounds.contains(tmp.x,tmp.y)) { toSettings(); }
            else if(tutorialBounds.contains(tmp.x,tmp.y)) { toTutorial(); }
        }
    }

    /**
     * Updates the current state of the screen.
     * Calls function to see if button was pressed
     * @param dt
     */
    @Override
    public void update(float dt) {
        checkIfPressedButton();
    }

    /**
     * Rerenders the screen with updated values
     * @param sb
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(backgroundImage, 0,0, MENU_WIDTH, MENU_HEIGHT);
        sb.draw(logo, MENU_WIDTH /2 - logo.getWidth()/2,3* MENU_HEIGHT /4);
        sb.draw(highscoreButton, highscoreButtonXPosition, highscoreButtonYPosition);
        sb.draw(createGameButton, createGameButtonXPosition, createGameButtonYPosition);
        sb.draw(settingsButton, settingsButtonXPosition, settingsButtonYPosition);
        sb.draw(tutorialButton, tutorialButtonXPosition, tutorialButtonYPosition);
        sb.end();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Main Menu");
        highscoreButton.dispose();
        createGameButton.dispose();
        settingsButton.dispose();
        tutorialButton.dispose();
        logo.dispose();
        backgroundImage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
