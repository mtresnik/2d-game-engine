package game.editor;

import java.util.ArrayList;
import java.util.List;

public class EditorFireEventObject implements EditorFireEventInterface {

    public List<EditorEventListener> listenerList;

    public EditorFireEventObject() {
        listenerList = new ArrayList();
    }

    @Override
    public void fireEvent(EditorEvent event) {
        for (EditorEventListener listener : listenerList) {
            listener.messageRecieved(event);
        }
    }

    @Override
    public void addListener(EditorEventListener listener) {
        if (listener == null) {
            return;
        }
        listenerList.add(listener);
    }

    @Override
    public void removeListener(EditorEventListener listener) {
        if (listener == null) {
            return;
        }
        listenerList.remove(listener);
    }

    @Override
    public List<EditorEventListener> getListeners() {
        return listenerList;
    }

}
