package com.tdt4240.paint2win.controller.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;

public class GameStateManager {

    private java.util.Stack<State> states;

    /**
     * Stack containing the different screens/states. Now we use set(state) when switching screens
     * so the current screen gets disposed and we always contain 1 screen on the stack
     */
    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Add a new state to stack
     * @param state
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Sets the new state and dispose the old
     * @param state
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Update the current screen/state
     * @param dt
     */
    public void update(float dt) {
        states.peek().update(dt);
    }

    /**
     * render the current screen/state
     * @param sb
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
