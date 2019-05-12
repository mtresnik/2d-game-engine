package game.editor;

import java.util.List;

public interface EditorFireEventInterface {

    public void fireEvent(EditorEvent event);

    public void addListener(EditorEventListener listener);

    public void removeListener(EditorEventListener listener);

    public default void addListeners(List<EditorEventListener> listeners) {
        for (EditorEventListener listener : listeners) {
            this.addListener(listener);
        }
    }

    public default void removeListeners(List<EditorEventListener> listeners) {
        for (EditorEventListener listener : listeners) {
            this.removeListener(listener);
        }
    }

    public List<EditorEventListener> getListeners();

}
