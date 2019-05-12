package main.java.utilities.math.vector;

import main.java.utilities.math.operations.NumberOperation;

public class MathVector3D<TYPE extends Number> extends MathVector<TYPE, MathVector2D, MathVector3D, MathVector4D> {

    protected TYPE x;
    protected TYPE y;
    protected TYPE z;


    public MathVector3D(Long time, TYPE x, TYPE y, TYPE z) {
        super(time, x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public MathVector3D(TYPE x, TYPE y, TYPE z) {
        super(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public MathVector3D generate(Long time, TYPE[] values) {
        return new MathVector3D<TYPE>(time, values[0], values[1], values[2]);
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


    public void setX(TYPE x) {
        this.x = x;
    }


    public void setY(TYPE y) {
        this.y = y;
    }


    public void setZ(TYPE z) {
        this.z = z;
    }


    public static <T extends Number> MathVector3D<T> cross(MathVector3D<T> vector1, MathVector3D<T> vector2) {
        T[] u_vector = vector1.toArray();
        T[] v_vector = vector2.toArray();
        NumberOperation<T> unitMultiplication = vector1.multiplication.getOperation();
        NumberOperation<T> unitSubtraction = vector1.subtraction.getOperation();
        T newX, newY, newZ;
        {
            /*
                    u_vector  x   v_vector =
                    <   u2*v3 - u3*v2      ,
                        u3*v1 - u1*v3       ,
                        u1*v2 - u2*v1       >
             */
            // Delcare all u_vector component values.
            T u1 = u_vector[0], u2 = u_vector[1], u3 = u_vector[2];
            // Declare all v_vector component values.
            T v1 = v_vector[0], v2 = v_vector[1], v3 = v_vector[2];
            // Declare all needed(*) multiplication values.
            T u1v2 = unitMultiplication.performOperation(u1, v2);
            T u1v3 = unitMultiplication.performOperation(u1, v3);
            T u2v1 = unitMultiplication.performOperation(u2, v1);
            T u2v3 = unitMultiplication.performOperation(u2, v3);
            T u3v1 = unitMultiplication.performOperation(u3, v1);
            T u3v2 = unitMultiplication.performOperation(u3, v2);
            // Perform the subtraction of the various multiplication values.
            newX = unitSubtraction.performOperation(u2v3, u3v2);
            newY = unitSubtraction.performOperation(u3v1, u1v3);
            newZ = unitSubtraction.performOperation(u1v2, u2v1);
        }
        return new MathVector3D<T>(newX, newY, newZ);
    }


    @Override
    public MathVector2D<TYPE> stepDown() {
        return new MathVector2D(this.getTime(), this.getX(), this.getY());
    }


    @Override
    public MathVector4D stepUp(TYPE value) {
        return new MathVector4D(this.getTime(), this.getX(), this.getY(), this.getZ(), value);
    }


}
