package main.java.utilities.math.vector;

public class MathVector4D<TYPE extends Number> extends MathVector<TYPE, MathVector3D, MathVector4D, MathVector4D> {

    protected TYPE x;
    protected TYPE y;
    protected TYPE z;
    protected TYPE t;


    public MathVector4D(Long time, TYPE x, TYPE y, TYPE z, TYPE t) {
        super(time, x, y, z, t);
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }


    public MathVector4D(TYPE x, TYPE y, TYPE z, TYPE t) {
        super(x, y, z, t);
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }


    @Override
    public MathVector4D generate(Long time, TYPE[] values) {
        return new MathVector4D<TYPE>(time, values[0], values[1], values[2], values[3]);
    }


    public TYPE getX() {
        return x;
    }


    public TYPE getY() {
        return y;
    }


    public TYPE getZ() {
        return z;
    }


    public TYPE getT() {
        return t;
    }


    public void setX(TYPE x) {
        this.x = x;
    }


    public void setY(TYPE y) {
        this.y = y;
    }


    public void setZ(TYPE z) {
        this.z = z;
    }


    public void setT(TYPE t) {
        this.t = t;
    }


    @Override
    public MathVector3D<TYPE> stepDown() {
        return new MathVector3D(this.getTime(), this.getX(), this.getY(), this.getZ());
    }


    @Override
    public MathVector4D<TYPE> stepUp(TYPE value) {
        return this.clone();
    }


}
