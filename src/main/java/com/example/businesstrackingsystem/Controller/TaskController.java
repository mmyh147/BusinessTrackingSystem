package com.example.businesstrackingsystem.Controller;


import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Model.Task;
import com.example.businesstrackingsystem.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/get")
    public ResponseEntity getAllTask() {
        logger.info("request to get all tasks");
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @PostMapping("/add")
    public ResponseEntity addNewTask(@RequestBody @Valid Task task) {

        logger.info("request to add new task with body : " + task);
        Task getTask = taskService.addTask(task);
        logger.info("add new task with body : " + task);
        return ResponseEntity.ok("Task added successfully\n " + getTask);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@PathVariable Integer id, @RequestBody @Valid Task task) {

        logger.info("request to update task with body : " + task);
        Task updatedTask = taskService.updateTask(id, task);
        logger.info("update task with body: " + task);
        return ResponseEntity.ok("Task updated successfully\n" + updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Integer id) {
        logger.info("request to delete task with ID : " + 1);
        taskService.deleteTask(id);
        logger.info("task with ID : " + id + " has been deleted");
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully with ID : " + id));
    }

    @PutMapping("status/{id}/{userid}/{status}")
    public ResponseEntity<String> changeTaskStatus(@PathVariable Integer id,
                                                   @PathVariable Integer userid,
                                                   @PathVariable String status) {
            logger.info("request to change status to {"+ status + "} for task ID :" + id + " by employee ID : " + userid);
            taskService.changeStatus(id, userid, status);
            logger.info(" status changed to {"+ status + "} for task ID :" + id + " by employee ID : " + userid);

        return ResponseEntity.ok("Task status changed successfully");

    }

    @PutMapping("review/{id}/{userid}/{status}/{comment}")
    public ResponseEntity<String> managerReview(@PathVariable Integer id,
                                                   @PathVariable Integer userid,
                                                   @PathVariable String status,
                                                @PathVariable String comment ) {
        logger.info("request to review task ID : " + id + " by manager ID : " + userid + " with review : " + status + " and comment : " + comment);
        taskService.managerReview(id, userid, status,comment);
        logger.info("task with ID : " + id + " reviewed by manager ID : " + userid + " with review : " + status + " and comment : " + comment);
        return ResponseEntity.ok("Task status changed successfully");

    }


//counts number of days takes to submit the task
    @GetMapping("count-days/{id}")
    public ResponseEntity timeToSubmit(@PathVariable Integer id) {
        logger.info("request count of submit task for employee ID : " + id);
        Long days = taskService.calculateDaysToSubmit(id);
        logger.info("retrieve the count of submit task for employee ID : " + id);

        return ResponseEntity.ok(days);

    }

//assign task to user
    @PutMapping("assign/{id}/{managerId}/{userId}")
    public ResponseEntity assignTask(@PathVariable Integer id,
                                                @PathVariable Integer managerId,
                                                @PathVariable Integer userId) {
        logger.info("request by manager ID : " + managerId + " To assign task ID : " + id + " to employee : " + userId);
        taskService.assignTask(id, managerId, userId);
        logger.info(" manager ID : " + managerId + " assigned task ID : " + id + " to employee : " + userId);

        return ResponseEntity.ok("Task assigned to user with ID: " + userId);

    }

    //get average of days for user to submit the tasks
    @GetMapping("user-average/{userId}")
    public ResponseEntity getAverageNumberOfDaysForUser(
                                     @PathVariable Integer userId) {
        logger.info("request average of submissions for employee : " + userId);
        Double avg = taskService.getAverageOfNumberOfDays(userId);
        logger.info("Retrieve the average of submissions for employee : " + userId);
        return ResponseEntity.ok(avg);

    }

    //get average of days for all user to submit the tasks
    @GetMapping("all-user-average")
    public ResponseEntity getAverageNumberOfDaysForAllUsers(
            ) {
        logger.info("Request to average days of submissions for all employee");
        Double avg = taskService.getAverageOfNumberOfDaysForAllEmployee();
        logger.info("Retrieve the average days of submissions for all employee");

        return ResponseEntity.ok(avg);

    }

    //get number of tasks assigned to the user
    @GetMapping("user-num-task/{userId}")
    public ResponseEntity getNumOfTaskForUser(
            @PathVariable Integer userId) {
        logger.info("Request to get number of tasks assigned to user ID : " + userId);
        Integer num = taskService.getNumberOfTasksForEmployee(userId);
        logger.info("get number of tasks assigned to user ID : " + userId);
        return ResponseEntity.ok(num);

    }


    //get task by user
    @GetMapping("get-task-by-user/{userId}")
    public ResponseEntity getTaskByUser(
            @PathVariable Integer userId) {
        logger.info("Request the task by user ID : " + userId);
        Task task = taskService.getTaskById(userId);
        logger.info("get the task by user ID : " + userId);
        return ResponseEntity.ok(task);

    }

    //extend date of deadline by manager
    @PutMapping("extend/{id}/{managerId}/{date}")
    public ResponseEntity extendDeadline(@PathVariable Integer id,
                                     @PathVariable Integer managerId,
                                     @PathVariable LocalDateTime date) {
        logger.info("Request to extend the deadline for task ID : " + id + " by manager ID: " + managerId);
        taskService.extendDeadline(id, managerId, date);
        logger.info("deadline extended for task ID : " + id + " by manager ID: " + managerId);

        return ResponseEntity.ok("Task extends to " + date);

    }

    //get tasks by status
    @GetMapping("get-task-by-status/{status}")
    public ResponseEntity getTasksByStatus(
            @PathVariable String status) {
        logger.info("Request to retrieve all tasks by status : " + status);
        List<Task> task = taskService.getTasksByStatus(status);
        logger.info(" retrieve all tasks by status : " + status);

        return ResponseEntity.ok(task);

    }

    @GetMapping("get-employee-tasks/{userId}")
    public ResponseEntity getTasksForEmployee(
            @PathVariable Integer userId) {
        logger.info("Request to get all task by User ID : " + userId);
        List<Task> task = taskService.getAllTasksForEmployee(userId);
        logger.info("Retrieve all task by User ID : " + userId);

        return ResponseEntity.ok(task);

    }
//get number of employee late submission
    @GetMapping("count-late/{userId}")
    public ResponseEntity getNumberOfEmployeeSubmission(
            @PathVariable Integer userId) {
        logger.info("Request to get number of late submission by employee ID: " + userId);
        Integer num = taskService.getNumberOfEmployeeLateTasks(userId);
        logger.info(" get number of late submission by employee ID: " + userId);

        return ResponseEntity.ok(num);

    }
//get all late tasks
    @GetMapping("count-late-all")
    public ResponseEntity getNumberOfAllEmployeeSubmission() {
        logger.info("Request to get number of late submission for all employee");
        Integer num = taskService.getNumberOfLateTasks();
        logger.info("get number of late submission for all employee");

        return ResponseEntity.ok(num);

    }

    @GetMapping("best-employee")
    public ResponseEntity getBestEmployee() {
        logger.info("Request to get best employee");
        Employee employee = taskService.bestEmployee();
        logger.info("get best employee : " + employee);

        return ResponseEntity.ok(employee);

    }

    @GetMapping("worst-employee")
    public ResponseEntity getWorstEmployee() {
        logger.info("Request to get worst employee");
        Employee employee = taskService.worstEmployee();
        logger.info("get worst employee : " + employee);

        return ResponseEntity.ok(employee);

    }




}
