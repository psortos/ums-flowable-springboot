package com.flowable.flowableboot.service;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Scanner;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("assignServiceTask")
@Scope("prototype")
public class AssignServiceTask implements JavaDelegate, Serializable
  {

  @Autowired
  private TaskService taskService;

  @Override
  public void execute(DelegateExecution execution) {

    System.out.println(String.format("Execution id: %s", execution.getId()));

    Scanner scanner = new Scanner(System.in);
    System.out.println(String.format("Service task triggered: %s", execution));
    incrementCount(execution);

    System.out.println("Enter assignee username:");
    String assignee = scanner.nextLine();
    System.out.println(String.format("Task assignee: %s", assignee));
    execution.setVariable("assignedUsername", assignee);
  }

//  @Override
//  public void trigger(DelegateExecution execution, String signalName, Object signalData) {
//    System.out.println("trigger");
//    incrementCount(execution);
//  }

    public void incrementCount(DelegateExecution execution) {
      String variableName = "count";
      int count = 0;
      if (execution.hasVariable(variableName)) {
        count = (int) execution.getVariable(variableName);
      }
      count++;
      System.out.println(String.format("Count: %s", count));
      execution.setVariable(variableName, count);
    }
}