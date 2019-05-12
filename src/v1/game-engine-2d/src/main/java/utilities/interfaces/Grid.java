package main.java.utilities.interfaces;

public abstract class Grid<T> {


    public abstract T[][] getAllElements();


    public final boolean contains(T element) {
        for (int i = 0; i < this.getAllElements().length; i++) {
            for (int j = 0; j < this.getAllElements()[i].length; j++) {
                if (element.equals(this.getAllElements()[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }


    public final T getElement(int row, int col) {
        try {
            return this.getAllElements()[row][col];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }


}
