package game.objects;

import game.objects.GameObject;
import game.physics.PhysicsObject;
import game.render.DrawSprite;

public class Structure extends GameObject {

    public Structure(String name, PhysicsObject physicsObject, String fileLocation) {
        super(name, physicsObject, fileLocation);
    }

    private Structure(String name) {
        super(name);
    }

    public static Structure generateBlank(String name) {
        return new Structure(name);
    }

    public static Structure generateLegacy(String name, float[] location, float[] dimensions, String file_location) {
        Structure blank = generateBlank(name);
        PhysicsObject po = PhysicsObject.staticStructureObject(location, dimensions);
        blank.physicsObject = po;
        blank.renderObject = DrawSprite.generate2d(new double[]{0, 0}, new double[]{0, 0}, file_location);
        return blank;
    }

}
