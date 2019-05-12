package main.java.engine.info;

import main.java.engine.output.render.objects.atlas.FontAtlas;
import main.java.engine.output.render.objects.font.Font;

public abstract class global {

    public static final String engineLocation = "src/main/res/engine/";

    public static final class ui_objects {

        public static final String uiLocation = engineLocation + "ui_objects/";

        public static final class template_font {

            public static final String fontLocation = uiLocation + "fonts/";
            public static final Font times_font = new Font("Times New Roman", fontLocation + "times/times_new_roman.png", fontLocation + "times/times_new_roman.fnt");
            public static final FontAtlas times_atlas = new FontAtlas(times_font);
        }

        public static final class template_button {

            public static final String template_buttonLocation = uiLocation + "template_button/";

            public static final String name = "template_button";
            public static final String unpressedLocation = template_buttonLocation + "unpressed.png";
            public static final String hoverLocation = template_buttonLocation + "hover.png";
            public static final String pressedLocation = template_buttonLocation + "pressed.png";
            public static final String visitedLocation = template_buttonLocation + "visited.png";
        }

    }


    private global() {

    }


}
