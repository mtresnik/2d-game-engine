/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.engine.program.game.physics.handlers;

import main.java.engine.output.render.objects._2d.DrawFrame;
import main.java.engine.program.game.physics.objects.CollisionMask;

/**
 *
 * @author fr0z3n2
 */
public abstract class CollisionHandler {

    /**
     * Generates a collision mask based on a DrawFrame.
     *
     * @param drawFrame Reference frame.
     * @return CollisionMask based on the reference frame.
     */
    public static CollisionMask generateCollisionMask(DrawFrame drawFrame) {
        return new CollisionMask(drawFrame);
    }


    /**
     * Generates a collision mask array based on an array of DrawFrames.
     * @param drawFrameArray
     * @see
     * CollisionHandler#generateCollisionMask(main.java.engine.output.render.objects._2d.DrawFrame)
     * @return
     */
    public static CollisionMask[] generateCollisionMaskArray(DrawFrame[] drawFrameArray) {
        CollisionMask[] retArray = new CollisionMask[drawFrameArray.length];
        for (int index = 0; index < drawFrameArray.length; index++) {
            retArray[index] = generateCollisionMask(drawFrameArray[index]);
        }
        return retArray;
    }


}
