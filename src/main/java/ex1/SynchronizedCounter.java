package ex1;

/**
 * A thread-safe integer counter using synchronized methods.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class SynchronizedCounter {

    // Shared state variable
    private int c = 0;

    /**
     * Increases the counter by one.
     */
    public synchronized void increment() {
        int b = c;
        b = b + 1;
        c = b;
    }

    /**
     * Decreases the counter by one.
     */
    public synchronized void decrement() {
        int d = c;
        d = d - 1;
        c = d;
    }

    /**
     * @return the current counter-value
     */
    public synchronized int value() {
        return c;
    }
}
