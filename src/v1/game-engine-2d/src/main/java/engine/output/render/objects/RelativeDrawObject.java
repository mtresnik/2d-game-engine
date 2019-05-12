package main.java.engine.output.render.objects;

import java.util.ArrayList;
import java.util.List;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.utilities.interfaces.RelativeInterface;
import main.java.utilities.math.vector.MathVector2D;

public abstract class RelativeDrawObject extends DrawObject implements RelativeInterface<DrawObject> {

    protected DrawObject objectRelative;
    protected List<DrawObject> childrenList = new ArrayList();
    protected MathVector2D<Double> relativePercentCoordinates;
    protected Double[] relativePercentDimensions;


    public RelativeDrawObject(String name, String fileLocation,
            MathVector2D<Double> relativeCoordinates, Double[] relativeDimensions,
            DrawObject... relativeArray) {
        super(name, fileLocation, new MathVector2D<Double>(0.0, 0.0), new Double[]{100.0, 100.0});
        this.initRelativePercentages(relativeCoordinates, relativeDimensions, relativeArray);
    }


    public RelativeDrawObject(String name, DrawFrame frame,
            MathVector2D<Double> percentCoordinates, Double[] percentDimensions,
            DrawObject... relativeArray) {
        super(name, frame, new MathVector2D<Double>(0.0, 0.0), new Double[]{100.0, 100.0});
        this.initRelativePercentages(percentCoordinates, percentDimensions, relativeArray);
    }


    private void initRelativePercentages(MathVector2D<Double> relativeCoordinates, Double[] relativeDimensions,
            DrawObject... relativeArray) {
        this.relativePercentCoordinates = new MathVector2D<Double>(relativeCoordinates.getX(), relativeCoordinates.getY());
        this.relativePercentDimensions = new Double[]{relativeDimensions[0], relativeDimensions[1]};
        try {
            this.objectRelative = relativeArray[0];
        } catch (ArrayIndexOutOfBoundsException AIOB_ex) {
            this.objectRelative = DrawObject.screen;
        }
        if (this.objectRelative != null) {
            this.objectRelative.addChild(this);
            this.updateAbsoluteCoordinatesDimensions();
        } else {
            // System.out.println("objectRelative is NULL for:" + this.toString());
        }
    }


    // TODO : Add relative pixel drawing
    //<editor-fold desc="Pixel Constructors">
    public RelativeDrawObject(String name, String location,
            MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        super(name, location, pixelCoordinates, pixelDimensions);
    }


    public RelativeDrawObject(String name, DrawFrame frame,
            MathVector2D<Integer> pixelCoordinates, Integer[] pixelDimensions) {
        super(name, frame, pixelCoordinates, pixelDimensions);
    }

    // </editor-fold>

    @Override
    public final DrawObject getObjectRelative() {
        return this.objectRelative;
    }


    @Override
    public final void setObjectRelative(DrawObject objectRelative) {
        this.objectRelative = objectRelative;
        this.updateAbsoluteCoordinatesDimensions();
    }


    public MathVector2D<Double> getRelativePercentCoordinates() {
        return relativePercentCoordinates;
    }


    public Double[] getRelativePercentDimensions() {
        return relativePercentDimensions;
    }


    public void setRelativePercentDimensions(Double[] relativePercentDimensions) {
        this.relativePercentDimensions = relativePercentDimensions;
        this.updateChildren();
    }


    public void setRelativePercentCoordinates(MathVector2D<Double> relativePercentCoordinates) {
        this.relativePercentCoordinates = relativePercentCoordinates;
        this.updateAbsoluteCoordinatesDimensions();
    }


    public void updateAbsoluteCoordinatesDimensions() {
        double xa1, ya1, wa1, ha1;
        MathVector2D<Double> parentCoordinates = this.objectRelative.getAbsolutePercentCoordinates();
        xa1 = parentCoordinates.getX();
        ya1 = parentCoordinates.getY();
        Double[] parentDimensions = this.objectRelative.getAbsolutePercentDimensions();
        wa1 = parentDimensions[0];
        ha1 = parentDimensions[1];
        // ---------------------------------------- //
        double xr2, yr2, wr2, hr2;
        xr2 = this.relativePercentCoordinates.getX();
        yr2 = this.relativePercentCoordinates.getY();
        wr2 = this.relativePercentDimensions[0];
        hr2 = this.relativePercentDimensions[1];
        double xa2, ya2, wa2, ha2;
        // System.out.println("Relatives:" + strings.genericToString(xr2, yr2, wr2, hr2));
        xa2 = (xr2 / 100.0) * wa1 + xa1;
        ya2 = (yr2 / 100.0) * ha1 + ya1;
        wa2 = (wr2 / 100.0) * wa1;
        ha2 = (hr2 / 100.0) * ha1;
        this.absolutePercentCoordinates.setX(xa2);
        this.absolutePercentCoordinates.setY(ya2);
        this.absolutePercentDimensions[0] = wa2;
        this.absolutePercentDimensions[1] = ha2;
        // System.out.println(strings.genericToString(xa2, ya2, wa2, ha2));
        this.updateChildren();
    }


    public void updateChildren() {
        for (DrawObject child : this.childrenList) {
            child.updateAbsoluteCoordinatesDimensions();
        }
    }


    @Override
    protected final void addChild(RelativeDrawObject child) {
        this.childrenList.add(child);
    }


    public List<DrawObject> getChildrenList() {
        return childrenList;
    }
    
    


}
