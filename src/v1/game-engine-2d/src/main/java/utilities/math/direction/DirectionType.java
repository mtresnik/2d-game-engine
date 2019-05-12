package main.java.utilities.math.direction;

public enum DirectionType {

    UP(0.0),
    LEFT(90.0),
    DOWN(180.0),
    RIGHT(270.0);
    private double theta;


    private DirectionType(double theta) {
        this.theta = theta;
    }


}
