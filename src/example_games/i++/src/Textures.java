import java.io.IOException;
import java.io.InputStream;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Textures {
    
        private Texture texture;
        private int xLocation = 0, yLocation = 0;
        private double width = 100, height = 100;
        private String name = "UNNAMED", location = "ERROR";
        private boolean enabled = false;
        public int renderOrder = 0;
    
    //Constructor
    public Textures(
        int x, int y, double tWidth, double tHeight, 
        String tName, String tLocation,
        boolean tEnabled) throws IOException
    {
        /*
         * = new Textures(x, y, tWidth, tHeight, tName, tLocation, tEnabled)
         */
        xLocation = x;
        yLocation = y;
        width = tWidth;
        height = tHeight;
        name = tName;
        location = tLocation;
        enabled = tEnabled;
        texture = loadTexture(location);
        
    }
    
    public int[] getXY(){
        int[] tempArray =  {this.xLocation,this.yLocation};
        return tempArray;
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getWidth(){
        return this.width;
    }
    
    public double getHeight(){
        return this.height;
    }
    
    public String getLocation(){
        return this.location;
    }
    
    public boolean getNable(){
        return this.enabled;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setWidth(double width){
        this.width = width;
    }
    
    public void setHeight(double height){
        this.height = height;
    }
    
    public void setXY(int x, int y){
        this.xLocation = x;
        this.yLocation = y;
    }
    
    public void setLoc(String location){
        this.location = location;
        try{
        this.texture = loadTexture(location);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void setNable(boolean truthValue){
        this.enabled = truthValue;
    }
    
    //Draw Method
    public void draw(Texture texture){
            
            int newWid = (int)width;
            int newHei = (int)height;
            texture.bind();
            
            //Color.white.bind();
            //tempCol.bind();
            //glTranslatef(xLocation,yLocation,0);
            glBegin(GL_QUADS);
                glTexCoord2d(0,0);
                glVertex2f(xLocation, yLocation);
                glTexCoord2d(1.0,0.0);
                glVertex2f(xLocation+newWid, yLocation);
                glTexCoord2d(1.0,1.0);
                glVertex2f(xLocation+newWid, yLocation+newHei);
                glTexCoord2d(0,1.0);
                glVertex2f(xLocation, yLocation+newHei);
                glLoadIdentity();
            glEnd();
        }
    
    //Load Texture Method
    private static Texture loadTexture(String key) throws IOException{
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream("res/" + key + ".png");
        tex = TextureLoader.getTexture("PNG", in);
        return tex;
    }
    
    //Is the mouse cursor over a texture?
    public boolean inBounds(int mousex, int mousey){
            return mousex > xLocation && mousex < xLocation + width && mousey > yLocation && mousey < yLocation+height;
    }  
    
    public Texture getTexture(){
        return this.texture;
    }
    
    public Textures cloner() throws IOException{
        return new Textures(this.getXY()[0], this.getXY()[1], this.getWidth(), this.getHeight(), this.getName(), this.getLocation(), this.getNable());
    }
}