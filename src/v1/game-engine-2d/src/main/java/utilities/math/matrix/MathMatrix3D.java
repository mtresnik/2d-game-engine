/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.utilities.math.matrix;

import java.util.ArrayList;
import main.java.utilities.math.vector.MathVector3D;

public class MathMatrix3D<T extends Number> extends MathMatrix<T, MathVector3D<T>> {

    @Override
    public void add(T... values) {
        this.add(3, values);
    }

    protected void addVectorValues(ArrayList<T> vectorValues) {
        this.vectors.add(new MathVector3D<T>(vectorValues.get(0), vectorValues.get(1), vectorValues.get(2)));
    }

}
