package com.flowable.flowableboot.model;

public class UserTaskRepresentation {
  private String taskName;
  private String taskId;

  public UserTaskRepresentation(String taskName, String taskId) {
    this.taskName = taskName;
    this.taskId = taskId;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }
}