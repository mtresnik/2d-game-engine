package main.java.engine.output.render.objects.atlas;

import java.util.ArrayList;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.utilities.arrays.arrays;
import main.java.utilities.math.vector.MathVector2D;

public class TextureAtlas extends Atlas {

    // <editor-fold desc="Atlas Properties">
    private boolean isWidthStatic, isHeightStatic;
    private boolean allDimensionsFilled = false;
    private int UNIVERSAL_WIDTH, UNIVERSAL_HEIGHT;
    private int padding;
    private ArrayList<ArrayList<Integer[/*x_pos, y_pos, w, h*/]>> dimensions;
    private DrawFrame[][] frameArray;
    private int maxColumnHeight, maxRowHeight;
    private ArrayList<Integer[]> rowList;
    // </editor-fold>

    // <editor-fold desc="Cursor Properties">
    private int[/*x_pos, y_pos*/] xCursor, yCursor, originCursor;
    private int[/*min, max*/] xBounds, yBounds;

    // </editor-fold>

    // <editor-fold desc="Atlas Methods">
    public TextureAtlas(String name, String fileLocation, boolean isWidthStatic,
            boolean isHeightStatic, int UNIVERSAL_WIDTH,
            int UNIVERSAL_HEIGHT, int padding) {
        super(name, fileLocation);
        this.isWidthStatic = isWidthStatic;
        this.isHeightStatic = isHeightStatic;
        this.UNIVERSAL_WIDTH = UNIVERSAL_WIDTH;
        this.UNIVERSAL_HEIGHT = UNIVERSAL_HEIGHT;
        this.padding = padding;
        this.dimensions = new ArrayList();
        this.xBounds = new int[2];
        this.yBounds = new int[2];
        this.xCursor = new int[2];
        this.yCursor = new int[2];
        this.setOrigin(new int[]{padding, padding});

        this.xBounds = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        this.yBounds = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        this.maxColumnHeight = Integer.MIN_VALUE;
        this.maxRowHeight = Integer.MIN_VALUE;
        this.rowList = new ArrayList();
        this.loadAtlas();
    }


    public boolean isStatic() {
        return isWidthStatic && isHeightStatic;
    }


    public boolean isDynamic() {
        return !(isStatic());
    }


    private void loadAtlas() {
        if (this.isDynamic()) {
            this.testPadding();
        }
        initFrames();
        initTextures();
    }


    private void testPadding() {
        if (padding <= 0) {
            throw new IllegalArgumentException("Padding must be greater than zero.");
        }
    }


    private void initFrames() {
        while (this.allDimensionsFilled == false) {
            this.findSubtextureDimensions();
        }

    }


    private void findSubtextureDimensions() {
        if (this.isDynamic()) {
            this.scaleDimensions();
        } else {
            this.setAllDimensions();
            this.allDimensionsFilled = true;
        }
    }


    private void initTextures() {
        // Subtexture all of the given dimensions and put them into the drawframe array
        // initialize the drawframe array to the lengths of the ArrayLists
        int numberOfFramesX = this.dimensions.size();
        int numberOfFramesY = this.dimensions.get(0).size();
        this.frameArray = new DrawFrame[numberOfFramesX][numberOfFramesY];
        for (int x_index = 0; x_index < numberOfFramesX; x_index++) {
            for (int y_index = 0; y_index < this.dimensions.get(x_index).size(); y_index++) {
                Integer[] currentArray = this.dimensions.get(x_index).get(y_index);
                int x_pos = currentArray[0];
                int y_pos = currentArray[1];
                int width = currentArray[2];
                int height = currentArray[3];
                DrawFrame child = this.parent.subFrame(new MathVector2D<Integer>(x_pos, y_pos), width, height);
                if (arrays.allBytesEquals(child.pixels, 3, (byte) 0)) {
                    continue;
                }
                this.frameArray[x_index][y_index] = child;
            }
        }
    }


    /**
     * This method is used to statically set all of the dimensions of textures
     * that are loaded in from a file. This means that the textures that are
     * included in the file must all be of the same size.
     *
     */
    private void setAllDimensions() {
        /*
         * Add the static amount of dimensions to the dimensions lists
         * based on the size of the file and the size of the universal dimensions
         */
        this.dimensions.clear();
        int numberOfFramesX = this.parent.getWidth() / this.UNIVERSAL_WIDTH;
        int numberOfFramesY = this.parent.getHeight() / this.UNIVERSAL_HEIGHT;
        for (int y_index = 0; y_index < numberOfFramesY; y_index++) {
            ArrayList<Integer[]> rowList = new ArrayList();
            for (int x_index = 0; x_index < numberOfFramesX; x_index++) {
                int x_pos = x_index * UNIVERSAL_WIDTH;
                int y_pos = y_index * UNIVERSAL_HEIGHT;
                rowList.add(new Integer[]{x_pos, y_pos, UNIVERSAL_WIDTH, UNIVERSAL_HEIGHT});
            }
            this.dimensions.add(rowList);
        }
    }


