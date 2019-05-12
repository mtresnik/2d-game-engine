package main.java.engine.input.keyboard;

import main.java.engine.events.input.InputEventFireObject;
import main.java.engine.events.input.key.KeyEvent;
import main.java.engine.events.input.key.press.KeyPressEvent;
import main.java.engine.events.input.key.press.KeyPressEventData;
import main.java.engine.input.Input;
import org.lwjgl.glfw.GLFW;

public class Keyboard extends InputEventFireObject {

    
    public final static String[] actionMap = new String[]{"released", "pressed", "held"};


    public Keyboard(Input input) {
        this.addListener(input);
    }


    public void keyHandle(int key, int scancode, int action, int mods) {
        switch (action) {
            case GLFW.GLFW_RELEASE: {
                KeyPressEventData dataToFire = new KeyPressEventData(key, scancode, action, mods);
                KeyEvent eventToFire = new KeyPressEvent(this, dataToFire);
                this.fireEvent(eventToFire);
            }
            break;
            case GLFW.GLFW_PRESS: {
                KeyPressEventData dataToFire = new KeyPressEventData(key, scancode, action, mods);
                KeyEvent eventToFire = new KeyPressEvent(this, dataToFire);
                this.fireEvent(eventToFire);
            }
            break;
            case GLFW.GLFW_REPEAT: {
                KeyPressEventData dataToFire = new KeyPressEventData(key, scancode, action, mods);
                KeyEvent eventToFire = new KeyPressEvent(this, dataToFire);
                this.fireEvent(eventToFire);
            }
            break;
            default:
                System.out.println("Invalid input.");
        }

    }


    // TODO : Handle text entry.
    public void charHandle(int codepoint) {
        
    }


}
