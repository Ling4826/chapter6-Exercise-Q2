package se233.lambda.chapter2.controller;
import javafx.application.Platform;
import javafx.concurrent.Task;
import se233.lambda.chapter2.Launcher;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RefreshTask extends Task<Void> { // [cite: 3564]
    @Override
    protected Void call() throws InterruptedException { // [cite: 3564]
        for (;;) { // [cite: 3564]
            try {
                Thread.sleep((long) (60 * 1e3)); // [cite: 3564]
            } catch (InterruptedException e) { // [cite: 3565]
                System.out.println("Encountered an interrupted exception"); // [cite: 3565]
            }
            Platform.runLater(new Runnable() { // [cite: 3566]
                @Override
                public void run() { // [cite: 3566]
                    try {
                        Launcher.refreshPane(); // [cite: 3566]
                    } catch (Exception e) { // [cite: 3568]
                        e.printStackTrace(); // [cite: 3568]
                    }
                }
            });
            FutureTask futureTask = new FutureTask(new WatchTask()); // [cite: 3595]
            Platform.runLater(futureTask); // [cite: 3595]
            try {
                futureTask.get(); // [cite: 3596]
            } catch (InterruptedException e) { // [cite: 3596]
                System.out.println("Encountered an interrupted exception"); // [cite: 3596]
            } catch (ExecutionException e) { // [cite: 3597]
                System.out.println("Encountered an execution exception"); // [cite: 3597]
            }
        }
    }
}
