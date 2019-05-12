/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.utilities.math.matrix;

import java.util.ArrayList;
import main.java.utilities.math.vector.MathVector4D;

public class MathMatrix4D<T extends Number> extends MathMatrix<T, MathVector4D<T>> {

    @Override
    public void add(T... values) {
        this.add(4, values);
    }

    protected void addVectorValues(ArrayList<T> vectorValues) {
        if (vectorValues.size() < 4) {
            return;
        }
        this.vectors.add(new MathVector4D<T>(vectorValues.get(0), vectorValues.get(1), vectorValues.get(2), vectorValues.get(3)));
    }
    
    //TODO: Remove after implementation is complete.
    public static void main(String[] args) {
        MathMatrix1D<Double> dimensions = new MathMatrix1D();
        dimensions.add(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0);
        System.out.println(dimensions.toString());
        MathMatrix2D<Double> coordinates = new MathMatrix2D();
        coordinates.add(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0);
        System.out.println(coordinates.toString());
        MathMatrix3D<Double> velocities = new MathMatrix3D();
        velocities.add(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0);
        System.out.println(velocities.toString());
        MathMatrix4D<Double> positionTime = new MathMatrix4D();
        positionTime.add(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0);
        System.out.println(positionTime.toString());        
    }

}
