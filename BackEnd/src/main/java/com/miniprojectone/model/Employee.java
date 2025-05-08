package com.miniprojectone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "employee_id")
    @JsonProperty("employee_id")
    private String employeeId;

    @Column(name = "employee_name")
    @JsonProperty("employee_name")
    private String employeeName;

    @Column(name = "employee_email")
    @JsonProperty("employee_email")
    private String employeeEmail;

    @Column(name = "employee_salary")
    @JsonProperty("employee_salary")
    private double employeeSalary;

}
