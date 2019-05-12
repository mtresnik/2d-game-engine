/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.engine.program.game;

import main.java.engine.events.input.key.KeyEvent;
import main.java.engine.events.input.mouse.MouseEvent;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.program.game.physics.environment.Universe;

/**
 *
 * @author fr0z3n2
 */
public interface Game extends Drawable {

    public void checkAllTimers();


    public void handleKeyEvent(KeyEvent keyEvent);


    public void handleMouseEvent(MouseEvent mouseEvent);


    public void handlePhysics();


    public void gameLoop();


    public void initTimerList();


    public void initGlobals();


    public Universe getUniverse();


}
