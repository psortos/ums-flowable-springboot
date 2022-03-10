package com.flowable.flowableboot.service;

import com.flowable.flowableboot.repository.PersonRepository;
import com.flowable.flowableboot.model.Person;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
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

  //  Method to initiate process with an assignee name
  public void startProcess(String assignee) {

    Person person = personRepository.findByUsername(assignee);

    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put("person", person);
    runtimeService.startProcessInstanceByKey("multiTaskProcess", variables);
//    runtimeService.startProcessInstanceByKey("multiTaskProcess");
  }

// Method to get tasks by assignee name
  public List<Task> getTasks(String assignee){
    return taskService.createTaskQuery().taskAssignee(assignee).list();
  }

//  Method to get current processes
  public List<ProcessInstance> getProcessInstances(){

    List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
        .active()
        .list();
    return processInstances;
  }

  public void processInstanceDetails(String processId){
    System.out.println(String.format("processId: %s", processId));
//    Suspend all process instances
//    runtimeService.suspendProcessInstanceById(processId);
  }

//  Method to add a new user
  public void addUser(String username, String firstName, String lastName, Date birthDate){
    Person person = new Person();
    personRepository.save(new Person(username, firstName, lastName, birthDate));
  }

  public void createDemoUsers(){
    if (personRepository.findAll().size() == 0){
//      personRepository.save(new Person("estyl", "pedro", "sorto", new Date()));
    }
  }

//  This method needs to be passed a process instance ID value to be able
  // to search for specific tasks within the current process
  public Task retrieveTask(String taskName, String processId){
    Task task = taskService.createTaskQuery()
        .taskId(processId)
        .taskName(taskName)
        .singleResult();
    return task;
  }

  public void completeTask(Task task){
    this.taskService.complete(task.getId());
  }

  public void setStatus(String status, String taskId)
  {
    Map<String, Object> variables = new HashMap<>();
    variables.put("status", status);
    this.taskService.complete(taskId, variables);
  }
}
