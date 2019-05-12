package main.java.utilities.timer;

public class Timer {

    Long start_time;
    Long end_time;
    double firePeriod;
    boolean repeat;
    Runnable runObject;


    public Timer(Runnable runObject) {
        this(runObject, 1.0);
    }


    // One time use constructor
    public Timer(Runnable runObject, double firePeriod) {
        this(runObject, false, firePeriod);
    }


    public Timer(Runnable runObject, boolean repeat, double firePeriod) {
        this(runObject, repeat, firePeriod, System.currentTimeMillis(), System.currentTimeMillis() + (int) (firePeriod * 1000));
    }


    public Timer(Runnable runObject, boolean repeat, double firePeriod, Long start_time, Long end_time) {
        this.runObject = runObject;
        this.repeat = repeat;
        this.firePeriod = firePeriod;
        this.start_time = start_time;
        this.end_time = end_time;
    }


    public boolean check() {
        if (System.currentTimeMillis() > this.end_time) {
            return false;
        }
        if (System.currentTimeMillis() >= this.start_time + (int) (firePeriod * 1000)) {
            return true;
        }
        return false;
    }


    public void fire() {
        this.start_time = System.currentTimeMillis();
        if (this.repeat == true) {
            this.end_time = this.start_time + (int) (firePeriod * 1000);
        }
        this.runObject.run();
    }


}
