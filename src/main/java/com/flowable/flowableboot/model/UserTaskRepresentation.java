package com.flowable.flowableboot.model;

public class UserTaskRepresentation {
  private String taskName;
  private String processId;

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }
}