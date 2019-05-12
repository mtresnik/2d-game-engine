package game.editor;

import java.util.function.Function;
import game.render.Display;

public class Editor {

    public static boolean isOpen = false;
    public static DisplayThread dt;
    public static EditorThread et;

    public static void main(String[] args) throws Exception {
        isOpen = true;
        dt = new DisplayThread();
        dt.start();
        et = new EditorThread();
        et.start();
    }

    public static class DisplayThread extends Thread {

        public void run() {
            try {
                System.out.println(Thread.currentThread());
                Display.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class EditorThread extends Thread {

        public void run() {
            try {
                System.out.println(Thread.currentThread());
                new EditorGui().init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void executeFunction(Function<Void, Void> func) {
            System.out.println(Thread.currentThread());
            func.apply(null);
        }
    }

}
