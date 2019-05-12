package main.java.engine.program.output.screens;

import main.java.engine.events.EventListenerInterface;
import main.java.engine.events.input.key.press.KeyPressEventData;
import main.java.engine.events.input.mouse.click.ClickEvent;
import main.java.engine.events.input.mouse.click.ClickEventListener;
import main.java.engine.events.program.control.ControlEvent;
import main.java.engine.events.program.control.ControlEventFireInterface;
import main.java.engine.input.mouse.Mouse;
import java.util.List;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.utilities.math.vector.MathVector2D;
import main.java.engine.input.mouse.Pressable;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;

//TODO: Place holder until a better implementation is found.
public abstract class Screen extends ClickEventListener implements Drawable, ControlEventFireInterface {

    protected Layer backgroundLayer;
    protected Layer foregroundLayer;
    protected boolean completed;
    protected List<EventListenerInterface<ControlEvent>> listeners;


    public Screen() {
        this(new Layer(), new Layer());
    }


    public Screen(Drawable... elements) {
        this();
        this.backgroundLayer.addElements(elements);
    }


    public Screen(Layer backgroundLayer, Layer foregroundLayer) {
        this.backgroundLayer = backgroundLayer;
        this.foregroundLayer = foregroundLayer;
        this.completed = false;
        this.listeners = new ArrayList();
    }


    @Override
    public void draw() {
        this.backgroundLayer.draw();
        this.foregroundLayer.draw();
    }


    public void remove(Drawable element) {
        if (backgroundLayer.contains(element)) {
            backgroundLayer.removeElement(element);
        } else if (foregroundLayer.contains(element)) {
            foregroundLayer.removeElement(element);
        } else {
            // not contained in any layer
            return;
        }
    }


    public void removeAll() {
        backgroundLayer.removeAll();
        foregroundLayer.removeAll();
    }


    public boolean isCompleted() {
        return completed;
    }


    // TODO: Replace mouse position percentages with pixels when testing in bounds.
    @Override
    public void messageRecieved(ClickEvent clickEvent) {
        // go through all textures backwards and see if clickable and in bounds
        System.out.println(clickEvent.getSource());
        if (clickEvent.getSource() == null) {
            return;
        }
        Mouse mouse = (Mouse) clickEvent.getSource();
        MathVector2D<Double> position;
        position = mouse.getCursorPositionPercentages();
        boolean isPressed = (clickEvent.getEventData().getAction() == GLFW.GLFW_PRESS);
        boolean foregroundSuccess = clickLayer(foregroundLayer, position, isPressed);
        if (foregroundSuccess == false) {
            clickLayer(backgroundLayer, position, isPressed);
        }
    }


    private boolean clickLayer(Layer passedLayer, MathVector2D<Double> position, boolean isPressed) {
        System.out.println("CLICK LAYER:" + position + "  -  " + isPressed);
        List<Drawable> elements = passedLayer.getLayerElements();
        for (int index = elements.size() - 1; index >= 0; index--) {
            Drawable element = elements.get(index);
            if (element instanceof Pressable) {
                Pressable clickElement = ((Pressable) element);
                if (clickElement.inBounds(position)) {
                    if (isPressed == true) {
                        clickElement.press();
                    } else {
                        clickElement.release();
                    }
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void fireEvent(ControlEvent event) {
        for (EventListenerInterface<ControlEvent> listener : this.listeners) {
            listener.messageRecieved(event);
        }
    }


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {
        if (foregroundLayer.inBounds(detectionPoint)) {
            return true;
        }
        if (backgroundLayer.inBounds(detectionPoint)) {
            return true;
        }
        return false;
    }


    public abstract void keyHandle(KeyPressEventData data);


    @Override
    public void addListener(EventListenerInterface<ControlEvent> listener) {
        if (this.listeners.contains(listener) == false) {
            this.listeners.add(listener);
            System.out.println("ADDING:" + listener.toString());
        }
    }


    @Override
    public void removeListener(EventListenerInterface<ControlEvent> listener) {
        this.listeners.remove(listener);
    }


    @Override
    public void removeListeners(List<EventListenerInterface<ControlEvent>> listeners) {
        for (EventListenerInterface<ControlEvent> listener : listeners) {
            this.removeListener(listener);
        }
    }


    @Override
    public void addListeners(List<EventListenerInterface<ControlEvent>> listeners) {
        for (EventListenerInterface<ControlEvent> listener : listeners) {
            this.addListener(listener);
        }
    }



}
