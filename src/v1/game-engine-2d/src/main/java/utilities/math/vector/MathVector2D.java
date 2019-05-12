package main.java.utilities.math.vector;

public class MathVector2D<TYPE extends Number> extends MathVector<TYPE, MathVector1D, MathVector2D, MathVector3D> {

    protected TYPE x;
    protected TYPE y;


    public MathVector2D(Long time, TYPE x, TYPE y) {
        super(time, x, y);
        this.x = x;
        this.y = y;
    }


    public MathVector2D(TYPE x, TYPE y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }


    @Override
    public MathVector2D<TYPE> generate(Long time, TYPE[] values) {
        return new MathVector2D<TYPE>(time, values[0], values[1]);
    }


    public TYPE getX() {
        return x;
    }


    public TYPE getY() {
        return y;
    }


    public void setX(TYPE x) {
        this.x = x;
    }


    public void setY(TYPE y) {
        this.y = y;
    }


    @Override
    public MathVector1D<TYPE> stepDown() {
        return new MathVector1D(this.getTime(), this.getX());
    }


    @Override
    public MathVector3D stepUp(TYPE value) {
        return new MathVector3D(this.getTime(), this.getX(), this.getY(), value);
    }


}
