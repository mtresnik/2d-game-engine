package main.java.engine.output.render.objects.font;

import java.util.*;
import main.java.utilities.chars.strings;
import main.java.utilities.io.input;

public class Font {

    public final String name, imageLocation, infoLocation;
    protected ArrayList<CharacterMetadata> characterInfo;
    protected FontMetadata metadata;


    public Font(String font_name, String font_imageLocation, String font_infoLocation) {
        input.checkFileExists(font_imageLocation, font_infoLocation);
        this.name = font_name;
        this.imageLocation = font_imageLocation;
        this.infoLocation = font_infoLocation;
        this.characterInfo = new ArrayList();
        initFontInfo();
        initCharacterInfo();
    }


    public void initFontInfo() {
        ArrayList<HashMap<String, String>> infoMap = input.getValuesFromFile(this.infoLocation, FontMetadata.keys);
        if (infoMap.isEmpty()) {
            throw new IllegalStateException("Font file must have proper format.");
        }
        HashMap<String, String> currentMap = infoMap.get(0);
        this.metadata = FontMetadata.generate(currentMap);
    }


    public void initCharacterInfo() {
        ArrayList<HashMap<String, String>> infoMaps = input.getValuesFromFile(this.infoLocation, CharacterMetadata.keys);
        for (HashMap<String, String> currentMap : infoMaps) {
            HashMap<String, Integer> conversionMap = new HashMap();
            for (String key : CharacterMetadata.keys) {
                conversionMap.put(key, Integer.parseInt(strings.numbersOnly(currentMap.get(key))));
            }
            CharacterMetadata tempMetadata = CharacterMetadata.generate(conversionMap);
            this.characterInfo.add(tempMetadata);
        }
    }


    public ArrayList<CharacterMetadata> getCharacterInfo() {
        return characterInfo;
    }


    public FontMetadata getMetadata() {
        return metadata;
    }


}
