package main.java.engine.output.render.objects.atlas;

import java.util.HashMap;
import java.util.Map;
import main.java.engine.output.render.objects._2d.TextureFrame;
import main.java.engine.output.render.objects.font.CharacterFrame;
import main.java.engine.output.render.objects.font.CharacterMetadata;
import main.java.engine.output.render.objects.font.Font;

public class FontAtlas extends Atlas {

    protected Map<Character, TextureFrame> characterFrameMap;
    protected Font font;


    public FontAtlas(Font font) {
        super(font.name + " - FontAtlas", font.imageLocation);
        this.characterFrameMap = new HashMap();
        this.font = font;
        this.loadCharacterFrameMap();
    }


    protected void loadCharacterFrameMap() {
        for (CharacterMetadata metadata : font.getCharacterInfo()) {
            CharacterFrame tempFrame = new CharacterFrame(metadata, this.parent);
            this.characterFrameMap.put(tempFrame.getRepresentation(), (TextureFrame) tempFrame);
        }
    }


    public TextureFrame getTextureFrame(Character letter) {
        if (this.characterFrameMap.containsKey(letter) == false) {
            throw new IllegalArgumentException("Character map does not contain the character:" + letter);
        }
        return characterFrameMap.get(letter);
    }


    public Font getFont() {
        return font;
    }

    public int getWidthPixels(char ... values){
        int retValue = 0;
        for(char value : values){
            TextureFrame currentFrame = characterFrameMap.get(value);
            if(currentFrame == null){
                continue;
            }
            retValue += currentFrame.getX_advance();
        }
        return retValue;
    }

}
