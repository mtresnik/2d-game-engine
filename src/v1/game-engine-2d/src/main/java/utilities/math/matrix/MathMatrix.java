package main.java.utilities.math.matrix;

import main.java.utilities.math.vector.MathVector;
import java.util.ArrayList;

public abstract class MathMatrix<T extends Number, K extends MathVector> {

    protected ArrayList<K> vectors;


    //Default constructor.
    public MathMatrix() {
        this.vectors = new ArrayList();
    }


    //Constructor where a width is passed in.
    public MathMatrix(int width, T... values) {
        this.add(width, values);
    }


    //Constructor where a NumericalVector is passed in.
    public MathMatrix(K... vectors) {
        this.add(vectors);
    }


    protected abstract void add(T... values);


    protected abstract void addVectorValues(ArrayList<T> vectorValues);


    public final void add(K... vectors) {
        for (K vector : vectors) {
            this.vectors.add(vector);
        }
    }


    protected final void add(int width, T... values) {
        int numberOfVectors = values.length / width;

        for (int heightIndex = 0; heightIndex < numberOfVectors; heightIndex++) {
            ArrayList<T> tempList = new ArrayList();
            for (int widthIndex = 0; widthIndex < width; widthIndex++) {
                tempList.add(values[heightIndex * width + widthIndex]);
            }
            this.addVectorValues(tempList);
        }
    }


    @Override
    public final String toString() {
        String retString = "[\n";
        for (int index = 0; index < vectors.size(); index++) {
            retString += vectors.get(index).toString();
            if (index < vectors.size() - 1) {
                retString += ", ";
                retString += "\n";
            }
        }
        retString += "\n]";
        return retString;
    }


}
