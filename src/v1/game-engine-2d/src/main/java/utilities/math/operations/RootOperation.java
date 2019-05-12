package main.java.utilities.math.operations;

public class RootOperation<T extends Number> implements NumberOperation<T> {

    @Override
    public T performOperation(T first, T second) {
        return (T) new Double(Math.pow(first.doubleValue(), second.doubleValue()));
    }


    public T performRoot(T first, Double second) {
        return (T) new Double(Math.pow(first.doubleValue(), second));
    }


}
