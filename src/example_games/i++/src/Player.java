
import java.io.IOException;

public class Player {

    private long score;
    private Tiles currTile;
    private direction dir = direction.RIGHT;

    public enum direction {
        RIGHT, UP, DOWN
    }

    public Player() throws IOException {
        this.score = 0;
        this.currTile = new Tiles(0, 2, Displayer.tileBoard[0][2].getTexture().cloner());
        this.currTile.getTexture().setLoc("tiles/arrowtile");
        Displayer.renderOrder.add(this.currTile.getTexture());
    }

    public long getScore() {
        return score;
    }

    public Tiles getCurrTile() {
        return currTile;
    }

    public direction getDir() {
        return dir;
    }

    public void setScore(long score) {
        this.score = score;
        //System.out.println("score:" + score);
    }

    public void setCurrTile(Tiles currTile) {
        this.currTile = currTile;
    }

    public void setDir(direction dir) {
        this.dir = dir;
    }

    public void rotateUp() {
        this.currTile.getTexture().setLoc("tiles/arrowup");
        this.dir = direction.UP;
    }

    public void rotateDown() {
        this.currTile.getTexture().setLoc("tiles/arrowdown");
        this.dir = direction.DOWN;
    }

    public void rotateRight() {
        this.currTile.getTexture().setLoc("tiles/arrowtile");
        this.dir = direction.RIGHT;
    }

}
