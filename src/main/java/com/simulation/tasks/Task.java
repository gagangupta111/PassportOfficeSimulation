package com.simulation.tasks;

import com.simulation.constants.Status;
import com.simulation.entity.Person;
import com.simulation.workers.Worker;

public interface Task {

    public void execute(Worker worker);

    public long getId();

    public Person getPerson();

    public Enum<Status> getType();

    public void setType(Enum<Status> type);

}
