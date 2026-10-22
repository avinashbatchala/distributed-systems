package ex1;

/**
 * A mutable integer counter. Not thread-safe.
 *
 * @author Avinash Batchala, Bhargav Parvatha, Nithin Kumar
 */
class Counter {

    // Shared state variable
    private int c = 0;

    /**
     * Increases the counter by one.
     */
    public void increment() {
        int b = c;
        b = b + 1;
        c = b;
    }

    /**
     * Decreases the counter by one.
     */
    public void decrement() {
        int d = c;
        d = d - 1;
        c = d;
    }

    /**
     * @return the current counter-value
     */
    public int value() {
        return c;
    }
}
