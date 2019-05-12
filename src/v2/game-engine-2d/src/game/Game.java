package game;

import game.objects.Camera;
import game.objects.Player;
import util.Temporal;
import game.data.LevelData;
import game.editor.ClearEvent;
import game.editor.EditorEvent;
import game.editor.EditorEventListener;
import game.editor.modify.ModifyGetEvent;
import game.editor.modify.ModifyRemoveEvent;
import game.editor.modify.ModifySetEvent;
import game.editor.modify.ModifySetEventData;
import game.editor.serial.LoadEvent;
import game.editor.serial.SaveEvent;
import java.util.ArrayDeque;
import game.render.Display;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import jbox2d.common.Vec2;
import jbox2d.dynamics.World;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements EditorEventListener {

    public static World world;
    public static Game instance;
    public static Level level;
    public static Player player;
    public static Camera camera;

    public Temporal temporal;
    public static Queue<EditorEvent> eventQueue;
    public static Map<Integer, Boolean> keyMap = new HashMap();

    public Game() {
    }

    public void init() {
        temporal = new Temporal();
        eventQueue = new ArrayDeque();
        setupObjects();
    }

    public float pixelsPerMeter() {
        final int FACTOR = 16;
        return Math.min(Display.width(), Display.height()) / FACTOR;
    }

    public void setupObjects() {
        clearLevel();
    }

    public void loop() {
        logic();
        update();
        render();
        animationFrame();
    }

    public void logic() {
        if (keyMap.getOrDefault(GLFW_KEY_C, Boolean.FALSE)) {
            if (player != null) {
                if (camera.attached == null) {
                    camera.attach(player);
                } else {
                    camera.detach();
                }
            }
            keyMap.replace(GLFW_KEY_C, Boolean.FALSE);
        }
    }

    public void update() {
        updateFPS();
        world.step(1.0f / temporal.FPS(), 8, 3);
        camera.update();
        messageRecievedUpdate();
        if (level != null) {
            level.update();
        }
    }

    public void updateFPS() {
        temporal.update();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        if (level != null) {
            level.render();
        }
    }

    public void animationFrame() {
        level.animationFrame();
    }

    // <editor-fold desc="Static Conversion Methods">
    public static float[] metersToRelative(float mx, float my) {
        float px = metersToPixels(mx);
        float py = metersToPixels(my);
        double[] retArrayD = Display.pixelsToRelative(px, py);
        float[] retArray = new float[]{(float) retArrayD[0], (float) retArrayD[1]};
        return retArray;
    }

    public static float pixelsToMeters(float pixels) {
        return pixels / instance.pixelsPerMeter();
    }

    public static float[] pixelsToMeters(float[] pixels) {
        float[] retArray = new float[pixels.length];
        for (int i = 0; i < pixels.length; i++) {
            retArray[i] = pixelsToMeters(pixels[i]);
        }
        return retArray;
    }

    public static float metersToPixels(float meters) {
        return instance.pixelsPerMeter() * meters;
    }

    public static float[] metersToPixels(float[] meters) {
        float[] retArray = new float[meters.length];
        for (int i = 0; i < meters.length; i++) {
            retArray[i] = metersToPixels(meters[i]);
        }
        return retArray;
    }
    // </editor-fold>

    // <editor-fold desc="Key Handling">
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (instance == null) {
            return;
        }
        if (action == 1) {
            keyMap.put(key, Boolean.TRUE);
        } else if (action == 0) {
            keyMap.replace(key, Boolean.TRUE, Boolean.FALSE);
        } else {
            // System.out.println("hold");
        }
    }

    public static boolean forAllKeymap(int... vals) {
        boolean retVal = true;
        for (int i = 0; i < vals.length && retVal; i++) {
            retVal &= keyMap.getOrDefault(vals[i], Boolean.FALSE);
        }
        return retVal;
    }

    public static boolean forAllKeymapTF(int[] trueVals, int[] falseVals) {
        return forAllKeymap(trueVals) && (forAllKeymap(falseVals) == false);
    }
    // </editor-fold>

    // <editor-fold desc="Serial Methods">
    public static void clearLevel() {
        world = new World(new Vec2(0, 0));
        level = new Level();
        player = null;
        camera = new Camera(0, 0);
    }

    public static void loadLevel(Level toLoad) {
        level = toLoad;
        player = level.player;
        camera = new Camera(0, 0);
        camera.attach(player);
    }

    public static void loadLevel(String location) {
        loadLevel(Level.load(location));
    }

    public static void saveLevel(String location) {
        level.save(location);
    }
    // </editor-fold>

    // <editor-fold desc="Event Handling">
    private void messageRecievedUpdate() {
        while (eventQueue.isEmpty() == false) {
            EditorEvent event = eventQueue.poll();
            this.messageRecieved(event);
        }
    }

    public static void messageRecieved_s(EditorEvent event) {
        eventQueue.add(event);
    }

    @Override
    public void messageRecieved(EditorEvent event) {
        System.out.println("MessageRecieved:" + event.getClass());
        if (event instanceof ClearEvent) {
            clearLevel();
        } else if (event instanceof LoadEvent) {
            clearLevel();
            LoadEvent loadEvent = (LoadEvent) event;
            loadLevel(loadEvent.data.file_location);
        } else if (event instanceof SaveEvent) {
            SaveEvent saveEvent = (SaveEvent) event;
            saveLevel(saveEvent.data.file_location);
        } else if (event instanceof ModifyGetEvent) {
            // get level data in its current state
            ModifyGetEvent getEvent = (ModifyGetEvent) event;
            LevelData levelData = LevelData.generate(this.level);
            getEvent.data.setData(levelData);
        } else if (event instanceof ModifyRemoveEvent) {
            System.out.println("REMOVE EVENT:");
            World tempWorld = new World(new Vec2(0, 0));
            world = tempWorld;
            level.updateValuesRemove(((ModifyRemoveEvent) event).data.level_data);
            Level tempLevel = level.clone();
            clearLevel();
            loadLevel(tempLevel);
            world = tempWorld;
        } else if (event instanceof ModifySetEvent) {
            System.out.println("SET EVENT:" + ((ModifySetEvent<ModifySetEventData>) event).data.level_data);
            World tempWorld = new World(new Vec2(0, 0));
            world = tempWorld;
            level.updateValuesModify(((ModifySetEvent<ModifySetEventData>) event).data.level_data);
            Level tempLevel = level.clone();
            clearLevel();
            loadLevel(tempLevel);
            world = tempWorld;
        }
    }

    // </editor-fold>
}
