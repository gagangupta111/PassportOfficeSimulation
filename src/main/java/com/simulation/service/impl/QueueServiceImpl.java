package com.simulation.service.impl;

import com.simulation.constants.Status;
import com.simulation.queues.Queue;
import com.simulation.service.QueueService;
import com.simulation.workers.WorkerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Configuration
@Service
@PropertySource("classpath:application.properties")
public class QueueServiceImpl implements QueueService {

    private Queue initialQueue;
    private Queue docVerQueue;
    private Queue policeVerQueue;
    private Queue bioVerQueue;

    @Value("${agents.initial.Ver}")
    private int agentsInitialVer;

    @Value("${agents.doc.Ver}")
    private int agentsDocVer;

    @Value("${agents.police.Ver}")
    private int agentsPoliceVer;

    @Value("${agents.bio.Ver}")
    private int agentsBioVer;


    @Value("${time.initial.Ver}")
    private int timeInitialVer;

    @Value("${time.doc.Ver}")
    private int timeDocVer;

    @Value("${time.police.Ver}")
    private int timePoliceVer;

    @Value("${time.bio.Ver}")
    private int timeBioVer;


    @Value("${size.initial.Ver}")
    private int sizeInitialVer;

    @Value("${size.doc.Ver}")
    private int sizeDocVer;

    @Value("${size.police.Ver}")
    private int sizePoliceVer;

    @Value("${size.bio.Ver}")
    private int sizeBioVer;

    @PostConstruct
    public void init() {

        initialQueue    = new Queue(sizeInitialVer);
        docVerQueue     = new Queue(sizeDocVer);
        policeVerQueue  = new Queue(sizePoliceVer);
        bioVerQueue     = new Queue(sizeBioVer);

        for (int i = 0; i < agentsInitialVer; i++){

            Thread thread1 = new Thread(new WorkerImpl(initialQueue, docVerQueue, Status.INITIAL_INTRODUCTION_QUEUE, timeInitialVer*1000));
            thread1.start();

        }

        for (int i = 0; i < agentsDocVer; i++){

            Thread thread1 = new Thread(new WorkerImpl(docVerQueue, policeVerQueue, Status.DOCUMENTS_VERIFICATION_QUEUE, timeDocVer*1000));
            thread1.start();

        }

        for (int i = 0; i < agentsPoliceVer; i++){

            Thread thread1 = new Thread(new WorkerImpl(policeVerQueue, bioVerQueue, Status.POLICE_VERIFICATION_QUEUE, timePoliceVer*1000));
            thread1.start();

        }

        for (int i = 0; i < agentsBioVer; i++){

            Thread thread1 = new Thread(new WorkerImpl(bioVerQueue, Status.BIO_METRIC_VERIFICATION_QUEUE, timeBioVer*1000));
            thread1.start();

        }

    }

    @Override
    public Queue getInitialQueue() {
        return initialQueue;
    }

    public void setInitialQueue(Queue initialQueue) {
        this.initialQueue = initialQueue;
    }

    @Override
    public Queue getDocVerQueue() {
        return docVerQueue;
    }

    public void setDocVerQueue(Queue docVerQueue) {
        this.docVerQueue = docVerQueue;
    }

    @Override
    public Queue getPoliceVerQueue() {
        return policeVerQueue;
    }

    public void setPoliceVerQueue(Queue policeVerQueue) {
        this.policeVerQueue = policeVerQueue;
    }

    @Override
    public Queue getBioVerQueue() {
        return bioVerQueue;
    }

    public void setBioVerQueue(Queue bioVerQueue) {
        this.bioVerQueue = bioVerQueue;
    }

    @Override
    public String toString() {

        return "QueueService{" +
                "initialQueue=" + initialQueue +
                ", docVerQueue=" + docVerQueue +
                ", policeVerQueue=" + policeVerQueue +
                ", bioVerQueue=" + bioVerQueue +
                '}';

    }

}
