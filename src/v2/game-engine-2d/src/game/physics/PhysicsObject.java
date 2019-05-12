package game.physics;

import game.Game;
import game.globals;
import jbox2d.collision.shapes.PolygonShape;
import jbox2d.common.Vec2;
import jbox2d.dynamics.Body;
import jbox2d.dynamics.BodyDef;
import jbox2d.dynamics.BodyType;
import jbox2d.dynamics.FixtureDef;
import util.Initializable;

public class PhysicsObject implements Initializable {

    public Body frameBody;
    public Body hitboxBody;
    public Hitbox hitbox;
    public BodyType type;
    public float restitution;
    public short MASK;
    public float[] location_meters;
    public float[] dimensions_meters;

    public PhysicsObject(BodyType type, float restitution, final short MASK, float[] location_meters, float[] dimensions_meters) {
        this(type, restitution, MASK, null, location_meters, dimensions_meters);
    }

    public PhysicsObject(BodyType type, float restitution, final short MASK, Hitbox hitbox, float[] location_meters, float[] dimensions_meters) {
        this.type = type;
        this.restitution = restitution;
        this.MASK = MASK;
        if (hitbox == null) {
            this.hitbox = new Hitbox(0.0f, 0.0f, 1.0f, 1.0f);
        } else {
            this.hitbox = hitbox;
        }
        this.location_meters = location_meters;
        this.dimensions_meters = dimensions_meters;
    }

    @Override
    public void init() {
        this.initFrameBody();
        this.initHitboxBody();
    }

    private void initFrameBody() {
        BodyDef frameBoxDef = new BodyDef();
        if (this.frameBody != null) {
            Vec2 pos = this.frameBody.getPosition();
            frameBoxDef.position.set(pos.x, pos.y);
        } else {
            frameBoxDef.position.set(location_meters[0], location_meters[1]);
        }
        frameBoxDef.type = this.type;
        PolygonShape frameBoxShape = new PolygonShape();
        frameBoxShape.setAsBox(dimensions_meters[0], dimensions_meters[1]);
        frameBody = Game.world.createBody(frameBoxDef);
        FixtureDef frameBoxFixture = new FixtureDef();
        frameBoxFixture.density = 1;
        frameBoxFixture.restitution = restitution;
        frameBoxFixture.filter.categoryBits = globals.MASK_BACKGROUND;
        frameBoxFixture.shape = frameBoxShape;
        frameBody.createFixture(frameBoxFixture);
        frameBody.setFixedRotation(true);
    }

    private void initHitboxBody() {
        BodyDef hitboxDef = new BodyDef();
        if (this.hitboxBody != null) {
            Vec2 pos = this.hitboxBody.getPosition();
            hitboxDef.position.set(pos.x, pos.y);
        } else {
            float c1x, c1y;
            c1x = hitbox.tPos.x * dimensions_meters[0] + location_meters[0];
            c1y = hitbox.tPos.y * dimensions_meters[1] + location_meters[1];
            hitboxDef.position.set(c1x, c1y);
        }
        hitboxDef.type = this.type;
        PolygonShape hitboxShape = new PolygonShape();
        hitboxShape.setAsBox(hitbox.tDim.width() * dimensions_meters[0], hitbox.tDim.height() * dimensions_meters[1]);
        hitboxBody = Game.world.createBody(hitboxDef);
        FixtureDef hitboxFixture = new FixtureDef();
        hitboxFixture.density = 1;
        hitboxFixture.restitution = restitution;
        hitboxFixture.filter.categoryBits = MASK;
        hitboxFixture.shape = hitboxShape;
        hitboxBody.createFixture(hitboxFixture);
        hitboxBody.setFixedRotation(true);
    }

    public float[] getLocationMeters() {
        Vec2 bodyPosition = frameBody.getPosition();
        if (hitboxBody != null && hitbox != null) {
            Vec2 hitboxPosition = hitboxBody.getPosition();
            float c1x = hitboxPosition.x;
            float c1y = hitboxPosition.y;

            float cx = c1x - this.hitbox.tPos.x * dimensions_meters[0];
            float cy = c1y - this.hitbox.tPos.y * dimensions_meters[1];

            bodyPosition = new Vec2(cx, cy);
        }
        return new float[]{bodyPosition.x, bodyPosition.y};
    }

    public float[] getDimensionsMeters() {
        return dimensions_meters;
    }

    public static PhysicsObject staticStructureObject(float[] location_meters, float[] dimensions_meters) {
        return new PhysicsObject(BodyType.STATIC, 0.0f, globals.MASK_STRUCTURE, location_meters, dimensions_meters);
    }

    public static PhysicsObject staticObject(final short MASK, float[] location_meters, float[] dimensions_meters) {
        return new PhysicsObject(BodyType.STATIC, 0.0f, MASK, location_meters, dimensions_meters);
    }

    public static PhysicsObject dynamicPhysicsObject(final short MASK, Hitbox hitbox, float[] location_meters, float[] dimensions_meters) {
        return new PhysicsObject(BodyType.DYNAMIC, 0.0f, MASK, hitbox, location_meters, dimensions_meters);
    }

    public static PhysicsObject dynamicPhysicsObject(final short MASK, float[] location_meters, float[] dimensions_meters) {
        return new PhysicsObject(BodyType.DYNAMIC, 0.0f, MASK, location_meters, dimensions_meters);
    }

}
