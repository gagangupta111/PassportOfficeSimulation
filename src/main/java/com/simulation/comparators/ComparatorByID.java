package com.simulation.comparators;

import com.simulation.entity.Person;
import com.simulation.tasks.Task;

import java.util.Comparator;

public class ComparatorByID implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (o1.getId() > o2.getId()){
            return 1;
        }else if (o1.getId() < o2.getId()){
            return -1;
        }else {
            return 0;
        }
    }
}
