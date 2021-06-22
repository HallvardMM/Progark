package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
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
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tdt4240.paint2win.Paint2Win;
import com.tdt4240.paint2win.controller.managers.SoundAssets;
import com.tdt4240.paint2win.model.maps.AbstractMap;

import static com.tdt4240.paint2win.Paint2Win.MENU_HEIGHT;
import static com.tdt4240.paint2win.Paint2Win.MENU_WIDTH;

public class CreateGame extends State {
    private OrthographicCamera cam;
    private final Texture colorsImage;
    private Texture header;

    private final Texture mainMenuButton;
    private final int mainMenuButtonXPosition;
    private final int mainMenuButtonYPosition;

    private final Texture startGameButton;
    private final int startGameButtonXPosition;
    private final int startGameButtonYPosition;

    private Viewport viewport;
    private AbstractMap.valid_maps chosen_map;
    private Stage stage;
    private SelectBox<AbstractMap.valid_maps> selectMapBox;
    TextField usernameTextField;
    private String username;
    private Color playerColor;

    /**
     * Page for creating a new game. Let's the user select a map and then start
     * a new lobby
     * @param gsm
     */
    public CreateGame(GameStateManager gsm) {
        super(gsm);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        viewport = new StretchViewport(MENU_WIDTH, MENU_HEIGHT, cam);

        colorsImage = new Texture("colors.jpg");
        header = new Texture("create_game_header_X6.png");
        
        Paint2Win.AUDIO_MANAGER.playMusic(((Paint2Win)Gdx.app.getApplicationListener()).assets.assetManager.get(SoundAssets.menu_soundtrack_2));

        mainMenuButton = new Texture("main_menu.png");
        mainMenuButtonXPosition = MENU_WIDTH/4 - mainMenuButton.getWidth()/2;
        mainMenuButtonYPosition = MENU_HEIGHT/4;

        startGameButton = new Texture("start.png");
        startGameButtonXPosition = 3* MENU_WIDTH /4 - startGameButton.getWidth()/2;
        startGameButtonYPosition = MENU_HEIGHT /4;

        chosen_map = AbstractMap.valid_maps.DESERT;
        renderStageComponents();
    }

    /**
     * Function for rendering the stage components. Just implemented to make the code more readable
     */
    private void renderStageComponents() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();

        Label selectNameLabel = new Label("Enter name:", skin);
        selectNameLabel.setColor(Color.BLACK);
        selectNameLabel.setAlignment(Align.center);

        usernameTextField = new TextField(username, skin);
        usernameTextField.setSize(100, 30);
        usernameTextField.setMaxLength(8);
        usernameTextField.setText("Player");

        Label selectMapLabel = new Label("Choose map:", skin);
        selectMapLabel.setColor(Color.BLACK);
        selectMapLabel.setAlignment(Align.center);

        selectMapBox = new SelectBox<AbstractMap.valid_maps>(skin);
        selectMapBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                chosen_map = selectMapBox.getSelected();
            }
        });
        selectMapBox.setItems(AbstractMap.valid_maps.DESERT, AbstractMap.valid_maps.URBAN);

        Label selectColorLabel = new Label("Choose player color:", skin);
        selectColorLabel.setColor(Color.BLACK);
        selectColorLabel.setAlignment(Align.center);

        SelectBox<String> selectColorBox = new SelectBox<String>(skin);
        selectColorBox.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                playerColor = Colors.get(selectColorBox.getSelected());
            }
        });
        selectColorBox.setItems(Colors.getColors().keys().toArray());

        table.add(selectNameLabel).width(135f).center().padRight(20);
        table.add(selectColorLabel).width(135f).center().padRight(20);
        table.add(selectMapLabel).width(135f).center();
        table.row();
        table.add(usernameTextField).width(135f).center().padRight(20);
        table.add(selectColorBox).width(135f).center().padRight(20);
        table.add(selectMapBox).width(135f).center();

        table.setPosition((int) (MENU_WIDTH/2), (int) (5*MENU_HEIGHT/8));

        stage.addActor(table);
    }

    /**
     * Function for going to start a game lobby
     */
    private void playGame(){
        if(!username.trim().equals("")){
            gsm.set(new PlayState(gsm, chosen_map, username, playerColor));
        }
    }

    /**
     * Function for returning to the Main Menu
     */
    private void toMainMenu(){ gsm.set(new MainMenu(gsm)); }

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
            Rectangle startGameBounds = new Rectangle(startGameButtonXPosition, startGameButtonYPosition, startGameButton.getWidth(), startGameButton.getHeight());

            if(mainMenuBounds.contains(tmp.x,tmp.y)) { toMainMenu(); }
            else if(startGameBounds.contains(tmp.x,tmp.y)) { playGame(); }
        }
    }

    /**
     * Updates the current state of the screen.
     * Calls function to see if button was pressed
     * @param dt
     */
    @Override
    public void update(float dt) {
        if (username != usernameTextField.getText()) {
            username = usernameTextField.getText();
        }
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
        sb.draw(colorsImage, 0,0, MENU_WIDTH, MENU_HEIGHT);
        sb.draw(header, MENU_WIDTH/2 - header.getWidth()/2,3*MENU_HEIGHT/4);
        sb.draw(mainMenuButton, mainMenuButtonXPosition, mainMenuButtonYPosition);
        sb.draw(startGameButton, startGameButtonXPosition, startGameButtonYPosition);
        sb.end();
        stage.draw();
        stage.act();
    }

    /**
     * Disposes the page when leaving it
     */
    @Override
    public void dispose() {
        System.out.println("Disposed Create Game");
        mainMenuButton.dispose();
        startGameButton.dispose();
        header.dispose();
        colorsImage.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
