package com.simulation.service;

import com.simulation.queues.Queue;

public interface QueueService {

    public Queue getInitialQueue();
    public Queue getDocVerQueue();
    public Queue getPoliceVerQueue();
    public Queue getBioVerQueue();

}
