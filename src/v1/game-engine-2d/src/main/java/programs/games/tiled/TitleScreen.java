package main.java.programs.games.tiled;

import main.java.engine.events.input.key.press.KeyPressEventData;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.program.output.screens.Screen;
import main.java.utilities.math.vector.MathVector2D;

/**
 *
 * @author Mike
 */
public class TitleScreen extends Screen {

    public TitleScreen() {
        Board b = new Board("board1", 5, 5, new MathVector2D<Double>(10.0,10.0), new Double[]{50.0,50.0});
        for(DrawObject be : b.getChildrenList()){
            this.foregroundLayer.addElement(be);
        }
    }


    @Override
    public void keyHandle(KeyPressEventData data) {
        System.out.println(data);
    }


}
