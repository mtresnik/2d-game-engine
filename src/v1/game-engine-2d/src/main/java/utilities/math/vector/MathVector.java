package main.java.utilities.math.vector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import main.java.utilities.math.operations.AdditionOperation;
import main.java.utilities.math.operations.DivisionOperation;
import main.java.utilities.math.operations.MultiplicationOperation;
import main.java.utilities.math.operations.RootOperation;
import main.java.utilities.math.operations.SubtractionOperation;

public abstract class MathVector<
                TYPE extends Number, PREV extends MathVector, CURR extends MathVector, NEXT extends MathVector> {

    protected final VectorOperation<TYPE, PREV, CURR, NEXT> addition, subtraction, multiplication, division;
    protected TYPE[] values;
    protected Long time;
    protected String units;


    public abstract CURR generate(Long time, TYPE... values);


    protected MathVector(TYPE... values) {
        this(System.currentTimeMillis(), values);
    }


    protected MathVector(Long time, TYPE... values) {
        this.values = values;
        this.initValues();
        this.addition = new VectorOperation(new AdditionOperation<TYPE>());
        this.subtraction = new VectorOperation(new SubtractionOperation<TYPE>());
        this.multiplication = new VectorOperation(new MultiplicationOperation<TYPE>());
        this.division = new VectorOperation(new DivisionOperation<TYPE>());
        this.setTime(time);
        this.units = "";
    }


    //<editor-fold desc="Math operations">
    public final CURR add(MathVector<TYPE, PREV, CURR, NEXT> vector2) {
        return this.addition.performBulkOperation(this, vector2);
    }


    public final CURR addAll(TYPE value2) {
        return this.addition.performUnitOperation(this, value2);
    }


    public final CURR subtract(MathVector<TYPE, PREV, CURR, NEXT> vector2) {
        return this.subtraction.performBulkOperation(this, vector2);
    }


    public final CURR subtractAll(TYPE value2) {
        return this.subtraction.performUnitOperation(this, value2);
    }


    /**
     * Multiplies each corresponding element together, never needed in
     * mathematics, but could be useful.
     *
     * @param vector2
     * @return
     */
    public final CURR multiply(MathVector<TYPE, PREV, CURR, NEXT> vector2) {
        return this.multiplication.performBulkOperation(this, vector2);
    }


    public final CURR multiplyAll(TYPE value2) {
        return this.multiplication.performUnitOperation(this, value2);
    }


    public final CURR divide(MathVector<TYPE, PREV, CURR, NEXT> vector2) {
        return this.division.performBulkOperation(this, vector2);
    }


    public final CURR divideAll(TYPE value2) {
        return this.division.performUnitOperation(this, value2);
    }


    public final TYPE getMagnitude() {
        TYPE selfDotProduct = MathVector.dot(this, this);
        RootOperation<TYPE> rOperation = new RootOperation();
        TYPE result = rOperation.performRoot(selfDotProduct, 0.5);
        return result;
    }


    public final CURR getUnitVector() {
        // Divide all by the magnitude
        TYPE magnitude = this.getMagnitude();
        MathVector<TYPE, PREV, CURR, NEXT> copy = this.clone();
        return copy.divideAll(magnitude);
    }


    public static final <T extends Number, K extends MathVector, V extends MathVector, L extends MathVector> T dot(MathVector<T, K, V, L> vector1, MathVector<T, K, V, L> vector2) {
        MathVector<T, K, V, L> multiplicationResult = vector1.multiply(vector2);
        T summationResult = sum(vector1, multiplicationResult);
        return summationResult;
    }


    private static final <T extends Number, K extends MathVector, V extends MathVector, L extends MathVector> T sum(MathVector<T, K, V, L> vector1, MathVector<T, K, V, L> vector2) {
        T sum = (T) (Number) 0.0;
        for (int index = 0; index < vector2.toArray().length; index++) {
            sum = vector1.addition.getOperation().performOperation(sum, vector2.toArray()[index]);
        }
        return sum;
    }
    // </editor-fold>


    private void initValues() {
        Number[] tempValues = this.values;
        TYPE[] newValues = (TYPE[]) Array.newInstance(this.values[0].getClass(), this.values.length);
        for (int index = 0; index < newValues.length; index++) {
            newValues[index] = (TYPE) tempValues[index];
        }
        this.values = newValues;
    }


    @Override
    public final CURR clone() {
        return this.generate(this.getTime(), Arrays.copyOf(values, values.length));
    }


    @Override
    public final String toString() {
        String retString = "";
        retString += "< ";
        char[] charMap = new char[]{'x', 'y', 'z', 't'};
        for (int index = 0; index < this.values.length; index++) {
            retString += charMap[index] + ":" + this.values[index];
            if (index != this.values.length - 1) {
                retString += ", ";
            }
        }
        retString += ">\t";
        retString += "time(ms):" + this.getTime();
        return retString;
    }


    public abstract PREV stepDown();


    public final NEXT stepUp() {
        return this.stepUp(zero());
    }


    public abstract NEXT stepUp(TYPE value);


    public final TYPE[] toArray() {
        return this.values;
    }


    public static final <TYPE extends Number> TYPE zero() {
        return (TYPE) (Number) 0.0;
    }


    public final <NEWTYPE extends Number> CURR cast(MathVector<NEWTYPE, PREV, CURR, NEXT> resultVector) {
        NEWTYPE n = null;
        NEWTYPE[] tempValues = (NEWTYPE[]) Array.newInstance(resultVector.getMagnitude().getClass(), this.values.length);
        ArrayList<NEWTYPE> tempList = new ArrayList();
        TYPE[] values = this.values;
        for (int index = 0; index < this.values.length; index++) {
            tempList.add(castIndividual(values[index], resultVector.toArray()[0]));
        }
        tempValues = tempList.toArray((NEWTYPE[]) Array.newInstance(tempList.get(0).getClass(), tempList.size()));
        return resultVector.generate(resultVector.getTime(), tempValues);
    }


    public final <NEWTYPE extends Number> NEWTYPE castIndividual(TYPE object, NEWTYPE object1) {
        if (object1 instanceof Integer) {
            return (NEWTYPE) new Integer(object.intValue());
        } else if (object1 instanceof Double) {
            return (NEWTYPE) new Double(object.doubleValue());
        } else if (object1 instanceof Long) {
            return (NEWTYPE) new Long(object.longValue());
        } else if (object1 instanceof Float) {
            return (NEWTYPE) new Float(object.floatValue());
        }
        return null;
    }


    public Long getTime() {
        return time;
    }


    public void setTime(Long time) {
        this.time = time;
    }


    public String getUnits() {
        return units;
    }


    public void setUnits(String units) {
        this.units = units;
    }


}
