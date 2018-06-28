package com.simulation.tasks;

import com.simulation.entity.Person;
import java.util.concurrent.atomic.AtomicInteger;

public class InitialTask implements Task {

    private long id;
    private Person person;
    private String type = Task.INITIAL_TASK;
    private static final AtomicInteger count = new AtomicInteger(0);

    public InitialTask(Person person) {
        this.person = person;
        this.id = count.incrementAndGet();
    }

    @Override
    public void execute() {

        System.out.println(" Person ");

    }
}
