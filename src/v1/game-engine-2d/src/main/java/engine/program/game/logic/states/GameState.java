/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.engine.program.game.logic.states;

import main.java.engine.events.EventObject;
import main.java.engine.program.game.Game;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.utilities.interfaces.State;

/**
 *
 * @author fr0z3n2
 */
public abstract class GameState implements State {

    protected Drawable extra;
    protected Game game;


    public GameState(Game game) {
        this.game = game;
        this.setExtra();
    }


    /**
     * Allowing extra to be up to the implementation.
     */
    public abstract void setExtra();


    public State next(EventObject event) {
        return null;
    }


}
