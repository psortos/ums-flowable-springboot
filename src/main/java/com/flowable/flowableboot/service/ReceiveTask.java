package com.flowable.flowableboot.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReceiveTask   {

  @Autowired
  private RuntimeService runtimeService;

  public void receive() {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("multiTaskProcess");
    Execution execution = runtimeService.createExecutionQuery()
        .processInstanceId(processInstance.getId())
        .activityId("waitTask")
        .singleResult();

    runtimeService.trigger(execution.getId());
  }
}
