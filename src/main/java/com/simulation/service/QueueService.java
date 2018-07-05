package com.simulation.service;

import com.simulation.queues.Queue;

import java.util.Collection;
import java.util.List;

public interface QueueService {

    public List<Queue> getList();
    public void reInitialize(List<Queue> queues);

}
