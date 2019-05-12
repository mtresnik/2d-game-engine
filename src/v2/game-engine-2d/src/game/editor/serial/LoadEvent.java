package game.editor.serial;

import game.editor.EditorEvent;

public class LoadEvent extends EditorEvent<LoadEventData> {

    public LoadEvent(Object source, LoadEventData data) {
        super(source, data);
    }

}
