/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import frontend.Item;
import frontend.MiddlewareHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class testThread implements Runnable {

    MiddlewareHandler handler = new MiddlewareHandler();
    private int num;

    public testThread(int number) {
        num = number;
    }

    @Override
    public void run() {
        try {
            while (true) {
                //Thread.sleep(5000);
                Item item = handler.getRandomItem();
                System.out.println("Thread "+num+" : "+item.itemID);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

/**
 *
 * @author root
 */
public class Test {

    public Test() throws InterruptedException {
        int number = 1000;
        ExecutorService executor = Executors.newCachedThreadPool();
        long t1 = System.currentTimeMillis();
        long t2;
        for (int i = 0; i < number; i++) {
            executor.execute(new testThread(i));
        }
        //executor.shutdown();
        //executor.awaitTermination(0, TimeUnit.NANOSECONDS);
        t2 = System.currentTimeMillis();
        System.out.println("Time to complete " + number + " req: " + (t2 - t1) + " ms");
        System.out.println("Evarage: " + (number * 1.0 / (t2 - t1) * 1000) + " req for 1 second.");
    }
}
