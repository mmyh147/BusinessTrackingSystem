package com.example.businesstrackingsystem.Service;


import com.example.businesstrackingsystem.Api.ApiException;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;


    //Get all employee
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    //Add new user
    public Employee addEmployee(Employee employee) {
        employee.setRegistrationDate(LocalDateTime.now());
        return employeeRepository.save(employee);
    }

    //update User
    public Employee updateEmployee(Integer employeeId, Employee employee) {
        if (employeeRepository.existsById(employeeId)) {
            employee.setId(employeeId);
            return employeeRepository.save(employee);
        }
        throw new ApiException("Employee not found");
    }

    //Delete user
    public void deleteEmployee(Integer employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        }else{
            throw new ApiException("Employee not found");
        }

    }

}
