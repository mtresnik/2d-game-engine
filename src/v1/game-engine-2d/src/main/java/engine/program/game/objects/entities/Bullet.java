package main.java.engine.program.game.objects.entities;

import main.java.utilities.math.direction.DirectionType;

public abstract class Bullet<FIRER_TYPE extends BulletFirer> extends Entity {

    protected FIRER_TYPE owner;


    protected Bullet() {
        super(Double.MAX_VALUE);
    }


    public abstract FIRER_TYPE getOwner();


    protected void fire(DirectionType angle) {
        return;
    }


}
