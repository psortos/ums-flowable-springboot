package com.flowable.flowableboot.service;


import java.io.Serializable;
import java.util.HashMap;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("triggerableServiceTask")
@Scope("prototype")
public class TriggerableServiceTask implements JavaDelegate
    , Serializable
  {

  @Autowired
  private TaskService taskService;

  @Override
  public void execute(DelegateExecution execution) {
    System.out.println(String.format("Service task triggered: %s", execution));
    incrementCount(execution);
//    execution.setVariable("username", "PEDROSORTO");
//    return;
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