package main.java.engine.output.render.objects.atlas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.output.render.objects._2d.TextureLoader;

public abstract class Atlas {

    protected String name;
    protected DrawFrame parent;


    protected Atlas(String name, String imageLocation) {
        this.name = name;
        this.loadParent(imageLocation);
    }


    protected Atlas(String name, DrawFrame parent) {
        this.name = name;
        this.parent = parent;
    }


    private final void loadParent(String imageLocation) {
        try {
            File png = new File(imageLocation);
            BufferedImage bi = ImageIO.read(png);
            this.parent = TextureLoader.loadByBufferedImage(bi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
