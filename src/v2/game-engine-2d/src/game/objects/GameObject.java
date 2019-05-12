package game.objects;

import game.Game;
import game.logic.LogicObject;
import util.Updatable;
import game.render.Display;
import game.render.Renderable;
import game.physics.PhysicsObject;
import game.render.RenderObject;

public abstract class GameObject implements Renderable, Updatable {

    public String name;
    public RenderObject renderObject;
    public PhysicsObject physicsObject;
    public LogicObject logicObject;

    private String fileLocation;

    public GameObject(String name, PhysicsObject physicsObject, String fileLocation) {
        this.name = name;
        this.fileLocation = fileLocation;
        this.physicsObject = physicsObject;
        init();
    }

    protected GameObject(String name) {
        this.name = name;
    }

    private void init() {
        initPhysicsObject();
        initRenderObject();
    }

    public void initPhysicsObject() {
        this.physicsObject.init();
    }

    private void initRenderObject() {
        float[] dimensions_pixels = Game.metersToPixels(this.physicsObject.dimensions_meters);
        float[] location_pixels = Game.metersToPixels(this.physicsObject.location_meters);
        double[] dimensions_relative = Display.pixelsToRelative(dimensions_pixels[0], dimensions_pixels[1]);
        double[] location_relative = Display.pixelsToRelative(location_pixels[0], location_pixels[1]);
        this.renderObject = RenderObject.generate2d(location_relative, dimensions_relative, fileLocation);
    }

    @Override
    public void update() {
        float[] location_relative = this.location_relative();
        this.renderObject.setPosition(location_relative[0] - Game.camera.x, location_relative[1] - Game.camera.y);
    }

    public float[] location_relative() {
        float[] physicsPosition = this.physicsObject.getLocationMeters();
        float[] location_pixels = Game.metersToPixels(physicsPosition);
        double[] location_relative = Display.pixelsToRelative(location_pixels[0], location_pixels[1]);
        return new float[]{(float) location_relative[0], (float) location_relative[1]};
    }

    @Override
    public void render() {
        this.renderObject.render();
    }

}
