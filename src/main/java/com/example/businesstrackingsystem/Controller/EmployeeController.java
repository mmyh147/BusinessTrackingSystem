package com.example.businesstrackingsystem.Controller;

import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity getAllEmployee() {
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping("/add")
    public ResponseEntity addNewEmployee(@RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Employee getEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.ok("Employee added successfully\n " + getEmployee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id, @RequestBody @Valid Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok("Employee updated successfully\n" + updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ApiResponse("Employee deleted successfully with ID : " + id));
    }


}
