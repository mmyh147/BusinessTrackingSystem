package com.example.businesstrackingsystem.Controller;

import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(CommentController.class);



    @GetMapping("/get")
    public ResponseEntity getAllEmployee() {

        logger.info("request to get all employee");
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping("/add")
    public ResponseEntity addNewEmployee(@RequestBody @Valid Employee employee) {

        logger.info("request to add Employee with body :" + employee);

        Employee getEmployee = employeeService.addEmployee(employee);
        logger.info("add employee : " + employee);
        return ResponseEntity.ok("Employee added successfully\n " + getEmployee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id, @RequestBody @Valid Employee employee) {

        logger.info("request to update employee with body : " + employee);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        logger.info("employee updated: " + employee);
        return ResponseEntity.ok("Employee updated successfully\n" + updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        logger.info("request to delete employee with ID : " + id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ApiResponse("Employee deleted successfully with ID : " + id));
    }


}
