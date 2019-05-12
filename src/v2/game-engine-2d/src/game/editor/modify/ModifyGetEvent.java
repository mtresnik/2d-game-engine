package game.editor.modify;

import game.editor.EditorEvent;

public class ModifyGetEvent extends EditorEvent<ModifyGetEventData> {

    public ModifyGetEvent(Object source, ModifyGetEventData data) {
        super(source, data);
    }

}
