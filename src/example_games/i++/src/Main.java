
import java.io.IOException;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Main {

    public static Player player;
    public static Controller controller;


    public static void main(String[] args) throws IOException {
        Displayer dip = new Displayer(1000, 800, "i++");
        try {
            dip.addTo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        player = new Player();
        controller = new Controller();
        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);
            Render.renderOrder();
            Render.keyRender();
            Render.clockAdvance();
            Display.update();
            Display.sync(60);
        }
    }


}
