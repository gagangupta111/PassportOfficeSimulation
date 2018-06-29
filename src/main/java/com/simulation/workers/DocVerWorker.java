package com.simulation.workers;

import com.simulation.queues.InitialQueue;
import com.simulation.tasks.Task;

public class DocVerWorker implements Worker, Runnable{

    private InitialQueue queue;
    private int id;
    private String type;

    public DocVerWorker(InitialQueue queue, int id) {
        this.queue = queue;
        this.id = id;
    }

    public DocVerWorker(InitialQueue queue, int id, String type) {
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