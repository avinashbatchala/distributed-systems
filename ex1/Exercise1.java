package ex1;

public class Exercise1 {

    static void main() throws InterruptedException {
        Counter counter = new Counter();
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();

        int iterations = 10000000;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                counter.increment();
                synchronizedCounter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                counter.decrement();
                synchronizedCounter.decrement();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Value of non-synchronized counter: " + counter.value());
        System.out.println("Value of synchronized counter: " + synchronizedCounter.value());
    }
}
