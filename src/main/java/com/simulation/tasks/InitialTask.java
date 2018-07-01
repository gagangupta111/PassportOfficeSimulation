package com.simulation.tasks;

import com.simulation.constants.Status;
import com.simulation.dao.PersonRepository;
import com.simulation.entity.Person;
import com.simulation.util.BeanUtil;
import com.simulation.workers.Worker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

public class InitialTask implements Task {

    private PersonRepository personRepository = BeanUtil.getBean(PersonRepository.class);

    private long id;
    private Person person;
    private Enum<Status> type = Status.INITIAL_INTRODUCTION_QUEUE;
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

        Status newType = Status.map.get(((Status)type).getLevelCode()+1);
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

    public Enum<Status> getType() {
        return type;
    }

    public void setType(Enum<Status> type) {
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
