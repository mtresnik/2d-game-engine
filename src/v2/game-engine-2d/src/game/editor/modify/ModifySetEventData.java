package game.editor.modify;

import game.data.LevelData;
import game.editor.EditorEventData;

public class ModifySetEventData extends EditorEventData {

    public final LevelData level_data;

    public ModifySetEventData(LevelData levelData) {
        this.level_data = levelData;
    }

    @Override
    public String toString() {
        return "ModifySetEventData{" + "level_data=" + level_data + '}';
    }

}
