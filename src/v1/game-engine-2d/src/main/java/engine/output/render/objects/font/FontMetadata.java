package main.java.engine.output.render.objects.font;

import java.util.HashMap;
import main.java.utilities.chars.strings;

public class FontMetadata {

    // <editor-fold desc="Variables">
    public final String infoFace;
    public final int size;
    public final boolean bold;
    public final boolean italic;
    public final String charset;
    public final boolean unicode;
    public final int stretchH;
    public final boolean smooth;
    public final boolean aa;
    public final int[] padding;
    public final int[] spacing;
    // </editor-fold>

    public static final String[] keys
            = new String[]{"info face=", "size=", "bold=", "italic=", "charset=", "unicode=", "stretchH=", "smooth=", "aa=", "padding=", "spacing="};


    public FontMetadata(String infoFace, int size, boolean bold, boolean italic, String charset, boolean unicode, int stretchH, boolean smooth, boolean aa, int[] padding, int[] spacing) {
        this.infoFace = infoFace;
        this.size = size;
        this.bold = bold;
        this.italic = italic;
        this.charset = charset;
        this.unicode = unicode;
        this.stretchH = stretchH;
        this.smooth = smooth;
        this.aa = aa;
        this.padding = padding;
        this.spacing = spacing;
    }


    public static FontMetadata generate(HashMap<String, String> valueMap) {
        return new FontMetadata(
                valueMap.get(keys[0]),
                Integer.parseInt(strings.numbersOnly(valueMap.get(keys[1]))),
                strings.parseBoolean(valueMap.get(keys[2])),
                strings.parseBoolean(valueMap.get(keys[3])),
                valueMap.get(keys[4]),
                strings.parseBoolean(valueMap.get(keys[5])),
                Integer.parseInt(strings.numbersOnly(valueMap.get(keys[6]))),
                strings.parseBoolean(valueMap.get(keys[7])),
                strings.parseBoolean(valueMap.get(keys[8])),
                strings.parseIntArray(valueMap.get(keys[9]).split(",")),
                strings.parseIntArray(valueMap.get(keys[10]).split(","))
        );
    }


}
