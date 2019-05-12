package game.objects;

import game.Game;
import game.globals;
import game.physics.Hitbox;
import static game.Game.forAllKeymapTF;
import jbox2d.common.Vec2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import game.physics.PhysicsObject;
import game.render.DrawSprite;

public class Player extends MovableGameObject {

    public static final Vec2 PLAYER_SPEED = new Vec2(4f, 4f);

    public Player(String name, PhysicsObject physicsObject, String fileLocation) {
        super(name, physicsObject, PLAYER_SPEED, fileLocation);
    }
    
    public Player(String name, PhysicsObject physicsObject, Vec2 movementSpeed, String fileLocation) {
        super(name, physicsObject, movementSpeed, fileLocation);
    }

    private Player(String name) {
        super(name);
    }

    @Override
    public int mov_x() {
        if (Game.forAllKeymapTF(new int[]{GLFW_KEY_A}, new int[]{GLFW_KEY_D})) {
            return -1;
        } else if (Game.forAllKeymapTF(new int[]{GLFW_KEY_D}, new int[]{GLFW_KEY_A})) {
            return 1;
        } else if (Game.forAllKeymapTF(new int[]{}, new int[]{GLFW_KEY_A, GLFW_KEY_D})) {
            return 0;
        }
        return 0;
    }

    @Override
    public int mov_y() {
        if (forAllKeymapTF(new int[]{GLFW_KEY_W}, new int[]{GLFW_KEY_S})) {
//            System.out.println("up");
            return 1;
        } else if (forAllKeymapTF(new int[]{GLFW_KEY_S}, new int[]{GLFW_KEY_W})) {
//            System.out.println("down");
            return -1;
        } else if (forAllKeymapTF(new int[]{}, new int[]{GLFW_KEY_W, GLFW_KEY_S})) {
            return 0;
        }
        return 0;
    }

    public static Player generateBlank(String name) {
        return new Player(name);
    }

    public static Player generateLegacy(String name, float[] coordinates, float[] dimensions, Hitbox hitbox, String fileLoc) {
        Player retPlayer = generateBlank(name);
        PhysicsObject physicsObject = PhysicsObject.dynamicPhysicsObject(globals.MASK_PLAYER, hitbox, coordinates, dimensions);
        retPlayer.movementSpeed = PLAYER_SPEED;
        retPlayer.physicsObject = physicsObject;
        retPlayer.renderObject = DrawSprite.generate2d(new double[]{0, 0}, new double[]{0, 0}, fileLoc);
        return retPlayer;
    }

}
