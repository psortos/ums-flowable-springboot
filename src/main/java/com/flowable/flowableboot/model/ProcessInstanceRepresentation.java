// This class is used to represent a ProcessInstance object
// which returns a process instance ID value

package com.flowable.flowableboot.model;

public class ProcessInstanceRepresentation {
  private String processId;

  public ProcessInstanceRepresentation(String processId) {
    this.processId = processId;
  }

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }
}
