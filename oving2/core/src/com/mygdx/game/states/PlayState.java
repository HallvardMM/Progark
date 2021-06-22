package com.mygdx.game.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Oving1;
import com.mygdx.game.sprites.BallObserver;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.sprites.Ball;


public class PlayState extends State implements BallObserver {

    private Player player1;
    private Player player2;
    private Ball ball;
    private int player1Point;
    private int player2Point;
    private BitmapFont pointDisplay;
    private String winnerString;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        player1 = new Player(10,180);
        player2 = new Player(610,180);
        ball = Ball.getInstance();
        ball.addObserver(player1);
        ball.addObserver(player2);
        ball.addObserver(this);
        pointDisplay = new BitmapFont();
        player1Point = 0;
        player2Point = 0;
        winnerString = "";
        cam.setToOrtho(false, Oving1.WIDTH,Oving1.HEIGHT);
    }

    public void ponghit(){
        if(player1.getBounds().overlaps(ball.getBounds())){
            ball.switchVelocity();
        }
        else if(player2.getBounds().overlaps(ball.getBounds())){
            ball.switchVelocity();
        }
    }

    public int getPlayer1Point() {
        return player1Point;
    }

    public void setPlayer1Point(int player1Point) {
        this.player1Point = player1Point;
    }

    public int getPlayer2Point() {
        return player2Point;
    }

    public void setPlayer2Point(int player2Point) {
        this.player2Point = player2Point;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            player1.move(player1.getPosition().y+2);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            player1.move(player1.getPosition().y-2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player2.move(player2.getPosition().y+2);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player2.move(player2.getPosition().y-2);
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        ponghit();

        if (winnerString.equals("")) {
            ball.update(dt);
            player1.update(dt);
            player2.update(dt);
        }
    }

    public void displayPoints(SpriteBatch sb){
        if(winnerString.equals("")){
            pointDisplay.setColor(1F, 1F, 1F, 1F);
            pointDisplay.getData().setScale(2);
            pointDisplay.draw(sb, "Player1: " + getPlayer1Point() , 10, 460);
            pointDisplay.draw(sb, "Player2: " + getPlayer2Point(), 500, 460);
        }
        else{
            pointDisplay.setColor(1F, 1F, 1F, 1F);
            pointDisplay.getData().setScale(2);
            pointDisplay.draw(sb, winnerString , 260, 460);
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        displayPoints(sb);
        sb.end();
        if (winnerString.equals("")) {
            player1.draw();
            player2.draw();
            ball.draw();
        }
    }

    @Override
    public void dispose() {
        player1.dispose();
        player2.dispose();
        ball.dispose();
        pointDisplay.dispose();
    }

    @Override
    public void goalScored(float posx) {
        if(posx<=0){
            if(getPlayer2Point()+1<21){
                setPlayer2Point(getPlayer2Point()+1);
                ball.centerPosition();
                ball.setVelocity();
            }
            else{
                ball.dispose();
                winnerString = "Player 2 is winner";
            }
        }
        else{
            if(getPlayer1Point()+1<21){
                setPlayer1Point(getPlayer1Point()+1);
                ball.centerPosition();
                ball.setVelocity();

            }
            else{
                ball.dispose();
                winnerString = "Player 1 is winner";
            }
        }
    }
}

