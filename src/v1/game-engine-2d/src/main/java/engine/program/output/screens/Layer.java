package main.java.engine.program.output.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.java.engine.output.render.interfaces.Drawable;
import main.java.utilities.math.vector.MathVector2D;

public class Layer implements Drawable {

    private List<Drawable> layerElements;


    public Layer(Drawable... elements) {
        // Initializes layerElements to passed elements.
        this.layerElements = new ArrayList(Arrays.asList(elements));
    }


    @Override
    public void draw() {
        for (Drawable element : layerElements) {
            element.draw();
        }
    }


    public void addElement(Drawable element) {
        this.addElements(element);
    }


    public void addElements(Drawable... elements) {
        for (Drawable element : elements) {
            this.layerElements.add(element);
        }
    }


    public boolean contains(Drawable element) {
        return layerElements.contains(element);
    }


    @Override
    public boolean inBounds(MathVector2D<Double> detectionPoint) {
        for(Drawable element : this.layerElements){
            if(element.inBounds(detectionPoint)){
                return true;
            }
        }
        return false;
    }


    public void removeElement(Drawable element) {
        this.removeElements(element);
    }


    public void removeElements(Drawable... elements) {
        for (Drawable element : elements) {
            this.layerElements.remove(element);
        }
    }


    public void removeAll() {
        layerElements.clear();
    }


    public List<Drawable> getLayerElements() {
        return layerElements;
    }


}