    /**
     * This method dynamically scales the dimensions of each frame to those that
     * are found in the passed in file. This method consists of two helper
     * methods: scanForTexture and buildTextureStorage.
     */
    private void scaleDimensions() {
        // Begin scalling the dimensions and ind the first intersection.
        boolean intersection = false;
        while (intersection == false) {
            if (xCursor[0] + 1 > this.parent.getWidth()) {
                this.nextRow();
                return;
            }
            if (yCursor[1] + 1 > this.parent.getHeight()) {
                this.allDimensionsFilled = true;
                return;
            }
            boolean intersectionX = false, intersectionY = false;
            intersectionX = arrays.allBytesEquals(this.getVerticalArray(), 3, (byte) 0) == false;
            intersectionY = arrays.allBytesEquals(this.getHorizontalArray(), 3, (byte) 0) == false;
            intersection = intersectionX || intersectionY;
            if (intersection == false) {
                // System.out.println("false:" + Arrays.toString(this.getHorizontalArray()));
                moveRight();
                moveDown();
            }
        }

        this.scanForTexture(intersection);

        this.buildTextureStorage();
    }


    /**
     * This is the second helper method for the scaleDimensions method. It loads
     * the dynamically found textures into a usable container that can be
     * indexed.
     *
     */
    public void buildTextureStorage() {
        int width = xBounds[1] - xBounds[0] + 1;
        int height = yBounds[1] - yBounds[0] + 1;
        this.maxColumnHeight = Math.max(xBounds[1], maxColumnHeight);
//        System.out.println("xCursor" + Arrays.toString(xCursor) + " yCursor" + Arrays.toString(yCursor));
//        System.out.println("xBounds:" + Arrays.toString(xBounds) + " ybounds:" + Arrays.toString(yBounds));
//        System.out.println("width:" + width + " height:" + height);
//        System.out.println("-----");
        int[] coordinates = new int[]{xBounds[0], yBounds[0]};
        this.xBounds = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        this.yBounds = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        rowList.add(new Integer[]{coordinates[0], coordinates[1], width, height});
        this.nextColumn(width);
        // System.out.println(Arrays.toString(originCursor));
        if (this.originCursor[0] > this.parent.getWidth()) {
            this.nextRow();
        }
    }


    /**
     * This method scans the passed in texture file for the textures
     *
     * @param intersection
     */
    private void scanForTexture(boolean intersection) {
        while (intersection == true) {
            // expand until intersection is false
            boolean intersectionX = arrays.allBytesEquals(this.getVerticalArray(), 3, (byte) 0) == false;
            boolean intersectionY = arrays.allBytesEquals(this.getHorizontalArray(), 3, (byte) 0) == false;
            if (intersectionX == true) {
                moveRight();
            }
            if (intersectionY == true) {
                moveDown();
            }
            intersection = intersectionX || intersectionY;
            this.xBounds[0] = Math.min(xBounds[0], this.minMaxXValueOfAllIntersections()[0]);
            this.xBounds[1] = Math.max(xBounds[1], this.minMaxXValueOfAllIntersections()[1]);
            this.yBounds[0] = Math.min(yBounds[0], this.minMaxYValueOfAllIntersections()[0]);
            this.yBounds[1] = Math.max(yBounds[1], this.minMaxYValueOfAllIntersections()[1]);
            this.maxRowHeight = Math.max(yBounds[1] - yBounds[0] + 1, maxRowHeight);
            if (intersection == false) {
                break;
            }
        }
    }


    public int[] minMaxXValueOfAllIntersections() {
        int minimumValue = Integer.MAX_VALUE;
        int maximumValue = Integer.MIN_VALUE;
        for (Integer[] subData : this.getXIntersectionPoints()) {
            minimumValue = Math.min(minimumValue, subData[0]);
            maximumValue = Math.max(maximumValue, subData[0]);
        }
        for (Integer[] subData : this.getYIntersectionPoints()) {
            minimumValue = Math.min(minimumValue, subData[0]);
            maximumValue = Math.max(maximumValue, subData[0]);
        }
        return new int[]{minimumValue, maximumValue};
    }


