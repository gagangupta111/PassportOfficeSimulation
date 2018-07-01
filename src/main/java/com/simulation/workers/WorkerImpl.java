package com.simulation.workers;

import com.simulation.constants.Status;
import com.simulation.dao.PersonRepository;
import com.simulation.entity.Person;
import com.simulation.tasks.Task;
import com.simulation.queues.*;
import com.simulation.util.BeanUtil;
import java.util.concurrent.atomic.AtomicInteger;

public final class WorkerImpl implements Worker, Runnable{

    private int id;
    private final Queue queue1;
    private final Queue queue2;
    private final Enum<Status> type;
    private final long sleepTime;
    private static final AtomicInteger count = new AtomicInteger(0);

    public WorkerImpl(Queue queue1, Queue queue2, long sleepTime) {
        this.sleepTime = sleepTime;
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.id = count.incrementAndGet();
        this.type = Status.INITIAL_INTRODUCTION_QUEUE;
    }

    public WorkerImpl(Queue queue1, Queue queue2, Enum<Status> type,  long sleepTime) {
        this.sleepTime = sleepTime;
        this.queue1 = queue1;
        this.queue2 = queue2;
        this.id = count.incrementAndGet();
        this.type = type;
    }

    public WorkerImpl(Queue queue1, Enum<Status> type,  long sleepTime) {
        this.sleepTime = sleepTime;
        this.queue1 = queue1;
        this.queue2 = null;
        this.id = count.incrementAndGet();
        this.type = type;
    }

    @Override
    public void run() {

        Task task;
        while (true) {

            synchronized (queue1) {
                while (queue1.isEmpty()) {
                    try {
                        queue1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                task = queue1.fetch();
                queue1.notifyAll();
            }

                Status newType = Status.map.get(((Status)task.getType()).getLevelCode()+1);
                task.getPerson().setQueueAddition(Person.getCurrentTime());
                task.getPerson().setQueueStatus(( newType).getLevelCode());
                task.getPerson().setVerificationStatus(( newType).getMessage());
                task.setType(newType);
                BeanUtil.getBean(PersonRepository.class).save(task.getPerson());

            try {

                Thread.sleep(sleepTime);

            } catch (InterruptedException e) {

                System.out.println( e.getCause() );
                System.out.println( e.getMessage() );

            }

            task.execute(this);

            if (queue2 != null) {
                synchronized (queue2) {
                    while (queue2.isFull()) {
                        try {
                            queue2.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    queue2.add(task);
                    queue2.notifyAll();

                }
            }else {
                long totalTime = Person.getCurrentTime(task.getPerson().getQueueAddition()).getTime() - Person.getCurrentTime(task.getPerson().getInitialAddition()).getTime();
                task.getPerson().setTotalTime(totalTime);
                BeanUtil.getBean(PersonRepository.class).save(task.getPerson());
            }
        }
    }

    public Queue getQueue1() {
        return queue1;
    }

    public int getId() {
        return id;
    }

    @Override
    public Enum<Status> getType() {
        return type;
    }

}
