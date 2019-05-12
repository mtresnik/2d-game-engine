package main.java.engine.program.game.logic;

import main.java.engine.events.EventObject;
import main.java.engine.program.logic.Controller;
import main.java.engine.events.input.InputEvent;
import main.java.engine.events.output.OutputEvent;
import java.util.ArrayList;
import java.util.List;
import main.java.engine.output.Output;
import main.java.engine.program.game.Game;
import main.java.engine.events.EventFireInterface;
import main.java.engine.events.EventListenerInterface;
import main.java.engine.events.input.key.KeyEvent;
import main.java.engine.events.input.mouse.MouseEvent;
import main.java.engine.events.output.render.display.resize.ResizeEvent;
import main.java.engine.events.output.render.display.resize.ResizeEventData;
import main.java.engine.events.program.control.ControlEvent;
import main.java.engine.events.program.control.screen.ScreenChangeEvent;
import main.java.engine.output.render.Render;
import main.java.engine.program.output.screens.Screen;

public abstract class GameController extends Controller
        implements EventFireInterface<EventObject> {

    protected Screen currentScreen;
    protected Screen gameScreen;
    protected Game game;
    protected List<EventListenerInterface> listeners;


    public GameController(String name, Output output) {
        super(name, output);
        this.listeners = new ArrayList();
    }


    @Override
    public final void run() {
        this.game.gameLoop();
        this.game.handlePhysics();
        this.game.draw();
    }

    @Override
    public abstract void init();


    protected abstract void newGame();


    public Screen getCurrentScreen() {
        return this.currentScreen;
    }


    public void setCurrentScreen(Screen currentScreen) {

        this.removeListener(this.currentScreen);
        this.currentScreen = currentScreen;
        this.addListener(currentScreen);

    }


    // <editor-fold desc="Event-Related Methods">
    @Override
    public final void messageRecieved(EventObject event) {
        if (event instanceof InputEvent) {
            this.messageRecieved((InputEvent) event);
        } else if (event instanceof OutputEvent) {
            this.messageRecieved((OutputEvent) event);
        } else if (event instanceof ControlEvent) {
            this.messageRecieved((ControlEvent) event);
        }
    }


    @Override
    public void addListener(EventListenerInterface listener) {
        if (listeners == null) {
            this.listeners = new ArrayList();
        }
        if (listeners.contains(listener) == false) {
            listeners.add(listener);
        }
    }


    @Override
    public void addListeners(List<EventListenerInterface<EventObject>> listeners) {
        for (EventListenerInterface listener : listeners) {
            this.addListener(listener);
        }
    }


    @Override
    public void removeListener(EventListenerInterface listener) {
        this.listeners.remove(listener);
    }


    @Override
    public void removeListeners(List<EventListenerInterface<EventObject>> listeners) {
        for (EventListenerInterface listener : listeners) {
            this.removeListener(listener);
        }
    }


    @Override
    public final void messageRecieved(InputEvent inputEvent) {
        if (inputEvent instanceof MouseEvent) {
            this.game.handleMouseEvent((MouseEvent) inputEvent);
        } else if (inputEvent instanceof KeyEvent) {
            this.game.handleKeyEvent((KeyEvent) inputEvent);
        }
    }


    @Override
    public final void messageRecieved(OutputEvent outputEvent) {
        System.out.println("OUTPUT EVENT:");
        System.out.println(outputEvent.getEventData().toString());
        if (outputEvent instanceof ResizeEvent) {
            // Handle resizing in the Render class
            System.out.println("RESIZE EVENT");
            ResizeEventData data = ((ResizeEvent) outputEvent).getEventData();
            Render.resizeScreen(data.getWindow_width(), data.getWindow_height());
        } else {
            System.out.println("NOT RESIZE EVENT");
            System.out.println(outputEvent.toString());
        }
    }


    @Override
    public final void messageRecieved(ControlEvent controlEvent) {
        System.out.println("CONTROL EVENT");

        if (controlEvent instanceof ScreenChangeEvent) {
            this.setCurrentScreen(((ScreenChangeEvent) controlEvent).getEventData().getScreen());
        }

    }


    public Class<EventObject> getEventType() {
        return EventObject.class;
    }


    // </editor-fold>
}
