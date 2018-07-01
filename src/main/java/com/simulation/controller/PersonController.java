package com.simulation.controller;

import com.simulation.constants.Status;
import com.simulation.entity.Person;
import com.simulation.service.PersonService;
import com.simulation.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{id}")
    @ResponseBody
    public Person getPersonById(@PathVariable("id") Long id) {

        Optional<Person> personOptional = personService.findPersonById(id);
        return personOptional.get();

    }

    @GetMapping("/person/")
    @ResponseBody
    public List<Person> getAllPersons() {

        return personService.findAll();

    }

    @GetMapping("/queues/")
    @ResponseBody
    public WorkerService getQueuesStatus() {

        return WorkerService.getInstance();

    }

    @PostMapping("/person/save")
    @ResponseBody
    public Person saveProduct(@RequestBody Person person) {

        person.setInitialAddition(Person.getCurrentTime());
        person.setQueueAddition(Person.getCurrentTime());
        person.setQueueStatus(Status.INITIAL_INTRODUCTION_QUEUE.getLevelCode());
        person.setVerificationStatus(Status.INITIAL_INTRODUCTION_QUEUE.getMessage());
        return personService.save(person);

    }

}
