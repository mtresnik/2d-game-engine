package game.editor.modify;

import game.data.LevelData;

public class ModifyRemoveEventData extends ModifySetEventData {

    public ModifyRemoveEventData(LevelData levelData) {
        super(levelData);
    }

    @Override
    public String toString() {
        return "ModifyRemoveEventData{" + this.level_data + '}';
    }

}
