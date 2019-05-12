package game.data;

import org.w3c.dom.Element;

public final class globals {

    private globals() {

    }

    public static class TAGS {

        public static final String ROOT = "level_data";
        public static final String PLAYER = "player_data";
        public static final String ENTITY_PL = "entities";
        public static final String STRUCTURE_PL = "structures";
        public static final String BACKGROUND_PL = "backgrounds";
        public static final String ENTITY = "entity";
        public static final String STRUCTURE = "structure";
        public static final String BACKGROUND = "background";

        public static String name = "name";

        //<editor-fold desc="render_object">
        public static String render_object = "render_object";
        public static String file_location = "file_location";
        // </editor-fold>

        // <editor-fold desc="physics_object">
        public static String physics_object = "physics_object";
        public static String type = "type";
        public static String MASK = "MASK";
        public static String hitbox = "hitbox";
        public static String dimensions = "dimensions";
        public static String width = "width", height = "height";
        public static String[] wh = new String[]{width, height};
        public static String location = "location";
        public static String x = "x", y = "y";
        public static String[] xy = new String[]{x, y};
        // </editor-fold>

        //<editor-fold desc="logic_object">
        public static String logic_object = "logic_object";
        //</editor-fold>

        public static String movement_speed = "movement_speed";

        public static float[] parseFloatValues(Element root, String... tags) {
            float[] retArray = new float[tags.length];
            for (int i = 0; i < tags.length; i++) {
                String temp_element = root.getElementsByTagName(tags[i]).item(0).getTextContent();
                retArray[i] = Float.parseFloat(temp_element);
            }
            return retArray;
        }

        public static double[] parseDoubleValues(Element root, String... tags) {
            double[] retArray = new double[tags.length];
            for (int i = 0; i < tags.length; i++) {
                String temp_element = root.getElementsByTagName(tags[i]).item(0).getTextContent();
                retArray[i] = Double.parseDouble(temp_element);
            }
            return retArray;
        }

    }
}
