package com.example.businesstrackingsystem.Repository;

import com.example.businesstrackingsystem.Model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {


}
