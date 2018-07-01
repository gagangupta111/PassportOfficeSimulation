package com.simulation.queues;

import com.simulation.comparators.ComparatorByAdditionTime;
import com.simulation.tasks.Task;

import java.util.*;

public class Queue {

    public static final Comparator<Task> COMPARATOR = new ComparatorByAdditionTime();

    private int capacity;
    private Collection<Task> list;

    public Queue(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<Task>(capacity);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public boolean isFull(){

        if (list.isEmpty()){
            return false;
        }

        if (list.size() < capacity){
            return false;
        }

        return true;

    }

    public boolean add(Task task){

        if (isFull()){
            return false;
        }

        synchronized (this){

            list.add(task);
            if (COMPARATOR != null){
                Collections.sort((List<Task>)(list), COMPARATOR);
            }
            this.notifyAll();

        }

        return true;

    }

    public Task fetch(){

        Task task = null;
        task = list.iterator().next();
        list.remove(task);
        return task;

    }

    public Collection<Task> getList() {
        return list;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "capacity=" + capacity +
                ", list=" + list +
                '}';
    }
}
