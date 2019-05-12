/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.utilities.math.matrix;

import java.util.ArrayList;
import main.java.utilities.math.vector.MathVector2D;

public class MathMatrix2D<T extends Number> extends MathMatrix<T, MathVector2D<T>> {

    @Override
    public void add(T... values) {
        this.add(2, values);
    }

    protected void addVectorValues(ArrayList<T> vectorValues) {
        this.vectors.add(new MathVector2D<T>(vectorValues.get(0), vectorValues.get(1)));
    }
}
