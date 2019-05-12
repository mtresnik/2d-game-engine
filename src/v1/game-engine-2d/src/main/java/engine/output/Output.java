package main.java.engine.output;

import main.java.engine.events.EventObject;
import main.java.engine.events.EventFireObject;
import main.java.engine.events.output.OutputEvent;
import main.java.engine.events.output.OutputEventListenerInterface;
import main.java.engine.events.output.audio.AudioEvent;
import main.java.engine.events.output.render.RenderEvent;
import main.java.engine.program.logic.Controller;
import main.java.engine.output.audio.Audio;
import main.java.engine.output.render.OpenGL.Display;
import main.java.engine.output.render.Render;
import main.java.engine.events.EventListenerInterface;

public final class Output
        extends EventFireObject<EventObject>
        implements OutputEventListenerInterface<OutputEvent> {

    private Audio audio;
    private Render render;


    public Output(Display display) {
        this.audio = new Audio(this);
        this.render = new Render(display, this);
    }


    @Override
    public void fireEvent(EventObject event) {
        if (event.getSource() instanceof Controller) {
            if (event instanceof RenderEvent) {
                this.render.messageRecieved((RenderEvent) event);
            } else if (event instanceof AudioEvent) {
                this.audio.messageRecieved((AudioEvent) event);
            }
        } else {
            for (EventListenerInterface<EventObject> L : listeners) {
                L.messageRecieved(event);
            }
        }
    }


    public Audio getAudio() {
        return audio;
    }


    @Override
    public Class<OutputEvent> getEventType() {
        return OutputEvent.class;
    }


    public Render getRender() {
        return render;
    }


    @Override
    public void messageRecieved(OutputEvent event) {
        this.fireEvent(event);
    }


}
