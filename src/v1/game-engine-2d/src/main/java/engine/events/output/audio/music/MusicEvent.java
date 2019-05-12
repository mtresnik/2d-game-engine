/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.engine.events.output.audio.music;

import main.java.engine.events.output.audio.AudioEvent;

public class MusicEvent<T extends MusicEventData> extends AudioEvent<T> {

    public MusicEvent(Object source, T data) {
        super(source, data);
    }


}
