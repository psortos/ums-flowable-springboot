// The class is used to represent the Process Instance
// start object, which is represented by a JSON object
//  containing an assignee
package com.flowable.flowableboot.model;

public class StartProcessRepresentation {
  private String assignee;

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }
}
