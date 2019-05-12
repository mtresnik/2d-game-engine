package main.java.utilities.math.vector;

public class MathVector1D<TYPE extends Number> extends MathVector<TYPE, MathVector1D, MathVector1D, MathVector2D> {

    protected TYPE x;


    public MathVector1D(Long time, TYPE x) {
        super(time, x);
        this.x = x;
    }


    public MathVector1D(TYPE x) {
        super(x);
        this.x = x;
    }


    @Override
    public MathVector1D<TYPE> generate(Long time, TYPE[] values) {
        return new MathVector1D<TYPE>(time, values[0]);
    }


    public TYPE getX() {
        return this.x;
    }


    public void setX(TYPE x) {
        this.x = x;
    }


    @Override
    public MathVector1D<TYPE> stepDown() {
        return this.clone();
    }


    @Override
    public MathVector2D<TYPE> stepUp(TYPE value) {
        return new MathVector2D(this.getTime(), this.getX(), value);
    }


}
