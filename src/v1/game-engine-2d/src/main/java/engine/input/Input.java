package main.java.engine.input;

import main.java.engine.input.keyboard.Keyboard;
import main.java.engine.events.input.InputEvent;
import main.java.engine.events.input.InputEventFireObject;
import main.java.engine.events.input.InputEventListenerInterface;
import main.java.engine.input.mouse.Mouse;

public class Input extends InputEventFireObject implements InputEventListenerInterface<InputEvent> {

    private Mouse mouse;
    private Keyboard keyboard;


    public Input() {
        this.mouse = new Mouse(this);
        this.keyboard = new Keyboard(this);
    }


    @Override
    public Class<InputEvent> getEventType() {
        return InputEvent.class;
    }


    public Mouse getMouse() {
        return mouse;
    }


    public Keyboard getKeyboard() {
        return keyboard;
    }


    @Override
    public final void messageRecieved(InputEvent event) {
        this.fireEvent(event);
    }


    public void poll() {
    }


}
