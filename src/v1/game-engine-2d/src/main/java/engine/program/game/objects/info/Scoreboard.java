package main.java.engine.program.game.objects.info;

import java.math.BigInteger;
import java.util.HashMap;
import javafx.util.Pair;
import main.java.utilities.interfaces.Serializable;

public abstract class Scoreboard implements Serializable {

    protected Pair<String, BigInteger> highScore;
    protected HashMap<String, BigInteger> scores;


    public abstract Pair<String, BigInteger> getHighScore();


    public abstract HashMap<String, BigInteger> getScores();


    @Override
    public abstract String toString();


}
