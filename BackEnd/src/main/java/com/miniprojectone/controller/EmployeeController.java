package com.miniprojectone.controller;

import com.miniprojectone.exception.ResourceNotFoundException;
import com.miniprojectone.model.Employee;
import com.miniprojectone.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("http://127.0.0.1:5500")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> listallemployee() {
        List<Employee> emp = employeeService.listallemployee();
        if (emp != null) {
            return new ResponseEntity<>(emp, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); // 204 NO_CONTENT
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> findbyemployeeId(@PathVariable String employeeId) {
        Employee emp = employeeService.findbyemployeeId(employeeId);
        if (emp != null) {
            return ResponseEntity.ok(emp); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found, " + employeeId); // 404 NOT_FOUND
        }
    }

    @GetMapping("/search/{employeeEmail}")
    public ResponseEntity<?> findbyemployeeEmail(@PathVariable String employeeEmail) {
        Employee emp = employeeService.findbyemployeeEmail(employeeEmail);
        if (emp != null) {
            return ResponseEntity.ok(emp); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found, " + employeeEmail); // 404 NOT_FOUND
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> newemployee(@RequestBody Employee employee) {
        Employee emp = employeeService.newemployee(employee);
        if (emp != null) {
            return new ResponseEntity<>(emp, HttpStatus.CREATED); // 201 CREATED
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // 400 BAD_REQUEST
        }
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<String> updateemployee(@PathVariable String employeeId, @RequestBody Employee updateemployee) {
        String emp = employeeService.updateemployee(employeeId, updateemployee);
        if (emp != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully."); // 200 OK
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found, " + employeeId); // 404 NOT_FOUND
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteemployee(@PathVariable String employeeId) {
        int isDeleted = employeeService.deleteemployee(employeeId);
        if (isDeleted > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found, " + employeeId); // 404 NOT_FOUND
        }
    }

}
