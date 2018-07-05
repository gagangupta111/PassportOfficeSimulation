package com.simulation.service.impl;

import com.simulation.constants.Status;
import com.simulation.queues.Queue;
import com.simulation.service.QueueService;
import com.simulation.workers.WorkerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@Service
@PropertySource("classpath:application.properties")
public class QueueServiceImpl implements QueueService {

    public static final String IN_QUEUE_ = "IN_QUEUE_";
    public static final String IN_PROGRESS_ = "IN_PROGRESS_";
    Map<WorkerImpl, Thread> map = new HashMap<>();

    @Value("${agents.initial.Ver}")
    private int agentsInitialVer;

    @Value("${time.initial.Ver}")
    private int timeInitialVer;

    @Value("${capacity.initial.Ver}")
    private int capacityInitialVer;

    private List<Queue> list = new ArrayList<Queue>();

    public List<Queue> getList() {
        return list;
    }

    public void setList(List<Queue> list) {
        this.list = list;
    }

    public void reInitialize(List<Queue> queues){
        this.list.addAll(queues);
        init();
    }

    @PostConstruct
    public void init() {

        Set<WorkerImpl> set = map.keySet();
        for (WorkerImpl worker : set){
            worker.terminate();
            map.get(worker).interrupt();
            try {
                map.get(worker).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (list.isEmpty()){
            Queue queue = new Queue(capacityInitialVer);
            queue.setTime(timeInitialVer);
            queue.setAgents(agentsInitialVer);
            list.add(queue);
        }

        int count = 0;
        for (int i = 0 ; i < list.size(); i++, count = count+2){

            Queue queue1 = list.get(i);
            Queue queue2 = (i+1) >= list.size() ? null : list.get(i+1);
            Status newType = Status.map.get(count);
            if (newType == null){
                Status status;
                    status = new Status(count, IN_QUEUE_ + "QUEUE" + count);
                    Status.map.put(count, status);
                    Status.map.put(count + 1, new Status(count + 1, IN_PROGRESS_ + "QUEUE" + count + 1));
                    newType = status;

            }
            int agents = queue1.getAgents();
            for (int j = 0; j < agents; j++){

                WorkerImpl worker = new WorkerImpl(queue1, queue2, newType, queue1.getTime()*1000);
                Thread thread1 = new Thread( worker,"WorkerImpl");
                map.put(worker, thread1);
                thread1.start();

            }

        }

    }

}
