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

    @GetMapping("/queues/")
    @ResponseBody
    public String getQueuesStatus() {

        String message;
        JSONObject json = new JSONObject();
        json.put("docVerQueue", queueService.getDocVerQueue());
        json.put("policeVerQueue", queueService.getPoliceVerQueue());
        json.put("bioVerQueue", queueService.getBioVerQueue());

        message = json.toString();
        return message;

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
        person.setQueueStatus(Status.INITIAL_INTRODUCTION_QUEUE.getLevelCode());
        person.setVerificationStatus(Status.INITIAL_INTRODUCTION_QUEUE.getMessage());
        return personService.save(person);

    }

}
