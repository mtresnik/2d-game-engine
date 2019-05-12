package main.java.engine.output.render.objects.font;

import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects._2d.TextureFrame;

public class CharacterFrame extends TextureFrame {

    protected Character representation;


    public CharacterFrame(Character representation, int x_offset, int y_offset,  int x_advance, int x_pos, int y_pos, int width, int height, DrawFrame parent) {
        super(x_offset, y_offset, x_advance, x_pos, y_pos, width, height, parent);
        this.representation = representation;
    }


    public CharacterFrame(Character representation, TextureFrame parent) {
        super(parent.getX_offset(), parent.getY_offset(), parent.getX_advance(), parent.textureID, parent.image, parent.pixels);
        this.representation = representation;
    }


    public CharacterFrame(CharacterMetadata metadata, DrawFrame parent) {
        this(metadata.representation, metadata.x_offset, metadata.y_offset, metadata.x_advance, metadata.x_pos, metadata.y_pos, metadata.width, metadata.height, parent);
        System.out.println("charId:" + (int)(char)metadata.representation+ "yOffsetCreation:" + metadata.y_offset);
    }


    public Character getRepresentation() {
        return representation;
    }


}