    public int[] minMaxYValueOfAllIntersections() {
        int minimumValue = Integer.MAX_VALUE;
        int maximumValue = Integer.MIN_VALUE;

        for (Integer[] subData : this.getXIntersectionPoints()) {
            minimumValue = Math.min(minimumValue, subData[1]);
            maximumValue = Math.max(maximumValue, subData[1]);
        }
        for (Integer[] subData : this.getYIntersectionPoints()) {
            minimumValue = Math.min(minimumValue, subData[1]);
            maximumValue = Math.max(maximumValue, subData[1]);
        }
        return new int[]{minimumValue, maximumValue};
    }


    public boolean rowEnded() {
        if (this.originCursor[0] > this.parent.getWidth()) {
            return true;
        }
        return false;

    }


    public void nextRow() {
        this.setOrigin(new int[]{padding, originCursor[1] + this.maxRowHeight + this.padding});
        this.maxRowHeight = 0;
        ArrayList<Integer[]> cloneList = new ArrayList(rowList);
        this.dimensions.add(cloneList);
        this.rowList.clear();
    }


    public void nextColumn(int currentWidth) {
        this.setOrigin(new int[]{originCursor[0] + currentWidth + padding, originCursor[1]});
    }


    public DrawFrame[][] getFrames() {
        return this.frameArray;
    }


    public DrawFrame getFrame(int x, int y) {
        return this.frameArray[y][x];
    }
    // </editor-fold>


    // <editor-fold desc="Cursor Methods">
    private void moveRight() {
        // Increases the x_pos value of the y_cursor
        this.xCursor[0]++;
    }


    private void moveDown() {
        // Increases the y_pos value of the x_cursor
        this.yCursor[1]++;
    }


    private void setOrigin(int[/*x_pos, y_pos*/] originPosition) {
        this.xCursor = new int[]{originPosition[0], originPosition[1]};
        this.yCursor = new int[]{originPosition[0], originPosition[1]};
        this.originCursor = new int[]{originPosition[0], originPosition[1]};
    }


    private byte[/*x_index*/][/*r, g, b, a*/] getHorizontalArray() {
        int size = xCursor[0] - originCursor[0];
        if (size == 0) {
            size = 1;
        }
        byte[][] retArray = new byte[size][4];
        for (int index = 0; index < size; index++) {
            for (int channelIndex = 0; channelIndex < 4; channelIndex++) {
                retArray[index][channelIndex] = this.parent.pixels[yCursor[0] + index][yCursor[1]][channelIndex];
            }
        }
        return retArray;
    }


    private ArrayList<Integer[]> getXIntersectionPoints() {
        if (arrays.allBytesEquals(this.getHorizontalArray(), 3, (byte) 0)) {
            return new ArrayList();
        }
        ArrayList<Integer[]> intersectionPoints = new ArrayList();
        int xIndex = 0;
        for (byte[] subData : this.getHorizontalArray()) {
            if (subData[3] != (byte) 0) {
                intersectionPoints.add(new Integer[]{yCursor[0] + xIndex, yCursor[1]});
            }

            xIndex++;
        }
        return intersectionPoints;
    }


    private byte[]/*y_index*/[/*r, g, b, a*/] getVerticalArray() {
        int size = yCursor[1] - originCursor[1];
        if (size == 0) {
            size = 1;
        }
        byte[][] retArray = new byte[size][4];
        for (int index = 0; index < size; index++) {
            for (int channelIndex = 0; channelIndex < 4; channelIndex++) {
                retArray[index][channelIndex] = this.parent.pixels[xCursor[0]][xCursor[1] + index][channelIndex];
            }
        }
        return retArray;
    }


    private ArrayList<Integer[]> getYIntersectionPoints() {
        if (arrays.allBytesEquals(this.getVerticalArray(), 3, (byte) 0)) {
            return new ArrayList();
        }
        ArrayList<Integer[]> intersectionPoints = new ArrayList();
        int yIndex = 0;
        for (byte[] subData : this.getVerticalArray()) {
            if (subData[3] != (byte) 0) {
                Integer[] coordinates = new Integer[]{xCursor[0], xCursor[1] + yIndex};
                intersectionPoints.add(coordinates);
                // System.out.println(Arrays.toString(coordinates));
            }
            yIndex++;
        }
        return intersectionPoints;
    }


    private int[/*x_pos, y_pos*/] getCursorIntersection() {
        return new int[]{yCursor[0], xCursor[1]};
    }

    // </editor-fold>

}
