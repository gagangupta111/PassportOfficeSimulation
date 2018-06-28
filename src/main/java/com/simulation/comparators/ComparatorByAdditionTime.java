package com.simulation.comparators;

import com.simulation.entity.Person;

import java.util.Comparator;

public class ComparatorByAdditionTime implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {

        if (Person.getCurrentTime(o1.getQueueAddition()).before(Person.getCurrentTime(o2.getQueueAddition()))){
            return 1;
        }else if (Person.getCurrentTime(o1.getQueueAddition()).after(Person.getCurrentTime(o2.getQueueAddition()))){
            return 1;
        }else {
            return 0;
        }

    }
}