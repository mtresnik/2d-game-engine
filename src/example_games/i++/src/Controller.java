
import java.math.BigInteger;

public class Controller {

    private double turnTimer;

    public Controller() {
        this.turnTimer = 10.0;
    }

    public double getTurnTimer() {
        return turnTimer;
    }

    public void setTurnTimer(double turnTimer) {
        this.turnTimer = turnTimer;
    }

    public void advanceTurn() {
        //Move player in direction
        //Move all tiles left
        //Set turn timer to 10 again
        this.turnTimer = 10.0;
        this.moveLeft();
        this.spawnNew();
        this.updateScore();
    }

    public void moveLeft() {
        //Looks at each section of a table and advances each one left
        for (int i = 0; i < Displayer.tileBoard.length; i++) {
            //Every collumn
            for (int j = 0; j < Displayer.tileBoard[0].length; j++) {
                //Every row
                if (i == 0) {
                    Displayer.tileBoard[i][j].getTexture().setNable(false);
                } else {
                    Displayer.tileBoard[i][j].getTexture().setNable(false);
                    Displayer.tileBoard[i - 1][j].getTexture().setLoc(Displayer.tileBoard[i][j].getTexture().getLocation());
                    Displayer.tileBoard[i][j].getTexture().setLoc("misc/tileback");
                    Displayer.tileBoard[i - 1][j].getTexture().setNable(true);
                }
            }
        }
    }

    public void moveCharacter() {
        switch (Main.player.getDir()) {
            case UP:
                if (Main.player.getCurrTile().getCoordinates()[1] - 1 >= 0) {
                    int[] coords = Main.player.getCurrTile().getCoordinates();
                    coords[1]--;
                    Main.player.getCurrTile().setCoordinates(coords);
                    int[] xy = Displayer.tileBoard[coords[0]][coords[1]].getTexture().getXY();
                    Main.player.getCurrTile().getTexture().setXY(xy[0], xy[1]);
                    this.updateScore();
                    Displayer.tileBoard[coords[0]][coords[1]].getTexture().setNable(false);
                }
                break;
            case DOWN:
                if (Main.player.getCurrTile().getCoordinates()[1] + 1 < Displayer.tileBoard.length) {
                    int[] coords = Main.player.getCurrTile().getCoordinates();
                    coords[1]++;
                    Main.player.getCurrTile().setCoordinates(coords);
                    int[] xy = Displayer.tileBoard[coords[0]][coords[1]].getTexture().getXY();
                    Main.player.getCurrTile().getTexture().setXY(xy[0], xy[1]);
                    this.updateScore();
                    Displayer.tileBoard[coords[0]][coords[1]].getTexture().setNable(false);
                }
                break;
            case RIGHT:
                break;
            default:
        }
    }

    public void updateScore() {
        //Compare same tile occupant of player
        int[] coords = Main.player.getCurrTile().getCoordinates();
        if (Displayer.tileBoard[coords[0]][coords[1]].getTexture().getNable()) {
            String loc = Displayer.tileBoard[coords[0]][coords[1]].getTexture().getLocation();
            switch (loc) {
                case "tiles/plustile":
                    Main.player.setScore(Main.player.getScore() + 1);
                    break;
                case "tiles/minustile":
                    Main.player.setScore(Main.player.getScore() - 1);
                    break;
                case "tiles/multtile":
                    Main.player.setScore((long)Math.ceil(Main.player.getScore() * 1.1));
                    break;
                case "tiles/divtile":
                    Main.player.setScore((long)(Main.player.getScore() / 1.3));
                    break;
                case "tiles/uptile":
                    Main.player.setScore((long)Math.ceil(Math.pow(Main.player.getScore(), 1.05)));
                    break;
                case "tiles/roottile":
                    Main.player.setScore((long)(Math.pow(Main.player.getScore(), 1.0/1.5)));
                    break;
            }
        } 
        Displayer.tileBoard[coords[0]][coords[1]].getTexture().setLoc("misc/tileback");
        Displayer.tileBoard[coords[0]][coords[1]].getTexture().setNable(false);
    }

    public void spawnNew() {
        long[] rands;
        double rand;
        //randsRet(none,plus,minus,mult,div,square,root);
        if(Main.player.getScore()  == Math.pow(10, 10)){
            rands = randsRet(0, 0, 0, 0, 0, 0, 0);
        }
        else if(Main.player.getScore() > 200){
            rands = randsRet(6, 0, 0, 1, 1, 2, 1);
        }else if (Main.player.getScore() > 50) {
            rands = randsRet(3 + Main.player.getScore() / 10, 1, 1 + Main.player.getScore() / 20, 1 + Main.player.getScore() / 30, 1 + Main.player.getScore() / 40, 0, 0);
        } else if (Main.player.getScore() > 30) {
            rands = randsRet(6, 1, 2, 1, 0, 0, 0);
        } else if (Main.player.getScore() > 10) {
            rands = randsRet(8, 3, 3, 0, 0, 0, 0);
        } else {
            rands = randsRet(10, 4, 3, 0, 0, 0, 0);
            //rands = randsRet(10, 0, 0, 0, 0, 2, 1);
        }
        double sum = sum(rands);
        double[] randPerc = new double[rands.length];
        for (int i = 0; i < randPerc.length; i++) {
            randPerc[i] = rands[i] / sum;
        }
        randPerc = summer(randPerc);
        for (int j = 0; j < Displayer.tileBoard[0].length; j++) {
            rand = Math.random();
            int indexWhere = 0;
            for (int i = 0; i < randPerc.length; i++) {
                if (rand > randPerc[i]) {
                    indexWhere = i;
                    //System.out.println("i" + i + " rand" + rand + "\n" + "rP" + randPerc[i]);
                    break;
                }
            }
            String location = loc(indexWhere);
            //System.out.println("index:" + indexWhere);
            Displayer.tileBoard[Displayer.tileBoard.length - 1][j].getTexture().setLoc(location);
            Displayer.tileBoard[Displayer.tileBoard.length - 1][j].getTexture().setNable(true);
        }
    }

    public long[] randsRet(long a, long b, long c, long d, long e, long f, long g) {
        long[] retArr = new long[7];
        retArr[0] = a;
        retArr[1] = b;
        retArr[2] = c;
        retArr[3] = d;
        retArr[4] = e;
        retArr[5] = f;
        retArr[6] = g;
        return retArr;
    }

    public long sum(long[] input) {
        int sum = 0;
        for (long i : input) {
            sum += i;
        }
        return sum;
    }

    public double[] summer(double[] input) {
        double prevs = 0.0;
        double[] output = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = 1 - (prevs + input[i]);
            prevs += input[i];
        }
        return output;
    }

    public String loc(int index) {
        String[] stringer = new String[7];
        stringer[0] = "misc/tileback";
        stringer[1] = "tiles/plustile";
        stringer[2] = "tiles/minustile";
        stringer[3] = "tiles/multtile";
        stringer[4] = "tiles/divtile";
        stringer[5] = "tiles/uptile";
        stringer[6] = "tiles/roottile";
        return stringer[index];
    }
}
