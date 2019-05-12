package game.objects;

import game.Game;
import game.Movable;
import game.objects.GameObject;
import static game.Game.forAllKeymapTF;
import jbox2d.common.Vec2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class Camera implements Movable {

    public float x, y;
    public GameObject attached;
    public Vec2 screenScrollSpeed;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
        screenScrollSpeed = new Vec2(1f, 1f);
    }

    public void update() {
        if (attached == null) {
            updateMovements();
            return;
        }
        float[] location_relative = attached.location_relative();
        this.x = location_relative[0];
        this.y = location_relative[1];
    }

    public void attach(GameObject toAttach) {
        this.attached = toAttach;
    }

    public void detach() {
        this.attached = null;
    }

    @Override
    public Vec2 updateSpeed() {
        return screenScrollSpeed;
    }

    @Override
    public int mov_y() {
        if (forAllKeymapTF(new int[]{GLFW_KEY_UP}, new int[]{GLFW_KEY_DOWN})) {
            return 1;
        } else if (forAllKeymapTF(new int[]{GLFW_KEY_DOWN}, new int[]{GLFW_KEY_UP})) {
            return -1;
        } else if (forAllKeymapTF(new int[]{}, new int[]{GLFW_KEY_UP, GLFW_KEY_DOWN})) {
            return 0;
        }
        return 0;
    }

    @Override
    public int mov_x() {
        if (Game.forAllKeymapTF(new int[]{GLFW_KEY_LEFT}, new int[]{GLFW_KEY_RIGHT})) {
            return -1;
        } else if (Game.forAllKeymapTF(new int[]{GLFW_KEY_RIGHT}, new int[]{GLFW_KEY_LEFT})) {
            return 1;
        } else if (Game.forAllKeymapTF(new int[]{}, new int[]{GLFW_KEY_LEFT, GLFW_KEY_RIGHT})) {
            return 0;
        }
        return 0;
    }

    @Override
    public void goUp() {
        this.y += this.screenScrollSpeed.y * 1 / 60.0f;
    }

    @Override
    public void goDown() {
        this.y -= this.screenScrollSpeed.y * 1 / 60.0f;
    }

    @Override
    public void goLeft() {
        this.x -= this.screenScrollSpeed.x * 1 / 60.0f;
    }

    @Override
    public void goRight() {
        this.x += this.screenScrollSpeed.x * 1 / 60.0f;
    }

    @Override
    public void setInternalVelocity(Vec2 vel) {
    }

    @Override
    public Vec2 internalVelocity() {
        return this.screenScrollSpeed;
    }

}
