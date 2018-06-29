package com.simulation.workers;

import com.simulation.constants.WorkerConstants;
import com.simulation.tasks.Task;
import com.simulation.queues.*;

import java.util.concurrent.atomic.AtomicInteger;

public class InitialWorker implements Worker, Runnable{

    private InitialQueue queue;
    private int id;
    private String type;
    private static final AtomicInteger count = new AtomicInteger(0);

    public InitialWorker(InitialQueue queue) {
        this.queue = queue;
        this.id = count.incrementAndGet();
        this.type = WorkerConstants.INITIAL_WORKER;
    }

    public InitialWorker(InitialQueue queue, int id, String type) {
        this.queue = queue;
        this.id = id;
        this.type = type;
    }

    @Override
    public void run() {
        Task task;
        while (true) {
            synchronized (queue) {

                while (queue.isEmpty()) {

                    try {

                        queue.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }
                }

                task= queue.fetch();
                task.execute(this);
                queue.notifyAll();
            }

        }
    }

    public InitialQueue getQueue() {
        return queue;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
