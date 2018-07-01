package com.simulation.workers;

import com.simulation.constants.Status;

public interface Worker {

    public int getId();
    public Enum<Status> getType();

}
