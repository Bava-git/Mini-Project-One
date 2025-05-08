package com.miniprojectone.serviceTest;

import com.miniprojectone.model.Employee;
import com.miniprojectone.repository.EmployeeRepository;
import com.miniprojectone.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class EmployeeTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setup() {
        employee1 = new Employee("EM1", "Bava", "bava@gmail.com", 50000);
        employee2 = new Employee("EM2", "Khan", "khan@gmail.com", 50000);
    }

    @Test
    void getAllEmployee() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        List<Employee> employeeList = employeeService.listallemployee();

        // validate
        assertEquals(2, employeeList.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void saveEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        Employee employee = employeeService.newemployee(employee1);

        // validate
        assertNotNull(employee);
        assertEquals("Bava", employee.getEmployeeName());
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void getEmployeeById() {
        when(employeeRepository.findByemployeeId(employee2.getEmployeeId())).thenReturn(employee2);
        Employee employee = employeeService.findbyemployeeId(employee2.getEmployeeId());

        assertEquals("Khan", employee.getEmployeeName());
        verify(employeeRepository, times(1)).findByemployeeId(employee2.getEmployeeId());
    }

    @Test
    void updateEmployee() {
        Employee updateEmp = new Employee("EM1", "Bava", "bava@gmail.com", 70000);

        when(employeeRepository.findByemployeeId("EM1")).thenReturn(employee1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(updateEmp);

        employeeService.updateemployee("EM1", updateEmp);
        verify(employeeRepository, times(1)).findByemployeeId("EM1");
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void deleteEmployee() {
        employeeService.deleteemployee("EM2");
        verify(employeeRepository, times(1)).deleteByemployeeId("EM2");
    }
}
