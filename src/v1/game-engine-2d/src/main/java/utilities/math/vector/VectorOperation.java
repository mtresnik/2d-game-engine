package main.java.utilities.math.vector;

import main.java.utilities.math.vector.MathVector;
import java.util.ArrayList;
import java.util.Arrays;
import main.java.utilities.math.operations.NumberOperation;

public final class VectorOperation<TYPE extends Number, PREV extends MathVector, CURR extends MathVector, NEXT extends MathVector> {

    protected final NumberOperation<TYPE> operation;


    public VectorOperation(NumberOperation<TYPE> operation) {
        this.operation = operation;
    }


    public final CURR performBulkOperation(MathVector<TYPE, PREV, CURR, NEXT> vector1, MathVector<TYPE, PREV, CURR, NEXT> vector2) {
        if (vector1.toArray().length != vector2.toArray().length) {
            throw new IllegalArgumentException("Values cannot differ in length.");
        }
        ArrayList<TYPE> tempList = new ArrayList<TYPE>();
        for (int index = 0; index < vector1.toArray().length; index++) {
            TYPE firstValue = vector1.toArray()[index];
            TYPE secondValue = vector2.toArray()[index];
            TYPE resultValue = operation.performOperation(firstValue, secondValue);
            tempList.add(resultValue);
        }
        TYPE[] tempArray = tempList.toArray(Arrays.copyOf(vector1.toArray(), vector1.toArray().length));
        return vector1.generate(vector2.getTime(), tempArray);
    }


    public final CURR performUnitOperation(MathVector<TYPE, PREV, CURR, NEXT> vector1, TYPE value) {
        ArrayList<TYPE> tempList = new ArrayList<TYPE>();
        for (int index = 0; index < vector1.toArray().length; index++) {
            TYPE firstValue = vector1.toArray()[index];
            TYPE secondValue = value;
            TYPE resultValue = operation.performOperation(firstValue, secondValue);
            tempList.add(resultValue);
        }
        TYPE[] tempArray = tempList.toArray(Arrays.copyOf(vector1.toArray(), vector1.toArray().length));
        return vector1.generate(vector1.getTime(), tempArray);
    }


    public NumberOperation<TYPE> getOperation() {
        return operation;
    }


}
