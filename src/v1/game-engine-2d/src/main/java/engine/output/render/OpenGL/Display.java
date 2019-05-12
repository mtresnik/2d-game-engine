package main.java.engine.output.render.OpenGL;

import main.java.engine.events.output.render.RenderEvent;
import main.java.engine.events.output.render.RenderEventFireObject;
import main.java.engine.events.output.render.display.DisplayEvent;
import main.java.engine.events.output.render.display.DisplayEventListenerInterface;
import main.java.engine.events.output.render.display.resize.ResizeEventData;
import main.java.engine.program.logic.Controller;
import main.java.engine.input.keyboard.Keyboard;
import main.java.engine.input.mouse.Mouse;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;
import static org.lwjgl.system.MemoryUtil.NULL;
import main.java.utilities.interfaces.Initializable;
import main.java.utilities.math.vector.MathVector2D;
import main.java.utilities.math.vector.MathVector4D;
import main.java.engine.events.EventListenerInterface;
import main.java.engine.events.output.render.display.resize.ResizeEvent;
import main.java.engine.output.render.info.global;

public class Display
        extends RenderEventFireObject<RenderEvent>
        implements DisplayEventListenerInterface<DisplayEvent>,
        Runnable, Initializable {

    private int[] pixelDimensions;
    private String title;
    private Thread thread;
    private long window;
    private Controller controller;
    public static Display instance;
    public boolean isOpen = true;


    /**
     * Creates a new Display with the given title, width, height, and linked
     * controller.
     *
     * @param title The title of the screen which will appear in the upper left
     * of the Operating System's window.
     * @param width The width in pixels of the screen.
     * @param height The height in pixels of the screen.
     * @param controller The connected controller which is responsible for
     * holding the Display.
     */
    public Display(int width, int height) {
        this.pixelDimensions = new int[]{width, height};
        System.out.println("width:" + width + " height:" + height);
        global.screen_ratio = ((double) width) / ((double) height);
    }


    @Override
    public void fireEvent(RenderEvent event) {
        for (EventListenerInterface<RenderEvent> L : listeners) {
            L.messageRecieved(event);
        }
    }


    @Override
    public Class<DisplayEvent> getEventType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void messageRecieved(DisplayEvent event) {
        // handles it
    }


    public void setController(Controller controller) {
        this.controller = controller;
        this.title = controller.getName();
    }


    // Callback method for when the buffer for a given frame is changed.
    /**
     * Callback method for when the buffer for a given frame is changed.
     *
     * @param window The LWJGL memory address of the OpenGL window instance in
     * question.
     * @param width The width of the screen in pixels.
     * @param height The height of the screen in pixels.
     * @see Display#windowSizeChanged(long, int, int)
     */
    private static void framebufferSizeChanged(long window, int width, int height) {
        glViewport(0, 0, width, height);
    }


    /**
     * Returns the width and height of the screen in pixels.
     *
     * @return An Integer array
     */
    public int[] getWidthHeight() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, w, h);
        return new int[]{w.get(0), h.get(0)};
    }


    @Override
    public void init() {

        System.setProperty("java.awt.headless", "true");
        isOpen = true;
        if (glfwInit() != true) {
            System.err.println("GLFW initialization failed!");
        }

        // Setup window hints.
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        window = glfwCreateWindow(this.pixelDimensions[0], this.pixelDimensions[1], title, NULL, NULL);

        if (window == NULL) {
            System.err.println("Could not create our Window!");
        }

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, 100, 100);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(1);

        // Setting up the drawing coordinate system.
        glViewport(0, 0, this.pixelDimensions[0], this.pixelDimensions[1]);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, this.pixelDimensions[0], this.pixelDimensions[1], 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        // Setup texture details.
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_NORMALIZE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Setup wrap functions.
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        // Setup blend functions.
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Setup callback functions.
        Display.instance = this;
        glfwSetWindowSizeCallback(window, this::windowSizeChanged);
        glfwSetFramebufferSizeCallback(window, Display::framebufferSizeChanged);

        initMouse();
        initKeyboard();

        // Open the window.
        glfwShowWindow(window);
    }


    private void initMouse() {
        Mouse mouse = controller.getInput().getMouse();

        // Cursor position update method.
        GLFWCursorPosCallback posCallback;
        GLFW.glfwSetCursorPosCallback(window, posCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                // Call the mouse method
                MathVector2D<Integer> cursorPosition;
                mouse.positionHandle(cursorPosition = new MathVector2D<Integer>(System.currentTimeMillis(), (int) xpos, (int) ypos));
            }


        });

        // Mouse click update method.
        GLFWMouseButtonCallback mouseClickCallback;
        GLFW.glfwSetMouseButtonCallback(window, mouseClickCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                // Call the mouse method
                mouse.clickHandle(button, action, mods);
            }


        });

        // Scroll velocity update method.
        GLFWScrollCallback scrollCallback;
        GLFW.glfwSetScrollCallback(window, scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                MathVector4D<Double> scrollVelocity;
                mouse.scrollHandle(scrollVelocity = new MathVector4D<Double>(xoffset, yoffset, 0.0, 0.0));
            }


        });

        // Context entering update method.
        GLFWCursorEnterCallback enterCallback;
        GLFW.glfwSetCursorEnterCallback(window, enterCallback = new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                // Call the enter callback
                mouse.enterScreenHandle(entered);
            }


        });

    }


    private void initKeyboard() {

        Keyboard keyboard = controller.getInput().getKeyboard();

        GLFWKeyCallback keyCallback;
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyboard.keyHandle(key, scancode, action, mods);
            }


        });

        GLFWCharCallback charCallback;
        GLFW.glfwSetCharCallback(window, new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
                keyboard.charHandle(codepoint);
            }


        });
    }


    @Override
    public void run() {
        init();
        controller.init();
        while (isOpen) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            controller.run();
            glfwSwapBuffers(window);
            glfwPollEvents();
            if (glfwWindowShouldClose(window) == true) {
                isOpen = false;
                break;
            }
        }

    }


    /**
     * Starts the current thread, important for asynchronous tasks.
     */
    public void start() {
        this.isOpen = true;
        this.thread = new Thread(this, "endlessrunner");
        this.thread.start();
    }


    /**
     * Updates the buffers of the windows.
     */
    public void update() {
        // Polls for any window events such as the window closing etc.
        glfwSwapBuffers(window);
    }


    /**
     * Callback method for when the window's dimesnions have been resized.
     *
     * @param window The LWJGL memory address of the OpenGL window instance in
     * question.
     * @param width The width of the screen in pixels.
     * @param height The height of the screen in pixels.
     */
    private void windowSizeChanged(long window, int width, int height) {

        this.pixelDimensions = new int[]{width, height};
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        this.handleResize(width, height);

    }


    private void handleResize(int width, int height) {
        ResizeEventData dataToFire = new ResizeEventData(width, height);
        RenderEvent eventToFire = new ResizeEvent(this, dataToFire);
        this.fireEvent(eventToFire);
    }


}
