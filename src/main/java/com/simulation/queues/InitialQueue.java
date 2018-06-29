package com.simulation.queues;

import com.simulation.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InitialQueue{

    private int capacity;
    private List<Task> list;

    public InitialQueue(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<>(capacity);
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
            this.notifyAll();

        }

        return true;

    }

    public Task fetch(){

        Task task = null;
        if (isEmpty()){
            return task;
        }

        task = list.get(0);
        list.remove(task);
        return task;

    }

}
