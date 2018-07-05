package com.simulation.tasks;

import com.simulation.constants.Status;
import com.simulation.dao.PersonRepository;
import com.simulation.entity.Person;
import com.simulation.util.BeanUtil;
import com.simulation.workers.Worker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

import static com.simulation.service.impl.QueueServiceImpl.IN_PROGRESS_;
import static com.simulation.service.impl.QueueServiceImpl.IN_QUEUE_;

public class InitialTask implements Task {

    private PersonRepository personRepository = BeanUtil.getBean(PersonRepository.class);

    private long id;
    private Person person;
    private Status type = Status.map.get(0);
    private static final AtomicInteger count = new AtomicInteger(0);

    public InitialTask(Person person) {
        this.person = person;
        this.id = count.incrementAndGet();
    }

    public InitialTask(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void execute(Worker worker) {

        Status status = null;
        int level = ((Status)type).getLevelCode()+1;
        Status newType = Status.map.get(level);
        if (newType == null){
            if (worker.getQueue2() != null) {
                status = new Status(level, IN_QUEUE_ + "QUEUE" + level);
                Status.map.put(level, status);
                Status.map.put(level + 1, new Status(level + 1, IN_PROGRESS_ + "QUEUE" + level + 1));
                newType = status;
            }else {
                status = new Status(level, "PROCESSED");
                Status.map.put(level, status);
                newType = status;
            }
        }
        this.type = newType;
        person.setQueueAddition(Person.getCurrentTime());
        person.setQueueStatus(( newType).getLevelCode());
        person.setVerificationStatus(( newType).getMessage());
        personRepository.save(person);
        System.out.println(" Person: '" + person.getId() + " : " + person.getName() + "' worked upon by Worker: '" + worker.getId() + " : " + worker.getType() + "'");

    }

    public long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public String toString() {
        return "InitialTask{" +
                ", id=" + id +
                ", person=" + person +
                ", type=" + type +
                '}';
    }
}
