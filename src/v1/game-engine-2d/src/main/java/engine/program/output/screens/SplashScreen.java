package main.java.engine.program.output.screens;

import main.java.engine.events.input.key.press.KeyPressEventData;
import main.java.engine.output.render.interfaces.Drawable;

public abstract class SplashScreen extends Screen {

    public SplashScreen(Drawable backgroundTexture, Drawable logoTexture) {
        this.backgroundLayer.addElement(backgroundTexture);
        this.foregroundLayer.addElement(logoTexture);
    }
    
    @Override
    public abstract void keyHandle(KeyPressEventData data);



}
