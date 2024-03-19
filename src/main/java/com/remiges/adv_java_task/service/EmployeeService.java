package com.remiges.adv_java_task.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.remiges.adv_java_task.dto.EmployeeDetailsDto;
import com.remiges.adv_java_task.dto.EmployeeDto;
import com.remiges.adv_java_task.dto.UpdateEmployeeRequest;
import com.remiges.adv_java_task.entity.Employee;

@Service
public interface EmployeeService {

    public void addEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployees();

    EmployeeDetailsDto getEmployeeDetails(Long employeeId);

    Employee updateEmployee(UpdateEmployeeRequest  request);

    ResponseEntity<Object> deleteEmployee(Long id);

    public int getEmployeeContribution(String department, String employeeId);




}