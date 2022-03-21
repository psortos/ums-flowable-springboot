# Running the application

## This flowable springboot application uses the following technologies:
- Java version 11
- Spring Boot Start Parent 2.6.3
- Flowable Spring Boot Start 6.7.2

## Current worrkflow for testing workflow
- Run the FloweablebootApplication
- Add a user by hitting the /user endpoint
    - curl -H "Content-Type: application/json" -d '{"username" : "tester", "firstName":"test_first", "lastName":"test_last", "birthDate":"1999-07-23"}' http://localhost:8080/user
- Kick off a new workflow process and assign a user
    - curl -H "Content-Type: application/json" -d '{"assignee" : "PEDROSORTO"}' http://localhost:8080/process
    - IDE console/Terminal will ask for user task asignee, enter username and hit enter
- Query for all tasks assigned to asignee
    - curl http://localhost:8080/tasks\?assignee\=<username>
    - take note of the id value returned, this is the task ID at the "analyst status" step in the workflow
- At this step the "analyst" must update the status of the task
    - curl -H "Content-Type: application/json" -d '{"status":"COMPLETE", "id":"4a21a85b-a945-11ec-8c96-acde48001122"}' http://localhost:8080/status
- If the status of the task is set to complete, the workflow will continue to the "last" task in the workflow
- Retrieve the id for the last task
    - curl http://localhost:8080/tasks\?assignee\=PEDROSORTO
    - returns: [{"id":"1c256402-a946-11ec-8c96-acde48001122","name":"last"}]
- The last step is a "usertask" and can be completed by hitting the user task endpoint
    - curl -H "Content-Type: application/json" -d '{"taskName":"last", "taskId":"1c256402-a946-11ec-8c96-acde48001122"}' http://localhost:8080/usertask
- Upon sending the curl request the tasks endpoint should return an empty list of tasks
    - curl http://localhost:8080/tasks\?assignee\=PEDROSORTO
    - []