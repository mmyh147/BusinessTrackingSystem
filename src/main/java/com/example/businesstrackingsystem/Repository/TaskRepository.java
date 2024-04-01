package com.example.businesstrackingsystem.Repository;

import com.example.businesstrackingsystem.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {


    @Query("SELECT t FROM Task t WHERE t.id = ?1")
    Task findTaskById(Integer id);

    List<Task> findAllByStatus(String status);

    List<Task> findAllByUserId(Integer id);
}
