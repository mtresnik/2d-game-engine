package main.java.engine.program.output.ui_tools;

import main.java.engine.output.render.interfaces.Drawable;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects._2d.DrawTexture;
import java.awt.Dimension;
import main.java.utilities.interfaces.Grid;
import main.java.utilities.math.vector.MathVector2D;

public class UIGrid<T extends Drawable> extends Grid<T> implements Drawable {

    double padding;
    DrawObject background;
    Dimension gridDimension;


    // TODO : Replace most of DrawObject with a DrawMesh
    public UIGrid(Dimension gridDimension, double padding, MathVector2D<Double> percentCoordinates, Double[] percentDimensions) {
        this.gridDimension = gridDimension;
        this.padding = padding;
        this.background = new DrawTexture("ui_grid" + this.toString(), DrawFrame.EMPTYFRAME, percentCoordinates, percentDimensions);
    }


    protected UIGrid() {
    }


    @Override
    public void draw() {

    }


    @Override
    public T[][] getAllElements() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public double getPadding() {
        return padding;
    }


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {
        return false;
    }


}
