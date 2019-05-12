package main.java.programs.games.tiled;

import main.java.engine.events.EventObject;
import main.java.engine.program.game.logic.GameController;
import main.java.engine.output.Output;
import main.java.engine.events.EventListenerInterface;

public class SIGameController extends GameController {

    public SIGameController(Output output) {
        super("Space Invaders Clone", output);
    }


    @Override
    public void fireEvent(EventObject event) {
        for (EventListenerInterface listener : listeners) {
            if (listener.getEventType().isAssignableFrom(event.getClass())) {
                listener.messageRecieved(event);
            }
        }
    }


    @Override
    protected void newGame() {
        System.out.println("HANDLE START");
        this.setCurrentScreen(null);
    }

    @Override
    public void init() {
        this.game = new SpaceInvaders(this, new TitleScreen());
        registerListeners();
        registerFirers();
    }


    private void registerListeners() {
    }


    private void registerFirers() {
        // keyboard.addListener(this);
        // mouse.addListener(this);
        // currentState.addListener(this);
        this.currentScreen.addListener(this);
    }


}
