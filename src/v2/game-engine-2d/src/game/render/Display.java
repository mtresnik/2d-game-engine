package game.render;

import game.editor.Editor;
import game.Game;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import util.Initializable;

public class Display implements Runnable {

    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWFramebufferSizeCallback framebufferCallback;
    // List of init objects
    public static List<Initializable> initializableList = new ArrayList();
    // Top left of window
    public static int xpos, ypos;
    public static boolean isOpen = false;

    public static int[] dimensions = new int[]{2000, 1280};
    public static Game game;

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        System.out.println("GLFW Initialized");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        window = glfwCreateWindow(dimensions[0], dimensions[1], "", NULL, NULL);
        if (window == NULL) {
            throw new AssertionError("Failed to create the GLFW window");
        }
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE) {
                    glfwSetWindowShouldClose(window, true);
                }
                Game.keyCallback(window, key, scancode, action, mods);
            }
        });

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

        GL.createCapabilities();
        System.out.println("Capabilities created.");

        glfwSetWindowSizeCallback(window, windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                System.out.println("width:" + width + "\theight:" + height);
                glViewport(0, 0, width, height);
                dimensions = new int[]{width, height};
            }
        });

        glfwSetFramebufferSizeCallback(window, framebufferCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                System.out.printf("framebuffer:\tw:%s\th:%s\n", width, height);
                glViewport(0, 0, width, height);
                dimensions = new int[]{width, height};
            }
        });

        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                DoubleBuffer x_pos = BufferUtils.createDoubleBuffer(1), y_pos = BufferUtils.createDoubleBuffer(1);
                glfwGetCursorPos(window, x_pos, y_pos);
                double x = x_pos.get(), y = y_pos.get();
                // System.out.printf("button:%s\tx_loc:%s\ty_loc:%s\trel:%s\n", button, x, y, Arrays.toString(pixelsToRelative(x, y)));
                // translate all by 1 pixel to the right
                if (action == GLFW_PRESS) {
                }
            }
        });

        glfwSetWindowPosCallback(window,
                new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                Display.xpos = xpos;
                Display.ypos = ypos;
            }
        }
        );

        IntBuffer xBuff = BufferUtils.createIntBuffer(1), yBuff = BufferUtils.createIntBuffer(1);
        glfwGetWindowPos(window, xBuff, yBuff);
        Display.xpos = xBuff.get();
        Display.ypos = yBuff.get();

        game = new Game();
        Game.instance = game;
        game.init();

        for (Initializable toInit : initializableList) {
            toInit.init();
        }
        Display.isOpen = true;
        if (Editor.isOpen == false) {
            Game.loadLevel("level1.xml");
        }
    }

    private void loop() {
        while (!glfwWindowShouldClose(window) && Display.isOpen) {
            glfwPollEvents();
            game.loop();
            glfwSwapBuffers(window);
        }
    }

    @Override
    public void run() {
        try {
            init();
            loop();
            keyCallback.free();
            glfwDestroyWindow(window);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            glfwTerminate();
        }
        Game.saveLevel("BACKUP.xml");
        System.exit(0);
    }

    public static void close() {
        Display.isOpen = false;
    }

    public static void main(String[] args) {
        Display display = new Display();
        System.out.println("Start:" + System.currentTimeMillis());
        display.run();
    }

    // <editor-fold desc="Converseion Methods">
    public static double[] pixelsToRelative(double p_x, double p_y) {
        double r_x = (2.0 / dimensions[0]) * p_x;
        double r_y = (-2.0 / dimensions[1]) * p_y;
        return new double[]{r_x, r_y};
    }

    public static double[] pixelsToRelativeDelta(double p_x_t, double p_y_t) {
        double r_x_t = p_x_t * 2.0 / dimensions[0];
        double r_y_t = p_y_t * -2.0 / dimensions[1];
        return new double[]{r_x_t, r_y_t};
    }

    public static double[] relativeToPixels(double r_x, double r_y) {
        double p_x = (r_x) * dimensions[0] / 2.0;
        double p_y = (r_y) * dimensions[1] / -2.0;
        return new double[]{p_x, p_y};
    }

    public static double[] relativeToPixelsDelta(double r_x_t, double r_y_t) {
        double p_x_t = r_x_t * dimensions[0] / 2.0;
        double p_y_t = r_y_t * dimensions[1] / -2.0;
        return new double[]{p_x_t, p_y_t};
    }

    public static int width() {
        return dimensions[0];
    }

    public static int height() {
        return dimensions[1];
    }

    // </editor-fold>
}
