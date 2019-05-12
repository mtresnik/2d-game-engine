package game.objects;

import game.globals;
import game.objects.GameObject;
import static game.objects.Player.PLAYER_SPEED;
import static game.objects.Player.generateBlank;
import game.physics.Hitbox;
import game.physics.PhysicsObject;
import game.render.DrawSprite;

public class Background extends GameObject {

    public Background(String name, PhysicsObject physicsObject, String file_location) {
        super(name, physicsObject, file_location);
    }
    
    private Background(String name){
        super(name);
    }
    
    public static Background generateBlank(String name){
        return new Background(name);
    }
    
    public static Background generateLegacy(String name, float[] coordinates, float[] dimensions, String fileLoc){
        Background retBackground = generateBlank(name);
        PhysicsObject physicsObject = PhysicsObject.staticObject(globals.MASK_STRUCTURE, coordinates, dimensions);
        retBackground.physicsObject = physicsObject;
        retBackground.renderObject = DrawSprite.generate2d(new double[]{0, 0}, new double[]{0, 0}, fileLoc);
        return retBackground;
    }

}
