package game.editor.serial;

import game.editor.EditorEventData;

public class SaveEventData extends EditorEventData {

    public String file_location;

    public SaveEventData(String file_location) {
        this.file_location = file_location;
    }

}
