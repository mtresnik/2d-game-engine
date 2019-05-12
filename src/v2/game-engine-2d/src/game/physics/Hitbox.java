package game.physics;

import jbox2d.common.Vec2;
import util.math.Dimensions2d;

public final class Hitbox {

    public final Vec2 tPos;
    public final Dimensions2d tDim;
    public static final Hitbox DEFAULT = new Hitbox(new float[]{0.5f, 0.5f}, new float[]{1.0f,1.0f});

    public Hitbox(Vec2 tPos, Dimensions2d tDim) {
        this.tPos = tPos;
        this.tDim = tDim;
    }

    public Hitbox(float[] coord, float[] dim) {
        this(coord[0], coord[1], dim[0], dim[1]);
    }

    public Hitbox(float x, float y, float w, float h) {
        this(new Vec2(x, y), new Dimensions2d(w, h));
    }

    @Override
    public String toString() {
        return "Hitbox{" + "tPos=" + tPos + ", tDim=" + tDim + '}';
    }

    public float[] location() {
        return new float[]{this.tPos.x, this.tPos.y};
    }

    public float[] dimensions() {
        return new float[]{this.tDim.width(), this.tDim.height()};
    }

}
