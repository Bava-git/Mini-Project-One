package com.miniprojectone.service;

import com.miniprojectone.model.Employee;
import com.miniprojectone.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> listallemployee() {
        return employeeRepository.findAll();
    }

    public Employee findbyemployeeId(String employeeId) {
        return employeeRepository.findByemployeeId(employeeId);
    }

    public Employee findbyemployeeEmail(String employeeEmail) {
        return employeeRepository.findByemployeeEmail(employeeEmail);
    }

    public Employee newemployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public String updateemployee(String employeeId, Employee updateemployee) {
        Employee ifexists = employeeRepository.findByemployeeId(employeeId);

        if (ifexists != null) {
            ifexists.setEmployeeName(updateemployee.getEmployeeName());
            ifexists.setEmployeeEmail(updateemployee.getEmployeeEmail());
            ifexists.setEmployeeSalary(updateemployee.getEmployeeSalary());
            employeeRepository.save(ifexists);
            return "Done";
        }
        return null;
    }

    @Transactional
    public int deleteemployee(String employeeId) {
        return employeeRepository.deleteByemployeeId(employeeId);
    }

}
