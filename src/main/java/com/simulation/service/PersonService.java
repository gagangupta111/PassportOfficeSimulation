package com.simulation.service;

import com.simulation.entity.Person;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> findPersonById(Long id);
    List<Person> findAll();
    Person save(Person person);
    Person update(Person person);
    public List<Person> findPersonsByProgress(String progress);
    public List<Person> findAllByOrderByQueueAdditionDesc();
    public List<Person> findAllByOrderByTotalTimeDesc();

}
