package main.java.utilities.math.operations;

public class DivisionOperation<T extends Number> implements NumberOperation<T> {

    @Override
    public T performOperation(T first, T second) {
        return (T) new Double(first.doubleValue() / second.doubleValue());
    }


}
