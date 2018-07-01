package com.simulation.service;

import com.simulation.constants.Status;
import com.simulation.queues.Queue;
import com.simulation.workers.WorkerImpl;

public class WorkerService {

    private static volatile WorkerService instance;
    private static Object mutex = new Object();
    private Queue initialQueue;
    private Queue docVerQueue;
    private Queue policeVerQueue;
    private Queue bioVerQueue;

    private WorkerService() {

        initialQueue    = new Queue(100);
        docVerQueue     = new Queue(20);
        policeVerQueue  = new Queue(20);
        bioVerQueue     = new Queue(20);

        for (int i = 0; i < 20; i++){

            Thread thread1 = new Thread(new WorkerImpl(initialQueue, docVerQueue, Status.INITIAL_INTRODUCTION_QUEUE, 15000));
            thread1.start();

        }

        for (int i = 0; i < 15; i++){

            Thread thread1 = new Thread(new WorkerImpl(docVerQueue, policeVerQueue, Status.DOCUMENTS_VERIFICATION_QUEUE, 10000));
            thread1.start();

        }

        for (int i = 0; i < 10; i++){

            Thread thread1 = new Thread(new WorkerImpl(policeVerQueue, bioVerQueue, Status.POLICE_VERIFICATION_QUEUE, 15000));
            thread1.start();

        }

        for (int i = 0; i < 12; i++){

            Thread thread1 = new Thread(new WorkerImpl(bioVerQueue, Status.BIO_METRIC_VERIFICATION_QUEUE, 10000));
            thread1.start();

        }

    }

    public Queue getInitialQueue() {
        return initialQueue;
    }

    public Queue getDocVerQueue() {
        return docVerQueue;
    }

    public Queue getPoliceVerQueue() {
        return policeVerQueue;
    }

    public Queue getBioVerQueue() {
        return bioVerQueue;
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

    @Override
    public String toString() {

        return "WorkerService{" +
                "initialQueue=" + initialQueue +
                ", docVerQueue=" + docVerQueue +
                ", policeVerQueue=" + policeVerQueue +
                ", bioVerQueue=" + bioVerQueue +
                '}';

    }

}
