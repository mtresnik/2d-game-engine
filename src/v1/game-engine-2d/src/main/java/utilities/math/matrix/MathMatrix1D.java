package main.java.utilities.math.matrix;

import java.util.ArrayList;
import main.java.utilities.math.vector.MathVector1D;

public class MathMatrix1D<T extends Number> extends MathMatrix<T, MathVector1D<T>> {

    @Override
    protected void add(T... values) {
        this.add(1, values);
    }

    @Override
    protected void addVectorValues(ArrayList<T> vectorValues) {
        this.vectors.add(new MathVector1D<T>(vectorValues.get(0)));
    }
}
