package game.editor.serial;

import game.editor.EditorEventData;

public class LoadEventData extends EditorEventData {

    public String file_location;

    public LoadEventData(String file_location) {
        this.file_location = file_location;
    }

}
