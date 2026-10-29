package ex1;

/**
 * A mutable integer counter. Not thread-safe.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
class Counter {

    // Shared state variable
    private int c = 0;

    /**
     * Increases the counter by one.
     */
    public void increment() {
        c++;
    }

    /**
     * Decreases the counter by one.
     */
    public void decrement() {
       c--;
    }

    /**
     * @return the current counter-value
     */
    public int value() {
        return c;
    }
}
