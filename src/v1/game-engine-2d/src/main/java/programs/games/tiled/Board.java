package main.java.programs.games.tiled;

import main.java.engine.input.mouse.Pressable;
import main.java.engine.output.render.objects.DrawObject;
import main.java.engine.output.render.objects.RelativeDrawObject;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects._2d.TextureLoader;
import main.java.utilities.math.vector.MathVector2D;

/**
 *
 * @author Mike
 */
public class Board extends RelativeDrawObject {

    private BoardElement[][] grid;


    public Board(String name, int m, int n, MathVector2D<Double> relativeCoordinates, Double[] relativeDimensions, DrawObject... relativeArray) {
        super(name, DrawFrame.EMPTYFRAME, relativeCoordinates, relativeDimensions, relativeArray);
        this.initGrid(m, n);
    }


    @Override
    public DrawFrame getCurrentFrame() {
        return DrawFrame.EMPTYFRAME;
    }


    @Override
    public void initFrames() {
        return;
    }


    private void initGrid(int m, int n) {
        this.grid = new BoardElement[m][n];
        for (int x_index = 0; x_index < n; x_index++) {
            for (int y_index = 0; y_index < m; y_index++) {
                Double x_pos, y_pos;
                x_pos = (100.0 * x_index) / n;
                y_pos = (100.0 * y_index) / m;
                Double width = 100.0 / n;
                Double height = 100.0 / m;
                this.grid[x_index][y_index]
                        = new BoardElement(
                                this, x_index, y_index,
                                new MathVector2D<Double>(x_pos, y_pos),
                                new Double[]{width, height}
                        );
            }
        }
    }


    @Override
    public String toString() {
        return this.name;
    }


    private static class BoardElement extends RelativeDrawObject implements Pressable {

        public Board board;
        public int x, y;
        private static final DrawFrame one = TextureLoader.loadByString("src/main/res/engine/ui_objects/template_button/pressed.png"),
                hover = TextureLoader.loadByString("src/main/res/engine/ui_objects/template_button/hover.png");

        private DrawFrame currentFrame;

        public BoardElement(Board board, int x, int y, MathVector2D<Double> relativeCoordinates, Double[] relativeDimensions) {
            super("boardelement " + x + ":" + y, one, relativeCoordinates, relativeDimensions, board);
            this.board = board;
            this.x = x;
            this.y = y;
            System.out.println("be create");
            this.currentFrame = one;
        }


        @Override
        public DrawFrame getCurrentFrame() {
            return this.currentFrame;
        }

        @Override
        public boolean inBounds(MathVector2D<Double> detectionPoint){
            if(super.inBounds(detectionPoint)){
                this.hover();
                System.out.println("hovering");
                return true;
            }
            this.currentFrame = one;
            return false;
        }

        @Override
        public void hover() {
            System.out.println("hov");
            return;
        }


        @Override
        public void initFrames() {
            return;
        }


        @Override
        public void press() {
            System.out.println(this.toString() + " pressed");
        }


        @Override
        public void release() {
            System.out.println(this.toString() + " released");
        }


        @Override
        public String toString() {
            return "BoardElement{" + "board=" + board + ", x=" + x + ", y=" + y + '}';
        }


    }

}
