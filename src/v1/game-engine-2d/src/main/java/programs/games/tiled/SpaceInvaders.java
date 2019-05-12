package main.java.programs.games.tiled;

import main.java.engine.events.input.key.KeyEvent;
import main.java.engine.events.input.key.press.KeyPressEventData;
import main.java.engine.events.input.mouse.MouseEvent;
import main.java.engine.events.input.mouse.click.ClickEvent;
import main.java.engine.program.game.logic.GameController;
import main.java.engine.program.game.physics.dimensions._2d.Game2d;
import main.java.engine.program.game.objects.info.Scoreboard;
import main.java.engine.program.output.screens.Screen;
import java.util.ArrayList;
import java.util.List;
import main.java.engine.program.game.physics.environment.Universe;
import main.java.engine.program.game.physics.handlers.PhysicsHandler;
import main.java.utilities.interfaces.Advanceable;
import main.java.utilities.math.vector.MathVector2D;
import main.java.utilities.timer.Timer;

public class SpaceInvaders implements Game2d, Advanceable {

    public static Scoreboard scoreboard;
    private List<Timer> timerList;
    private GameController controller;
    private final PhysicsHandler physicsHandler;
    private Universe universe;


    public SpaceInvaders(SIGameController controller, Screen initial_screen) {
        this.controller = controller;
        this.initGlobals();
        this.initScreens(initial_screen);
        this.initTimerList();
        this.universe = new Universe();
        this.physicsHandler = null;
    }


    @Override
    public Universe getUniverse() {
        return this.universe;
    }


    @Override
    public void handlePhysics() {
        
    }


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {
        return this.controller.getCurrentScreen().inBounds(detectionPoint);
    }


    @Override
    public void initGlobals() {
    }


    public void initScreens(Screen initial_screen) {
        this.controller.setCurrentScreen(initial_screen);
    }


    @Override
    public void initTimerList() {
        this.timerList = new ArrayList();
    }


    @Override
    public void checkAllTimers() {
        for (Timer t : timerList) {
            boolean success = t.check();
            if (success == true) {
                t.fire();
            }
        }
    }


    @Override
    public void gameLoop() {
        this.checkAllTimers();
    }


    @Override
    public void advance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void handleKeyEvent(KeyEvent keyEvent) {
        System.out.println("Handling Keys");
        this.controller.getCurrentScreen().keyHandle((KeyPressEventData) keyEvent.getEventData());
    }


    @Override
    public void handleMouseEvent(MouseEvent mouseEvent) {
        if (mouseEvent instanceof ClickEvent) {
            this.controller.getCurrentScreen().messageRecieved((ClickEvent) mouseEvent);
        }
        System.out.println(mouseEvent.getEventData().toString());
    }


    @Override
    public void draw() {
        this.controller.getCurrentScreen().draw();
    }


}
