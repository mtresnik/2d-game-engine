package main.java.utilities.math.operations;

public class SubtractionOperation<T extends Number> implements NumberOperation<T> {

    @Override
    public T performOperation(T first, T second) {
        if (first instanceof Double || second instanceof Double) {
            return (T) new Double(first.doubleValue() - second.doubleValue());
        } else if (first instanceof Float || second instanceof Float) {
            return (T) new Float(first.floatValue() - second.floatValue());
        } else if (first instanceof Long || second instanceof Long) {
            return (T) new Long(first.longValue() - second.longValue());
        } else {
            return (T) new Integer(first.intValue() - second.intValue());
        }
    }


}
