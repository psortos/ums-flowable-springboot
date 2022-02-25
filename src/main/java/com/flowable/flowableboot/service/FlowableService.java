package com.flowable.flowableboot.service;

import com.flowable.flowableboot.repository.PersonRepository;
import com.flowable.flowableboot.model.Person;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlowableService {

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private PersonRepository personRepository;

  public void startProcess(String assignee) {

    Person person = personRepository.findByUsername(assignee);

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("person", person);
    runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
  }

  public List<Task> getTasks(String assignee){
    return taskService.createTaskQuery().taskAssignee(assignee).list();
  }

  public void addUser(String username, String firstName, String lastName, Date birthDate){
    personRepository.save(new Person(username, firstName, lastName, birthDate));
  }

  public void createDemoUsers(){
    if (personRepository.findAll().size() == 0){
//      personRepository.save(new Person("estyl", "pedro", "sorto", new Date()));
//      personRepository.save(new Person("lawlerz", "savio", "nguyen", new Date()));
    }
  }

}
