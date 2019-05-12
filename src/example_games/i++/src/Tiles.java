
public class Tiles {
    private Textures texture;
    private int[] coordinates;
    
    public Tiles(int xco, int yco, Textures texture){
        this.coordinates = new int[2];
        this.coordinates[0] = xco;
        this.coordinates[1] = yco;
        this.texture = texture;
    }

    public Textures getTexture() {
        return texture;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setTexture(Textures texture) {
        this.texture = texture;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
    
}
