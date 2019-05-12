package main.java.engine.events.output.audio.sound;

import main.java.engine.events.output.audio.AudioEvent;

public class SoundEvent<T extends SoundEventData> extends AudioEvent<T> {

    public SoundEvent(Object source, T data) {
        super(source, data);
    }


}
