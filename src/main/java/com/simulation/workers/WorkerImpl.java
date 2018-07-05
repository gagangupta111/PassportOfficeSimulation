package com.simulation.workers;

import com.simulation.constants.Status;
import com.simulation.dao.PersonRepository;
import com.simulation.entity.Person;
import com.simulation.tasks.Task;
import com.simulation.queues.*;
import com.simulation.util.BeanUtil;
import java.util.concurrent.atomic.AtomicInteger;

import static com.simulation.service.impl.QueueServiceImpl.IN_PROGRESS_;
import static com.simulation.service.impl.QueueServiceImpl.IN_QUEUE_;

public final class WorkerImpl implements Worker, Runnable{

    private int id;
    private final Queue queue1;
    private final Queue queue2;
    private final Status type;
    private final long sleepTime;
    private static final AtomicInteger count = new AtomicInteger(0);
    private volatile boolean running = true;

    public WorkerImpl(Queue queue1, Queue queue2, long sleepTime) {
        this.sleepTime = sleepTime;
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.id = count.incrementAndGet();
        this.type = Status.map.get(0);
    }

    public WorkerImpl(Queue queue1, Queue queue2, Status type,  long sleepTime) {
        this.sleepTime = sleepTime;
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.id = count.incrementAndGet();
        this.type = type;
    }

    public WorkerImpl(Queue queue1, Status type,  long sleepTime) {
        this.sleepTime = sleepTime;
        this.queue1 = queue1;
        this.queue2 = null;
        this.id = count.incrementAndGet();
        this.type = type;
    }

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {

        Task task;
        while (running) {
            try {
                synchronized (queue1) {
                    while (queue1.isEmpty()) {
                        queue1.wait();
                    }
                    task = queue1.fetch();
                    queue1.notifyAll();
                }

                Status status = null;
                int level = ((Status) type).getLevelCode() + 1;
                Status newType = Status.map.get(level);
                if (newType == null) {
                    if (queue2 != null) {
                        status = new Status(level, IN_QUEUE_ + "QUEUE" + level);
                        Status.map.put(level, status);
                        Status.map.put(level + 1, new Status(level + 1, IN_PROGRESS_ + "QUEUE" + level + 1));
                        newType = status;
                    } else {
                        status = new Status(level, "PROCESSED");
                        Status.map.put(level, status);
                        newType = status;
                    }
                }
                task.getPerson().setQueueAddition(Person.getCurrentTime());
                task.getPerson().setQueueStatus((newType).getLevelCode());
                task.getPerson().setVerificationStatus((newType).getMessage());
                task.setType(newType);
                BeanUtil.getBean(PersonRepository.class).save(task.getPerson());

                try {

                    Thread.sleep(sleepTime);

                } catch (InterruptedException e) {
                    System.out.println(id + "Interrupted");
                }

                task.execute(this);

                if (queue2 != null) {
                    synchronized (queue2) {
                        while (queue2.isFull()) {
                            queue2.wait();
                        }

                        queue2.add(task);
                        queue2.notifyAll();

                    }
                } else {
                    long totalTime = Person.getCurrentTime(task.getPerson().getQueueAddition()).getTime() - Person.getCurrentTime(task.getPerson().getInitialAddition()).getTime();
                    task.getPerson().setTotalTime(totalTime);
                    BeanUtil.getBean(PersonRepository.class).save(task.getPerson());
                }
            } catch (InterruptedException e) {
                System.out.println(id + " Interrupted!");
                running = false;
            }
        }
    }
    public Queue getQueue1() {
        return queue1;
    }

    @Override
    public Queue getQueue2() {
        return queue2;
    }

    public int getId() {
        return id;
    }

    @Override
    public Status getType() {
        return type;
    }

}
