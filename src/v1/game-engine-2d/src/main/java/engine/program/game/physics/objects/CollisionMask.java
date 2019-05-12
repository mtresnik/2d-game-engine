package main.java.engine.program.game.physics.objects;

import java.util.Arrays;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects._2d.TextureLoader;
import main.java.utilities.arrays.arrays;

public class CollisionMask {

    private DrawFrame reference_frame;
    private int[/*n*/][/*2*/] collision_points;
    private int complexity;

    private static final double[][] ONE = new double[][]{{0.0, 0.0}, {1.0, 0.0}, {1.0, 1.0}, {0.0, 1.0}},
            TWO = arrays.union(ONE, new double[][]{{0.5, 0.0}, {1.0, 0.5}, {0.5, 1.0}, {0.0, 0.5}}),
            THREE = arrays.union(TWO, new double[][]{{0.25, 0.0}, {0.75, 0.0}, {1.0, 0.25}, {1.0, 0.75}, {0.75, 1.0}, {0.25, 1.0}, {0.0, 0.75}, {0.0, 0.25}});


    public CollisionMask(DrawFrame reference_frame) {
        this(reference_frame, 3);
    }


    public CollisionMask(DrawFrame reference_frame, int complexity) {
        if (reference_frame == null || complexity < 1) {
            throw new IllegalArgumentException(
                    "Bad Combination ["
                    + reference_frame
                    + ", " + complexity
                    + "] \n DrawFrame must be non-null and complexity must be greater than zero.");
        }
        this.reference_frame = reference_frame;
        this.complexity = complexity;
        this.initCollisionPoints();
    }


    // HELPER METHOD
    private void initCollisionPoints() {
        double[][] normalized;
        switch (this.complexity) {
            case 1:
                normalized = ONE;
                break;
            case 2:
                normalized = TWO;
                break;
            case 3:
                normalized = THREE;
                break;
            default:
                throw new IllegalArgumentException("BAD COMPLEXITY:" + this.complexity);
        }
        this.collision_points = new int[normalized.length][2];
        final int w = this.reference_frame.getWidth(), h = this.reference_frame.getHeight();
        for (int index = 0; index < this.collision_points.length; index++) {
            this.collision_points[index] = new int[]{
                (int) (w * normalized[index][0]),
                (int) (h * normalized[index][1])
            };
            int x = this.collision_points[index][0], y = this.collision_points[index][1];
            this.collision_points[index][0] = (x >= w) ? w - 1 : x;
            this.collision_points[index][1] = (y >= h) ? h - 1 : y;
        }
        System.out.println(Arrays.deepToString(this.collision_points));
        this.narrowCollisionPoints();
        System.out.println(Arrays.deepToString(this.collision_points));
    }


    private void narrowCollisionPoints() {
        double[] center_pixel = new double[]{this.reference_frame.getWidth() / 2.0, this.reference_frame.getHeight() / 2.0};
        for (int[] pixel_coordinate : this.collision_points) {
            double[] transformation = arrays.subtractArrays(center_pixel, pixel_coordinate);
            double magnitude = arrays.findMagnitude(transformation);
            double[] direction = arrays.scale(transformation, 1.0 / magnitude);
            double[] pixel_coordinate_clone = arrays.castToDoubleArray(pixel_coordinate);
            while (hasCollided(arrays.castToIntArray(pixel_coordinate_clone)) == false) {
                pixel_coordinate_clone = arrays.addArrays(pixel_coordinate_clone, direction);
            }
            pixel_coordinate[0] = (int) pixel_coordinate_clone[0];
            pixel_coordinate[1] = (int) pixel_coordinate_clone[1];
        }
    }


    // Helper Method
    private boolean hasCollided(int[] pixel_coordinate) {
        if (pixel_coordinate == null || pixel_coordinate.length != 2) {
            throw new IllegalArgumentException();
        }
        byte[][][] pixels = this.reference_frame.pixels;
        System.out.println(Arrays.toString(pixels[pixel_coordinate[0]][pixel_coordinate[1]]));
        if (pixels[pixel_coordinate[0]][pixel_coordinate[1]][3] == (byte) 0) {
            return false;
        }
        return true;
    }


    public int[][] getCollision_points() {
        return collision_points;
    }


    public DrawFrame getReference_frame() {
        return reference_frame;
    }


    public int getComplexity() {
        return complexity;
    }


    public void setReference_frame(DrawFrame reference_frame) {
        this.reference_frame = reference_frame;
    }


    public DrawFrame generateVisualization() {
        final int w = this.reference_frame.getWidth(), h = this.reference_frame.getHeight();
        byte[][][] pixels = new byte[w][h][4];
        for (int[] pixel_coordinate : this.collision_points) {
            pixels[pixel_coordinate[0]][pixel_coordinate[1]] = new byte[]{(byte) 255, (byte) 0, (byte) 255, (byte) 255};
        }
        DrawFrame frameOfAffectedPixels = TextureLoader.loadByPixels(pixels);
        return frameOfAffectedPixels;
    }


}
