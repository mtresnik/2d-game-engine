package game.editor;

public abstract class EditorEvent<DATA extends EditorEventData> {

    public Object source;
    public DATA data;

    public EditorEvent(Object source, DATA data) {
        this.source = source;
        this.data = data;
    }

}
