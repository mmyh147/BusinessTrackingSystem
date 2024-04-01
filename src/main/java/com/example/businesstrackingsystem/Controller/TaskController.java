package com.example.businesstrackingsystem.Controller;


import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Model.Task;
import com.example.businesstrackingsystem.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/get")
    public ResponseEntity getAllTask() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @PostMapping("/add")
    public ResponseEntity addNewTask(@RequestBody @Valid Task task, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Task getTask = taskService.addTask(task);
        return ResponseEntity.ok("Task added successfully\n " + getTask);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@PathVariable Integer id, @RequestBody @Valid Task task, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok("Task updated successfully\n" + updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully with ID : " + id));
    }

    @PutMapping("status/{id}/{userid}/{status}")
    public ResponseEntity<String> changeTaskStatus(@PathVariable Integer id,
                                                   @PathVariable Integer userid,
                                                   @PathVariable String status) {
            taskService.changeStatus(id, userid, status);
            return ResponseEntity.ok("Task status changed successfully");

    }

    @PutMapping("review/{id}/{userid}/{status}/{comment}")
    public ResponseEntity<String> managerReview(@PathVariable Integer id,
                                                   @PathVariable Integer userid,
                                                   @PathVariable String status,
                                                @PathVariable String comment ) {
        taskService.managerReview(id, userid, status,comment);
        return ResponseEntity.ok("Task status changed successfully");

    }


//counts number of days takes to submit the task
    @GetMapping("count-days/{id}")
    public ResponseEntity timeToSubmit(@PathVariable Integer id) {
        Long days = taskService.calculateDaysToSubmit(id);
        return ResponseEntity.ok(days);

    }

//assign task to user
    @PutMapping("assign/{id}/{managerId}/{userId}")
    public ResponseEntity assignTask(@PathVariable Integer id,
                                                @PathVariable Integer managerId,
                                                @PathVariable Integer userId) {
        taskService.assignTask(id, managerId, userId);
        return ResponseEntity.ok("Task assigned to user with ID: " + userId);

    }

    //get average of days for user to submit the tasks
    @GetMapping("user-average/{userId}")
    public ResponseEntity getAverageNumberOfDaysForUser(
                                     @PathVariable Integer userId) {
        Double avg = taskService.getAverageOfNumberOfDays(userId);
        return ResponseEntity.ok(avg);

    }

    //get average of days for all user to submit the tasks
    @GetMapping("all-user-average")
    public ResponseEntity getAverageNumberOfDaysForAllUsers(
            ) {
        Double avg = taskService.getAverageOfNumberOfDaysForAllEmployee();
        return ResponseEntity.ok(avg);

    }

    //get number of tasks assigned to the user
    @GetMapping("user-num-task/{userId}")
    public ResponseEntity getNumOfTaskForUser(
            @PathVariable Integer userId) {
        Integer num = taskService.getNumberOfTasksForEmployee(userId);
        return ResponseEntity.ok(num);

    }


    //get task by user
    @GetMapping("get-task-by-user/{userId}")
    public ResponseEntity getTaskByUser(
            @PathVariable Integer userId) {
        Task task = taskService.getTaskById(userId);
        return ResponseEntity.ok(task);

    }

    //extend date of deadline by manager
    @PutMapping("extend/{id}/{managerId}/{date}")
    public ResponseEntity extendDeadline(@PathVariable Integer id,
                                     @PathVariable Integer managerId,
                                     @PathVariable LocalDateTime date) {
        taskService.extendDeadline(id, managerId, date);
        return ResponseEntity.ok("Task extends to " + date);

    }

    //get tasks by status
    @GetMapping("get-task-by-status/{status}")
    public ResponseEntity getTasksByStatus(
            @PathVariable String status) {
        List<Task> task = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(task);

    }

    @GetMapping("get-employee-tasks/{userId}")
    public ResponseEntity getTasksForEmployee(
            @PathVariable Integer userId) {
        List<Task> task = taskService.getAllTasksForEmployee(userId);
        return ResponseEntity.ok(task);

    }
//get number of employee late submission
    @GetMapping("count-late/{userId}")
    public ResponseEntity getNumberOfEmployeeSubmission(
            @PathVariable Integer userId) {
        Integer num = taskService.getNumberOfEmployeeLateTasks(userId);
        return ResponseEntity.ok(num);

    }
//get all late tasks
    @GetMapping("count-late-all")
    public ResponseEntity getNumberOfAllEmployeeSubmission() {
        Integer num = taskService.getNumberOfLateTasks();
        return ResponseEntity.ok(num);

    }

    @GetMapping("best-employee")
    public ResponseEntity getBestEmployee() {
        Employee employee = taskService.bestEmployee();
        return ResponseEntity.ok(employee);

    }

    @GetMapping("worst-employee")
    public ResponseEntity getWorstEmployee() {
        Employee employee = taskService.worstEmployee();
        return ResponseEntity.ok(employee);

    }




}
