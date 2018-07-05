package com.simulation.service.impl;

import com.simulation.dao.PersonRepository;
import com.simulation.entity.Person;
import com.simulation.service.PersonService;
import com.simulation.service.QueueService;
import com.simulation.tasks.InitialTask;
import com.simulation.util.BeanUtil;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = RuntimeException.class)
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private QueueService queueService;

    public PersonServiceImpl(PersonRepository personRepository, QueueService queueService) {
        this.personRepository = personRepository;
        this.queueService = queueService;
    }

    @Override
    public Optional<Person> findPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> findPersonsByProgress(String progress) {
        return personRepository.findPersonsByProgress(progress);
    }

    @Override
    public List<Person> findAllByOrderByQueueAdditionDesc() {
        return personRepository.findAllByOrderByQueueAdditionDesc();
    }

    @Override
    public List<Person> findAllByOrderByTotalTimeDesc() {
        return personRepository.findAllByOrderByTotalTimeDesc();
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {

        Person person1 = personRepository.save(person);
        queueService.getList().get(0).add(new InitialTask(person1));
        return person1;

    }

    @Override
    public Person update(Person person) {

        if (personRepository.existsById(person.getId())){
            return personRepository.save(person);
        }

        return null;

    }
}
