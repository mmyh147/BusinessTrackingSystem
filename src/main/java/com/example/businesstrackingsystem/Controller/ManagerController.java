package com.example.businesstrackingsystem.Controller;

import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {


    private final ManagerService managerService;

    @GetMapping("/get")
    public ResponseEntity getAllManager() {
        return ResponseEntity.ok(managerService.getAllManager());
    }

    @PostMapping("/add")
    public ResponseEntity addNewManager(@RequestBody @Valid Manager manager, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Manager getManager = managerService.addManager(manager);
        return ResponseEntity.ok("Manager added successfully\n " + getManager);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateManager(@PathVariable Integer id, @RequestBody @Valid Manager manager, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }

        Manager updatedManager = managerService.updateManager(id, manager);
        return ResponseEntity.ok("Manager updated successfully\n" + updatedManager);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteManager(@PathVariable Integer id) {
        managerService.deleteManager(id);
        return ResponseEntity.ok(new ApiResponse("Manager deleted successfully with ID : " + id));
    }

}
