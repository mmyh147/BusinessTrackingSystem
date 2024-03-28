package com.example.businesstrackingsystem.Service;


import com.example.businesstrackingsystem.Api.ApiException;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Model.Task;
import com.example.businesstrackingsystem.Repository.ManagerRepository;
import com.example.businesstrackingsystem.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }


    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer taskId, Task task) {
        if (taskRepository.existsById(taskId)) {
            task.setId(taskId);
            return taskRepository.save(task);
        }
        throw new ApiException("Task not found");
    }

    public void deleteTask(Integer taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new ApiException("Task not found");
        }


    }
}
