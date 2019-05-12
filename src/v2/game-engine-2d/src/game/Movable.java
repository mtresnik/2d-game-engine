/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import jbox2d.common.Vec2;

/**
 *
 * @author Mike
 */
public interface Movable {

    public default void resetX() {
        this.setInternalVelocity(new Vec2(0.0f, this.internalVelocity().y));
    }

    public default void resetY() {
        this.setInternalVelocity(new Vec2(this.internalVelocity().x, 0.0f));
    }

    public default void goUp() {
        this.setInternalVelocity(new Vec2(this.internalVelocity().x, this.updateSpeed().y));
    }

    public default void goDown() {
        this.setInternalVelocity(new Vec2(this.internalVelocity().x, -1 * this.updateSpeed().y));
    }

    public default void goLeft() {
        this.setInternalVelocity(new Vec2(-1 * this.updateSpeed().x, this.internalVelocity().y));
    }

    public default void goRight() {
        this.setInternalVelocity(new Vec2(this.updateSpeed().x, this.internalVelocity().y));
    }

    public int mov_y();

    public int mov_x();

    public Vec2 updateSpeed();

    public void setInternalVelocity(Vec2 vel);

    public Vec2 internalVelocity();

    public default void updateMovements() {
        if (mov_x() < 0) {
            this.goLeft();
        } else if (mov_x() > 0) {
            this.goRight();
        } else {
            this.resetX();
        }
        if (mov_y() < 0) {
            this.goDown();
        } else if (mov_y() > 0) {
            this.goUp();
        } else {
            this.resetY();
        }
    }

}
