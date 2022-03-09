package com.flowable.flowableboot.model;

public class TaskStatusRepresentation{
  private String status;
  private String id;

  public TaskStatusRepresentation(String status, String id) {
    this.status = status;
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
