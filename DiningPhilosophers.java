package daa;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    public static void main(String[] args) {
        final int NUM_PHILOSOPHERS = 5;
        Lock[] forks = new Lock[NUM_PHILOSOPHERS];
        Thread[] threads = new Thread[NUM_PHILOSOPHERS];

        // Initialize locks for forks
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new ReentrantLock();
        }

        // Create philosopher threads
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            int philosopherId = i;
            int leftFork = philosopherId;
            int rightFork = (philosopherId + 1) % NUM_PHILOSOPHERS;

            threads[philosopherId] = new Thread(() -> {
                while (true) {
                    try {
                        // Thinking
                        System.out.println("Philosopher " + philosopherId + " is thinking.");
                        Thread.sleep((int) (Math.random() * 100));

                        // Pick up forks (odd philosophers pick right fork first, even pick left first)
                        if (philosopherId % 2 == 0) {
                            forks[leftFork].lock();
                            forks[rightFork].lock();
                        } else {
                            forks[rightFork].lock();
                            forks[leftFork].lock();
                        }

                        // Eating
                        System.out.println("Philosopher " + philosopherId + " is eating.");
                        Thread.sleep((int) (Math.random() * 100));

                        // Put down forks
                        forks[leftFork].unlock();
                        forks[rightFork].unlock();
                        System.out.println("Philosopher " + philosopherId + " has finished eating.");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Start all threads
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            threads[i].start();
        }
    }
}
