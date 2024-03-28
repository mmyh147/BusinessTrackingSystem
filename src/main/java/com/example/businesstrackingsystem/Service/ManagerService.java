package com.example.businesstrackingsystem.Service;

import com.example.businesstrackingsystem.Api.ApiException;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;


    //Get all manager
    public List<Manager> getAllManager() {
        return managerRepository.findAll();
    }

    //Add new manager
    public Manager addManager(Manager manager) {
        manager.setRegistrationDate(LocalDateTime.now());
        return managerRepository.save(manager);
    }

    //update manager
    public Manager updateManager(Integer managerId, Manager manager) {
        if (managerRepository.existsById(managerId)) {
            manager.setId(managerId);
            return managerRepository.save(manager);
        }
        throw new ApiException("Manager not found");
    }

    //Delete Manager
    public void deleteManager(Integer managerId) {
        if (managerRepository.existsById(managerId)) {
            managerRepository.deleteById(managerId);
        }else{
            throw new ApiException("Manager not found");
        }

    }


}
