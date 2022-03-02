package com.flowable.flowableboot.service;


import java.io.Serializable;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.delegate.TriggerableActivityBehavior;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("triggerableServiceTask")
@Scope("prototype")
public class TriggerableServiceTask implements JavaDelegate
    , TriggerableActivityBehavior
    , Serializable
  {

  @Override
  public void execute(DelegateExecution execution) {
    System.out.println("execute");
    incrementCount(execution);
  }

  @Override
  public void trigger(DelegateExecution execution, String signalName, Object signalData) {
    System.out.println("trigger");
    incrementCount(execution);
  }
  public void incrementCount(DelegateExecution execution) {
    String variableName = "count";
    int count = 0;
    execution.setVariable(variableName, count);
    if (execution.hasVariable(variableName)) {
      count = (int) execution.getVariable(variableName);
    }
    count++;
    System.out.println("count: " + count);
  }
}