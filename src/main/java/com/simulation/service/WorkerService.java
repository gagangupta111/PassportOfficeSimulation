package com.simulation.service;

import com.simulation.queues.InitialQueue;
import com.simulation.workers.InitialWorker;

public class WorkerService {

    private static volatile WorkerService instance;
    private static Object mutex = new Object();
    private InitialQueue queue;

    private WorkerService() {

        queue = new InitialQueue(10);
        for (int i = 0; i < 10; i++){

            Thread thread1 = new Thread(new InitialWorker(queue));
            thread1.start();

        }

    }

    public InitialQueue getQueue() {
        return queue;
    }

    public static WorkerService getInstance() {
        WorkerService result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new WorkerService();
            }
        }
        return result;
    }

}
