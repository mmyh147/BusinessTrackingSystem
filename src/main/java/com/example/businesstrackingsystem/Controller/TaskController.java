package com.example.businesstrackingsystem.Controller;


import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Comment;
import com.example.businesstrackingsystem.Model.Task;
import com.example.businesstrackingsystem.Service.CommentService;
import com.example.businesstrackingsystem.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity deleteComment(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully with ID : " + id));
    }
}
