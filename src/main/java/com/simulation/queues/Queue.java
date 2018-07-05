package com.simulation.queues;

import com.simulation.comparators.ComparatorByAdditionTime;
import com.simulation.comparators.ComparatorByID;
import com.simulation.tasks.Task;
import java.util.*;

public class Queue {

    public static final Comparator<Task> COMPARATOR = new ComparatorByAdditionTime();

    private int capacity;
    private int agents;
    private int time;
    private Collection<Task> list = new ArrayList<Task>();

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAgents() {
        return agents;
    }

    public void setAgents(int agents) {
        this.agents = agents;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setList(Collection<Task> list) {
        this.list = list;
    }

    public Collection<Task> getList() {
        return list;
    }

    public Queue() {
    }

    public Queue(int capacity) {
        this.capacity = capacity;
        list = new ArrayList<Task>(capacity);
    }

    public Queue(int capacity, int agents, int time, int size) {
        this.capacity = capacity;
        this.agents = agents;
        this.time = time;
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
        if (COMPARATOR != null){
            Collections.sort((List<Task>)(list), COMPARATOR);
        }
        task = list.iterator().next();
        list.remove(task);
        return task;

    }

    @Override
    public String toString() {
        return "Queue{" +
                "capacity=" + capacity +
                ", agents=" + agents +
                ", time=" + time +
                ", list=" + list +
                '}';
    }
}
