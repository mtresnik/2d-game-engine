package game.editor.modify;

import game.editor.EditorEvent;

public class ModifySetEvent<DATA extends ModifySetEventData> extends EditorEvent<DATA> {

    public ModifySetEvent(Object source, DATA data) {
        super(source, data);
    }

}
