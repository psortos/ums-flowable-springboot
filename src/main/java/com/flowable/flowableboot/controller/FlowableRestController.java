package com.flowable.flowableboot.controller;

import com.flowable.flowableboot.service.FlowableService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

  public FlowableRestController(
      FlowableService flowableService) {
    this.flowableService = flowableService;
  }

  @PostMapping(value="/process")
  public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation){
    String assignee = startProcessRepresentation.getAssignee();
    flowableService.startProcess(assignee);
  }

  @PostMapping(value="/user")
  public void CreateUserEndpoint(@RequestBody CreateUser createUser){
//    List<Object> userDetails = new ArrayList<Object>();
    String username = createUser.getUsername();
    String firstName = createUser.getFirstName();
    String lastName = createUser.getLastName();
    Date birthdate = createUser.getBirthDate();
    flowableService.addUser(username, firstName, lastName, birthdate);
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

  static class TaskRepresentation {
    private String id;
    private String name;

    public TaskRepresentation(String id, String name){
      this.id = id;
      this.name = name;
    }

    public String getId(){
      return id;
    }
    public void setId(String id){
      this.id = id;
    }
    public String getName(){
      return name;
    }
    public void setName(String name){
      this.name = name;
    }
  }

  static class StartProcessRepresentation {
    private String assignee;

    public String getAssignee() {
      return assignee;
    }

    public void setAssignee(String assignee) {
      this.assignee = assignee;
    }
  }

  static class CreateUser{
    private String username;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public Date getBirthDate() {
      return birthDate;
    }

    public void setBirthDate(Date birthDate) {
      this.birthDate = birthDate;
    }
  }
}
