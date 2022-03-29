// The purpose of this class is to provide an object
//representation of a flowable process task
// Tasks can vary from users tasks, services, etc.
package com.flowable.flowableboot.model;

public class TaskRepresentation {
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