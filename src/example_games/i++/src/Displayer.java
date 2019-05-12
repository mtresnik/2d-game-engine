
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.TrueTypeFont;

public class Displayer {

    public static List<Textures> renderOrder;
    public static List<Tiles> tileList;
    public static Tiles[][] tileBoard;
    private final int[] dim;
    public static TrueTypeFont font;
    public static int[] bCoord;


    public Displayer(int widthPixels, int heightPixels, String name) {
        renderOrder = new ArrayList();
        tileList = new ArrayList();
        tileBoard = new Tiles[5][5];

        try {
            Display.setDisplayMode(new DisplayMode(widthPixels, heightPixels));
            Display.setTitle(name);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        this.dim = new int[2];
        this.dim[0] = widthPixels;
        this.dim[1] = heightPixels;
    }


    public void addTo() throws IOException {
        /*
         * = new Textures(x, y, tWidth, tHeight, tName, tLocation, tSelected, tEnabled, click)
         */
        this.initTex();
        double boardWidth = (int) (5 * dim[1] / 7);
        Textures background = new Textures(0, 0, this.dim[0], this.dim[1], "background", "misc/bkgrd", true);
        Textures board = new Textures((int) (boardWidth / 5), (int) (boardWidth / 5), boardWidth, boardWidth, "board", "misc/board", true);
        bCoord = board.getXY();
        renderOrder.add(background);
        renderOrder.add(board);
        double diff = board.getWidth() / 100;
        double width = 0.94 * board.getWidth() / 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int locx = (int) (diff * (1 + i) + (i) * width) + board.getXY()[0];
                int locy = (int) (diff * (1 + j) + (j) * width) + board.getXY()[1];
                Textures tempTexture = new Textures(locx, locy, width, width, "btile:" + i + ", " + j, "misc/tileback", true);
                Textures tileOver = new Textures(locx, locy, width, width, "tile:" + i + ", " + j, "misc/tileback", true);
                renderOrder.add(tempTexture);
                renderOrder.add(tileOver);
                tileBoard[i][j] = new Tiles(i, j, tileOver);
                tileList.add(tileBoard[i][j]);
            }
        }
        this.initFon();
    }


    public void initTex() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, dim[0], dim[1], 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_NORMALIZE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }


    public void initFon() {
        Font awtFont = new Font("Arial", Font.BOLD, (int) tileBoard[0][0].getTexture().getHeight() / 2);
        font = new TrueTypeFont(awtFont, false);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glViewport(0, 0, dim[0], dim[1]);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, dim[0], dim[1], 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }


    public int[] getDim() {
        return this.dim;
    }


}
