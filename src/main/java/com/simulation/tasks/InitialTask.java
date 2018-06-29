package com.simulation.tasks;

import com.simulation.entity.Person;
import com.simulation.workers.Worker;
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
    public void execute(Worker worker) {

        System.out.println(" Person: '" + person.getId() + " : " + person.getName() + "' worked upon by Worker: '" + worker.getId() + " : " + worker.getType() + "'");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
