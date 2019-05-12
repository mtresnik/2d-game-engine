package game.editor.modify;

import game.data.LevelData;
import game.data.intuitive.PlayerData;
import game.editor.EditorEventData;

public class ModifyGetEventData extends EditorEventData {

    private LevelData data;
    private final LevelDataCallbackI callback;

    public ModifyGetEventData(LevelDataCallbackI callback) {
        this.callback = callback;
//        System.out.println("Callback:" + callback.getClass());
    }

    public void setData(LevelData data) {
        this.data = data;
        this.update();
    }

    public LevelData getData() {
        return data;
    }

    public PlayerData getPlayerObjectData() {
        final PlayerData retData = data.player_data;
        return retData;
    }

    public void update() {
        this.callback.update(data);
    }

    public static interface LevelDataCallbackI {

        public void update(LevelData data);
    }

}
