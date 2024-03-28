package com.example.businesstrackingsystem.Repository;

import com.example.businesstrackingsystem.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
