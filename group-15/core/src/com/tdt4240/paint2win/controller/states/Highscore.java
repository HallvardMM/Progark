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
import com.tdt4240.paint2win.model.maps.AbstractMap;
import com.tdt4240.paint2win.networking.DataTransfer;
import com.tdt4240.paint2win.networking.Dto.HighScoreRow;

import java.io.IOException;
import java.util.List;

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class Highscore extends State {
    private OrthographicCamera cam;

    private final Texture mainMenuButton;
    private final int mainMenuButtonXPosition;
    private final int mainMenuButtonYPosition;

    private final Texture rightButton;
    private final int rightButtonXPosition;
    private final int rightButtonYPosition;

    private final Texture leftButton;
    private final int leftButtonXPosition;
    private final int leftButtonYPosition;
    private final Viewport viewport;

    private final BitmapFont bitmapFont;
    private AbstractMap.valid_maps mapType;

    private List<HighScoreRow> highscores;

    private static final float getPositionOffset(BitmapFont bitmapFont, String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return (MENU_WIDTH - glyphLayout.width)/2 ;
    }

    private static final void renderHighScoreList(SpriteBatch sb, BitmapFont bitmapFont, List<HighScoreRow> highScoreRowList, AbstractMap.valid_maps mapType){
        String tmpString = "Highscore: "+mapType.toString()+"\n";
        int position = 1;
        for (HighScoreRow row : highScoreRowList) {
            if(position!=10){
                tmpString += position + ":    Player: " + row.getPlayerName() + "    Time: " + TimerWatch.fancyString(row.getTime())+"\n";
            }
            else{
                //Just to make it pretty
                tmpString += position + ":  Player: " + row.getPlayerName() + "    Time: " + TimerWatch.fancyString(row.getTime())+"\n";
            }
            position++;
        }
        bitmapFont.draw(sb, tmpString,getPositionOffset(bitmapFont,tmpString),MENU_HEIGHT);
    }

    private static final List<HighScoreRow> fetchHighScoreList(AbstractMap.valid_maps mapType){
        try{
            return DataTransfer.getHighscores(mapType);
        }
        catch (IOException e){
            return null;
        }
    }

    /**
     * Constructor, includes all logic that only has to get rendered once.
     * The Join Game let's the user join an already existing lobby
     * @param gsm
     */
    public Highscore(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);

        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.menu_soundtrack_2));

        mainMenuButton = new Texture("main_menu.png");
        mainMenuButtonXPosition = MENU_WIDTH /2 - mainMenuButton.getWidth()/2;
        mainMenuButtonYPosition = MENU_HEIGHT /9;

        rightButton = new Texture("Right_Arrow.png");
        rightButtonXPosition =  MENU_WIDTH - MENU_WIDTH /10 - rightButton.getWidth()/2;
        rightButtonYPosition = MENU_HEIGHT /2;

        leftButton = new Texture("Left_Arrow.png");
        leftButtonXPosition = MENU_WIDTH /10 - rightButton.getWidth()/2;
        leftButtonYPosition = MENU_HEIGHT /2;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto/Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        this.bitmapFont = generator.generateFont(parameter); // font size 30 pixels
        generator.dispose(); // Dispose to avoid memory leaks!
        bitmapFont.setColor(Color.OLIVE);

        this.mapType = AbstractMap.valid_maps.URBAN;
        this.highscores =fetchHighScoreList(mapType);
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
            Rectangle rightBounds = new Rectangle(rightButtonXPosition, rightButtonYPosition, rightButton.getWidth(), rightButton.getHeight());
            Rectangle leftBounds = new Rectangle(leftButtonXPosition, leftButtonYPosition,leftButton.getWidth(), leftButton.getHeight());

            if(mainMenuBounds.contains(tmp.x,tmp.y)) { toMainMenu(); }
            else if(rightBounds.contains(tmp.x,tmp.y)) {
                this.mapType = AbstractMap.valid_maps.DESERT;
                this.highscores = fetchHighScoreList(mapType);
            }
            else if(leftBounds.contains(tmp.x,tmp.y)) {
                this.mapType = AbstractMap.valid_maps.URBAN;
                this.highscores = fetchHighScoreList(mapType); }
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
    public void render(SpriteBatch sb)  {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        if(highscores != null){
            renderHighScoreList(sb,bitmapFont,highscores,mapType);
        }
        else{
            bitmapFont.draw(sb, "Error with fetching of scoreboard",getPositionOffset(bitmapFont, "Error with fetching scoreboard"), MENU_HEIGHT /1.5f);
        }
        sb.draw(mainMenuButton, mainMenuButtonXPosition, mainMenuButtonYPosition);
        sb.draw(rightButton, rightButtonXPosition, rightButtonYPosition);
        sb.draw(leftButton, leftButtonXPosition, leftButtonYPosition);
        sb.end();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Join Game");
        mainMenuButton.dispose();
        rightButton.dispose();
        leftButton.dispose();
        bitmapFont.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
