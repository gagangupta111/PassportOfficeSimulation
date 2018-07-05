package com.simulation.workers;

import com.simulation.constants.Status;
import com.simulation.queues.Queue;

public interface Worker {

    public int getId();
    public Status getType();
    public Queue getQueue1();
    public Queue getQueue2();

}
