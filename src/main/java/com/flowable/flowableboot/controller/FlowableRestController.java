package com.flowable.flowableboot.controller;

import com.flowable.flowableboot.model.Person;
import com.flowable.flowableboot.model.ProcessInstanceRepresentation;
import com.flowable.flowableboot.model.StartProcessRepresentation;
import com.flowable.flowableboot.model.TaskRepresentation;
import com.flowable.flowableboot.model.TaskStatusRepresentation;
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
  public void runUserTask(@RequestBody UserTaskRepresentation userTaskRepresentation){
    String taskName = userTaskRepresentation.getTaskName();
    String processId = userTaskRepresentation.getProcessId();
    Task task = flowableService.retrieveTask(taskName, processId);
    flowableService.completeTask(task);
  }

  @PostMapping(value="/status")
  public void setStatus(@RequestBody TaskStatusRepresentation taskStatusRepresentation){
    String status = taskStatusRepresentation.getStatus();
    String taskId = taskStatusRepresentation.getId();
    flowableService.setStatus(status, taskId);

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
  public List<ProcessInstanceRepresentation> getProcesses(){
    List<ProcessInstance> processes = flowableService.getProcessInstances();

    System.out.println("Current Processes");
    List<ProcessInstanceRepresentation> processIdList = new ArrayList<>();
    for(ProcessInstance process : processes){
      processIdList.add(new ProcessInstanceRepresentation(process.getId()));
//    System.out.println(process);
      flowableService.processInstanceDetails(process.getId());
    }
    return processIdList;
  }

}
