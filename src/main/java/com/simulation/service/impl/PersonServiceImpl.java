package com.simulation.service.impl;

import com.simulation.dao.PersonRepository;
import com.simulation.entity.Person;
import com.simulation.service.PersonService;
import com.simulation.service.WorkerService;
import com.simulation.tasks.InitialTask;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackOn = RuntimeException.class)
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private WorkerService workerService = WorkerService.getInstance();

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        Person person1 = personRepository.save(person);
        workerService.getQueue().add(new InitialTask(person1));
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
