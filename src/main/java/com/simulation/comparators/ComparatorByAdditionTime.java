package com.simulation.comparators;

import com.simulation.entity.Person;
import com.simulation.tasks.Task;

import java.util.Comparator;

public class ComparatorByAdditionTime implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {

        if (Person.getCurrentTime(o1.getPerson().getQueueAddition()).before(Person.getCurrentTime(o2.getPerson().getQueueAddition()))){
            return 1;
        }else if (Person.getCurrentTime(o1.getPerson().getQueueAddition()).after(Person.getCurrentTime(o2.getPerson().getQueueAddition()))){
            return 1;
        }else {
            return 0;
        }

    }
}