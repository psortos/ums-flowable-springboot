package com.flowable.flowableboot.controller;

import com.flowable.flowableboot.model.Person;
import com.flowable.flowableboot.model.ProcessInstanceRepresentation;
import com.flowable.flowableboot.model.StartProcessRepresentation;
import com.flowable.flowableboot.model.TaskRepresentation;
import com.flowable.flowableboot.model.TaskStatusRepresentation;
import com.flowable.flowableboot.service.FlowableService;
import com.flowable.flowableboot.service.ReceiveTask;
import java.util.ArrayList;
import java.util.List;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowableRestController {

  private final FlowableService flowableService;
  private final ReceiveTask receiveTask;

  public FlowableRestController(
      FlowableService flowableService, ReceiveTask receiveTask) {
    this.flowableService = flowableService;
    this.receiveTask = receiveTask;
  }

//  Endpoint used to start a proces instance
  @PostMapping(value="/process")
  public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation){
    String assignee = startProcessRepresentation.getAssignee();
    flowableService.startProcess(assignee);
  }

//  Endpoint used to complete a user task
  @PostMapping(value="/usertask")
  public void runUserTask(@RequestBody TaskRepresentation taskRepresentation){
    String taskId = taskRepresentation.getId();
    String taskName = taskRepresentation.getName();
    // This is retrieving tasks by name and id
    Task task = flowableService.retrieveTask(taskName, taskId);
    flowableService.completeTask(task);
  }

//  Endpoint used to update a task status
  @PostMapping(value="/status")
  public void setStatus(@RequestBody TaskStatusRepresentation taskStatusRepresentation){
    String taskId = taskStatusRepresentation.getId();
    String status = taskStatusRepresentation.getStatus();
    flowableService.setStatus(taskId, status);

  }

//  Endpoint to create a user task
  @PostMapping(value="/user")
  public void CreateUserEndpoint(@RequestBody Person person){
    flowableService.addUser(person.getUsername(),
        person.getFirstName(),
        person.getLastName());
  }

//  Endpoint used to retrieve task information
  @RequestMapping(value="/tasks", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
    //  This is retrieving tasks by assignee name
    List<Task> tasks = flowableService.getTasks(assignee);
    List<TaskRepresentation> dtos = new ArrayList<>();
    for (Task task : tasks) {
      dtos.add(new TaskRepresentation(task.getId(), task.getName()));
    }
    return dtos;
  }

//  Returns all active processes
  @RequestMapping(value="/processes", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public List<ProcessInstanceRepresentation> getProcesses(){
    List<ProcessInstance> processes = flowableService.getProcessInstances();

    System.out.println("Current Processes");
    List<ProcessInstanceRepresentation> processIdList = new ArrayList<>();
    for(ProcessInstance process : processes){
      processIdList.add(new ProcessInstanceRepresentation(process.getId()));
      flowableService.processInstanceDetails(process.getId());
    }
    return processIdList;
  }

}
