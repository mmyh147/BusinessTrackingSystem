package com.example.businesstrackingsystem.Controller;

import com.example.businesstrackingsystem.Api.ApiResponse;
import com.example.businesstrackingsystem.Model.Employee;
import com.example.businesstrackingsystem.Model.Manager;
import com.example.businesstrackingsystem.Service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {


    private final ManagerService managerService;
    Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @GetMapping("/get")
    public ResponseEntity getAllManager() {
        logger.info("request to get all employee");
        return ResponseEntity.ok(managerService.getAllManager());

    }

    @PostMapping("/add")
    public ResponseEntity addNewManager(@RequestBody @Valid Manager manager) {

        logger.info("request to add new Manager with body : " + manager);
        Manager getManager = managerService.addManager(manager);
        logger.info("add new manager with body: " + manager);

        return ResponseEntity.ok("Manager added successfully\n " + getManager);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateManager(@PathVariable Integer id, @RequestBody @Valid Manager manager) {

        logger.info("request to update Manager ID : " + id + " with body : " + manager);
        Manager updatedManager = managerService.updateManager(id, manager);
        logger.info(" update Manager ID : " + id + " with body : " + manager);
        return ResponseEntity.ok("Manager updated successfully\n" + updatedManager);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteManager(@PathVariable Integer id) {
        logger.info("Request to delete manager with ID: " + id);
        managerService.deleteManager(id);
        logger.info("manager with ID: " + id + " has been deleted");

        return ResponseEntity.ok(new ApiResponse("Manager deleted successfully with ID : " + id));
    }

}
