package com.flowable.flowableboot.controller;

import com.flowable.flowableboot.model.Person;
import com.flowable.flowableboot.model.StartProcessRepresentation;
import com.flowable.flowableboot.model.TaskRepresentation;
import com.flowable.flowableboot.model.UserTaskRepresentation;
import com.flowable.flowableboot.service.FlowableService;
import com.flowable.flowableboot.service.ReceiveTask;
import java.util.ArrayList;
import java.util.Date;
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

  @PostMapping(value="/process")
  public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation){
    String assignee = startProcessRepresentation.getAssignee();
    flowableService.startProcess(assignee);
  }

  @PostMapping(value="/usertask")
  public void runUserTask(@RequestBody UserTaskRepresentation UserTaskRepresentation){
    String taskName = UserTaskRepresentation.getTaskName();
    String processId = UserTaskRepresentation.getProcessId();
    Task task = flowableService.retrieveTask(taskName, processId);
    flowableService.completeTask(task);
  }

  @PostMapping(value="/wait")
  public void runUserTask(){
    receiveTask.receive();
//    flowableService.completeTask(task);
  }

  @PostMapping(value="/user")
  public void CreateUserEndpoint(@RequestBody Person person){
    flowableService.addUser(person.getUsername(),
        person.getFirstName(),
        person.getLastName(),
        person.getBirthDate());
  }

  @RequestMapping(value="/tasks", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
    List<Task> tasks = flowableService.getTasks(assignee);
    List<TaskRepresentation> dtos = new ArrayList<>();
    for (Task task : tasks) {
      dtos.add(new TaskRepresentation(task.getId(), task.getName()));
    }
    return dtos;
  }

  @RequestMapping(value="/processes", method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
  public void getProcesses(){
    List<ProcessInstance> processes = flowableService.getProcessInstances();

    System.out.println("Current Processes");
    for(ProcessInstance process : processes){
//    System.out.println(process);
      flowableService.processInstanceDetails(process.getId());
    }

  }

}
