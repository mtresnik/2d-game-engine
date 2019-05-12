package main.java.engine.program.game.physics.objects;

import main.java.engine.program.game.physics.interfaces.Interactable;
import main.java.utilities.interfaces.Grid;

public abstract class InteractableGrid<T extends Interactable> extends Grid<T> implements Interactable {

    @Override
    public abstract void interact(Interactable other);


    @Override
    public abstract boolean isAlive();


    @Override
    public abstract Number kill();


}
