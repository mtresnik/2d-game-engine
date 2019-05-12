
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.KEY_DOWN;
import static org.lwjgl.input.Keyboard.KEY_RIGHT;
import static org.lwjgl.input.Keyboard.KEY_UP;
import org.newdawn.slick.Color;

public class Render {

    private static boolean oneTime = true;
    private static boolean enabled = true;
    private static long currTime = 0;

    public static void renderOrder() {
        for (Textures tex : Displayer.renderOrder) {
            if (tex != null && tex.getNable()) {
                tex.draw(tex.getTexture());
            }
        }
        Color.white.bind();
        Displayer.font.drawString(Displayer.bCoord[0], 20, "Score: " + Main.player.getScore(), Color.gray);
        Color.white.bind();
    }

    public static void keyRender() {
        if (Keyboard.next() && enabled) {
            if (oneTime) {
                //Detect key in here
                //Try to advance turn
                if (Keyboard.isKeyDown(KEY_RIGHT)) {
                    if (Main.player.getDir() != Player.direction.RIGHT) {
                        Main.player.rotateRight();
                        //System.out.println("dir:" + Main.player.getDir());
                    }
                } else if (Keyboard.isKeyDown(KEY_UP)) {
                    if (Main.player.getDir() != Player.direction.UP) {
                        Main.player.rotateUp();
                        //System.out.println("dir:" + Main.player.getDir());
                    }
                    Main.controller.moveCharacter();
                } else if (Keyboard.isKeyDown(KEY_DOWN)) {
                    if (Main.player.getDir() != Player.direction.DOWN) {
                        Main.player.rotateDown();
                        //System.out.println("dir:" + Main.player.getDir());
                    }
                    Main.controller.moveCharacter();
                }
                oneTime = false;
            } else {
                oneTime = true;
            }
        }
    }

    public static void clockAdvance() {
        long score = Main.player.getScore();
        if(score == 0){
            score = 1;
        }
        long temp = 0;
        try{
            temp = 700 - (10 * (score / 5))*(50/(score*50)) + 50*(score/20) - 150*(score/50);
        }catch(ArithmeticException e){
            score = 1;
            temp = 700 - (10 * (score / 5))*(50/(score*50)) + 50*(score/20) - 150*(score/50);
        }
        if(temp < 400){
            temp = 400;
        }
        if (System.currentTimeMillis() > currTime + temp) {
            currTime = System.currentTimeMillis();
            Main.controller.advanceTurn();
        }
    }
}
