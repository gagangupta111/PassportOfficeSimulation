package com.simulation.controller;

import com.simulation.constants.Status;
import com.simulation.entity.Person;
import com.simulation.queues.Queue;
import com.simulation.service.PersonService;
import com.simulation.service.QueueService;
import com.simulation.service.impl.QueueServiceImpl;
import com.simulation.util.BeanUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    private PersonService personService;
    private QueueService queueService;

    public PersonController(PersonService personService, QueueService queueService) {
        this.personService = personService;
        this.queueService = queueService;
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

    @GetMapping("/personOrderBy/queueAdditionDesc")
    @ResponseBody
    public List<Person> findAllByOrderByQueueAdditionDesc() {

        return personService.findAllByOrderByQueueAdditionDesc();

    }

    @GetMapping("/personOrderBy/totalTimeDesc")
    @ResponseBody
    public List<Person> findAllByOrderByTotalTimeDesc() {

        return personService.findAllByOrderByTotalTimeDesc();

    }

    @GetMapping("/queues/{id}")
    @ResponseBody
    public Queue getQueuesStatus(@PathVariable("id") int id) {

        return queueService.getList().get(id);

    }

    @GetMapping("/queues/")
    @ResponseBody
    public List<Queue> getQueues() {

        return queueService.getList();

    }

    @GetMapping("/person/verificationStatus/{progress}")
    @ResponseBody
    public List<Person> getPersonsByProgress(@PathVariable("progress") String progress) {

        return personService.findPersonsByProgress(progress);

    }

    @PostMapping("/person/save")
    @ResponseBody
    public Person saveProduct(@RequestBody Person person) {

        person.setInitialAddition(Person.getCurrentTime());
        person.setQueueAddition(Person.getCurrentTime());
        person.setQueueStatus(Status.map.get(0).getLevelCode());
        person.setVerificationStatus(Status.map.get(0).getMessage());
        return personService.save(person);

    }

    @PostMapping("/initializeQueues")
    @ResponseBody
    public List<Queue> initializeQueues(@RequestBody List<Queue> queues) {

        queueService.reInitialize(queues);
        return queueService.getList();

    }

}
