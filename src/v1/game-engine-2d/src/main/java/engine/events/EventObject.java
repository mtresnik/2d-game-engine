package main.java.engine.events;

public abstract class EventObject<T extends EventData> {

    /**
     * Where the EventObject is coming from.
     */
    protected final Object source;

    /**
     * The data that is contained in the EventObject.
     */
    protected final T data;


    protected EventObject(Object source, T data) {
        this.source = source;
        this.data = data;
    }


    /**
     * @see #source
     * @return Where the EventObject is coming from.
     */
    public final Object getSource() {
        return source;
    }


    /**
     *
     * @see #data
     * @return The data that is contained in the EventObject.
     */
    public final T getEventData() {
        return data;
    }


}
