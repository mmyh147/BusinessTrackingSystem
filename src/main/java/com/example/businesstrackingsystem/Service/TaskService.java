package com.example.businesstrackingsystem.Service;


import com.example.businesstrackingsystem.Api.ApiException;
import com.example.businesstrackingsystem.Model.Comment;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Model.Task;
import com.example.businesstrackingsystem.Repository.CommentRepository;
import com.example.businesstrackingsystem.Repository.EmployeeRepository;
import com.example.businesstrackingsystem.Repository.ManagerRepository;
import com.example.businesstrackingsystem.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final CommentService commentService;
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }


    public Task addTask(Task task) {
//        if (!employeeRepository.existsById(task.getUserId())){
//            throw new ApiException("user id with : " + task.getUserId() + " not found!");
//        }
        task.setCreateAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTask(Integer taskId, Task task) {
        if (!employeeRepository.existsById(task.getUserId())){
            throw new ApiException("user id with : " + task.getUserId() + " not found!");
        }
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

// get task by task id
    public Task getTaskById(Integer id){
        if (!taskRepository.existsById(id)){
            throw new ApiException("Task not found with ID : " + id);
        }
        return taskRepository.findTaskById(id);
    }

    //extend the deadline
    public void extendDeadline(Integer taskId,Integer managerId ,LocalDateTime date){

       if (!taskRepository.existsById(taskId)){
           throw new ApiException("Task not found");
       }

        if (!managerRepository.existsById(managerId)){
            throw new ApiException("manager not found");
        }
        if (date.isAfter(LocalDateTime.now())){
            Task task = taskRepository.findTaskById(taskId);
            task.setDeadline(date);
            taskRepository.save(task);
        }

    }
    public void changeStatus(Integer id,Integer userId, String status){
        if (!taskRepository.existsById(id)){
            throw new ApiException("Task not found!");
        }


        Task task = taskRepository.findTaskById(id);

        if (!task.getUserId().equals(userId)){
            throw new ApiException("user not found");
        }

        if (status.equals("to-do)")){
            task.setStatus("to-do");
            taskRepository.save(task);
    }
        else if(status.equals("in-progress")){
            task.setStatus("in-progress");
            taskRepository.save(task);

        }
        else if (status.equals("submit")) {
            task.setSubmitDate(LocalDateTime.now());
            task.setStatus("in-review");
            taskRepository.save(task);


        }else {
            throw new ApiException("Status must be 'in-progress', 'submit', or 'to-do'");
        }

    }

    // approve or reject the task by the manager
    public void managerReview(Integer managerId, Integer taskId, String status, String comment){

        if(!managerRepository.existsById(managerId)){
            throw new ApiException("Manager not found");
        }

        if (!taskRepository.existsById(taskId)){
            throw new ApiException("task not found");
        }
        Comment c = new Comment();
        c.setContent(comment);
        c.setTaskId(taskId);
        c.setUserId(managerId);

        Task task = taskRepository.findTaskById(taskId);
        if (status.equals("approve")){
            task.setStatus("complete");
            task.setReview("approve");
            commentService.addComment(c);
            taskRepository.save(task);


        } else if (status.equals("reject")) {
            task.setStatus("in-progress");
            task.setReview("reject");
            commentService.addComment(c);
            taskRepository.save(task);

        }


    }

    //get tasks by status
    public List<Task> getTasksByStatus(String status)
    {
        if (status.equals("complete") || status.equals("in-progress") || status.equals("to-do")){

            List<Task> tasks = taskRepository.findAllByStatus(status);
            return tasks;
        }
        throw new ApiException("wrong status");
    }

    public List<Task> getAllTasksForEmployee(Integer userid){
        if (!employeeRepository.existsById(userid)){
            throw new ApiException("user not found");
        }

        return taskRepository.findAllByUserId(userid);
    }

//check the service please
public long calculateDaysToSubmit(Integer taskId) {
    Task task = taskRepository.findTaskById(taskId);
    if (task == null) {
        throw new ApiException("Task not found with ID: " + taskId);
    }
    LocalDateTime submissionDate = task.getSubmitDate();
    if (submissionDate == null) {
        throw new ApiException("Task has not been submitted yet");
    }
    LocalDateTime start = task.getCreateAt();
    if (start == null) {
        throw new ApiException("Task start date has not been set");
    }
    Duration duration = Duration.between(start, submissionDate);

    return Math.abs(duration.toDays());
}

//average number of days the employee takes to finish the tasks
    public Double getAverageOfNumberOfDays(Integer userid){
        if (!employeeRepository.existsById(userid)){
            throw new ApiException("user not found");
        }
        Double sum = 0.0;
        List<Task> tasks = taskRepository.findAllByUserId(userid);
        for (Task task : tasks){
           sum += calculateDaysToSubmit(task.getId());
        }
        return sum / tasks.size();
    }

    //average number of days all employee takes to finish the tasks
    public Double getAverageOfNumberOfDaysForAllEmployee(){

        Double sum = 0.0;
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks){
            sum += calculateDaysToSubmit(task.getId());
        }
        return sum / tasks.size();
    }

    public Integer getNumberOfLateTasks(){
        Integer num = 0;
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks){
            if (task.getSubmitDate().isAfter(task.getDeadline())) {
                num++;
            }
        }
        return num;

    }

    public Integer getNumberOfEmployeeLateTasks(Integer userId){
        Integer num = 0;
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        for (Task task : tasks){
            if (task.getSubmitDate().isAfter(task.getDeadline())) {
                num++;
            }
        }
        return num;

    }

    //return number of tasks for user by ID

    public Integer getNumberOfTasksForEmployee(Integer userid){
        if (!employeeRepository.existsById(userid)){
            throw new ApiException("user not found");
        }

        return taskRepository.findAllByUserId(userid).size();
    }

    //assign task to user by manager
    public void assignTask(Integer taskId,Integer managerId, Integer userId){
        if (!taskRepository.existsById(taskId)){
            throw new ApiException("No task found with ID: " + taskId);
        }
        if (!employeeRepository.existsById(userId)){
            throw new ApiException("NO employee found with ID: " + userId);
        }

        if (!managerRepository.existsById(managerId)){
            throw new ApiException("NO employee found with ID: " + userId);
        }
        Task task = taskRepository.findTaskById(taskId);

        task.setUserId(userId);
        taskRepository.save(task);

    }

    //best employee

    public Employee bestEmployee(){
        Employee bestEmployee = null;
        double min = 1000.0;
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee: employees){
            if (getAverageOfNumberOfDays(employee.getId()) < min){
                min = getAverageOfNumberOfDays(employee.getId());
                bestEmployee = employee;
            }
        }
        return bestEmployee;
    }

    public Employee worstEmployee(){
        Employee worstEmployee = null;
        double max = 0;
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee: employees){
            if (getAverageOfNumberOfDays(employee.getId()) > max){
                max = getAverageOfNumberOfDays(employee.getId());
                worstEmployee = employee;
            }
        }
        return worstEmployee;
    }





}
