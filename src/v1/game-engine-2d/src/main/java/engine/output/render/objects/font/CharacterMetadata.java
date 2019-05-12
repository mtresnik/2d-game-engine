package main.java.engine.output.render.objects.font;

import java.util.HashMap;

public class CharacterMetadata {

    public final Character representation;
    public final int x_offset, y_offset;
    public final int x_advance;
    public final int x_pos, y_pos;
    public final int width, height;
    public static final String[] keys = new String[]{"char id=", "x=", "y=", "width=", "height=", "xoffset=", "yoffset=", "xadvance="};


    public CharacterMetadata(Character representation, int x_pos, int y_pos, int width, int height, int x_offset, int y_offset, int x_advance) {
        this.representation = representation;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.width = width;
        this.height = height;
        this.x_advance = x_advance;
    }


    public static CharacterMetadata generate(HashMap<String, Integer> metadataMap) {
        for(String key : CharacterMetadata.keys){
            if(metadataMap.containsKey(key) == false){
                throw new IllegalArgumentException("Map must contain all keys in class:" + CharacterMetadata.class.getName());
            }
        }
        System.out.println("map:" + metadataMap.toString());
        return new CharacterMetadata(
                (char) metadataMap.get(keys[0]).intValue(),
                metadataMap.get(keys[1]),
                metadataMap.get(keys[2]),
                metadataMap.get(keys[3]),
                metadataMap.get(keys[4]),
                metadataMap.get(keys[5]),
                metadataMap.get(keys[6]),
                metadataMap.get(keys[7])
        );
    }


}
