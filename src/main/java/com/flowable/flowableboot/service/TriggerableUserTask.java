package com.flowable.flowableboot.service;

import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.hibernate.id.SequenceIdentityGenerator.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("prototype")
public class TriggerableUserTask implements JavaDelegate {

  @Autowired
  private TaskService taskService;

  public void execute( DelegateExecution execution){

    String processInstanceId = execution.getProcessInstanceId();
    Task task = retrieveUserTask(processInstanceId, "1st task");

    completeUserTask(task);
  }

  public Task retrieveUserTask( String processInstanceId, String taskName){

    Task task = this.taskService.createTaskQuery()
        .processInstanceId(processInstanceId)
        .taskName(taskName)
        .singleResult();
    return task;
  }

  public void completeUserTask(Task task){
    this.taskService.complete(task.getId());
  }
}
