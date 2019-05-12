package game.editor.serial;

import game.editor.EditorEvent;

public class SaveEvent extends EditorEvent<SaveEventData> {

    public SaveEvent(Object source, SaveEventData data) {
        super(source, data);
    }

}
