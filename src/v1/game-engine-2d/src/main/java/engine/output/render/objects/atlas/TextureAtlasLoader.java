package main.java.engine.output.render.objects.atlas;

public class TextureAtlasLoader {

    public static TextureAtlas loadStaticTextureAtlas(String name, String fileLocation, int UNIVERSAL_WIDTH, int UNIVERSAL_HEIGHT) {
        return new TextureAtlas(name, fileLocation, true, true, UNIVERSAL_WIDTH, UNIVERSAL_HEIGHT, 0);
    }

    public static TextureAtlas loadDynamicTextureAtlas(String name, String fileLocation, int padding){
        return new TextureAtlas(name, fileLocation, false, false, 0, 0, padding);
    }

}